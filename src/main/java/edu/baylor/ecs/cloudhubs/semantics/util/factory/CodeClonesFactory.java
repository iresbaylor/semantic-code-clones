package edu.baylor.ecs.cloudhubs.semantics.util.factory;

import edu.baylor.ecs.cloudhubs.semantics.entity.*;
import edu.baylor.ecs.cloudhubs.semantics.entity.graph.*;
import edu.baylor.ecs.cloudhubs.semantics.util.MsCache;
import java.util.List;
import java.util.stream.Collectors;

public class CodeClonesFactory {

    public void findCodeClones() {

        for (int i = 0; i < MsCache.modules.size() -1; i++) {
            for (int j = i + 1; j < MsCache.modules.size(); j++) {
                String iModule = MsCache.modules.get(i);
                String jModule = MsCache.modules.get(j);
                // get flows from i
                List<MsFlowEntity> iFlows = getFlowEntities(iModule);
                // get flows from j
                List<MsFlowEntity> jFlows = getFlowEntities(jModule);
                // compare each flow from i with each flow from j
                for (int k = 0; k < iFlows.size(); k++) {
                    for (int l = 0; l < jFlows.size(); l++) {
                        MsFlowEntity kFlow = iFlows.get(k);
                        MsFlowEntity lFlow = jFlows.get(l);
                        MsCodeClone msCodeClone = new MsCodeClone();
                        msCodeClone.setA(kFlow);
                        msCodeClone.setB(lFlow);
                        msCodeClone.setSimilarityController(compareController(kFlow, lFlow));
                        if (kFlow.getMsServiceMethod() != null && lFlow.getMsServiceMethod() != null) {
                            msCodeClone.setSimilarityService(compareService(kFlow.getMsServiceMethod(), lFlow.getMsServiceMethod()));
                        } else {
                            msCodeClone.setSimilarityService(0.0);
                        }
                        if (kFlow.getMsRepositoryMethod() != null && lFlow.getMsRepositoryMethod() != null) {
                            msCodeClone.setSimilarityRepository(compareRepository(kFlow.getMsRepositoryMethod(), lFlow.getMsRepositoryMethod()));
                        } else {
                            msCodeClone.setSimilarityRepository(0.0);
                        }
                        if (kFlow.getMsRestCalls() != null && lFlow.getMsRestCalls() != null) {
                            msCodeClone.setSimilarityRestCalls(compareRestCalls(kFlow.getMsRestCalls(), lFlow.getMsRestCalls()));
                        } else {
                            msCodeClone.setSimilarityRestCalls(0.0);
                        }
                        msCodeClone.setGlobalSimilarity(calculateGlobalSimilarity(msCodeClone));
                        classifyCodeClones(msCodeClone);
                    }
                }
            }
        }
    }

    private void classifyCodeClones(MsCodeClone msCodeClone) {

        if (msCodeClone.getGlobalSimilarity() > 0.0) {
            MsCache.addHighSimilar(msCodeClone);
        }
        if (msCodeClone.getSimilarityController() == 1.0) {
            MsCache.addSameController(msCodeClone);
        }
        if (msCodeClone.getSimilarityRepository() > 0.0) {
            MsCache.addSameRepository(msCodeClone);
        }
        if (msCodeClone.getSimilarityRestCalls() >= 3.0) {
            MsCache.addSameRestCall(msCodeClone);
        }
        if (msCodeClone.getGlobalSimilarity() < 0.8 && msCodeClone.getGlobalSimilarity() >= 0.6) {
            MsCache.typeC.add(msCodeClone);
        }
        if (msCodeClone.getGlobalSimilarity() < 0.9 && msCodeClone.getGlobalSimilarity() >= 0.8) {
            msCodeClone.setTypeB(true);
            MsCache.typeB.add(msCodeClone);
        }
        if (msCodeClone.getGlobalSimilarity() <= 1.0 && msCodeClone.getGlobalSimilarity() >= 0.9) {
            msCodeClone.setTypeA(true);
            MsCache.typeA.add(msCodeClone);
        }
        if (msCodeClone.isTypeA()) {
            System.out.println();
        }
        MsCache.addCodeClone(msCodeClone);
    }

