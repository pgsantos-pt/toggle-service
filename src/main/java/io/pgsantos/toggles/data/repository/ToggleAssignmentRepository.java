package io.pgsantos.toggles.data.repository;

import io.pgsantos.toggles.data.model.ToggleAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToggleAssignmentRepository extends JpaRepository<ToggleAssignment, Long> {
}