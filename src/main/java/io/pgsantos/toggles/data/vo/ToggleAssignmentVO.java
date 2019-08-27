package io.pgsantos.toggles.data.vo;

public class ToggleAssignmentVO {
    private long id;
    private String applicationCode;
    private ToggleVO toggle;
    private boolean toggleValue;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getApplicationCode() {
        return applicationCode;
    }

    public void setApplicationCode(String applicationCode) {
        this.applicationCode = applicationCode;
    }

    public ToggleVO getToggle() {
        return toggle;
    }

    public void setToggle(ToggleVO toggle) {
        this.toggle = toggle;
    }

    public boolean isToggleValue() {
        return toggleValue;
    }

    public void setToggleValue(boolean toggleValue) {
        this.toggleValue = toggleValue;
    }
}