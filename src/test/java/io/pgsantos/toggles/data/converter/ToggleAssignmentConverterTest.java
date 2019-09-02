package io.pgsantos.toggles.data.converter;

import io.pgsantos.toggles.data.model.builder.ToggleAssignmentBuilder;
import io.pgsantos.toggles.data.vo.ToggleAssignmentVO;
import io.pgsantos.toggles.data.vo.builder.ToggleAssignmentVOBuilder;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class ToggleAssignmentConverterTest {
    @Test
    public void convertToVO() {
        ToggleAssignmentVO expectedToggleAssignmentVO = ToggleAssignmentVOBuilder.aToggleAssignmentVO()
                .withToggleAssignmentId(new Random().nextLong())
                .withToggleOwner(RandomStringUtils.random(10))
                .withToggleValue(new Random().nextBoolean())
                .build();

        ToggleAssignmentVO toggleAssignmentVO = ToggleAssignmentConverter.convertToVO(
                ToggleAssignmentBuilder.aToggleAssignment()
                        .withId(expectedToggleAssignmentVO.getToggleAssignmentId())
                        .withToggleOwner(expectedToggleAssignmentVO.getToggleOwner())
                        .withToggleValue(expectedToggleAssignmentVO.getToggleValue())
                        .build());

        assertThat(toggleAssignmentVO).isEqualTo(expectedToggleAssignmentVO);
    }

    @Test
    public void convertToVO_withNoData_shouldThrowNPE() {
        Throwable exception = catchThrowable(() -> ToggleAssignmentConverter.convertToVO(ToggleAssignmentBuilder.aToggleAssignment().build()));
        assertThat(exception).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void convertToVO_withMissingFields_shouldThrowNPE() {
        Throwable exception = catchThrowable(() -> ToggleAssignmentConverter.convertToVO(
                ToggleAssignmentBuilder.aToggleAssignment()
                        .withToggleOwner(RandomStringUtils.random(10))
                        .build()));

        assertThat(exception).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void convertToVO_withNoToggleAssignment_shouldThrowNPE() {
        Throwable exception = catchThrowable(() -> ToggleAssignmentConverter.convertToVO(null));
        assertThat(exception).isInstanceOf(NullPointerException.class);
    }
}