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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(value = "/toggles/{id}/toggle-assignments", produces = MediaType.APPLICATION_JSON_VALUE)
public class ToggleAssignmentController {
    private ToggleAssignmentService toggleAssignmentService;

    public ToggleAssignmentController(ToggleAssignmentService toggleAssignmentService) {
        this.toggleAssignmentService = toggleAssignmentService;
    }

    @GetMapping
    public List<ToggleAssignmentVO> getToggleAssignmentsByToggleId(@PathVariable long id) {
        return toggleAssignmentService.getToggleAssignmentsByToggleId(id);
    }

    @GetMapping("/{toggleAssignmentId}")
    public ToggleAssignmentVO getToggleAssignment(@PathVariable long id, @PathVariable long toggleAssignmentId) {
        return toggleAssignmentService.getToggleAssignment(id, toggleAssignmentId);
    }

    @PostMapping
    public ResponseEntity<ToggleAssignmentVO> createToggleAssignment(@PathVariable long id, @Valid @RequestBody CreateToggleAssignmentVO createToggleAssignmentVO) throws URISyntaxException {
        ToggleAssignmentVO toggleAssignmentVO = toggleAssignmentService.createToggleAssignment(id, createToggleAssignmentVO);
        return ResponseEntity.created(new URI("/toggles/"+id+"/toggle-assignments/"+toggleAssignmentVO.getToggleAssignmentId())).body(toggleAssignmentVO);
    }

    @PutMapping("/{toggleAssignmentId}")
    public ToggleAssignmentVO updateToggleAssignment(@PathVariable long id, @PathVariable long toggleAssignmentId, @RequestBody UpdateToggleAssignmentVO updateToggleAssignmentVO) {
        return toggleAssignmentService.updateToggleAssignment(id, toggleAssignmentId, updateToggleAssignmentVO);
    }

    @DeleteMapping("/{toggleAssignmentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteToggleAssignment(@PathVariable long id, @PathVariable long toggleAssignmentId) {
        toggleAssignmentService.deleteToggleAssignment(id, toggleAssignmentId);
    }
}