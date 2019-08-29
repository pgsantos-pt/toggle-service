package io.pgsantos.toggles.data.vo;

import javax.validation.constraints.NotBlank;

public class ToggleRequestVO {
    @NotBlank(message = "The field 'name' is mandatory to have a human-readable identifier.")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}