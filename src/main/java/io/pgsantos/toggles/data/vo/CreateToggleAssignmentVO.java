package io.pgsantos.toggles.data.vo;

public class CreateToggleAssignmentVO {
    private String applicationCode;
    private ToggleVO toggleVO;
    private boolean toggleValue;

    public String getApplicationCode() {
        return applicationCode;
    }

    public void setApplicationCode(String applicationCode) {
        this.applicationCode = applicationCode;
    }

    public ToggleVO getToggleVO() {
        return toggleVO;
    }

    public void setToggleVO(ToggleVO toggleVO) {
        this.toggleVO = toggleVO;
    }

    public boolean isToggleValue() {
        return toggleValue;
    }

    public void setToggleValue(boolean toggleValue) {
        this.toggleValue = toggleValue;
    }
}