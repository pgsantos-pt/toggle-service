package io.pgsantos.toggles.data.vo.request;

import javax.validation.constraints.NotBlank;

public class ToggleRequestVO {
    @NotBlank(message = "The field 'name' is mandatory")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}