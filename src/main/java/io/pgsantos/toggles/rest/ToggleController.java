package io.pgsantos.toggles.rest;

import io.pgsantos.toggles.data.vo.CreateToggleAssignmentVO;
import io.pgsantos.toggles.data.vo.ToggleRequestVO;
import io.pgsantos.toggles.data.vo.ToggleVO;
import io.pgsantos.toggles.data.vo.UpdateToggleAssignmentVO;
import io.pgsantos.toggles.service.ToggleService;
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
@RequestMapping(value = "/toggles", produces = MediaType.APPLICATION_JSON_VALUE)
public class ToggleController {
    private ToggleService toggleService;

    public ToggleController(ToggleService toggleService) {
        this.toggleService = toggleService;
    }

    @GetMapping("/{id}")
    public ToggleVO getToggle(@PathVariable long id) {
        return toggleService.getToggle(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ToggleVO> createToggle(@Valid @RequestBody ToggleRequestVO toggleRequestVO) throws URISyntaxException {
        ToggleVO toggleVO = toggleService.createToggle(toggleRequestVO);

        return ResponseEntity.created(new URI("/toggles/"+toggleVO.getId())).body(toggleVO);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ToggleVO updateToggle(@PathVariable long id, @Valid @RequestBody ToggleRequestVO toggleRequestVO) {
        return toggleService.updateToggle(id, toggleRequestVO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteToggle(@PathVariable long id) {
        toggleService.deleteToggle(id);
    }

    @GetMapping("/{id}/toggle-assignments/{toggleAssignmentId}")
    public ToggleVO getToggleAssignment(@PathVariable long id, @PathVariable long toggleAssignmentId) {
        return toggleService.getToggleAssignment(id, toggleAssignmentId);
    }

    @PostMapping(value = "/{id}/toggle-assignments")
    public ResponseEntity<ToggleVO> createToggleAssignment(@PathVariable long id, @Valid @RequestBody CreateToggleAssignmentVO createToggleAssignmentVO) throws URISyntaxException {
        ToggleVO toggleVO = toggleService.createToggleAssignment(id, createToggleAssignmentVO);
        return ResponseEntity.created(new URI("/toggles/"+toggleVO.getId()+"/toggle-assignments/"+toggleVO.getToggleAssignmentsVOs().get(0).getId())).body(toggleVO);
    }

    @PutMapping(value = "/{id}/toggle-assignments/{toggleAssignmentId}")
    public ToggleVO updateToggleAssignment(@PathVariable long id, @PathVariable long toggleAssignmentId, @RequestBody UpdateToggleAssignmentVO updateToggleAssignmentVO) {
        return toggleService.updateToggleAssignment(id, toggleAssignmentId, updateToggleAssignmentVO);
    }

    @DeleteMapping("/{id}/toggle-assignments/{toggleAssignmentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateToggleAssignment(@PathVariable long id, @PathVariable long toggleAssignmentId) {
        toggleService.deleteToggleAssignment(id, toggleAssignmentId);
    }

    @GetMapping
    public List<ToggleVO> searchToggles(
            @RequestParam(required = false) String toggleName,
            @RequestParam(required = false) String toggleOwner,
            @RequestParam(required = false) Boolean hideToggleAssignments) {
        return toggleService.searchToggles(toggleName, toggleOwner, hideToggleAssignments != null ? hideToggleAssignments : false);
    }
}