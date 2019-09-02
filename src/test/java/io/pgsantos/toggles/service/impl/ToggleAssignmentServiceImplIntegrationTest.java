package io.pgsantos.toggles.service.impl;

import io.pgsantos.toggles.data.model.Toggle;
import io.pgsantos.toggles.data.model.ToggleAssignment;
import io.pgsantos.toggles.data.model.builder.ToggleAssignmentBuilder;
import io.pgsantos.toggles.data.model.builder.ToggleBuilder;
import io.pgsantos.toggles.data.repository.ToggleAssignmentRepository;
import io.pgsantos.toggles.data.repository.ToggleRepository;
import io.pgsantos.toggles.data.vo.AssignedTogglesVO;
import io.pgsantos.toggles.data.vo.ToggleAssignmentVO;
import io.pgsantos.toggles.data.vo.builder.AssignedTogglesVOBuilder;
import io.pgsantos.toggles.data.vo.builder.CreateToggleAssignmentRequestVOTestBuilder;
import io.pgsantos.toggles.data.vo.builder.ToggleAssignmentVOBuilder;
import io.pgsantos.toggles.data.vo.builder.UpdateToggleAssignmentRequestVOTestBuilder;
import io.pgsantos.toggles.data.vo.request.CreateToggleAssignmentRequestVO;
import io.pgsantos.toggles.exception.ResourceNotFoundException;
import io.pgsantos.toggles.service.ToggleAssignmentService;
import io.pgsantos.toggles.service.config.ServiceConfig;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceConfig.class)
public class ToggleAssignmentServiceImplIntegrationTest {
    @Autowired
    private ToggleAssignmentService toggleAssignmentService;

    @MockBean
    private ToggleRepository toggleRepository;

    @MockBean
    private ToggleAssignmentRepository toggleAssignmentRepository;

    @After
    public void tearDown() {
        verifyNoMoreInteractions(toggleRepository, toggleAssignmentRepository);
    }

    @Test
    public void getToggleAssignmentsByToggleId() {
        long toggleId = new Random().nextLong();

        AssignedTogglesVO expectedAssignedTogglesVO = AssignedTogglesVOBuilder.anAssignedTogglesVO()
                .withToggleId(toggleId)
                .withToggleName(RandomStringUtils.random(10))
                .withToggleAssignments(
                        List.of(
                                ToggleAssignmentVOBuilder.aToggleAssignmentVO()
                                        .withToggleAssignmentId(new Random().nextLong())
                                        .withToggleOwner(RandomStringUtils.random(10))
                                        .withToggleValue(new Random().nextBoolean())
                                        .build()))
                .build();

        when(toggleAssignmentRepository.findAllByToggle_Id(anyLong()))
                .thenReturn(
                        Stream.of(
                                ToggleAssignmentBuilder.aToggleAssignment()
                                        .withId(expectedAssignedTogglesVO.getToggleAssignments().get(0).getToggleAssignmentId())
                                        .withToggleOwner(expectedAssignedTogglesVO.getToggleAssignments().get(0).getToggleOwner())
                                        .withToggleValue(expectedAssignedTogglesVO.getToggleAssignments().get(0).getToggleValue())
                                        .withToggle(
                                                ToggleBuilder.aToggle()
                                                        .withId(expectedAssignedTogglesVO.getToggleId())
                                                        .withName(expectedAssignedTogglesVO.getToggleName())
                                                        .build())
                                        .build()));

        List<AssignedTogglesVO> toggleAssignmentVOsByToggleId = toggleAssignmentService.getToggleAssignmentsByToggleId(toggleId);

        assertThat(toggleAssignmentVOsByToggleId.get(0)).isEqualTo(expectedAssignedTogglesVO);

        verify(toggleAssignmentRepository).findAllByToggle_Id(toggleId);
    }

    @Test
    public void getToggleAssignmentsByToggleId_withNonExistingToggleAssignments_shouldReturnEmptyList() {
        long toggleId = new Random().nextLong();

        when(toggleAssignmentRepository.findAllByToggle_Id(anyLong())).thenReturn(Stream.empty());

        List<AssignedTogglesVO> toggleAssignmentVOsByToggleId = toggleAssignmentService.getToggleAssignmentsByToggleId(toggleId);

        assertThat(toggleAssignmentVOsByToggleId).isEmpty();

        verify(toggleAssignmentRepository).findAllByToggle_Id(toggleId);
    }

