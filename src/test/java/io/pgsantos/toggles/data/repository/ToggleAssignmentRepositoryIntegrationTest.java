package io.pgsantos.toggles.data.repository;

import io.pgsantos.toggles.data.model.Toggle;
import io.pgsantos.toggles.data.model.ToggleAssignment;
import io.pgsantos.toggles.data.model.builder.ToggleAssignmentBuilder;
import io.pgsantos.toggles.data.model.builder.ToggleBuilder;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ToggleAssignmentRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ToggleAssignmentRepository toggleAssignmentRepository;

    @Test
    public void findAllByToggle_Id() {
        Toggle toggle = entityManager.persist(ToggleBuilder.aToggle().withName(RandomStringUtils.random(10)).build());
        ToggleAssignment toggleAssignment1 = entityManager.persist(
                ToggleAssignmentBuilder.aToggleAssignment()
                        .withToggleOwner(RandomStringUtils.random(10))
                        .withToggleValue(new Random().nextBoolean())
                        .withToggle(toggle)
                        .build());

        ToggleAssignment toggleAssignment2 = entityManager.persist(
                ToggleAssignmentBuilder.aToggleAssignment()
                        .withToggleOwner(RandomStringUtils.random(10))
                        .withToggleValue(new Random().nextBoolean())
                        .withToggle(toggle)
                        .build());

        entityManager.flush();

        Stream<ToggleAssignment> toggleAssignments = toggleAssignmentRepository.findAllByToggle_Id(toggle.getId());

        assertThat(toggleAssignments).containsOnly(toggleAssignment1, toggleAssignment2);
    }

    @Test
    public void findAllByToggle_Id_whenNonExistingToggleAssignment_shouldReturnEmptyStream() {
        Toggle toggle = entityManager.persist(ToggleBuilder.aToggle().withName(RandomStringUtils.random(10)).build());

        entityManager.flush();

        Stream<ToggleAssignment> toggleAssignments = toggleAssignmentRepository.findAllByToggle_Id(toggle.getId());

        assertThat(toggleAssignments).isEmpty();
    }

    @Test
    public void findByIdAndToggle_Id() {
        Toggle toggle = entityManager.persist(ToggleBuilder.aToggle().withName(RandomStringUtils.random(10)).build());
        ToggleAssignment expectedToggleAssignment = entityManager.persist(
                ToggleAssignmentBuilder.aToggleAssignment()
                        .withToggleOwner(RandomStringUtils.random(10))
                        .withToggleValue(new Random().nextBoolean())
                        .withToggle(toggle)
                        .build());

        entityManager.flush();

        Optional<ToggleAssignment> toggleAssignment = toggleAssignmentRepository.findByIdAndToggle_Id(expectedToggleAssignment.getId(), toggle.getId());

        assertThat(toggleAssignment.get()).isEqualTo(expectedToggleAssignment);
    }

    @Test
    public void findByIdAndToggle_Id_withNonExistingToggleAssignment_shouldReturnOptionalEmpty() {
        Toggle toggle = entityManager.persist(ToggleBuilder.aToggle().withName(RandomStringUtils.random(10)).build());

        entityManager.flush();

        Optional<ToggleAssignment> toggleAssignment = toggleAssignmentRepository.findByIdAndToggle_Id(new Random().nextLong(), toggle.getId());

        assertThat(toggleAssignment.isPresent()).isFalse();
    }
}