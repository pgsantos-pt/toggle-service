package io.pgsantos.toggles.data.repository;

import io.pgsantos.toggles.data.model.Toggle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface ToggleRepository extends JpaRepository<Toggle, Long> {
    Optional<Toggle> findByName(String name);

    @Query("select t from Toggle t")
    Stream<Toggle> findAllAndStream();
}