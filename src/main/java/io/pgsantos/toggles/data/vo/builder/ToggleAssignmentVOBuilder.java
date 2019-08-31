package io.pgsantos.toggles.data.vo.builder;

import io.pgsantos.toggles.data.vo.ToggleAssignmentVO;

public final class ToggleAssignmentVOBuilder {
    private long toggleId;
    private long toggleAssignmentId;
    private String toggleName;
    private String toggleOwner;
    private boolean toggleValue;

    private ToggleAssignmentVOBuilder() {
    }

    public static ToggleAssignmentVOBuilder aToggleAssignmentVO() {
        return new ToggleAssignmentVOBuilder();
    }

    public ToggleAssignmentVOBuilder withToggleId(long toggleId) {
        this.toggleId = toggleId;
        return this;
    }

    public ToggleAssignmentVOBuilder withToggleAssignmentId(long toggleAssignmentId) {
        this.toggleAssignmentId = toggleAssignmentId;
        return this;
    }

    public ToggleAssignmentVOBuilder withToggleName(String toggleName) {
        this.toggleName = toggleName;
        return this;
    }

    public ToggleAssignmentVOBuilder withToggleOwner(String toggleOwner) {
        this.toggleOwner = toggleOwner;
        return this;
    }

    public ToggleAssignmentVOBuilder withToggleValue(boolean toggleValue) {
        this.toggleValue = toggleValue;
        return this;
    }

    public ToggleAssignmentVO build() {
        ToggleAssignmentVO toggleAssignmentVO = new ToggleAssignmentVO();
        toggleAssignmentVO.setToggleId(toggleId);
        toggleAssignmentVO.setToggleAssignmentId(toggleAssignmentId);
        toggleAssignmentVO.setToggleName(toggleName);
        toggleAssignmentVO.setToggleOwner(toggleOwner);
        toggleAssignmentVO.setToggleValue(toggleValue);
        return toggleAssignmentVO;
    }
}
