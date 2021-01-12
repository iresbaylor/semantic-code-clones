package edu.baylor.ecs.cloudhubs.semantics.entity.inconsistencies;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MsDefectSubcategories {
    private String name;
    private String level;
    private List<MsDefectInstance> msDefectInstances;
}
