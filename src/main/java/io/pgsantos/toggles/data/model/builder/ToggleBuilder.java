package io.pgsantos.toggles.data.model.builder;

import io.pgsantos.toggles.data.model.Toggle;

public final class ToggleBuilder {
    private Long id;
    private String name;

    private ToggleBuilder() {
    }

    public static ToggleBuilder aToggle() {
        return new ToggleBuilder();
    }

    public ToggleBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public ToggleBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public Toggle build() {
        Toggle toggle = new Toggle();
        toggle.setId(id);
        toggle.setName(name);
        return toggle;
    }
}
