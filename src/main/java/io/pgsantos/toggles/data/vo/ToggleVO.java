package io.pgsantos.toggles.data.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ToggleVO {
    private long id;
    
    private String name;

    @JsonProperty("toggleAssignments")
    private List<ToggleAssignmentVO> toggleAssignmentsVOs;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ToggleAssignmentVO> getToggleAssignmentsVOs() {
        return toggleAssignmentsVOs;
    }

    public void setToggleAssignmentsVOs(List<ToggleAssignmentVO> toggleAssignmentsVOs) {
        this.toggleAssignmentsVOs = toggleAssignmentsVOs;
    }
}