package edu.baylor.ecs.cloudhubs.semantics.entity.graph;

import lombok.Data;

import java.util.Arrays;

@Data
public class MsId {
    private String path;
    //first six
    private String directoryName;

    public MsId(String path) {
        this.setPath(path);
    }

    public void setPath(String path) {
        this.path = path;
        String[] split = path.split("/");
        String[] subarr = Arrays.asList(split)
                .subList(0, 6)
                .toArray(new String[0]);
        directoryName = String.join("/", subarr);
//        System.out.printf("");
    }
}
