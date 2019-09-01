package io.pgsantos.toggles.service.impl;

import io.pgsantos.toggles.data.model.builder.ToggleAssignmentBuilder;
import io.pgsantos.toggles.data.model.builder.ToggleBuilder;
import io.pgsantos.toggles.data.repository.ToggleAssignmentRepository;
import io.pgsantos.toggles.data.vo.ToggleAssignmentVO;
import io.pgsantos.toggles.data.vo.builder.ToggleAssignmentVOBuilder;
import io.pgsantos.toggles.service.ToggleAssignmentSearchService;
import io.pgsantos.toggles.service.config.ServiceConfig;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceConfig.class)
public class ToggleAssignmentSearchServiceImplIntegrationTest {
    @Autowired
    private ToggleAssignmentSearchService toggleAssignmentSearchService;

    @MockBean
    private ToggleAssignmentRepository toggleAssignmentRepository;

    @After
    public void tearDown() {
        verifyNoMoreInteractions(toggleAssignmentRepository);
    }

    @Test
    public void searchToggleAssignments() {
        String toggleName = RandomStringUtils.random(10);
        String toggleOwner = RandomStringUtils.random(10);

        ToggleAssignmentVO expectedToggleAssignmentVO = ToggleAssignmentVOBuilder.aToggleAssignmentVO()
                .withToggleId(new Random().nextLong())
                .withToggleName(toggleName)
                .withToggleAssignmentId(new Random().nextLong())
                .withToggleOwner(toggleOwner)
                .withToggleValue(new Random().nextBoolean())
                .build();

        when(toggleAssignmentRepository.findAll(any(Example.class)))
                .thenReturn(
                        List.of(
                                ToggleAssignmentBuilder.aToggleAssignment()
                                        .withId(expectedToggleAssignmentVO.getToggleAssignmentId())
                                        .withToggleOwner(expectedToggleAssignmentVO.getToggleOwner())
                                        .withToggleValue(expectedToggleAssignmentVO.getToggleValue())
                                        .withToggle(ToggleBuilder.aToggle()
                                                .withId(expectedToggleAssignmentVO.getToggleId())
                                                .withName(expectedToggleAssignmentVO.getToggleName())
                                                .build())
                                        .build()));

        List<ToggleAssignmentVO> toggleAssignmentVOs = toggleAssignmentSearchService.searchToggleAssignments(toggleName, toggleOwner);

        assertThat(toggleAssignmentVOs.get(0)).isEqualTo(expectedToggleAssignmentVO);

        verify(toggleAssignmentRepository)
                .findAll(
                        Example.of(
                                ToggleAssignmentBuilder.aToggleAssignment()
                                        .withToggleOwner(toggleOwner)
                                        .withToggle(
                                                ToggleBuilder.aToggle()
                                                        .withName(toggleName)
                                                        .build())
                                        .build(),
                                ExampleMatcher.matching().withIgnoreNullValues()));
    }

    @Test
    public void searchToggleAssignments_whenNoToggleAssignmentsMatchCriteria_shouldReturnEmptyList() {
        String toggleName = RandomStringUtils.random(10);
        String toggleOwner = RandomStringUtils.random(10);

        when(toggleAssignmentRepository.findAll(any(Example.class))).thenReturn(Collections.emptyList());

        List<ToggleAssignmentVO> toggleAssignmentVOs = toggleAssignmentSearchService.searchToggleAssignments(toggleName, toggleOwner);

        assertThat(toggleAssignmentVOs).isEmpty();

        verify(toggleAssignmentRepository)
                .findAll(
                        Example.of(
                                ToggleAssignmentBuilder.aToggleAssignment()
                                        .withToggleOwner(toggleOwner)
                                        .withToggle(
                                                ToggleBuilder.aToggle()
                                                        .withName(toggleName)
                                                        .build())
                                        .build(),
                                ExampleMatcher.matching().withIgnoreNullValues()));
    }
}