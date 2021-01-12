package edu.baylor.ecs.cloudhubs.semantics.entity.graph;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class MsRestCall extends MsMethodCall{
    private String api;
    private String httpMethod;
    private String returnType;
}
