package edu.baylor.ecs.cloudhubs.semantics.entity.quantification;

import edu.baylor.ecs.cloudhubs.semantics.entity.MsCodeClone;
import edu.baylor.ecs.cloudhubs.semantics.entity.MsFlow;
import edu.baylor.ecs.cloudhubs.semantics.entity.MsFlowEntity;
import lombok.Data;

import java.util.List;

@Data
public class ModuleClonePair {
    private int moduleAId;
    private String moduleA;
    private String moduleB;
    private int moduleBId;
    private List<MsFlowEntity> moduleAFlows;
    private List<MsFlowEntity> moduleBFlows;
    private List<MsCodeClone> codeClones;
    private double percentClonesModuleA;
    private double percentClonesModuleB;

    @Override
    public String toString() {
        return moduleA + " -> " + moduleB + " : " + percentClonesModuleA;
    }
}
