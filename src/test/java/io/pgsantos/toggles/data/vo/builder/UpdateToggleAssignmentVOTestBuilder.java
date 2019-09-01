package io.pgsantos.toggles.data.vo.builder;

import io.pgsantos.toggles.data.vo.UpdateToggleAssignmentVO;

public final class UpdateToggleAssignmentVOTestBuilder {
    private boolean toggleValue;

    private UpdateToggleAssignmentVOTestBuilder() {
    }

    public static UpdateToggleAssignmentVOTestBuilder anUpdateToggleAssignmentVO() {
        return new UpdateToggleAssignmentVOTestBuilder();
    }

    public UpdateToggleAssignmentVOTestBuilder withToggleValue(boolean toggleValue) {
        this.toggleValue = toggleValue;
        return this;
    }

    public UpdateToggleAssignmentVO build() {
        UpdateToggleAssignmentVO updateToggleAssignmentVO = new UpdateToggleAssignmentVO();
        updateToggleAssignmentVO.setToggleValue(toggleValue);
        return updateToggleAssignmentVO;
    }
}
