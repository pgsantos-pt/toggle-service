package io.pgsantos.toggles.rest;

import io.pgsantos.toggles.data.vo.ToggleAssignmentVO;
import io.pgsantos.toggles.data.vo.ToggleRequestVO;
import io.pgsantos.toggles.data.vo.ToggleVO;
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
import java.util.Set;

@RestController
@RequestMapping(value = "/toggles", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ToggleController {
    private ToggleService toggleService;

    public ToggleController(ToggleService toggleService) {
        this.toggleService = toggleService;
    }

    @GetMapping("/{id}")
    public ToggleVO getToggle(@PathVariable long id) {
        return toggleService.getToggleById(id);
    }

    @GetMapping
    public Set<ToggleVO> searchToggles(@RequestParam(required = false) String name) {
        return toggleService.searchToggles(name);
    }

    @PostMapping
    public ResponseEntity<ToggleVO> createToggle(@Valid @RequestBody ToggleRequestVO toggleRequestVO) throws URISyntaxException {
        ToggleVO toggleVO = toggleService.createToggle(toggleRequestVO);

        return ResponseEntity.created(new URI("/toggles/"+toggleVO.getId())).body(toggleVO);
    }

    @PutMapping("/{id}")
    public ToggleVO updateToggle(@PathVariable long id, @Valid @RequestBody ToggleRequestVO toggleRequestVO) {
        return toggleService.updateToggleById(id, toggleRequestVO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteToggle(@PathVariable long id) {
        toggleService.deleteToggleById(id);
    }

    @GetMapping("/{id}/toggle-assignments")
    public Set<ToggleAssignmentVO> getToggleAssignmentsByToggleId(@PathVariable long id) {
        return toggleService.getToggleAssignmentsByToggleId(id);
    }

    @GetMapping("/toggle-assignments")
    public Set<ToggleAssignmentVO> getToggleAssignmentsByToggleName(@RequestParam String name) {
        return toggleService.getToggleAssignmentsByToggleName(name);
    }
}