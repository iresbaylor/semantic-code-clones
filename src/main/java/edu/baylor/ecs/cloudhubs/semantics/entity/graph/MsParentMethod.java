package edu.baylor.ecs.cloudhubs.semantics.entity.graph;

import lombok.Data;

@Data
public class MsParentMethod {
    private String parentMethodName;
    private String parentClassName;
    private String parentPackageName;
}
