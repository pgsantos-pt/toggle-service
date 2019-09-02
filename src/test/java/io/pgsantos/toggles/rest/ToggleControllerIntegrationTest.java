package io.pgsantos.toggles.rest;

import io.pgsantos.toggles.data.converter.ToggleConverter;
import io.pgsantos.toggles.data.model.Toggle;
import io.pgsantos.toggles.data.model.builder.ToggleBuilder;
import io.pgsantos.toggles.data.repository.ToggleRepository;
import io.pgsantos.toggles.data.vo.ToggleVO;
import io.pgsantos.toggles.data.vo.builder.ToggleRequestVOTestBuilder;
import io.pgsantos.toggles.data.vo.request.ToggleRequestVO;
import io.pgsantos.toggles.rest.exception.ApiError;
import io.pgsantos.toggles.rest.exception.builder.ApiErrorBuilder;
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
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class ToggleControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private ToggleRepository toggleRepository;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    private Toggle toggle1, toggle2;

    @Before
    public void setUp() {
        toggle1 = toggleRepository.save(ToggleBuilder.aToggle().withName(RandomStringUtils.random(10, true, true)).build());
        toggle2 = toggleRepository.save(ToggleBuilder.aToggle().withName(RandomStringUtils.random(10, true, true)).build());
    }

    @After
    public void tearDown() {
        toggleRepository.deleteAll();
    }

    @Test
    public void findToggles_withNoCriteria_shouldReturnAllToggles() {
        ResponseEntity<List<ToggleVO>> response = restTemplate.exchange(
                createURL(""),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .containsExactlyInAnyOrder(
                        ToggleConverter.convertToVO(toggle1),
                        ToggleConverter.convertToVO(toggle2));
    }

    @Test
    public void findToggles_withName_shouldReturnAllTogglesMatchingCriteria() {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(createURL(""))
                .queryParam("name", toggle1.getName());

        ResponseEntity<List<ToggleVO>> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsExactlyInAnyOrder(ToggleConverter.convertToVO(toggle1));
    }

    @Test
    public void findToggles_withName_whenNotMatchingCriteria_shouldReturnEmptyList()  {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(createURL(""))
                .queryParam("name", RandomStringUtils.random(5, true, true));

        ResponseEntity<List<ToggleVO>> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEmpty();
    }

    @Test
    public void getToggle() {
        ResponseEntity<ToggleVO> response = restTemplate.exchange(
                createURL("/{id}"),
                HttpMethod.GET,
                null,
                ToggleVO.class,
                toggle1.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .isEqualTo(ToggleConverter.convertToVO(toggle1));
    }

    @Test
    public void getToggle_withNonExistingId_shouldReturnNotFound() {
        long toggleId = new Random().nextLong();

        ResponseEntity<String> response = restTemplate.exchange(
                createURL("/{id}"),
                HttpMethod.GET,
                null,
                String.class,
                toggleId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).contains("The requested toggle [" + toggleId + "] was not found");
    }

    @Test
    public void createToggle() {
        String toggleName = RandomStringUtils.random(10, true, true);

        HttpEntity<ToggleRequestVO> request = new HttpEntity<>(
                ToggleRequestVOTestBuilder.aToggleRequestVO()
                        .withName(toggleName)
                        .build());

        ResponseEntity<ToggleVO> response = restTemplate.exchange(
                createURL(""),
                HttpMethod.POST,
                request,
                ToggleVO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getToggleName()).isEqualTo(toggleName);
        assertThat(response.getBody().getToggleId()).isNotNull();
    }

    @Test
    public void createToggle_withAlreadyExistingName_shouldReturnUnprocessableEntity() {
        ApiError expectedApiError = ApiErrorBuilder.anApiError()
                .withStatus(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .withError(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase())
                .withMessage("It is not possible to process this request")
                .build();

        HttpEntity<ToggleRequestVO> request = new HttpEntity<>(
                ToggleRequestVOTestBuilder.aToggleRequestVO()
                        .withName(toggle1.getName())
                        .build());

        ResponseEntity<ApiError> response = restTemplate.exchange(
                createURL(""),
                HttpMethod.POST,
                request,
                ApiError.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(response.getBody()).isEqualToIgnoringGivenFields(expectedApiError, "timestamp");
        assertThat(response.getBody().getTimestamp()).isNotNull();
    }

    @Test
    public void createToggle_withMissingNameFieldInRequest_shouldReturnBadRequest() {
        ApiError expectedApiError = ApiErrorBuilder.anApiError()
                .withStatus(HttpStatus.BAD_REQUEST.value())
                .withError(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .withMessage("The field 'name' is mandatory")
                .build();

        HttpEntity<ToggleRequestVO> request = new HttpEntity<>(ToggleRequestVOTestBuilder.aToggleRequestVO().build());

        ResponseEntity<ApiError> response = restTemplate.exchange(
                createURL(""),
                HttpMethod.POST,
                request,
                ApiError.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualToIgnoringGivenFields(expectedApiError, "timestamp");
        assertThat(response.getBody().getTimestamp()).isNotNull();
    }

    @Test
    public void updateToggle() {
        String oldToggleName = toggle1.getName();
        String newToggleName = RandomStringUtils.random(10, true, true);

        HttpEntity<ToggleRequestVO> request = new HttpEntity<>(
                ToggleRequestVOTestBuilder.aToggleRequestVO()
                        .withName(newToggleName)
                        .build());

        ResponseEntity<ToggleVO> response = restTemplate.exchange(
                createURL("/{id}"),
                HttpMethod.PUT,
                request,
                ToggleVO.class,
                toggle1.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getToggleName())
                .isEqualTo(newToggleName)
                .isNotEqualTo(oldToggleName);
    }

    @Test
    public void updateToggle_withAlreadyExistingName_shouldReturnUnprocessableEntity() {
        ApiError expectedApiError = ApiErrorBuilder.anApiError()
                .withStatus(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .withError(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase())
                .withMessage("It is not possible to process this request")
                .build();

        HttpEntity<ToggleRequestVO> request = new HttpEntity<>(
                ToggleRequestVOTestBuilder.aToggleRequestVO()
                        .withName(toggle2.getName())
                        .build());

        ResponseEntity<ApiError> response = restTemplate.exchange(
                createURL("/{id}"),
                HttpMethod.PUT,
                request,
                ApiError.class,
                toggle1.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(response.getBody()).isEqualToIgnoringGivenFields(expectedApiError, "timestamp");
        assertThat(response.getBody().getTimestamp()).isNotNull();
    }

    @Test
    public void updateToggle_withMissingNameFieldInRequest_shouldReturnBadRequest(){
        ApiError expectedApiError = ApiErrorBuilder.anApiError()
                .withStatus(HttpStatus.BAD_REQUEST.value())
                .withError(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .withMessage("The field 'name' is mandatory")
                .build();

        HttpEntity<ToggleRequestVO> request = new HttpEntity<>(ToggleRequestVOTestBuilder.aToggleRequestVO().build());

        ResponseEntity<ApiError> response = restTemplate.exchange(
                createURL("/{id}"),
                HttpMethod.PUT,
                request,
                ApiError.class,
                toggle1.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualToIgnoringGivenFields(expectedApiError, "timestamp");
        assertThat(response.getBody().getTimestamp()).isNotNull();
    }

    @Test
    public void deleteToggle_shouldReturnNoContent_andRerunningRequest_shouldReturnNotFound() {
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                createURL("/{id}"),
                HttpMethod.DELETE,
                null,
                Void.class,
                toggle1.getId());

        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ApiError expectedApiError = ApiErrorBuilder.anApiError()
                .withStatus(HttpStatus.NOT_FOUND.value())
                .withError(HttpStatus.NOT_FOUND.getReasonPhrase())
                .withMessage("The request produces an empty result")
                .build();

        ResponseEntity<ApiError> response = restTemplate.exchange(
                createURL("/{id}"),
                HttpMethod.DELETE,
                null,
                ApiError.class,
                new Random().nextLong());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualToIgnoringGivenFields(expectedApiError, "timestamp");
        assertThat(response.getBody().getTimestamp()).isNotNull();
    }

    private String createURL(String uri) {
        return "http://localhost:" + port + "/toggles" + uri;
    }
}