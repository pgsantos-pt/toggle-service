package io.pgsantos.toggles.data.vo.converter;

import io.pgsantos.toggles.data.vo.AssignedTogglesVO;
import io.pgsantos.toggles.data.vo.ToggleAssignmentVO;
import io.pgsantos.toggles.data.vo.ToggleVO;
import io.pgsantos.toggles.data.vo.builder.AssignedTogglesVOBuilder;

import java.util.List;
import java.util.Map;

public class AssignedTogglesVOConverter {
    public static AssignedTogglesVO convertToAssignedTogglesVO(Map.Entry<ToggleVO, List<ToggleAssignmentVO>> entry) {
        return AssignedTogglesVOBuilder.anAssignedTogglesVO()
                .withToggleId(entry.getKey().getToggleId())
                .withToggleName(entry.getKey().getToggleName())
                .withToggleAssignments(entry.getValue())
                .build();
    }
}