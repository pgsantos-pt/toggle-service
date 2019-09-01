package io.pgsantos.toggles.data.vo.builder;

import io.pgsantos.toggles.data.vo.CreateToggleAssignmentVO;

public final class CreateToggleAssignmentVOTestBuilder {
    private String toggleOwner;
    private boolean toggleValue;

    private CreateToggleAssignmentVOTestBuilder() {
    }

    public static CreateToggleAssignmentVOTestBuilder aCreateToggleAssignmentVO() {
        return new CreateToggleAssignmentVOTestBuilder();
    }

    public CreateToggleAssignmentVOTestBuilder withToggleOwner(String toggleOwner) {
        this.toggleOwner = toggleOwner;
        return this;
    }

    public CreateToggleAssignmentVOTestBuilder withToggleValue(boolean toggleValue) {
        this.toggleValue = toggleValue;
        return this;
    }

    public CreateToggleAssignmentVO build() {
        CreateToggleAssignmentVO createToggleAssignmentVO = new CreateToggleAssignmentVO();
        createToggleAssignmentVO.setToggleOwner(toggleOwner);
        createToggleAssignmentVO.setToggleValue(toggleValue);
        return createToggleAssignmentVO;
    }
}
