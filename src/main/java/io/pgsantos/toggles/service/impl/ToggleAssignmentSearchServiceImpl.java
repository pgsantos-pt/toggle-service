package io.pgsantos.toggles.service.impl;

import io.pgsantos.toggles.data.converter.ToggleAssignmentConverter;
import io.pgsantos.toggles.data.model.builder.ToggleAssignmentBuilder;
import io.pgsantos.toggles.data.model.builder.ToggleBuilder;
import io.pgsantos.toggles.data.repository.ToggleAssignmentRepository;
import io.pgsantos.toggles.data.vo.ToggleAssignmentVO;
import io.pgsantos.toggles.service.ToggleAssignmentSearchService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ToggleAssignmentSearchServiceImpl implements ToggleAssignmentSearchService {
    private ToggleAssignmentRepository toggleAssignmentRepository;

    public ToggleAssignmentSearchServiceImpl(ToggleAssignmentRepository toggleAssignmentRepository) {
        this.toggleAssignmentRepository = toggleAssignmentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ToggleAssignmentVO> searchToggleAssignments(String toggleName, String toggleOwner) {
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
                .map(ToggleAssignmentConverter::convertToVO)
                .collect(Collectors.toList());
    }
}