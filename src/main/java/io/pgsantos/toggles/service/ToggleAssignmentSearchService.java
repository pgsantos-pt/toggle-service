package io.pgsantos.toggles.service;

import io.pgsantos.toggles.data.vo.ToggleAssignmentVO;

import java.util.List;

public interface ToggleAssignmentSearchService {
    List<ToggleAssignmentVO> searchToggleAssignments(String toggleName, String toggleOwner);
}