    private double calculateGlobalSimilarity(MsCodeClone msCodeClone) {
        return (msCodeClone.getSimilarityController() * 0.8)
                + (msCodeClone.getSimilarityService() * 0.05 )
                + (msCodeClone.getSimilarityRepository() * 0.05 )
                + (msCodeClone.getSimilarityRestCalls() * 0.1);
    }

    private double compareRestCalls(List<MsRestCall> aMsRestCalls, List<MsRestCall> bMsRestCalls) {
        double denominator = Math.max(aMsRestCalls.size(), bMsRestCalls.size());
        double nominator = 0.0;
        for (MsRestCall a: aMsRestCalls
             ) {
            for (MsRestCall b: bMsRestCalls
                 ) {
                nominator += compareRestCall(a, b);
            }
        }
        if (denominator == 0.0) {
            return 0.0;
        }
        return nominator / denominator;
    }

    private double compareRestCall(MsRestCall a, MsRestCall b) {
        double similarity = 0.0;
        if (a.getApi() != null && b.getApi() != null && a.getApi().equals(b.getApi())) {
            similarity += 1.0;
        }
        if (a.getReturnType() != null && b.getReturnType() != null && a.getReturnType().equals(b.getReturnType())) {
            similarity += 1.0;
        }
        if (a.getHttpMethod() != null && b.getHttpMethod() != null && a.getHttpMethod().equals(b.getHttpMethod())) {
            similarity += 1.0;
        }
        return similarity / 3.0;
    }

    private double compareRepository(MsMethod aMethod, MsMethod bMethod) {
        return compareService(aMethod, bMethod);
    }

    private double compareService(MsMethod aMethod, MsMethod bMethod) {
        double same = 0.0;
        if (aMethod.getReturnType() != null && bMethod.getReturnType() != null && aMethod.getReturnType().equals(bMethod.getReturnType())) {
            same += 0.5;
        }
        List<MsArgument> aArguments = aMethod.getMsArgumentList();
        List<MsArgument> bArguments = bMethod.getMsArgumentList();
        if (aArguments.size() != 0 && bArguments.size() != 0) {
            double sameArguments = 0.0;
            for (int i = 0; i < aArguments.size() - 1; i++) {
                for (int j = i + 1; j < bArguments.size(); j++) {

                    if (aArguments.get(i).getReturnType() != null
                            && aArguments.get(i).getReturnType() != null
                            && aArguments.get(i).getReturnType().equals(bArguments.get(j).getReturnType())) {
                        sameArguments += 1.0;
                    }
                }
            }
            double denominator = Math.max(aArguments.size(), bArguments.size());
            same += (sameArguments / denominator);
        }
        return same;
    }

    public List<MsFlowEntity> getFlowEntities(String module) {
        return MsCache.msFlows
                .stream()
                .filter(n -> n.getMsController().getMsId().getPath().contains(module)).collect(Collectors.toList());
    }

    private double compareController(MsFlowEntity a, MsFlowEntity b) {
        double same = 0.0;
        if (a == null || b == null) {
            return 0.0;
        }
        MsMethod aCtrl = a.getMsControllerMethod();
        MsMethod bCtrl = b.getMsControllerMethod();
        if (aCtrl == null || bCtrl == null) {
            return 0.0;
        }

        if (aCtrl.getMethodName().equals(bCtrl.getMethodName())){
            same += 1.0;
        }

        if (aCtrl.getReturnType().equals(bCtrl.getReturnType())){
            same += 1.0;
        }

        for (MsAnnotation aA: aCtrl.getMsAnnotations()
             ) {
            for (MsAnnotation bA: bCtrl.getMsAnnotations()
                 ) {
                if (aA.getAnnotationName().equals(bA.getAnnotationName())) {
                    if (aCtrl.getReturnType().equals(bCtrl.getReturnType())) {
                        same += 1.0;
                    }
                }
            }
        }

//        for (MsArgument msA: aCtrl.getMsArgumentList()
//             ) {
//            for (MsArgument msB: bCtrl.getMsArgumentList()
//                 ) {
//                if (msA.getReturnType().equals(msB.getReturnType())){
//                    same += 1.0;
//                }
//            }
//        }
        return same / 4;

    }
}
