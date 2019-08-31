package io.pgsantos.toggles.data.converter;

import io.pgsantos.toggles.data.model.ToggleAssignment;
import io.pgsantos.toggles.data.vo.ToggleAssignmentVO;

import static io.pgsantos.toggles.data.vo.builder.ToggleAssignmentVOBuilder.aToggleAssignmentVO;

public class ToggleAssignmentConverter {
    public static ToggleAssignmentVO convertToVO(ToggleAssignment toggleAssignment) {
        return aToggleAssignmentVO()
                .withToggleId(toggleAssignment.getToggle().getId())
                .withToggleName(toggleAssignment.getToggle().getName())
                .withToggleAssignmentId(toggleAssignment.getId())
                .withToggleOwner(toggleAssignment.getToggleOwner())
                .withToggleValue(toggleAssignment.getToggleValue())
                .build();
    }
}