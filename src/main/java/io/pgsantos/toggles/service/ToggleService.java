package io.pgsantos.toggles.service;

import io.pgsantos.toggles.data.vo.CreateToggleAssignmentVO;
import io.pgsantos.toggles.data.vo.ToggleRequestVO;
import io.pgsantos.toggles.data.vo.ToggleVO;
import io.pgsantos.toggles.data.vo.UpdateToggleAssignmentVO;

import java.util.List;

public interface ToggleService {
    ToggleVO getToggle(long id);
    ToggleVO createToggle(ToggleRequestVO toggleRequestVO);
    ToggleVO updateToggle(long id, ToggleRequestVO toggleRequestVO);
    void deleteToggle(long id);
    ToggleVO getToggleAssignment(long toggleId, long toggleAssignmentId);
    ToggleVO createToggleAssignment(long toggleId, CreateToggleAssignmentVO createToggleAssignmentVO);
    ToggleVO updateToggleAssignment(long toggleId, long toggleAssignmentId, UpdateToggleAssignmentVO updateToggleAssignmentVO);
    void deleteToggleAssignment(long toggleId, long toggleAssignmentId);
    List<ToggleVO> searchToggles(String toggleName, String toggleOwner, boolean hideToggleAssignments);
}