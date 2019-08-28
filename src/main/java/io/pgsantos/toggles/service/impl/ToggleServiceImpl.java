package io.pgsantos.toggles.service.impl;

import io.pgsantos.toggles.data.model.Toggle;
import io.pgsantos.toggles.data.repository.ToggleRepository;
import io.pgsantos.toggles.data.vo.ToggleAssignmentVO;
import io.pgsantos.toggles.data.vo.ToggleRequestVO;
import io.pgsantos.toggles.data.vo.ToggleVO;
import io.pgsantos.toggles.data.vo.converter.ToggleAssignmentConverter;
import io.pgsantos.toggles.data.vo.converter.ToggleConverter;
import io.pgsantos.toggles.service.ToggleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ToggleServiceImpl implements ToggleService {
    private ToggleRepository toggleRepository;

    public ToggleServiceImpl(ToggleRepository toggleRepository) {
        this.toggleRepository = toggleRepository;
    }

    @Override
    public List<ToggleVO> searchToggles(String name) {
        if(StringUtils.isNotBlank(name)) {
            return List.of(ToggleConverter.convertToVO(getToggle(fetchName -> toggleRepository.findByName(fetchName), name)));
        }

        return toggleRepository.findAll()
                .stream()
                .map(ToggleConverter::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public ToggleVO getToggleById(long id) {
        return ToggleConverter.convertToVO(getToggle(fetchId -> toggleRepository.findById(fetchId), id));
    }

    @Override
    public ToggleVO createToggle(ToggleRequestVO toggleRequestVO) {
        return ToggleConverter.convertToVO(toggleRepository.save(new Toggle(toggleRequestVO.getName())));
    }

    @Override
    public ToggleVO updateToggleById(long id, ToggleRequestVO toggleRequestVO) {
        Toggle toggle = getToggle(fetchId -> toggleRepository.findById(fetchId),id);

        toggle.setName(toggleRequestVO.getName());

        return ToggleConverter.convertToVO(toggleRepository.save(toggle));
    }

    @Override
    public void deleteToggleById(long id) {
        toggleRepository.deleteById(id);
    }

    @Override
    public List<ToggleAssignmentVO> getToggleAssignmentsByToggleId(long id) {
        return getToggle(fetchId -> toggleRepository
                .findById(fetchId), id)
                .getToggleAssignments()
                .stream()
                .map(ToggleAssignmentConverter::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ToggleAssignmentVO> getToggleAssignmentsByToggleName(String name) {
        return getToggle(fetchName -> toggleRepository
                .findByName(fetchName), name)
                .getToggleAssignments()
                .stream()
                .map(ToggleAssignmentConverter::convertToVO)
                .collect(Collectors.toList());
    }

    private <T> Toggle getToggle(Function<T,Optional<Toggle>> fetchFunction, T fetchCriteria) {
        return fetchFunction
                .apply(fetchCriteria)
                .orElseThrow(() -> new EntityNotFoundException("The requested toggle ["+ fetchCriteria +"] was not found"));
    }
}