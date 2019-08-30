package io.pgsantos.toggles.data.repository;

import io.pgsantos.toggles.data.model.ToggleAssignment;
import io.pgsantos.toggles.data.model.builder.ToggleAssignmentBuilder;
import io.pgsantos.toggles.data.model.builder.ToggleBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ToggleAssignmentRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ToggleAssignmentRepository toggleAssignmentRepository;

    @Test
    public void save() {
        Long toggleId = (Long) entityManager.persistAndGetId(ToggleBuilder.aToggle().withName("name").build());
        entityManager.flush();

        ToggleAssignment toggleAssignment = toggleAssignmentRepository.save(
                ToggleAssignmentBuilder.aToggleAssignment()
                        .withToggleOwner("owner")
                        .withToggleValue(true)
                        .withToggle(ToggleBuilder.aToggle().withId(toggleId).build())
                        .build());

        ToggleAssignment expectedToggleAssignment = ToggleAssignmentBuilder.aToggleAssignment()
                .withId(1L)
                .withToggleOwner("owner")
                .withToggleValue(true)
                .withToggle(ToggleBuilder.aToggle()
                        .withId(toggleId)
                        .withName("name")
                        .build())
                .build();

        assertThat(toggleAssignment).isEqualTo(expectedToggleAssignment);
    }

    @Test
    public void save_withNoPersistedToggle_shouldThrowJpaObjectRetrievalFailureException() {
        ToggleAssignment toggleAssignment = ToggleAssignmentBuilder.aToggleAssignment()
                .withToggleOwner("owner")
                .withToggleValue(true)
                .withToggle(ToggleBuilder.aToggle().withId(1L).build())
                .build();

        JpaObjectRetrievalFailureException exception = catchThrowableOfType(() -> toggleAssignmentRepository.save(toggleAssignment), JpaObjectRetrievalFailureException.class);

        assertThat(exception.getCause()).isInstanceOf(EntityNotFoundException.class);
        assertThat(exception.getCause().getMessage()).isEqualTo("Unable to find io.pgsantos.toggles.data.model.Toggle with id " + toggleAssignment.getToggle().getId());
    }

    @Test
    public void save_withNoToggle_shouldThrowJpaSystemExceptionWithNestedPersistedException() {
        ToggleAssignment toggleAssignment = ToggleAssignmentBuilder.aToggleAssignment()
                .withToggleOwner("owner")
                .withToggleValue(true)
                .withToggle(ToggleBuilder.aToggle().withName("name").build())
                .build();

        JpaSystemException exception = catchThrowableOfType(() -> toggleAssignmentRepository.save(toggleAssignment), JpaSystemException.class);

        assertThat(exception.getCause()).isInstanceOf(PersistenceException.class);
        assertThat(exception.getCause().getMessage()).isEqualTo("No toggle id was provided to create the toggle assignment");
    }
}