    @Test
    public void getToggleAssignment() {
        long toggleId = new Random().nextLong();
        long toggleAssignmentId = new Random().nextLong();

        ToggleAssignmentVO expectedToggleAssignmentVO = ToggleAssignmentVOBuilder.aToggleAssignmentVO()
                .withToggleAssignmentId(toggleAssignmentId)
                .withToggleOwner(RandomStringUtils.random(10))
                .withToggleValue(new Random().nextBoolean())
                .build();

        when(toggleAssignmentRepository.findByIdAndToggle_Id(anyLong(), anyLong()))
                .thenReturn(
                        Optional.of(
                                ToggleAssignmentBuilder.aToggleAssignment()
                                        .withId(expectedToggleAssignmentVO.getToggleAssignmentId())
                                        .withToggleOwner(expectedToggleAssignmentVO.getToggleOwner())
                                        .withToggleValue(expectedToggleAssignmentVO.getToggleValue())
                                        .build()));

        ToggleAssignmentVO toggleAssignmentVO = toggleAssignmentService.getToggleAssignment(toggleId, toggleAssignmentId);

        assertThat(toggleAssignmentVO).isEqualTo(expectedToggleAssignmentVO);

        verify(toggleAssignmentRepository).findByIdAndToggle_Id(toggleAssignmentId, toggleId);
    }

