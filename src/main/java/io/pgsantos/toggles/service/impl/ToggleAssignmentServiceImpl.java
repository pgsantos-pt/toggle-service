package io.pgsantos.toggles.service.impl;

import io.pgsantos.toggles.data.converter.ToggleAssignmentConverter;
import io.pgsantos.toggles.data.converter.ToggleConverter;
import io.pgsantos.toggles.data.model.ToggleAssignment;
import io.pgsantos.toggles.data.model.builder.ToggleAssignmentBuilder;
import io.pgsantos.toggles.data.repository.ToggleAssignmentRepository;
import io.pgsantos.toggles.data.repository.ToggleRepository;
import io.pgsantos.toggles.data.vo.AssignedTogglesVO;
import io.pgsantos.toggles.data.vo.ToggleAssignmentVO;
import io.pgsantos.toggles.data.vo.converter.AssignedTogglesVOConverter;
import io.pgsantos.toggles.data.vo.request.CreateToggleAssignmentRequestVO;
import io.pgsantos.toggles.data.vo.request.UpdateToggleAssignmentRequestVO;
import io.pgsantos.toggles.exception.ResourceNotFoundException;
import io.pgsantos.toggles.service.ToggleAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ToggleAssignmentServiceImpl implements ToggleAssignmentService {
    @Autowired
    private ToggleRepository toggleRepository;

    @Autowired
    private ToggleAssignmentRepository toggleAssignmentRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AssignedTogglesVO> getToggleAssignmentsByToggleId(long toggleId) {
        return toggleAssignmentRepository
                .findAllByToggle_Id(toggleId)
                .map(toggleAssignment -> new AbstractMap.SimpleEntry<>(ToggleConverter.convertToVO(toggleAssignment.getToggle()), ToggleAssignmentConverter.convertToVO(toggleAssignment)))
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.toList())))
                .entrySet()
                .stream()
                .map(AssignedTogglesVOConverter::convertToAssignedTogglesVO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ToggleAssignmentVO getToggleAssignment(long toggleId, long toggleAssignmentId) {
        return ToggleAssignmentConverter.convertToVO(loadToggleAssignment(toggleId, toggleAssignmentId));
    }

    @Override
    @Transactional
    public ToggleAssignmentVO createToggleAssignment(long toggleId, CreateToggleAssignmentRequestVO createToggleAssignmentRequestVO) {
        return toggleRepository
                .findById(toggleId)
                .map(toggle ->
                        toggleAssignmentRepository.save(
                                ToggleAssignmentBuilder.aToggleAssignment()
                                        .withToggle(toggle)
                                        .withToggleOwner(createToggleAssignmentRequestVO.getToggleOwner())
                                        .withToggleValue(createToggleAssignmentRequestVO.getToggleValue())
                                        .build()))
                .map(ToggleAssignmentConverter::convertToVO)
                .orElseThrow(() -> new ResourceNotFoundException("The requested toggle ["+ toggleId +"] was not found"));
    }

    @Override
    @Transactional
    public ToggleAssignmentVO updateToggleAssignment(long toggleId, long toggleAssignmentId, UpdateToggleAssignmentRequestVO updateToggleAssignmentRequestVO) {
        ToggleAssignment toggleAssignment = loadToggleAssignment(toggleId, toggleAssignmentId);
        toggleAssignment.setToggleValue(updateToggleAssignmentRequestVO.getToggleValue());
        return ToggleAssignmentConverter.convertToVO(toggleAssignmentRepository.save(toggleAssignment));
    }

    @Override
    @Transactional
    public void deleteToggleAssignment(long toggleId, long toggleAssignmentId) {
        toggleAssignmentRepository.delete(loadToggleAssignment(toggleId, toggleAssignmentId));
    }

    private ToggleAssignment loadToggleAssignment(long toggleId, long toggleAssignmentId) {
        return toggleAssignmentRepository
                .findByIdAndToggle_Id(toggleAssignmentId, toggleId)
                .orElseThrow(() -> new ResourceNotFoundException("The association between toggle ["+toggleId+"] and toggle assignment ["+toggleAssignmentId+"] is non existing"));
    }
}