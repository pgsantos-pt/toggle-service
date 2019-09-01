package io.pgsantos.toggles.data.vo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ToggleVO {
    private long toggleId;
    private String toggleName;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ToggleVO toggleVO = (ToggleVO) o;

        return new EqualsBuilder()
                .append(toggleId, toggleVO.toggleId)
                .append(toggleName, toggleVO.toggleName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(toggleId)
                .append(toggleName)
                .toHashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}