package edu.baylor.ecs.cloudhubs.semantics.util.factory;

import edu.baylor.ecs.cloudhubs.semantics.SemanticAnalysisCommand;
import edu.baylor.ecs.cloudhubs.semantics.entity.MsCodeClone;
import edu.baylor.ecs.cloudhubs.semantics.entity.MsFlowEntity;
import edu.baylor.ecs.cloudhubs.semantics.entity.graph.MsClass;
import edu.baylor.ecs.cloudhubs.semantics.entity.graph.MsMethod;
import edu.baylor.ecs.cloudhubs.semantics.entity.quantification.ModuleClone;
import edu.baylor.ecs.cloudhubs.semantics.util.MsCache;
import org.checkerframework.checker.units.qual.A;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModuleCloneFactory {
    /**
     * 1. take all code clones type a and b
     * 2. for each module
     * 3. for each flow in module
     * 4. look if flow is in A or B of code clone
     * 5. if it is, increment counter
     */

    public void createData(){
        List<ModuleClone> moduleClones = getModuleClones();
        List<String[]> dataLines = convertModuleClones(moduleClones);
        File csvOutputFile = new File(SemanticAnalysisCommand.cachePath + "/per-module-clones.csv");
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            dataLines.stream()
                    .map(this::convertToCSV)
                    .forEach(pw::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<String[]> convertModuleClones(List<ModuleClone> moduleClones) {
        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"id", "cfg", "percent"});
        for (ModuleClone moduleClone: moduleClones
             ) {
            data.add(getStringArray(moduleClone));
        }
        return data;
    }

    public String[] getStringArray(ModuleClone clone) {
        String[] data = new String[3];
        data[0] = Integer.toString(clone.getModuleId());
        data[1] = Integer.toString(clone.getCfgNr());
        data[2] = Double.toString(clone.getPercentageClones());
        return data;
    }

    public String convertToCSV(String[] data) {
        return Stream.of(data)
                .map(this::escapeSpecialCharacters)
                .collect(Collectors.joining(","));
    }

    public String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

    public List<ModuleClone> getModuleClones(){
        List<ModuleClone> moduleClones = new ArrayList<>();
        List<MsCodeClone> codeClones = getCodeClonesTypeAB();
        List<String> codeCloneIds = new ArrayList<>();
        for (MsCodeClone mcc: codeClones
             ) {
            String controllerA = mcc.getA().getMsController().getMsId().getPath();
            String methodA = mcc.getA().getMsControllerMethod().getMethodName();
            String controllerB = mcc.getB().getMsController().getMsId().getPath();
            String methodB = mcc.getB().getMsControllerMethod().getMethodName();
            codeCloneIds.add(controllerA + "." + methodA);
            codeCloneIds.add(controllerB + "." + methodB);
        }
        for (int i = 0; i < MsCache.modules.size(); i++) {
            String module = MsCache.modules.get(i);
            ModuleClone moduleClone = new ModuleClone();
            moduleClone.setModuleId(i+1);
            moduleClone.setModuleName(module);
            int counter = 0;
            List<MsFlowEntity> flowEntities = getModuleFlowEntity(module);
            for (MsFlowEntity mf: flowEntities
                 ) {
                String msFlowId = mf.getMsController().getMsId().getPath() + "." + mf.getMsControllerMethod().getMethodName();
                if (codeCloneIds.contains(msFlowId)) {
                    counter += 1;
                }
            }
            moduleClone.setClonedCfg(counter);
            moduleClone.setCfgNr(flowEntities.size());
            moduleClone.setPercentageClones(((double) counter / (double) flowEntities.size() * 100));
            moduleClones.add(moduleClone);
        }
        return moduleClones;
    }

    private List<MsFlowEntity> getModuleFlowEntity(String module){
        return MsCache.msFlows.stream()
                .filter(n -> n.getMsController().getMsId().getPath().contains(module))
                .collect(Collectors.toList());
    }

    private List<MsCodeClone> getCodeClonesTypeAB() {
        return MsCache.msCodeClones.stream()
                .filter(n -> n.isTypeA() || n.isTypeB())
                .collect(Collectors.toList());
    }

}
