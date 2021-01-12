package edu.baylor.ecs.cloudhubs.semantics.entity;

import edu.baylor.ecs.cloudhubs.semantics.entity.graph.*;
import lombok.Data;

import java.util.List;

@Data
public class MsFlowEntity {
    private MsClass msController;
    private MsMethod msControllerMethod;
    private MsMethodCall msServiceMethodCall;
    private MsField msControllerServiceField;
    private MsClass msService;
    private MsMethod msServiceMethod;
    private MsMethodCall msRepositoryMethodCall;
    private MsField msServiceRepositoryField;
    private MsClass msRepository;
    private List<MsRestCall> msRestCalls;
    private MsMethod msRepositoryMethod;
    public MsFlowEntity(MsClass msController, MsMethod msControllerMethod) {
        this.msController = msController;
        this.msControllerMethod = msControllerMethod;
    }

    public MsFlowEntity(MsMethod n) {
        this.msControllerMethod = n;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(msController.getClassName());
        sb.append(" -> ");
        sb.append(msControllerMethod.getMethodName());
        return sb.toString();
    }
}
