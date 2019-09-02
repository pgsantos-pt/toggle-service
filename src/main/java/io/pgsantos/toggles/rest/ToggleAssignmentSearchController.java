package io.pgsantos.toggles.rest;

import io.pgsantos.toggles.data.vo.AssignedTogglesVO;
import io.pgsantos.toggles.service.ToggleAssignmentSearchService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ToggleAssignmentSearchController {
    private ToggleAssignmentSearchService toggleAssignmentSearchService;

    public ToggleAssignmentSearchController(ToggleAssignmentSearchService toggleAssignmentSearchService) {
        this.toggleAssignmentSearchService = toggleAssignmentSearchService;
    }

    @GetMapping(value = "/search-toggle-assignments", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AssignedTogglesVO> searchToggleAssignments(@RequestParam(required = false) String toggleName, @RequestParam(required = false) String toggleOwner) {
        return toggleAssignmentSearchService.searchToggleAssignments(toggleName, toggleOwner);
    }
}