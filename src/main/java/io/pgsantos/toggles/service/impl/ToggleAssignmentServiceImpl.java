package io.pgsantos.toggles.service.impl;

import io.pgsantos.toggles.data.converter.ToggleAssignmentConverter;
import io.pgsantos.toggles.data.model.ToggleAssignment;
import io.pgsantos.toggles.data.model.builder.ToggleAssignmentBuilder;
import io.pgsantos.toggles.data.repository.ToggleAssignmentRepository;
import io.pgsantos.toggles.data.repository.ToggleRepository;
import io.pgsantos.toggles.data.vo.CreateToggleAssignmentVO;
import io.pgsantos.toggles.data.vo.ToggleAssignmentVO;
import io.pgsantos.toggles.data.vo.UpdateToggleAssignmentVO;
import io.pgsantos.toggles.exception.ResourceNotFoundException;
import io.pgsantos.toggles.service.ToggleAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ToggleAssignmentServiceImpl implements ToggleAssignmentService {
    @Autowired
    private ToggleRepository toggleRepository;

    @Autowired
    private ToggleAssignmentRepository toggleAssignmentRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ToggleAssignmentVO> getToggleAssignmentsByToggleId(long toggleId) {
        return toggleAssignmentRepository
                .findAllByToggle_Id(toggleId)
                .map(ToggleAssignmentConverter::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ToggleAssignmentVO getToggleAssignment(long toggleId, long toggleAssignmentId) {
        return ToggleAssignmentConverter.convertToVO(loadToggleAssignment(toggleId, toggleAssignmentId));
    }

    @Override
    @Transactional
    public ToggleAssignmentVO createToggleAssignment(long toggleId, CreateToggleAssignmentVO createToggleAssignmentVO) {
        return toggleRepository
                .findById(toggleId)
                .map(toggle ->
                        toggleAssignmentRepository.save(
                                ToggleAssignmentBuilder.aToggleAssignment()
                                        .withToggle(toggle)
                                        .withToggleOwner(createToggleAssignmentVO.getToggleOwner())
                                        .withToggleValue(createToggleAssignmentVO.getToggleValue())
                                        .build()))
                .map(ToggleAssignmentConverter::convertToVO)
                .orElseThrow(() -> new ResourceNotFoundException("The requested toggle ["+ toggleId +"] was not found"));
    }

    @Override
    @Transactional
    public ToggleAssignmentVO updateToggleAssignment(long toggleId, long toggleAssignmentId, UpdateToggleAssignmentVO updateToggleAssignmentVO) {
        ToggleAssignment toggleAssignment = loadToggleAssignment(toggleId, toggleAssignmentId);
        toggleAssignment.setToggleValue(updateToggleAssignmentVO.getToggleValue());
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