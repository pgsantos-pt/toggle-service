package io.pgsantos.toggles.data.vo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class AssignedTogglesVO {
    private long toggleId;
    private String toggleName;
    private List<ToggleAssignmentVO> toggleAssignments;

    public long getToggleId() {
        return toggleId;
    }

    public void setToggleId(long toggleId) {
        this.toggleId = toggleId;
    }

    public String getToggleName() {
        return toggleName;
    }

    public void setToggleName(String toggleName) {
        this.toggleName = toggleName;
    }

    public List<ToggleAssignmentVO> getToggleAssignments() {
        return toggleAssignments;
    }

    public void setToggleAssignments(List<ToggleAssignmentVO> toggleAssignments) {
        this.toggleAssignments = toggleAssignments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        AssignedTogglesVO that = (AssignedTogglesVO) o;

        return new EqualsBuilder()
                .append(toggleId, that.toggleId)
                .append(toggleName, that.toggleName)
                .append(toggleAssignments, that.toggleAssignments)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(toggleId)
                .append(toggleName)
                .append(toggleAssignments)
                .toHashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}