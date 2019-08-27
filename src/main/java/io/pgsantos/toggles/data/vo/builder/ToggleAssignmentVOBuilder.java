package io.pgsantos.toggles.data.vo.builder;

import io.pgsantos.toggles.data.vo.ToggleAssignmentVO;
import io.pgsantos.toggles.data.vo.ToggleVO;

public final class ToggleAssignmentVOBuilder {
    private long id;
    private String applicationCode;
    private ToggleVO toggle;
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

    public ToggleAssignmentVOBuilder withApplicationCode(String applicationCode) {
        this.applicationCode = applicationCode;
        return this;
    }

    public ToggleAssignmentVOBuilder withToggle(ToggleVO toggle) {
        this.toggle = toggle;
        return this;
    }

    public ToggleAssignmentVOBuilder withToggleValue(boolean toggleValue) {
        this.toggleValue = toggleValue;
        return this;
    }

    public ToggleAssignmentVO build() {
        ToggleAssignmentVO toggleAssignmentVO = new ToggleAssignmentVO();
        toggleAssignmentVO.setId(id);
        toggleAssignmentVO.setApplicationCode(applicationCode);
        toggleAssignmentVO.setToggle(toggle);
        toggleAssignmentVO.setToggleValue(toggleValue);
        return toggleAssignmentVO;
    }
}
