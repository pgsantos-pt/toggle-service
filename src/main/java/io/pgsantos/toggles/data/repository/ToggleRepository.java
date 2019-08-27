package io.pgsantos.toggles.data.repository;

import io.pgsantos.toggles.data.model.Toggle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ToggleRepository extends CrudRepository<Toggle, Long> {
    @Override
    Set<Toggle> findAll();

    Optional<Toggle> findByName(String name);
}