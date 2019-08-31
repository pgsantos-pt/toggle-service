package io.pgsantos.toggles.data.converter;

import io.pgsantos.toggles.data.model.Toggle;
import io.pgsantos.toggles.data.vo.ToggleVO;

import static io.pgsantos.toggles.data.vo.builder.ToggleVOBuilder.aToggleVO;

public class ToggleConverter {
    public static ToggleVO convertToVO(Toggle toggle) {
        return aToggleVO()
                .withToggleId(toggle.getId())
                .withToggleName(toggle.getName())
                .build();
    }
}