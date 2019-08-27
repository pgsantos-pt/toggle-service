package io.pgsantos.toggles.service;

import io.pgsantos.toggles.data.vo.ToggleAssignmentVO;
import io.pgsantos.toggles.data.vo.ToggleRequestVO;
import io.pgsantos.toggles.data.vo.ToggleVO;

import java.util.Set;

public interface ToggleService {
    Set<ToggleVO> searchToggles(String name);
    ToggleVO getToggleById(long id);
    ToggleVO createToggle(ToggleRequestVO toggleRequestVO);
    ToggleVO updateToggleById(long id, ToggleRequestVO toggleRequestVO);
    void deleteToggleById(long id);
    Set<ToggleAssignmentVO> getToggleAssignmentsByToggleId(long id);
    Set<ToggleAssignmentVO> getToggleAssignmentsByToggleName(String name);
}