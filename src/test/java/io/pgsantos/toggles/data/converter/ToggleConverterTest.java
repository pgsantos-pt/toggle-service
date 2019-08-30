package io.pgsantos.toggles.data.converter;

import io.pgsantos.toggles.data.vo.ToggleVO;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static io.pgsantos.toggles.data.model.builder.ToggleAssignmentBuilder.aToggleAssignment;
import static io.pgsantos.toggles.data.model.builder.ToggleBuilder.aToggle;
import static io.pgsantos.toggles.data.vo.builder.ToggleAssignmentVOBuilder.aToggleAssignmentVO;
import static io.pgsantos.toggles.data.vo.builder.ToggleVOBuilder.aToggleVO;
import static org.assertj.core.api.Assertions.assertThat;

public class ToggleConverterTest {
    @Test
    public void convertToVO() {
        ToggleVO expectedToggleVO =
                aToggleVO()
                        .withId(1L)
                        .withName("name")
                        .withToggleAssignmentsVOs(List.of(
                                aToggleAssignmentVO()
                                        .withId(1L).withToggleOwner("owner")
                                        .withToggleValue(true)
                                        .build()))
                        .build();

        ToggleVO toggleVO = ToggleConverter.convertToVO(
                aToggle()
                        .withId(1L)
                        .withName("name")
                        .withToggleAssignments(List.of(
                                aToggleAssignment()
                                        .withId(1L)
                                        .withToggleOwner("owner")
                                        .withToggleValue(true)
                                        .build()))
                        .build());

        assertThat(toggleVO).isEqualTo(expectedToggleVO);
    }

    @Test
    public void convertToVO_withNoToggleAssignments() {
        ToggleVO expectedToggleVO =
                aToggleVO()
                        .withId(1L)
                        .withName("name")
                        .withToggleAssignmentsVOs(Collections.emptyList())
                        .build();

        ToggleVO toggleVO = ToggleConverter.convertToVO(
                aToggle()
                        .withId(1L)
                        .withName("name")
                        .build());

        assertThat(toggleVO).isEqualTo(expectedToggleVO);
    }

    @Test
    public void convertToggleAssignmentToToggleVO() {
        ToggleVO expectedToggleVO =
                aToggleVO()
                        .withId(1L)
                        .withName("name")
                        .withToggleAssignmentsVOs(List.of(
                                aToggleAssignmentVO()
                                        .withId(1L).withToggleOwner("owner")
                                        .withToggleValue(true)
                                        .build()))
                        .build();

        ToggleVO toggleVO = ToggleConverter.convertToggleAssignmentToToggleVO(
                aToggleAssignment()
                        .withId(1L)
                        .withToggleOwner("owner")
                        .withToggleValue(true)
                        .withToggle(
                                aToggle()
                                        .withId(1L)
                                        .withName("name")
                                        .build())
                        .build());

        assertThat(toggleVO).isEqualTo(expectedToggleVO);
    }
}