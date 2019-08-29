package io.pgsantos.toggles.data.converter;

import io.pgsantos.toggles.data.model.Toggle;
import io.pgsantos.toggles.data.model.ToggleAssignment;
import io.pgsantos.toggles.data.vo.ToggleVO;
import io.pgsantos.toggles.data.vo.builder.ToggleVOBuilder;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static io.pgsantos.toggles.data.vo.builder.ToggleVOBuilder.aToggleVO;

public class ToggleConverter {
    public static ToggleVO convertToVO(Toggle toggle) {
        return aToggleVO()
                .withId(toggle.getId())
                .withName(toggle.getName())
                .withToggleAssignmentsVOs(toggle.getToggleAssignments() == null ?
                        Collections.emptyList() : toggle.getToggleAssignments()
                        .stream()
                        .map(ToggleAssignmentConverter::convertToVO)
                        .collect(Collectors.toList()))
                .build();
    }

    public static ToggleVO convertToggleAssignmentToToggleVO(ToggleAssignment toggleAssignment) {
        return ToggleVOBuilder.aToggleVO()
                .withId(toggleAssignment.getToggle().getId())
                .withName(toggleAssignment.getToggle().getName())
                .withToggleAssignmentsVOs(List.of(ToggleAssignmentConverter.convertToVO(toggleAssignment)))
                .build();
    }
}