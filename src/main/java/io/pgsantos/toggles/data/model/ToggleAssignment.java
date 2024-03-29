package io.pgsantos.toggles.data.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "toggle_assignments", uniqueConstraints = {@UniqueConstraint(columnNames = {"toggle_id", "toggle_owner"})})
public class ToggleAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "toggle_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Toggle toggle;

    @Column(name="toggle_owner", nullable = false)
    private String toggleOwner;

    @Column(name = "toggle_value", nullable = false)
    private Boolean toggleValue;

    public ToggleAssignment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToggleOwner() {
        return toggleOwner;
    }

    public void setToggleOwner(String toggleOwner) {
        this.toggleOwner = toggleOwner;
    }

    public Toggle getToggle() {
        return toggle;
    }

    public void setToggle(Toggle toggle) {
        this.toggle = toggle;
    }

    public Boolean getToggleValue() {
        return toggleValue;
    }

    public void setToggleValue(Boolean toggleValue) {
        this.toggleValue = toggleValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ToggleAssignment that = (ToggleAssignment) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(toggleOwner, that.toggleOwner)
                .append(toggle, that.toggle)
                .append(toggleValue, that.toggleValue)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(toggleOwner)
                .append(toggle)
                .append(toggleValue)
                .toHashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}