    @Test
    public void getToggleAssignment_withNoMatchingCriteria_shouldThrowResourceNotFoundException() {
        long toggleId = new Random().nextLong();
        long toggleAssignmentId = new Random().nextLong();

        when(toggleAssignmentRepository.findByIdAndToggle_Id(anyLong(), anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = catchThrowableOfType(() -> toggleAssignmentService.getToggleAssignment(toggleId, toggleAssignmentId), ResourceNotFoundException.class);

        assertThat(exception.getMessage()).isEqualTo("The association between toggle ["+toggleId+"] and toggle assignment ["+toggleAssignmentId+"] is non existing");

        verify(toggleAssignmentRepository).findByIdAndToggle_Id(toggleAssignmentId, toggleId);
    }

    @Test
    public void createToggleAssignment() {
        long toggleId = new Random().nextLong();
        String toggleOwner = RandomStringUtils.random(10);
        boolean toggleValue = new Random().nextBoolean();

        ToggleAssignmentVO expectedToggleAssignmentVO = ToggleAssignmentVOBuilder.aToggleAssignmentVO()
                .withToggleAssignmentId(new Random().nextLong())
                .withToggleOwner(toggleOwner)
                .withToggleValue(toggleValue)
                .build();

        Toggle toggle = ToggleBuilder.aToggle()
                .withId(toggleId)
                .withName(RandomStringUtils.random(10))
                .build();

        when(toggleRepository.findById(anyLong())).thenReturn(Optional.of(toggle));

        when(toggleAssignmentRepository.save(any(ToggleAssignment.class)))
                .thenReturn(
                        ToggleAssignmentBuilder.aToggleAssignment()
                                .withId(expectedToggleAssignmentVO.getToggleAssignmentId())
                                .withToggleOwner(expectedToggleAssignmentVO.getToggleOwner())
                                .withToggleValue(expectedToggleAssignmentVO.getToggleValue())
                                .build());

        ToggleAssignmentVO toggleAssignmentVO = toggleAssignmentService.createToggleAssignment(
                toggleId,
                CreateToggleAssignmentRequestVOTestBuilder.aCreateToggleAssignmentRequestVO()
                        .withToggleOwner(toggleOwner)
                        .withToggleValue(toggleValue)
                        .build());

        assertThat(toggleAssignmentVO).isEqualTo(expectedToggleAssignmentVO);

        verify(toggleRepository).findById(toggleId);
        verify(toggleAssignmentRepository).save(
                ToggleAssignmentBuilder.aToggleAssignment()
                        .withToggleOwner(expectedToggleAssignmentVO.getToggleOwner())
                        .withToggleValue(expectedToggleAssignmentVO.getToggleValue())
                        .withToggle(toggle)
                        .build());
    }

    @Test
    public void createToggleAssignment_withNonExistingToggle_shouldThrowResourceNotFoundException() {
        long toggleId = new Random().nextLong();

        when(toggleRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = catchThrowableOfType(() ->
                toggleAssignmentService.createToggleAssignment(toggleId, any(CreateToggleAssignmentRequestVO.class)),
                ResourceNotFoundException.class);

        assertThat(exception.getMessage()).isEqualTo("The requested toggle ["+ toggleId +"] was not found");

        verify(toggleRepository).findById(toggleId);
    }

    @Test
    public void updateToggleAssignment() {
        long toggleId = new Random().nextLong();
        long toggleAssignmentId = new Random().nextLong();
        boolean toggleValue = new Random().nextBoolean();

        ToggleAssignmentVO expectedToggleAssignmentVO = ToggleAssignmentVOBuilder.aToggleAssignmentVO()
                .withToggleAssignmentId(toggleAssignmentId)
                .withToggleOwner(RandomStringUtils.random(10))
                .withToggleValue(toggleValue)
                .build();

        ToggleAssignment toggleAssignment = spy(ToggleAssignment.class);

        when(toggleAssignmentRepository.findByIdAndToggle_Id(anyLong(), anyLong())).thenReturn(Optional.of(toggleAssignment));
        when(toggleAssignmentRepository.save(any(ToggleAssignment.class)))
                .thenReturn(
                        ToggleAssignmentBuilder.aToggleAssignment()
                                .withId(expectedToggleAssignmentVO.getToggleAssignmentId())
                                .withToggleOwner(expectedToggleAssignmentVO.getToggleOwner())
                                .withToggleValue(toggleValue)
                                .build());

        ToggleAssignmentVO toggleAssignmentVO = toggleAssignmentService.updateToggleAssignment(
                toggleId,
                toggleAssignmentId,
                UpdateToggleAssignmentRequestVOTestBuilder.anUpdateToggleAssignmentRequestVO().withToggleValue(toggleValue).build());

        assertThat(toggleAssignmentVO).isEqualTo(expectedToggleAssignmentVO);

        verify(toggleAssignmentRepository).findByIdAndToggle_Id(toggleAssignmentId, toggleId);
        verify(toggleAssignment).setToggleValue(toggleValue);
        verify(toggleAssignmentRepository).save(toggleAssignment);
        verifyNoMoreInteractions(toggleAssignment);
    }

    @Test
    public void updateToggleAssignment_withNoMatchingCriteria_shouldThrowResourceNotFoundException() {
        long toggleId = new Random().nextLong();
        long toggleAssignmentId = new Random().nextLong();

        when(toggleAssignmentRepository.findByIdAndToggle_Id(anyLong(), anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = catchThrowableOfType(
                () -> toggleAssignmentService.updateToggleAssignment(toggleId, toggleAssignmentId, UpdateToggleAssignmentRequestVOTestBuilder.anUpdateToggleAssignmentRequestVO().build()),
                ResourceNotFoundException.class);

        assertThat(exception.getMessage()).isEqualTo("The association between toggle ["+toggleId+"] and toggle assignment ["+toggleAssignmentId+"] is non existing");

        verify(toggleAssignmentRepository).findByIdAndToggle_Id(toggleAssignmentId, toggleId);
    }

    @Test
    public void deleteToggleAssignment() {
        long toggleId = new Random().nextLong();
        long toggleAssignmentId = new Random().nextLong();

        ToggleAssignment toggleAssignment = ToggleAssignmentBuilder.aToggleAssignment()
                .withId(toggleAssignmentId)
                .withToggleOwner(RandomStringUtils.random(10))
                .withToggleValue(new Random().nextBoolean())
                .withToggle(
                        ToggleBuilder.aToggle()
                                .withId(toggleId)
                                .withName(RandomStringUtils.random(10))
                                .build())
                .build();

        when(toggleAssignmentRepository.findByIdAndToggle_Id(anyLong(), anyLong())).thenReturn(Optional.of(toggleAssignment));
        doNothing().when(toggleAssignmentRepository).delete(any(ToggleAssignment.class));

        toggleAssignmentService.deleteToggleAssignment(toggleId, toggleAssignmentId);

        verify(toggleAssignmentRepository).findByIdAndToggle_Id(toggleAssignmentId, toggleId);
        verify(toggleAssignmentRepository).delete(toggleAssignment);
    }

    @Test
    public void deleteToggleAssignment_withNoMatchingCriteria_shouldThrowResourceNotFoundException() {
        long toggleId = new Random().nextLong();
        long toggleAssignmentId = new Random().nextLong();

        when(toggleAssignmentRepository.findByIdAndToggle_Id(anyLong(), anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = catchThrowableOfType(() ->
                toggleAssignmentService.deleteToggleAssignment(toggleId, toggleAssignmentId),
                ResourceNotFoundException.class);

        assertThat(exception.getMessage()).isEqualTo("The association between toggle ["+toggleId+"] and toggle assignment ["+toggleAssignmentId+"] is non existing");

        verify(toggleAssignmentRepository).findByIdAndToggle_Id(toggleAssignmentId, toggleId);
    }
}