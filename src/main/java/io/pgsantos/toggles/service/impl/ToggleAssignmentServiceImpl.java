package io.pgsantos.toggles.service.impl;

import io.pgsantos.toggles.data.converter.ToggleAssignmentConverter;
import io.pgsantos.toggles.data.model.ToggleAssignment;
import io.pgsantos.toggles.data.model.builder.ToggleAssignmentBuilder;
import io.pgsantos.toggles.data.repository.ToggleAssignmentRepository;
import io.pgsantos.toggles.data.repository.ToggleRepository;
import io.pgsantos.toggles.data.vo.CreateToggleAssignmentVO;
import io.pgsantos.toggles.data.vo.ToggleAssignmentVO;
import io.pgsantos.toggles.data.vo.UpdateToggleAssignmentVO;
import io.pgsantos.toggles.service.ToggleAssignmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ToggleAssignmentServiceImpl implements ToggleAssignmentService {
    private ToggleRepository toggleRepository;
    private ToggleAssignmentRepository toggleAssignmentRepository;

    public ToggleAssignmentServiceImpl(ToggleRepository toggleRepository, ToggleAssignmentRepository toggleAssignmentRepository) {
        this.toggleRepository = toggleRepository;
        this.toggleAssignmentRepository = toggleAssignmentRepository;
    }

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
                .orElseThrow(() -> new EntityNotFoundException("The requested toggle ["+ toggleId +"] was not found"));
    }

    @Override
    @Transactional
    public ToggleAssignmentVO updateToggleAssignment(long toggleId, long toggleAssignmentId, UpdateToggleAssignmentVO updateToggleAssignmentVO) {
        if(!toggleRepository.existsById(toggleId)) {
            throw new EntityNotFoundException("The requested toggle ["+ toggleId +"] was not found");
        }

        return toggleAssignmentRepository
                .findById(toggleAssignmentId)
                .map(toggleAssignment -> {
                    toggleAssignment.setToggleValue(updateToggleAssignmentVO.getToggleValue());
                    return toggleAssignmentRepository.save(toggleAssignment);
                })
                .map(ToggleAssignmentConverter::convertToVO)
                .orElseThrow(() -> new EntityNotFoundException("The requested toggle assignment ["+ toggleAssignmentId +"] was not found"));
    }

    @Override
    @Transactional
    public void deleteToggleAssignment(long toggleId, long toggleAssignmentId) {
        toggleAssignmentRepository.delete(loadToggleAssignment(toggleId, toggleAssignmentId));
    }

    private ToggleAssignment loadToggleAssignment(long toggleId, long toggleAssignmentId) {
        return toggleAssignmentRepository
                .findByIdAndToggle_Id(toggleAssignmentId, toggleId)
                .orElseThrow(() -> new EntityNotFoundException("The association between toggle ["+toggleId+"] and toggle assignment ["+toggleAssignmentId+"] is non existing"));
    }
}