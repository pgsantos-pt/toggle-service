package io.pgsantos.toggles.data.vo.builder;

import io.pgsantos.toggles.data.vo.AssignedTogglesVO;
import io.pgsantos.toggles.data.vo.ToggleAssignmentVO;

import java.util.List;

public final class AssignedTogglesVOBuilder {
    private long toggleId;
    private String toggleName;
    private List<ToggleAssignmentVO> toggleAssignments;

    private AssignedTogglesVOBuilder() {
    }

    public static AssignedTogglesVOBuilder anAssignedTogglesVO() {
        return new AssignedTogglesVOBuilder();
    }

    public AssignedTogglesVOBuilder withToggleId(long toggleId) {
        this.toggleId = toggleId;
        return this;
    }

    public AssignedTogglesVOBuilder withToggleName(String toggleName) {
        this.toggleName = toggleName;
        return this;
    }

    public AssignedTogglesVOBuilder withToggleAssignments(List<ToggleAssignmentVO> toggleAssignments) {
        this.toggleAssignments = toggleAssignments;
        return this;
    }

    public AssignedTogglesVO build() {
        AssignedTogglesVO assignedTogglesVO = new AssignedTogglesVO();
        assignedTogglesVO.setToggleId(toggleId);
        assignedTogglesVO.setToggleName(toggleName);
        assignedTogglesVO.setToggleAssignments(toggleAssignments);
        return assignedTogglesVO;
    }
}
