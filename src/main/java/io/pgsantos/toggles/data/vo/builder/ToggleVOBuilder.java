package io.pgsantos.toggles.data.vo.builder;

import io.pgsantos.toggles.data.vo.ToggleAssignmentVO;
import io.pgsantos.toggles.data.vo.ToggleVO;

import java.util.List;

public final class ToggleVOBuilder {
    private long id;
    private String name;
    private List<ToggleAssignmentVO> toggleAssignmentsVOs;

    private ToggleVOBuilder() {
    }

    public static ToggleVOBuilder aToggleVO() {
        return new ToggleVOBuilder();
    }

    public ToggleVOBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public ToggleVOBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ToggleVOBuilder withToggleAssignmentsVOs(List<ToggleAssignmentVO> toggleAssignmentsVOs) {
        this.toggleAssignmentsVOs = toggleAssignmentsVOs;
        return this;
    }

    public ToggleVO build() {
        ToggleVO toggleVO = new ToggleVO();
        toggleVO.setId(id);
        toggleVO.setName(name);
        toggleVO.setToggleAssignmentsVOs(toggleAssignmentsVOs);
        return toggleVO;
    }
}
