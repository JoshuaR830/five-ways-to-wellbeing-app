package com.joshuarichardson.fivewaystowellbeing;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class WellbeingHelperWayToWellbeingFormatTests {

    @Test
    public void stringsFromWaysToWellbeing_ShouldReturnReadableString() {
        String wellbeingType = WellbeingHelper.getStringFromWayToWellbeing(WaysToWellbeing.UNASSIGNED);
        assertThat(wellbeingType).isEqualTo("None");

        wellbeingType = WellbeingHelper.getStringFromWayToWellbeing(WaysToWellbeing.CONNECT);
        assertThat(wellbeingType).isEqualTo("Connect");

        wellbeingType = WellbeingHelper.getStringFromWayToWellbeing(WaysToWellbeing.BE_ACTIVE);
        assertThat(wellbeingType).isEqualTo("Be active");

        wellbeingType = WellbeingHelper.getStringFromWayToWellbeing(WaysToWellbeing.KEEP_LEARNING);
        assertThat(wellbeingType).isEqualTo("Keep learning");

        wellbeingType = WellbeingHelper.getStringFromWayToWellbeing(WaysToWellbeing.GIVE);
        assertThat(wellbeingType).isEqualTo("Give");

        wellbeingType = WellbeingHelper.getStringFromWayToWellbeing(WaysToWellbeing.TAKE_NOTICE);
        assertThat(wellbeingType).isEqualTo("Take notice");
    }

    @Test
    public void wayToWellbeingFromActivityType_ShouldReturnCorrectDefaults() {
        WaysToWellbeing wayToWellbeing = WellbeingHelper.getDefaultWayToWellbeingFromActivityType("App");
        assertThat(wayToWellbeing).isEqualTo(WaysToWellbeing.UNASSIGNED);

        wayToWellbeing = WellbeingHelper.getDefaultWayToWellbeingFromActivityType("Hobby");
        assertThat(wayToWellbeing).isEqualTo(WaysToWellbeing.KEEP_LEARNING);

        wayToWellbeing = WellbeingHelper.getDefaultWayToWellbeingFromActivityType("Learning");
        assertThat(wayToWellbeing).isEqualTo(WaysToWellbeing.KEEP_LEARNING);

        wayToWellbeing = WellbeingHelper.getDefaultWayToWellbeingFromActivityType("Pet");
        assertThat(wayToWellbeing).isEqualTo(WaysToWellbeing.CONNECT);

        wayToWellbeing = WellbeingHelper.getDefaultWayToWellbeingFromActivityType("Work");
        assertThat(wayToWellbeing).isEqualTo(WaysToWellbeing.KEEP_LEARNING);

        wayToWellbeing = WellbeingHelper.getDefaultWayToWellbeingFromActivityType("Sport");
        assertThat(wayToWellbeing).isEqualTo(WaysToWellbeing.BE_ACTIVE);

        wayToWellbeing = WellbeingHelper.getDefaultWayToWellbeingFromActivityType("Exercise");
        assertThat(wayToWellbeing).isEqualTo(WaysToWellbeing.BE_ACTIVE);

        wayToWellbeing = WellbeingHelper.getDefaultWayToWellbeingFromActivityType("Chores");
        assertThat(wayToWellbeing).isEqualTo(WaysToWellbeing.GIVE);

        wayToWellbeing = WellbeingHelper.getDefaultWayToWellbeingFromActivityType("Cooking");
        assertThat(wayToWellbeing).isEqualTo(WaysToWellbeing.GIVE);

        wayToWellbeing = WellbeingHelper.getDefaultWayToWellbeingFromActivityType("Relaxation");
        assertThat(wayToWellbeing).isEqualTo(WaysToWellbeing.TAKE_NOTICE);

        wayToWellbeing = WellbeingHelper.getDefaultWayToWellbeingFromActivityType("People");
        assertThat(wayToWellbeing).isEqualTo(WaysToWellbeing.CONNECT);

        wayToWellbeing = WellbeingHelper.getDefaultWayToWellbeingFromActivityType("Journaling");
        assertThat(wayToWellbeing).isEqualTo(WaysToWellbeing.TAKE_NOTICE);

        wayToWellbeing = WellbeingHelper.getDefaultWayToWellbeingFromActivityType("Faith");
        assertThat(wayToWellbeing).isEqualTo(WaysToWellbeing.TAKE_NOTICE);
    }
    
    @Test
    public void waysToWellbeingFromString_ShouldReturnCorrectType() {
        WaysToWellbeing wellbeingType = WellbeingHelper.getWayToWellbeingFromString("Unassigned");
        assertThat(wellbeingType).isEqualTo(WaysToWellbeing.UNASSIGNED);
        wellbeingType = WellbeingHelper.getWayToWellbeingFromString("None");
        assertThat(wellbeingType).isEqualTo(WaysToWellbeing.UNASSIGNED);
        wellbeingType = WellbeingHelper.getWayToWellbeingFromString("UNASSIGNED");
        assertThat(wellbeingType).isEqualTo(WaysToWellbeing.UNASSIGNED);

        wellbeingType = WellbeingHelper.getWayToWellbeingFromString("Connect");
        assertThat(wellbeingType).isEqualTo(WaysToWellbeing.CONNECT);
        wellbeingType = WellbeingHelper.getWayToWellbeingFromString("CONNECT");
        assertThat(wellbeingType).isEqualTo(WaysToWellbeing.CONNECT);

        wellbeingType = WellbeingHelper.getWayToWellbeingFromString("Be active");
        assertThat(wellbeingType).isEqualTo(WaysToWellbeing.BE_ACTIVE);
        wellbeingType = WellbeingHelper.getWayToWellbeingFromString("BE ACTIVE");
        assertThat(wellbeingType).isEqualTo(WaysToWellbeing.BE_ACTIVE);

        wellbeingType = WellbeingHelper.getWayToWellbeingFromString("Keep learning");
        assertThat(wellbeingType).isEqualTo(WaysToWellbeing.KEEP_LEARNING);
        wellbeingType = WellbeingHelper.getWayToWellbeingFromString("KEEP LEARNING");
        assertThat(wellbeingType).isEqualTo(WaysToWellbeing.KEEP_LEARNING);

        wellbeingType = WellbeingHelper.getWayToWellbeingFromString("Give");
        assertThat(wellbeingType).isEqualTo(WaysToWellbeing.GIVE);
        wellbeingType = WellbeingHelper.getWayToWellbeingFromString("GIVE");
        assertThat(wellbeingType).isEqualTo(WaysToWellbeing.GIVE);

        wellbeingType = WellbeingHelper.getWayToWellbeingFromString("Take notice");
        assertThat(wellbeingType).isEqualTo(WaysToWellbeing.TAKE_NOTICE);
        wellbeingType = WellbeingHelper.getWayToWellbeingFromString("TAKE NOTICE");
        assertThat(wellbeingType).isEqualTo(WaysToWellbeing.TAKE_NOTICE);
    }

    @Test
    public void waysToWellbeingStringResource_ShouldReturnCorrectString() {
        int stringResourceId = WellbeingHelper.getWellbeingStringResource(WaysToWellbeing.CONNECT);
        assertThat(stringResourceId).isEqualTo(R.string.wellbeing_connect);
        stringResourceId = WellbeingHelper.getWellbeingStringResource(WaysToWellbeing.BE_ACTIVE);
        assertThat(stringResourceId).isEqualTo(R.string.wellbeing_be_active);
        stringResourceId = WellbeingHelper.getWellbeingStringResource(WaysToWellbeing.KEEP_LEARNING);
        assertThat(stringResourceId).isEqualTo(R.string.wellbeing_keep_learning);
        stringResourceId = WellbeingHelper.getWellbeingStringResource(WaysToWellbeing.TAKE_NOTICE);
        assertThat(stringResourceId).isEqualTo(R.string.wellbeing_take_notice);
        stringResourceId = WellbeingHelper.getWellbeingStringResource(WaysToWellbeing.GIVE);
        assertThat(stringResourceId).isEqualTo(R.string.wellbeing_give);
    }
}
