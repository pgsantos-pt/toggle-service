package io.pgsantos.toggles.data.vo;

public final class ToggleRequestVOTestBuilder {
    private String name;

    private ToggleRequestVOTestBuilder() {
    }

    public static ToggleRequestVOTestBuilder aToggleRequestVO() {
        return new ToggleRequestVOTestBuilder();
    }

    public ToggleRequestVOTestBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ToggleRequestVO build() {
        ToggleRequestVO toggleRequestVO = new ToggleRequestVO();
        toggleRequestVO.setName(name);
        return toggleRequestVO;
    }
}
