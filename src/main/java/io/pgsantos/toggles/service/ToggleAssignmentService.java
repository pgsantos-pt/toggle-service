package io.pgsantos.toggles.service;

import io.pgsantos.toggles.data.vo.CreateToggleAssignmentVO;
import io.pgsantos.toggles.data.vo.ToggleAssignmentVO;
import io.pgsantos.toggles.data.vo.UpdateToggleAssignmentVO;

import java.util.List;

public interface ToggleAssignmentService {
    List<ToggleAssignmentVO> getToggleAssignmentsByToggleId(long toggleId);
    ToggleAssignmentVO getToggleAssignment(long toggleId, long toggleAssignmentId);
    ToggleAssignmentVO createToggleAssignment(long toggleId, CreateToggleAssignmentVO createToggleAssignmentVO);
    ToggleAssignmentVO updateToggleAssignment(long toggleId, long toggleAssignmentId, UpdateToggleAssignmentVO updateToggleAssignmentVO);
    void deleteToggleAssignment(long toggleId, long toggleAssignmentId);
}