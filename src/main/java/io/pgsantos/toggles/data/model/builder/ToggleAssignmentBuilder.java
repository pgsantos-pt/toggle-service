package io.pgsantos.toggles.data.model.builder;

import io.pgsantos.toggles.data.model.Toggle;
import io.pgsantos.toggles.data.model.ToggleAssignment;

public final class ToggleAssignmentBuilder {
    private Long id;
    private Toggle toggle;
    private String toggleOwner;
    private Boolean toggleValue;

    private ToggleAssignmentBuilder() {
    }

    public static ToggleAssignmentBuilder aToggleAssignment() {
        return new ToggleAssignmentBuilder();
    }

    public ToggleAssignmentBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public ToggleAssignmentBuilder withToggle(Toggle toggle) {
        this.toggle = toggle;
        return this;
    }

    public ToggleAssignmentBuilder withToggleOwner(String toggleOwner) {
        this.toggleOwner = toggleOwner;
        return this;
    }

    public ToggleAssignmentBuilder withToggleValue(Boolean toggleValue) {
        this.toggleValue = toggleValue;
        return this;
    }

    public ToggleAssignment build() {
        ToggleAssignment toggleAssignment = new ToggleAssignment();
        toggleAssignment.setId(id);
        toggleAssignment.setToggle(toggle);
        toggleAssignment.setToggleOwner(toggleOwner);
        toggleAssignment.setToggleValue(toggleValue);
        return toggleAssignment;
    }
}
