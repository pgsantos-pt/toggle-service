package io.pgsantos.toggles.data.vo.converter;

import io.pgsantos.toggles.data.model.ToggleAssignment;
import io.pgsantos.toggles.data.vo.ToggleAssignmentVO;

import static io.pgsantos.toggles.data.vo.builder.ToggleAssignmentVOBuilder.aToggleAssignmentVO;

public class ToggleAssignmentConverter {
    public static ToggleAssignmentVO convertToVO(ToggleAssignment toggleAssignment){
        return aToggleAssignmentVO()
                .withId(toggleAssignment.getId())
                .withApplicationCode(toggleAssignment.getApplicationCode())
                .withToggle(ToggleConverter.convertToVO(toggleAssignment.getToggle()))
                .withToggleValue(toggleAssignment.isToggleValue())
                .build();
    }
}