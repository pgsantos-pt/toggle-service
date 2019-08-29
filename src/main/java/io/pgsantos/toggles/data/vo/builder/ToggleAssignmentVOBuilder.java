package io.pgsantos.toggles.data.vo.builder;

import io.pgsantos.toggles.data.vo.ToggleAssignmentVO;

public final class ToggleAssignmentVOBuilder {
    private long id;
    private String toggleOwner;
    private boolean toggleValue;

    private ToggleAssignmentVOBuilder() {
    }

    public static ToggleAssignmentVOBuilder aToggleAssignmentVO() {
        return new ToggleAssignmentVOBuilder();
    }

    public ToggleAssignmentVOBuilder withId(long id) {
        this.id = id;
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
        toggleAssignmentVO.setId(id);
        toggleAssignmentVO.setToggleOwner(toggleOwner);
        toggleAssignmentVO.setToggleValue(toggleValue);
        return toggleAssignmentVO;
    }
}
