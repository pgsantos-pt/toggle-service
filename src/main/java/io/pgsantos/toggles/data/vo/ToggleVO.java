package io.pgsantos.toggles.data.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class ToggleVO {
    private long id;
    
    private String name;

    @JsonProperty("toggleAssignments")
    private List<ToggleAssignmentVO> toggleAssignmentsVOs;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ToggleAssignmentVO> getToggleAssignmentsVOs() {
        return toggleAssignmentsVOs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ToggleVO toggleVO = (ToggleVO) o;

        return new EqualsBuilder()
                .append(id, toggleVO.id)
                .append(name, toggleVO.name)
                .append(toggleAssignmentsVOs, toggleVO.toggleAssignmentsVOs)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(toggleAssignmentsVOs)
                .toHashCode();
    }

    public void setToggleAssignmentsVOs(List<ToggleAssignmentVO> toggleAssignmentsVOs) {
        this.toggleAssignmentsVOs = toggleAssignmentsVOs;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}