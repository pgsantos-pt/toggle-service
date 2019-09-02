package io.pgsantos.toggles.data.vo.builder;

import io.pgsantos.toggles.data.vo.request.UpdateToggleAssignmentRequestVO;

public final class UpdateToggleAssignmentRequestVOTestBuilder {
    private boolean toggleValue;

    private UpdateToggleAssignmentRequestVOTestBuilder() {
    }

    public static UpdateToggleAssignmentRequestVOTestBuilder anUpdateToggleAssignmentRequestVO() {
        return new UpdateToggleAssignmentRequestVOTestBuilder();
    }

    public UpdateToggleAssignmentRequestVOTestBuilder withToggleValue(boolean toggleValue) {
        this.toggleValue = toggleValue;
        return this;
    }

    public UpdateToggleAssignmentRequestVO build() {
        UpdateToggleAssignmentRequestVO updateToggleAssignmentRequestVO = new UpdateToggleAssignmentRequestVO();
        updateToggleAssignmentRequestVO.setToggleValue(toggleValue);
        return updateToggleAssignmentRequestVO;
    }
}
