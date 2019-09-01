package io.pgsantos.toggles.data.vo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
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
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ToggleAssignmentVO that = (ToggleAssignmentVO) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(toggleAssignmentId, that.toggleAssignmentId)
                .append(toggleValue, that.toggleValue)
                .append(toggleOwner, that.toggleOwner)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(toggleAssignmentId)
                .append(toggleOwner)
                .append(toggleValue)
                .toHashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}