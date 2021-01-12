package edu.baylor.ecs.cloudhubs.semantics.entity;

import edu.baylor.ecs.cloudhubs.semantics.entity.graph.MsClass;
import edu.baylor.ecs.cloudhubs.semantics.entity.graph.MsField;
import edu.baylor.ecs.cloudhubs.semantics.entity.graph.MsMethod;
import edu.baylor.ecs.cloudhubs.semantics.entity.graph.MsRestCall;
import lombok.Data;

import java.util.List;

@Data
public class MsFlow {
    private String module;
    // controller
    private MsClass controller;
    private MsMethod controllerMethod;
    private List<MsField> controllerServiceFields;
    // service
    private List<MsClass> services;
    private List<MsMethod> serviceMethods;
    private List<MsRestCall> restCall;
    // repository
    private List<MsClass> repositories;
    private List<MsMethod> repositoryMethods;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(controller.getClassName());
        sb.append(" -> ");
        sb.append(controllerMethod.getMethodName());
        return sb.toString();
    }
}
