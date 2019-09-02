package io.pgsantos.toggles.service.impl;

import io.pgsantos.toggles.data.converter.ToggleConverter;
import io.pgsantos.toggles.data.model.Toggle;
import io.pgsantos.toggles.data.model.builder.ToggleBuilder;
import io.pgsantos.toggles.data.repository.ToggleRepository;
import io.pgsantos.toggles.data.vo.ToggleVO;
import io.pgsantos.toggles.data.vo.request.ToggleRequestVO;
import io.pgsantos.toggles.exception.ResourceNotFoundException;
import io.pgsantos.toggles.service.ToggleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ToggleServiceImpl implements ToggleService {
    @Autowired
    private ToggleRepository toggleRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ToggleVO> findToggles(String name) {
        return toggleRepository
                .findAll(
                        Example.of(
                                ToggleBuilder.aToggle().withName(name).build(),
                                ExampleMatcher.matching().withIgnoreNullValues()))
                .stream()
                .map(ToggleConverter::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ToggleVO getToggle(long id) {
        return ToggleConverter.convertToVO(loadToggle(id));
    }

    @Override
    @Transactional
    public ToggleVO createToggle(ToggleRequestVO toggleRequestVO) {
        return ToggleConverter.convertToVO(toggleRepository.save(ToggleBuilder.aToggle().withName(toggleRequestVO.getName()).build()));
    }

    @Override
    @Transactional
    public ToggleVO updateToggle(long id, ToggleRequestVO toggleRequestVO) {
        Toggle toggle = loadToggle(id);

        toggle.setName(toggleRequestVO.getName());

        return ToggleConverter.convertToVO(toggleRepository.save(toggle));
    }

    @Override
    @Transactional
    public void deleteToggle(long id) {
        toggleRepository.deleteById(id);
    }

    private Toggle loadToggle(long id) {
        return toggleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("The requested toggle ["+ id +"] was not found"));
    }
}