package io.pgsantos.toggles.data.vo.converter;

import io.pgsantos.toggles.data.model.Toggle;
import io.pgsantos.toggles.data.vo.ToggleVO;

import static io.pgsantos.toggles.data.model.builder.ToggleBuilder.aToggle;
import static io.pgsantos.toggles.data.vo.builder.ToggleVOBuilder.aToggleVO;

public class ToggleConverter {
    public static ToggleVO convertToVO(Toggle toggle) {
        return aToggleVO()
                .withId(toggle.getId())
                .withName(toggle.getName())
                .build();
    }

    public static Toggle convertToEntity(ToggleVO toggleVO) {
        return aToggle()
                .withId(toggleVO.getId())
                .withName(toggleVO.getName())
                .build();
    }
}