package io.pgsantos.toggles.service.impl;

import io.pgsantos.toggles.data.model.builder.ToggleAssignmentBuilder;
import io.pgsantos.toggles.data.model.builder.ToggleBuilder;
import io.pgsantos.toggles.data.repository.ToggleAssignmentRepository;
import io.pgsantos.toggles.service.ToggleAssignmentService;
import io.pgsantos.toggles.service.config.ServiceConfig;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceConfig.class)
public class ToggleAssignmentServiceImplIntegrationTest {
    @Autowired
    private ToggleAssignmentService toggleAssignmentService;

    @MockBean
    private ToggleAssignmentRepository toggleAssignmentRepository;

    @Test
    public void getToggleAssignmentsByToggleId() {
        Long toggleId = new Random().nextLong();
        String toggleName = RandomStringUtils.random(10);
        Long toggleAssignmentId = new Random().nextLong();
        String toggleOwner = RandomStringUtils.random(10);
        Boolean toggleValue = new Random().nextBoolean();

        when(toggleAssignmentRepository.findAllByToggle_Id(anyLong()))
                .thenReturn(
                        Stream.of(
                                ToggleAssignmentBuilder.aToggleAssignment()
                                        .withId(toggleAssignmentId)
                                        .withToggleOwner(toggleOwner)
                                        .withToggleValue(toggleValue)
                                        .withToggle(
                                                ToggleBuilder.aToggle()
                                                        .withId(toggleId)
                                                        .withName(toggleName)
                                                        .build())
                                        .build()));
    }
}