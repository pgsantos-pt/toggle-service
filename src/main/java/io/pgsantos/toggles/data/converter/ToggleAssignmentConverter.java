package io.pgsantos.toggles.data.converter;

import io.pgsantos.toggles.data.model.ToggleAssignment;
import io.pgsantos.toggles.data.vo.ToggleAssignmentVO;

import static io.pgsantos.toggles.data.vo.builder.ToggleAssignmentVOBuilder.aToggleAssignmentVO;

class ToggleAssignmentConverter {
    static ToggleAssignmentVO convertToVO(ToggleAssignment toggleAssignment){
        return aToggleAssignmentVO()
                .withId(toggleAssignment.getId())
                .withToggleOwner(toggleAssignment.getToggleOwner())
                .withToggleValue(toggleAssignment.getToggleValue())
                .build();
    }
}