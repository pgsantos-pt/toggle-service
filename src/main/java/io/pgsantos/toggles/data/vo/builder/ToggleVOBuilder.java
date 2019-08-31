package io.pgsantos.toggles.data.vo.builder;

import io.pgsantos.toggles.data.vo.ToggleVO;

public final class ToggleVOBuilder {
    private long toggleId;
    private String toggleName;

    private ToggleVOBuilder() {
    }

    public static ToggleVOBuilder aToggleVO() {
        return new ToggleVOBuilder();
    }

    public ToggleVOBuilder withToggleId(long toggleId) {
        this.toggleId = toggleId;
        return this;
    }

    public ToggleVOBuilder withToggleName(String toggleName) {
        this.toggleName = toggleName;
        return this;
    }

    public ToggleVO build() {
        ToggleVO toggleVO = new ToggleVO();
        toggleVO.setToggleId(toggleId);
        toggleVO.setToggleName(toggleName);
        return toggleVO;
    }
}
