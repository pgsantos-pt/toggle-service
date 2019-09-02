package io.pgsantos.toggles.service.impl;

import io.pgsantos.toggles.data.converter.ToggleAssignmentConverter;
import io.pgsantos.toggles.data.converter.ToggleConverter;
import io.pgsantos.toggles.data.model.builder.ToggleAssignmentBuilder;
import io.pgsantos.toggles.data.model.builder.ToggleBuilder;
import io.pgsantos.toggles.data.repository.ToggleAssignmentRepository;
import io.pgsantos.toggles.data.vo.AssignedTogglesVO;
import io.pgsantos.toggles.data.vo.converter.AssignedTogglesVOConverter;
import io.pgsantos.toggles.service.ToggleAssignmentSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ToggleAssignmentSearchServiceImpl implements ToggleAssignmentSearchService {
    @Autowired
    private ToggleAssignmentRepository toggleAssignmentRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AssignedTogglesVO> searchToggleAssignments(String toggleName, String toggleOwner) {
        return toggleAssignmentRepository
                .findAll(
                        Example.of(
                                ToggleAssignmentBuilder.aToggleAssignment()
                                        .withToggleOwner(toggleOwner)
                                        .withToggle(
                                                ToggleBuilder.aToggle()
                                                        .withName(toggleName)
                                                        .build())
                                        .build(),
                                ExampleMatcher.matching().withIgnoreNullValues()))
                .stream()
                .map(toggleAssignment -> new AbstractMap.SimpleEntry<>(ToggleConverter.convertToVO(toggleAssignment.getToggle()), ToggleAssignmentConverter.convertToVO(toggleAssignment)))
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.toList())))
                .entrySet()
                .stream()
                .map(AssignedTogglesVOConverter::convertToAssignedTogglesVO)
                .collect(Collectors.toList());
    }
}