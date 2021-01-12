package edu.baylor.ecs.cloudhubs.semantics.entity.quantification;

import edu.baylor.ecs.cloudhubs.semantics.entity.graph.MsClass;
import lombok.Data;

import java.util.List;

@Data
public class ModuleClone {
    private String moduleName;
    private int moduleId;
    private int cfgNr;
    private int clonedCfg;
    private double percentageClones;
    // all controllers
    private List<MsClass> controllers;
    // cloned controllers
    private List<MsClass> cloneControllers;
}
