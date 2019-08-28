package io.pgsantos.toggles.rest;

import io.pgsantos.toggles.data.vo.CreateToggleAssignmentVO;
import io.pgsantos.toggles.data.vo.ToggleAssignmentVO;
import io.pgsantos.toggles.data.vo.UpdateToggleAssignmentVO;
import io.pgsantos.toggles.service.ToggleAssignmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(value = "/toggle-assignments", produces = MediaType.APPLICATION_JSON_VALUE)
public class ToggleAssignmentController {
    private ToggleAssignmentService toggleAssignmentService;

    public ToggleAssignmentController(ToggleAssignmentService toggleAssignmentService) {
        this.toggleAssignmentService = toggleAssignmentService;
    }

    @GetMapping
    public List<ToggleAssignmentVO> searchToggleAssignments(@RequestParam(required = false) String applicationCode, @RequestParam(required = false) String toggleName) {
        return toggleAssignmentService.searchToggleAssignments(applicationCode,toggleName);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ToggleAssignmentVO> createToggleAssignment(@Valid @RequestBody CreateToggleAssignmentVO createToggleAssignmentVO) throws URISyntaxException {
        ToggleAssignmentVO toggleAssignmentVO = toggleAssignmentService.createToggleAssignment(createToggleAssignmentVO);

        return ResponseEntity.created(new URI("/toggle-assignments/"+toggleAssignmentVO.getId())).body(toggleAssignmentVO);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ToggleAssignmentVO updateToggleAssignmentById(@PathVariable long id, @Valid @RequestBody UpdateToggleAssignmentVO updateToggleAssignmentVO) {
        return toggleAssignmentService.updateToggleAssignmentById(id, updateToggleAssignmentVO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteToggleAssignment(@PathVariable long id) {
        toggleAssignmentService.deleteToggleAssignmentById(id);
    }
}