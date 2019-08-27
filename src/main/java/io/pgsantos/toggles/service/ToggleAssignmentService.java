package io.pgsantos.toggles.service;

import io.pgsantos.toggles.data.vo.CreateToggleAssignmentVO;
import io.pgsantos.toggles.data.vo.ToggleAssignmentVO;
import io.pgsantos.toggles.data.vo.UpdateToggleAssignmentVO;

import java.util.List;

public interface ToggleAssignmentService {
    List<ToggleAssignmentVO> searchToggleAssignments(String applicationCode, String toggleName);
    ToggleAssignmentVO createToggleAssignment(CreateToggleAssignmentVO createToggleAssignmentVO);
    ToggleAssignmentVO updateToggleAssignmentById(long id, UpdateToggleAssignmentVO updateToggleAssignmentVO);
    void deleteToggleAssignmentById(long id);
}