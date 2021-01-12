package edu.baylor.ecs.cloudhubs.semantics.entity.graph;

import lombok.Data;

@Data
public class MsAnnotation {
    private boolean isHttpAnnotation;
    private String annotationName;
    private String key;
    private String value;
}
