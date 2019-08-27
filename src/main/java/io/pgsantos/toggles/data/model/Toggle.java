package io.pgsantos.toggles.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "toggles")
public class Toggle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "toggle")
    private Set<ToggleAssignment> toggleAssignments;

    public Toggle() {
    }

    public Toggle(String name) {
        this.name = name;
    }

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

    public Set<ToggleAssignment> getToggleAssignments() {
        return toggleAssignments;
    }

    public void setToggleAssignments(Set<ToggleAssignment> toggleAssignments) {
        this.toggleAssignments = toggleAssignments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Toggle toggle = (Toggle) o;
        return id == toggle.id &&
                name.equals(toggle.name) &&
                Objects.equals(toggleAssignments, toggle.toggleAssignments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, toggleAssignments);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Toggle{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", toggleAssignments=").append(toggleAssignments);
        sb.append('}');
        return sb.toString();
    }
}