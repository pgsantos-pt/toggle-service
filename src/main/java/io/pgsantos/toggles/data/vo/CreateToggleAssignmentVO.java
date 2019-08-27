package io.pgsantos.toggles.data.vo;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

public class CreateToggleAssignmentVO {
    @NotBlank
    private String applicationCode;

    @Valid
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