package io.pgsantos.toggles.data.vo;

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
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}