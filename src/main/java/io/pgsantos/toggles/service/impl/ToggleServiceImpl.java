package io.pgsantos.toggles.service.impl;

import io.pgsantos.toggles.data.converter.ToggleConverter;
import io.pgsantos.toggles.data.model.Toggle;
import io.pgsantos.toggles.data.model.ToggleAssignment;
import io.pgsantos.toggles.data.repository.ToggleAssignmentRepository;
import io.pgsantos.toggles.data.repository.ToggleRepository;
import io.pgsantos.toggles.data.vo.CreateToggleAssignmentVO;
import io.pgsantos.toggles.data.vo.ToggleRequestVO;
import io.pgsantos.toggles.data.vo.ToggleVO;
import io.pgsantos.toggles.data.vo.UpdateToggleAssignmentVO;
import io.pgsantos.toggles.service.ToggleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.pgsantos.toggles.data.model.builder.ToggleAssignmentBuilder.aToggleAssignment;
import static io.pgsantos.toggles.data.model.builder.ToggleBuilder.aToggle;

@Service
public class ToggleServiceImpl implements ToggleService {
    private ToggleRepository toggleRepository;
    private ToggleAssignmentRepository toggleAssignmentRepository;

    public ToggleServiceImpl(ToggleRepository toggleRepository, ToggleAssignmentRepository toggleAssignmentRepository) {
        this.toggleRepository = toggleRepository;
        this.toggleAssignmentRepository = toggleAssignmentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ToggleVO getToggle(long id) {
        return ToggleConverter.convertToVO(getToggle(fetchId -> toggleRepository.findById(fetchId), id));
    }

    @Override
    @Transactional
    public ToggleVO createToggle(ToggleRequestVO toggleRequestVO) {
        return ToggleConverter.convertToVO(toggleRepository.save(aToggle().withName(toggleRequestVO.getName()).build()));
    }

    @Override
    @Transactional
    public ToggleVO updateToggle(long id, ToggleRequestVO toggleRequestVO) {
        Toggle toggle = getToggle(fetchId -> toggleRepository.findById(fetchId),id);

        toggle.setName(toggleRequestVO.getName());

        return ToggleConverter.convertToVO(toggleRepository.save(toggle));
    }

    @Override
    @Transactional
    public void deleteToggle(long id) {
        toggleRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ToggleVO getToggleAssignment(long toggleId, long toggleAssignmentId) {
        return ToggleConverter.convertToggleAssignmentToToggleVO(loadToggleAssignment(toggleId, toggleAssignmentId));
    }

    @Override
    @Transactional
    public ToggleVO createToggleAssignment(long toggleId, CreateToggleAssignmentVO createToggleAssignmentVO) {
        ToggleAssignment toggleAssignment = toggleAssignmentRepository.save(
                aToggleAssignment()
                        .withToggle(getToggle(fetchId -> toggleRepository.findById(fetchId), toggleId))
                        .withToggleOwner(createToggleAssignmentVO.getToggleOwner())
                        .withToggleValue(createToggleAssignmentVO.getToggleValue())
                        .build());

        return ToggleConverter.convertToggleAssignmentToToggleVO(toggleAssignment);
    }

    @Override
    @Transactional
    public ToggleVO updateToggleAssignment(long toggleId, long toggleAssignmentId, UpdateToggleAssignmentVO updateToggleAssignmentVO) {
        ToggleAssignment toggleAssignment = loadToggleAssignment(toggleId, toggleAssignmentId);

        toggleAssignment.setToggleValue(updateToggleAssignmentVO.isToggleValue());

        return ToggleConverter.convertToggleAssignmentToToggleVO(toggleAssignmentRepository.save(toggleAssignment));
    }

    @Override
    @Transactional
    public void deleteToggleAssignment(long toggleId, long toggleAssignmentId) {
        toggleAssignmentRepository.delete(loadToggleAssignment(toggleId, toggleAssignmentId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ToggleVO> searchToggles(String toggleName, String toggleOwner, boolean hideToggleAssignments) {
        List<ToggleVO> toggleVOs;

        if(StringUtils.isBlank(toggleName) && StringUtils.isBlank(toggleOwner)) {
            toggleVOs = toggleRepository.findAllAndStream().map(ToggleConverter::convertToVO).collect(Collectors.toList());
        }
        else if(StringUtils.isNotBlank(toggleName) && StringUtils.isBlank(toggleOwner)) {
            toggleVOs = List.of(ToggleConverter.convertToVO(getToggle(fetchName -> toggleRepository.findByName(fetchName), toggleName)));
        }
        else {
            ToggleAssignment toggleAssignment = aToggleAssignment()
                    .withToggleOwner(toggleOwner)
                    .withToggle(aToggle().withName(toggleName).build())
                    .build();

            toggleVOs = toggleAssignmentRepository.findAll(Example.of(toggleAssignment, ExampleMatcher.matching().withIgnoreNullValues()))
                    .stream()
                    .map(ToggleConverter::convertToggleAssignmentToToggleVO)
                    .collect(Collectors.toList());
        }

        if(hideToggleAssignments) {
            toggleVOs.forEach(toggleVO -> toggleVO.setToggleAssignmentsVOs(Collections.emptyList()));
        }

        return toggleVOs;
    }

    private <T> Toggle getToggle(Function<T, Optional<Toggle>> fetchFunction, T fetchCriteria) {
        return fetchFunction
                .apply(fetchCriteria)
                .orElseThrow(() -> new EntityNotFoundException("The requested toggle ["+ fetchCriteria +"] was not found"));
    }

    private ToggleAssignment loadToggleAssignment(long toggleId, long toggleAssignmentId) {
        return toggleAssignmentRepository
                .findByIdAndToggle_Id(toggleAssignmentId, toggleId)
                .orElseThrow(() -> new EntityNotFoundException("The association between toggle ["+toggleId+"] and toggle assignment ["+toggleAssignmentId+"] is non existing"));
    }
}