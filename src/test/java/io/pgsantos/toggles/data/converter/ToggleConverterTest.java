package io.pgsantos.toggles.data.converter;

import io.pgsantos.toggles.data.model.builder.ToggleBuilder;
import io.pgsantos.toggles.data.vo.ToggleVO;
import io.pgsantos.toggles.data.vo.builder.ToggleVOBuilder;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class ToggleConverterTest {
    @Test
    public void convertToVO() {
        ToggleVO expectedToggleVO = ToggleVOBuilder.aToggleVO()
                .withToggleId(new Random().nextLong())
                .withToggleName(RandomStringUtils.random(10))
                .build();

        ToggleVO toggleVO = ToggleConverter.convertToVO(
                ToggleBuilder.aToggle()
                        .withId(expectedToggleVO.getToggleId())
                        .withName(expectedToggleVO.getToggleName())
                        .build());

        assertThat(toggleVO).isEqualTo(expectedToggleVO);
    }

    @Test
    public void convertToVO_withNoData_shouldThrowNPE() {
        Throwable exception = catchThrowable(() -> ToggleConverter.convertToVO(ToggleBuilder.aToggle().build()));
        assertThat(exception).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void convertToVO_withMissingFields_shouldThrowNPE() {
        Throwable exception = catchThrowable(() -> ToggleConverter.convertToVO(ToggleBuilder.aToggle().withName(RandomStringUtils.random(10)).build()));
        assertThat(exception).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void convertToVO_withNoToggle_shouldThrowNPE() {
        Throwable exception = catchThrowable(() -> ToggleConverter.convertToVO(null));
        assertThat(exception).isInstanceOf(NullPointerException.class);
    }
}