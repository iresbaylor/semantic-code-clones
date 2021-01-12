package edu.baylor.ecs.cloudhubs.semantics.util;

import edu.baylor.ecs.cloudhubs.semantics.entity.*;
import edu.baylor.ecs.cloudhubs.semantics.entity.graph.*;
import edu.baylor.ecs.cloudhubs.semantics.entity.inconsistencies.MsInconsistencies;

import java.util.ArrayList;
import java.util.List;

public class MsCache {
    public static List<MsClass> msClassList;
    public static List<MsMethod> msMethodList;
    public static List<MsMethodCall> msMethodCallList;
    public static List<MsRestCall> msRestCallList;
    public static List<MsField> msFieldList;
    public static MsInconsistencies msInconsistencies;
    public static List<String> modules;
    public static List<MsFlowEntity> msFlows;
    public static List<MsCodeClone> msCodeClones;
    public static MsCodeCloneCache msCodeCloneCache;
    public static List<MsCodeClone> sameRepositoryCC;
    public static List<MsCodeClone> sameRestCallsCC;
    public static List<MsCodeClone> sameControllerCC;
    // code clone types
    public static List<MsCodeClone> typeA;
    public static List<MsCodeClone> typeB;
    public static List<MsCodeClone> typeC;

    public static void init(){
        modules = new ArrayList<>();
        msClassList = new ArrayList<>();
        msMethodList = new ArrayList<>();
        msMethodCallList = new ArrayList<>();
        msInconsistencies = new MsInconsistencies();
        msRestCallList = new ArrayList<>();
        msFieldList = new ArrayList<>();
        msInconsistencies = new MsInconsistencies();
        msCodeClones = new ArrayList<>();
        msCodeCloneCache = new MsCodeCloneCache();
        sameRepositoryCC = new ArrayList<>();
        sameRestCallsCC = new ArrayList<>();
        sameControllerCC = new ArrayList<>();
        typeA = new ArrayList<>();
        typeB = new ArrayList<>();
        typeC = new ArrayList<>();
    }

    public static void addMsClass(MsClass msClass) {
        msClassList.add(msClass);
    }

    public static void addMsMethod(MsMethod msMethod) {
        msMethodList.add(msMethod);
    }

    public static void addMsMethodCall(MsMethodCall msMethodCall) {
        msMethodCallList.add(msMethodCall);
    }

    public static void addMsField(MsField msField) {
        msFieldList.add(msField);
    }

    public static void addMsRestMethodCall(MsRestCall msRestCall){
        msRestCallList.add(msRestCall);
    }

    public static void addMsFlow(MsFlowEntity msFlow) {
        msFlows.add(msFlow);
    }

    public static void print(){
//        System.out.printf("");
        System.out.println();
    }

    public static void addCodeClone(MsCodeClone msCodeClone) {
        msCodeClones.add(msCodeClone);
    }

    public static void addHighSimilar(MsCodeClone msCodeClone) {
        msCodeCloneCache.addHighSimilar(msCodeClone);
    }

    public static void addSameRepository(MsCodeClone msCodeClone) {
        sameRepositoryCC.add(msCodeClone);
    }

    public static void addSameController(MsCodeClone msCodeClone) {
        sameControllerCC.add(msCodeClone);
    }

    public static void addSameRestCall(MsCodeClone msCodeClone) {
        sameRestCallsCC.add(msCodeClone);
    }
}
