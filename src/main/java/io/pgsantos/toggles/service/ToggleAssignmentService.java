package io.pgsantos.toggles.service;

import io.pgsantos.toggles.data.vo.AssignedTogglesVO;
import io.pgsantos.toggles.data.vo.ToggleAssignmentVO;
import io.pgsantos.toggles.data.vo.request.CreateToggleAssignmentRequestVO;
import io.pgsantos.toggles.data.vo.request.UpdateToggleAssignmentRequestVO;

import java.util.List;

public interface ToggleAssignmentService {
    List<AssignedTogglesVO> getToggleAssignmentsByToggleId(long toggleId);
    ToggleAssignmentVO getToggleAssignment(long toggleId, long toggleAssignmentId);
    ToggleAssignmentVO createToggleAssignment(long toggleId, CreateToggleAssignmentRequestVO createToggleAssignmentRequestVO);
    ToggleAssignmentVO updateToggleAssignment(long toggleId, long toggleAssignmentId, UpdateToggleAssignmentRequestVO updateToggleAssignmentRequestVO);
    void deleteToggleAssignment(long toggleId, long toggleAssignmentId);
}