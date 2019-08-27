package io.pgsantos.toggles.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "toggle_assignments")
public class ToggleAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="application_code", nullable = false)
    private String applicationCode;

    @ManyToOne
    @JoinColumn(name = "toggle_id")
    private Toggle toggle;

    @Column(name = "toggle_value", nullable = false)
    private boolean toggleValue;

    public ToggleAssignment() {
    }

    public ToggleAssignment(String applicationCode, Toggle toggle, boolean toggleValue) {
        this.applicationCode = applicationCode;
        this.toggle = toggle;
        this.toggleValue = toggleValue;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getApplicationCode() {
        return applicationCode;
    }

    public void setApplicationCode(String applicationCode) {
        this.applicationCode = applicationCode;
    }

    public Toggle getToggle() {
        return toggle;
    }

    public void setToggle(Toggle toggle) {
        this.toggle = toggle;
    }

    public boolean isToggleValue() {
        return toggleValue;
    }

    public void setToggleValue(boolean toggleValue) {
        this.toggleValue = toggleValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToggleAssignment that = (ToggleAssignment) o;
        return id == that.id &&
                toggleValue == that.toggleValue &&
                applicationCode.equals(that.applicationCode) &&
                toggle.equals(that.toggle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, applicationCode, toggle, toggleValue);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ToggleAssignment{");
        sb.append("id=").append(id);
        sb.append(", applicationCode='").append(applicationCode).append('\'');
        sb.append(", toggle=").append(toggle);
        sb.append(", toggleValue=").append(toggleValue);
        sb.append('}');
        return sb.toString();
    }
}