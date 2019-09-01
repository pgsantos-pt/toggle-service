package io.pgsantos.toggles.service.impl;

import io.pgsantos.toggles.data.model.Toggle;
import io.pgsantos.toggles.data.model.builder.ToggleBuilder;
import io.pgsantos.toggles.data.repository.ToggleRepository;
import io.pgsantos.toggles.data.vo.ToggleRequestVO;
import io.pgsantos.toggles.data.vo.builder.ToggleRequestVOTestBuilder;
import io.pgsantos.toggles.data.vo.ToggleVO;
import io.pgsantos.toggles.data.vo.builder.ToggleVOBuilder;
import io.pgsantos.toggles.exception.ResourceNotFoundException;
import io.pgsantos.toggles.service.ToggleService;
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
import java.util.Optional;
import java.util.Random;

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
public class ToggleServiceImplIntegrationTest {
    @Autowired
    private ToggleService toggleService;

    @MockBean
    private ToggleRepository toggleRepository;

    @After
    public void tearDown() {
        verifyNoMoreInteractions(toggleRepository);
    }

    @Test
    public void findToggles() {
        String name = RandomStringUtils.random(10);

        ToggleVO expectedToggleVO = ToggleVOBuilder.aToggleVO()
                .withToggleId(new Random().nextLong())
                .withToggleName(name)
                .build();

        when(toggleRepository.findAll(any(Example.class)))
                .thenReturn(List.of(
                        ToggleBuilder.aToggle()
                                .withId(expectedToggleVO.getToggleId())
                                .withName(expectedToggleVO.getToggleName())
                                .build()));

        List<ToggleVO> toggleVOs = toggleService.findToggles(name);

        assertThat(toggleVOs.get(0)).isEqualTo(expectedToggleVO);

        verify(toggleRepository).findAll(
                Example.of(
                        ToggleBuilder.aToggle().withName(name).build(),
                        ExampleMatcher.matching().withIgnoreNullValues()));
    }

    @Test
    public void findToggles_whenNoTogglesMatchCriteria_shouldReturnEmptyList() {
        String name = RandomStringUtils.random(10);

        when(toggleRepository.findAll(any(Example.class))).thenReturn(Collections.emptyList());

        List<ToggleVO> toggleVOs = toggleService.findToggles(name);

        assertThat(toggleVOs).isEmpty();

        verify(toggleRepository).findAll(
                Example.of(
                        ToggleBuilder.aToggle().withName(name).build(),
                        ExampleMatcher.matching().withIgnoreNullValues()));
    }

    @Test
    public void getToggle() {
        long toggleId = new Random().nextLong();

        ToggleVO expectedToggleVO = ToggleVOBuilder.aToggleVO()
                .withToggleId(toggleId)
                .withToggleName(RandomStringUtils.random(10))
                .build();

        when(toggleRepository.findById(anyLong()))
                .thenReturn(
                        Optional.of(
                                ToggleBuilder.aToggle()
                                        .withId(expectedToggleVO.getToggleId())
                                        .withName(expectedToggleVO.getToggleName())
                                        .build()));

        ToggleVO toggleVO = toggleService.getToggle(toggleId);

        assertThat(toggleVO).isEqualTo(expectedToggleVO);

        verify(toggleRepository).findById(toggleId);
    }

    @Test
    public void getToggle_notFound_shouldThrowResourceNotFoundException() {
        long toggleId = new Random().nextLong();

        when(toggleRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = catchThrowableOfType(() -> toggleService.getToggle(toggleId), ResourceNotFoundException.class);

        assertThat(exception.getMessage()).isEqualTo("The requested toggle [" + toggleId + "] was not found");

        verify(toggleRepository).findById(toggleId);
    }

    @Test
    public void createToggle() {
        String toggleName = RandomStringUtils.random(10);

        ToggleVO expectedToggleVO = ToggleVOBuilder.aToggleVO()
                .withToggleId(new Random().nextLong())
                .withToggleName(toggleName)
                .build();

        when(toggleRepository.save(any(Toggle.class)))
                .thenReturn(
                        ToggleBuilder.aToggle()
                                .withId(expectedToggleVO.getToggleId())
                                .withName(expectedToggleVO.getToggleName())
                                .build());

        ToggleVO toggleVO = toggleService.createToggle(ToggleRequestVOTestBuilder.aToggleRequestVO().withName(toggleName).build());

        assertThat(toggleVO).isEqualTo(expectedToggleVO);

        verify(toggleRepository).save(ToggleBuilder.aToggle().withName(toggleName).build());
    }

    @Test
    public void updateToggle() {
        long toggleId = new Random().nextLong();
        String toggleName = RandomStringUtils.random(10);

        ToggleVO expectedToggleVO = ToggleVOBuilder.aToggleVO()
                .withToggleId(toggleId)
                .withToggleName(toggleName)
                .build();

        Toggle toggle = spy(Toggle.class);

        when(toggleRepository.findById(anyLong())).thenReturn(Optional.of(toggle));
        when(toggleRepository.save(any(Toggle.class)))
                .thenReturn(
                        ToggleBuilder.aToggle()
                                .withId(expectedToggleVO.getToggleId())
                                .withName(expectedToggleVO.getToggleName())
                                .build());

        ToggleVO toggleVO = toggleService.updateToggle(toggleId, ToggleRequestVOTestBuilder.aToggleRequestVO().withName(toggleName).build());

        assertThat(toggleVO).isEqualTo(expectedToggleVO);

        verify(toggleRepository).findById(toggleId);
        verify(toggle).setName(toggleName);
        verify(toggleRepository).save(toggle);
        verifyNoMoreInteractions(toggle);
    }

    @Test
    public void updateToggle_withNonExistingToggle_shouldThrowResourceNotFoundException() {
        long toggleId = new Random().nextLong();

        when(toggleRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = catchThrowableOfType(
                () -> toggleService.updateToggle(toggleId, any(ToggleRequestVO.class)),
                ResourceNotFoundException.class);

        assertThat(exception.getMessage()).isEqualTo("The requested toggle [" + toggleId + "] was not found");

        verify(toggleRepository).findById(toggleId);
    }

    @Test
    public void deleteToggle() {
        long toggleId = new Random().nextLong();

        doNothing().when(toggleRepository).deleteById(anyLong());

        toggleService.deleteToggle(toggleId);

        verify(toggleRepository).deleteById(toggleId);
    }
}