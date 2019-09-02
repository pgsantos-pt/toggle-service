package io.pgsantos.toggles.rest;

import io.pgsantos.toggles.data.converter.ToggleAssignmentConverter;
import io.pgsantos.toggles.data.model.Toggle;
import io.pgsantos.toggles.data.model.ToggleAssignment;
import io.pgsantos.toggles.data.model.builder.ToggleAssignmentBuilder;
import io.pgsantos.toggles.data.model.builder.ToggleBuilder;
import io.pgsantos.toggles.data.repository.ToggleAssignmentRepository;
import io.pgsantos.toggles.data.repository.ToggleRepository;
import io.pgsantos.toggles.data.vo.CreateToggleAssignmentVO;
import io.pgsantos.toggles.data.vo.ToggleAssignmentVO;
import io.pgsantos.toggles.data.vo.UpdateToggleAssignmentVO;
import io.pgsantos.toggles.data.vo.builder.CreateToggleAssignmentVOTestBuilder;
import io.pgsantos.toggles.data.vo.builder.UpdateToggleAssignmentVOTestBuilder;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class ToggleAssignmentControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private ToggleRepository toggleRepository;

    @Autowired
    private ToggleAssignmentRepository toggleAssignmentRepository;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    private Toggle toggle1, toggle2, toggle3;
    private ToggleAssignment toggleAssignment1, toggleAssignment2, toggleAssignment3;

    @Before
    public void setUp() {
        toggle1 = toggleRepository.save(ToggleBuilder.aToggle().withName(RandomStringUtils.random(10, true, true)).build());
        toggle2 = toggleRepository.save(ToggleBuilder.aToggle().withName(RandomStringUtils.random(10, true, true)).build());
        toggle3 = toggleRepository.save(ToggleBuilder.aToggle().withName(RandomStringUtils.random(10, true, true)).build());

        toggleAssignment1 = toggleAssignmentRepository.save(
                ToggleAssignmentBuilder.aToggleAssignment()
                        .withToggleOwner(RandomStringUtils.random(10, true, true))
                        .withToggleValue(new Random().nextBoolean())
                        .withToggle(toggle1)
                        .build());

        toggleAssignment2 = toggleAssignmentRepository.save(
                ToggleAssignmentBuilder.aToggleAssignment()
                        .withToggleOwner(RandomStringUtils.random(10, true, true))
                        .withToggleValue(new Random().nextBoolean())
                        .withToggle(toggle2)
                        .build());

        toggleAssignment3 = toggleAssignmentRepository.save(
                ToggleAssignmentBuilder.aToggleAssignment()
                        .withToggleOwner(RandomStringUtils.random(10, true, true))
                        .withToggleValue(new Random().nextBoolean())
                        .withToggle(toggle1)
                        .build());
    }

    @After
    public void tearDown() {
        toggleRepository.deleteAll();
    }

    @Test
    public void getToggleAssignmentsByToggleId() {
        ResponseEntity<List<ToggleAssignmentVO>> response = restTemplate.exchange(
                createURL(""),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {},
                toggle1.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .containsExactlyInAnyOrder(
                        ToggleAssignmentConverter.convertToVO(toggleAssignment1),
                        ToggleAssignmentConverter.convertToVO(toggleAssignment3));
    }

    @Test
    public void getToggleAssignmentsByToggleId_withNonExistingToggleId_shouldReturnEmptyList() {
        long toggleId = new Random().nextLong();

        ResponseEntity<List<ToggleAssignmentVO>> response = restTemplate.exchange(
                createURL(""),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {},
                toggleId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEmpty();
    }

    @Test
    public void getToggleAssignmentsByToggleId_withToggleIdHavingNoChildren_shouldReturnEmptyList() {
        ResponseEntity<List<ToggleAssignmentVO>> response = restTemplate.exchange(
                createURL(""),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {},
                toggle3.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEmpty();
    }

    @Test
    public void getToggleAssignment(){
        ResponseEntity<ToggleAssignmentVO> response = restTemplate.exchange(
                createURL("/{toggleAssignmentId}"),
                HttpMethod.GET,
                null,
                ToggleAssignmentVO.class,
                toggle1.getId(),
                toggleAssignment1.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(ToggleAssignmentConverter.convertToVO(toggleAssignment1));
    }

    @Test
    public void getToggleAssignment_withNonExistingToggleId_shouldReturnNotFound(){
        long nonExistingToggleId = new Random().nextLong();

        ResponseEntity<String> response = restTemplate.exchange(
                createURL("/{toggleAssignmentId}"),
                HttpMethod.GET,
                null,
                String.class,
                nonExistingToggleId,
                toggleAssignment1.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).contains("The association between toggle ["+nonExistingToggleId+"] and toggle assignment ["+toggleAssignment1.getId()+"] is non existing");
    }

    @Test
    public void getToggleAssignment_withExistingToggleId_butWithWrongChild_shouldReturnNotFound(){
        ResponseEntity<String> response = restTemplate.exchange(
                createURL("/{toggleAssignmentId}"),
                HttpMethod.GET,
                null,
                String.class,
                toggle1.getId(),
                toggleAssignment2.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).contains("The association between toggle ["+toggle1.getId()+"] and toggle assignment ["+toggleAssignment2.getId()+"] is non existing");
    }

    @Test
    public void getToggleAssignment_withNonExistingIds_shouldReturnNotFound(){
        long nonExistingToggleId = new Random().nextLong();
        long nonExistingToggleAssignmentId = new Random().nextLong();

        ResponseEntity<String> response = restTemplate.exchange(
                createURL("/{toggleAssignmentId}"),
                HttpMethod.GET,
                null,
                String.class,
                nonExistingToggleId,
                nonExistingToggleAssignmentId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).contains("The association between toggle ["+nonExistingToggleId+"] and toggle assignment ["+nonExistingToggleAssignmentId+"] is non existing");
    }

    @Test
    public void createToggleAssignment() {
        HttpEntity<CreateToggleAssignmentVO> request = new HttpEntity<>(
                CreateToggleAssignmentVOTestBuilder.aCreateToggleAssignmentVO()
                        .withToggleOwner(RandomStringUtils.random(10, true, true))
                        .withToggleValue(new Random().nextBoolean())
                        .build());

        ResponseEntity<ToggleAssignmentVO> response = restTemplate.exchange(
                createURL(""),
                HttpMethod.POST,
                request,
                ToggleAssignmentVO.class,
                toggle3.getId());

        ResponseEntity<ToggleAssignmentVO> getResponse = restTemplate.exchange(
                createURL("/{toggleAssignmentId}"),
                HttpMethod.GET,
                null,
                ToggleAssignmentVO.class,
                toggle3.getId(),
                response.getBody().getToggleAssignmentId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(getResponse.getBody());
    }

    @Test
    public void createToggleAssignment_withNonExistingToggleId_shouldReturnNotFound() {
        long nonExistingToggleId = new Random().nextLong();

        HttpEntity<CreateToggleAssignmentVO> request = new HttpEntity<>(
                CreateToggleAssignmentVOTestBuilder.aCreateToggleAssignmentVO()
                        .withToggleOwner(RandomStringUtils.random(10, true, true))
                        .withToggleValue(new Random().nextBoolean())
                        .build());

        ResponseEntity<String> response = restTemplate.exchange(
                createURL(""),
                HttpMethod.POST,
                request,
                String.class,
                nonExistingToggleId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).contains("The requested toggle [" + nonExistingToggleId + "] was not found");
    }

    @Test
    public void createToggleAssignment_withAnAlreadyExistingOwner_shouldReturnUnprocessableEntity() {
        HttpEntity<CreateToggleAssignmentVO> request = new HttpEntity<>(
                CreateToggleAssignmentVOTestBuilder.aCreateToggleAssignmentVO()
                        .withToggleOwner(toggleAssignment1.getToggleOwner())
                        .withToggleValue(new Random().nextBoolean())
                        .build());

        ResponseEntity<String> response = restTemplate.exchange(
                createURL(""),
                HttpMethod.POST,
                request,
                String.class,
                toggle1.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(response.getBody()).contains("It is not possible to process this request");
    }

    @Test
    public void createToggleAssignment_withMissingOwnerFieldInRequest_shouldReturnBadRequest() {
        HttpEntity<CreateToggleAssignmentVO> request = new HttpEntity<>(
                CreateToggleAssignmentVOTestBuilder.aCreateToggleAssignmentVO()
                        .withToggleValue(new Random().nextBoolean())
                        .build());

        ResponseEntity<String> response = restTemplate.exchange(
                createURL(""),
                HttpMethod.POST,
                request,
                String.class,
                toggle1.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains("The parameter 'toggleOwner' is mandatory");
    }

    @Test
    public void updateToggleAssignment() {
        boolean originalToggleValue = toggleAssignment2.getToggleValue();

        HttpEntity<UpdateToggleAssignmentVO> request = new HttpEntity<>(
                UpdateToggleAssignmentVOTestBuilder.anUpdateToggleAssignmentVO()
                        .withToggleValue(!originalToggleValue)
                        .build());

        ResponseEntity<ToggleAssignmentVO> response = restTemplate.exchange(
                createURL("/{toggleAssignmentId}"),
                HttpMethod.PUT,
                request,
                ToggleAssignmentVO.class,
                toggle2.getId(),
                toggleAssignment2.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getToggleValue()).isEqualTo(!originalToggleValue).isNotEqualTo(originalToggleValue);
    }

    @Test
    public void deleteToggleAssignment() {
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                createURL("/{toggleAssignmentId}"),
                HttpMethod.DELETE,
                null,
                Void.class,
                toggle1.getId(),
                toggleAssignment3.getId());

        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> getResponse = restTemplate.exchange(
                createURL("/{toggleAssignmentId}"),
                HttpMethod.GET,
                null,
                String.class,
                toggle1.getId(),
                toggleAssignment3.getId());

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(getResponse.getBody()).contains("The association between toggle ["+toggle1.getId()+"] and toggle assignment ["+toggleAssignment3.getId()+"] is non existing");
    }

    private String createURL(String uri) {
        return "http://localhost:" + port + "/toggles/{id}/toggle-assignments" + uri;
    }
}