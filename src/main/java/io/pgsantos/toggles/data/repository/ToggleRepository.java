package io.pgsantos.toggles.data.repository;

import io.pgsantos.toggles.data.model.Toggle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToggleRepository extends JpaRepository<Toggle, Long> {
}