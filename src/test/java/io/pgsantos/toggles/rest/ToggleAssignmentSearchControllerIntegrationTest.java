package io.pgsantos.toggles.rest;

import io.pgsantos.toggles.data.converter.ToggleAssignmentConverter;
import io.pgsantos.toggles.data.model.Toggle;
import io.pgsantos.toggles.data.model.ToggleAssignment;
import io.pgsantos.toggles.data.model.builder.ToggleAssignmentBuilder;
import io.pgsantos.toggles.data.model.builder.ToggleBuilder;
import io.pgsantos.toggles.data.repository.ToggleAssignmentRepository;
import io.pgsantos.toggles.data.repository.ToggleRepository;
import io.pgsantos.toggles.data.vo.ToggleAssignmentVO;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class ToggleAssignmentSearchControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private ToggleRepository toggleRepository;

    @Autowired
    private ToggleAssignmentRepository toggleAssignmentRepository;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    private String toggle1Name = RandomStringUtils.random(10, true, true);
    private String toggle2Name = RandomStringUtils.random(10, true, true);
    private String toggleOwner1 = RandomStringUtils.random(10, true, true);
    private String toggleOwner2 = RandomStringUtils.random(10, true, true);
    private ToggleAssignment toggleAssignment1, toggleAssignment2, toggleAssignment3;

    @Before
    public void setUp() {
        Toggle toggle1 = toggleRepository.save(ToggleBuilder.aToggle().withName(toggle1Name).build());
        Toggle toggle2 = toggleRepository.save(ToggleBuilder.aToggle().withName(toggle2Name).build());

        toggleAssignment1 = toggleAssignmentRepository.save(
                ToggleAssignmentBuilder.aToggleAssignment()
                        .withToggleOwner(toggleOwner1)
                        .withToggleValue(new Random().nextBoolean())
                        .withToggle(toggle1)
                        .build());

        toggleAssignment2 = toggleAssignmentRepository.save(
                ToggleAssignmentBuilder.aToggleAssignment()
                        .withToggleOwner(toggleOwner1)
                        .withToggleValue(new Random().nextBoolean())
                        .withToggle(toggle2)
                        .build());

        toggleAssignment3 = toggleAssignmentRepository.save(
                ToggleAssignmentBuilder.aToggleAssignment()
                        .withToggleOwner(toggleOwner2)
                        .withToggleValue(new Random().nextBoolean())
                        .withToggle(toggle1)
                        .build());
    }

    @After
    public void tearDown() {
        toggleRepository.deleteAll();
    }

    @Test
    public void searchToggleAssignments_withNoCriteria_shouldReturnAll() {
        ResponseEntity<List<ToggleAssignmentVO>> response = restTemplate.exchange(
                createURL(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .containsExactlyInAnyOrder(
                        ToggleAssignmentConverter.convertToVO(toggleAssignment1),
                        ToggleAssignmentConverter.convertToVO(toggleAssignment2),
                        ToggleAssignmentConverter.convertToVO(toggleAssignment3));
    }

    @Test
    public void searchToggleAssignments_withToggleName_shouldReturnAllToggleAssignmentsMatchingCriteria() {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(createURL())
                .queryParam("toggleName", toggle1Name);

        ResponseEntity<List<ToggleAssignmentVO>> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .containsExactlyInAnyOrder(
                        ToggleAssignmentConverter.convertToVO(toggleAssignment1),
                        ToggleAssignmentConverter.convertToVO(toggleAssignment3));
    }

    @Test
    public void searchToggleAssignments_withToggleOwner_shouldReturnAllToggleAssignmentsMatchingCriteria() {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(createURL())
                .queryParam("toggleOwner", toggleOwner1);

        ResponseEntity<List<ToggleAssignmentVO>> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .containsExactlyInAnyOrder(
                        ToggleAssignmentConverter.convertToVO(toggleAssignment1),
                        ToggleAssignmentConverter.convertToVO(toggleAssignment2));
    }

    @Test
    public void searchToggleAssignments_withToggleOwnerAndToggleName_shouldReturnAllToggleAssignmentsMatchingCriteria() {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(createURL())
                .queryParam("toggleName", toggle1Name)
                .queryParam("toggleOwner", toggleOwner1);

        ResponseEntity<List<ToggleAssignmentVO>> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .containsExactlyInAnyOrder(
                        ToggleAssignmentConverter.convertToVO(toggleAssignment1));
    }

    @Test
    public void searchToggleAssignments_withToggleOwnerAndToggleName_whenNotMatchingCriteria_shouldReturnEmptyList() {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(createURL())
                .queryParam("toggleName", RandomStringUtils.random(5, true, true));

        ResponseEntity<List<ToggleAssignmentVO>> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEmpty();
    }

    private String createURL() {
        return "http://localhost:" + port + "/search-toggle-assignments";
    }
}