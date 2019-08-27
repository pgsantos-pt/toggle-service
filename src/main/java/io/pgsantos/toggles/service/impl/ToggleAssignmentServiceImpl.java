package io.pgsantos.toggles.service.impl;

import io.pgsantos.toggles.data.model.Toggle;
import io.pgsantos.toggles.data.model.ToggleAssignment;
import io.pgsantos.toggles.data.repository.ToggleAssignmentRepository;
import io.pgsantos.toggles.data.vo.CreateToggleAssignmentVO;
import io.pgsantos.toggles.data.vo.ToggleAssignmentVO;
import io.pgsantos.toggles.data.vo.UpdateToggleAssignmentVO;
import io.pgsantos.toggles.data.vo.converter.ToggleAssignmentConverter;
import io.pgsantos.toggles.data.vo.converter.ToggleConverter;
import io.pgsantos.toggles.service.ToggleAssignmentService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ToggleAssignmentServiceImpl implements ToggleAssignmentService {
    private ToggleAssignmentRepository toggleAssignmentRepository;

    public ToggleAssignmentServiceImpl(ToggleAssignmentRepository toggleAssignmentRepository) {
        this.toggleAssignmentRepository = toggleAssignmentRepository;
    }

    @Override
    public List<ToggleAssignmentVO> searchToggleAssignments(String applicationCode, String toggleName) {
        ToggleAssignment toggleAssignment = new ToggleAssignment();
        toggleAssignment.setApplicationCode(applicationCode);
        toggleAssignment.setToggle(new Toggle(toggleName));

        return toggleAssignmentRepository.findAll(Example.of(toggleAssignment))
                .stream()
                .map(ToggleAssignmentConverter::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public ToggleAssignmentVO createToggleAssignment(CreateToggleAssignmentVO createToggleAssignmentVO) {
        return ToggleAssignmentConverter.convertToVO(toggleAssignmentRepository.save(new ToggleAssignment(
                createToggleAssignmentVO.getApplicationCode(),
                ToggleConverter.convertToEntity(createToggleAssignmentVO.getToggleVO()),
                createToggleAssignmentVO.isToggleValue())));
    }

    @Override
    public ToggleAssignmentVO updateToggleAssignmentById(long id, UpdateToggleAssignmentVO updateToggleAssignmentVO) {
        ToggleAssignment toggleAssignment = toggleAssignmentRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("The requested toggle assignment was not found"));

        toggleAssignment.setToggleValue(updateToggleAssignmentVO.isToggleValue());

        return ToggleAssignmentConverter.convertToVO(toggleAssignmentRepository.save(toggleAssignment));
    }

    @Override
    public void deleteToggleAssignmentById(long id) {
        ToggleAssignment toggleAssignment = toggleAssignmentRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("The requested toggle assignment was not found"));

        toggleAssignmentRepository.delete(toggleAssignment);
    }
}