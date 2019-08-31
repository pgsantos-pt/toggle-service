package io.pgsantos.toggles.data.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ToggleAssignmentVO extends ToggleVO {
    private long toggleAssignmentId;
    private String toggleOwner;
    private boolean toggleValue;

    public long getToggleAssignmentId() {
        return toggleAssignmentId;
    }

    public void setToggleAssignmentId(long toggleAssignmentId) {
        this.toggleAssignmentId = toggleAssignmentId;
    }

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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}