package io.pgsantos.toggles.service;

import io.pgsantos.toggles.data.vo.AssignedTogglesVO;

import java.util.List;

public interface ToggleAssignmentSearchService {
    List<AssignedTogglesVO> searchToggleAssignments(String toggleName, String toggleOwner);
}