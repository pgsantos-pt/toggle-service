package io.pgsantos.toggles.data.vo.builder;

import io.pgsantos.toggles.data.vo.request.CreateToggleAssignmentRequestVO;

public final class CreateToggleAssignmentRequestVOTestBuilder {
    private String toggleOwner;
    private boolean toggleValue;

    private CreateToggleAssignmentRequestVOTestBuilder() {
    }

    public static CreateToggleAssignmentRequestVOTestBuilder aCreateToggleAssignmentRequestVO() {
        return new CreateToggleAssignmentRequestVOTestBuilder();
    }

    public CreateToggleAssignmentRequestVOTestBuilder withToggleOwner(String toggleOwner) {
        this.toggleOwner = toggleOwner;
        return this;
    }

    public CreateToggleAssignmentRequestVOTestBuilder withToggleValue(boolean toggleValue) {
        this.toggleValue = toggleValue;
        return this;
    }

    public CreateToggleAssignmentRequestVO build() {
        CreateToggleAssignmentRequestVO createToggleAssignmentRequestVO = new CreateToggleAssignmentRequestVO();
        createToggleAssignmentRequestVO.setToggleOwner(toggleOwner);
        createToggleAssignmentRequestVO.setToggleValue(toggleValue);
        return createToggleAssignmentRequestVO;
    }
}
