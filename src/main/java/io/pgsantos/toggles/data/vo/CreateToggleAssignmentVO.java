package io.pgsantos.toggles.data.vo;

import javax.validation.constraints.NotBlank;

public class CreateToggleAssignmentVO {
    @NotBlank(message = "The parameter 'toggleOwner' is mandatory")
    private String toggleOwner;

    private boolean toggleValue;

    public String getToggleOwner() {
        return toggleOwner;
    }

    public void setToggleOwner(String toggleOwner) {
        this.toggleOwner = toggleOwner;
    }

    public boolean getToggleValue() {
        return toggleValue;
    }

    public void setToggleValue(boolean toggleValue) {
        this.toggleValue = toggleValue;
    }
}