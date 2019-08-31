package io.pgsantos.toggles.data.converter;

public class ToggleConverterTest {
    /*@Test
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
    }*/
}