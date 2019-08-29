package io.pgsantos.toggles.data.vo;

public class ToggleAssignmentVO {
    private long id;
    private String toggleOwner;
    private boolean toggleValue;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToggleOwner() {
        return toggleOwner;
    }

    public void setToggleOwner(String toggleOwner) {
        this.toggleOwner = toggleOwner;
    }

    public boolean isToggleValue() {
        return toggleValue;
    }

    public void setToggleValue(boolean toggleValue) {
        this.toggleValue = toggleValue;
    }
}