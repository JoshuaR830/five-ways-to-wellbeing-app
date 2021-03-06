package com.joshuarichardson.fivewaystowellbeing;

import android.content.Context;

/**
 * A helper class to facilitate displaying the correct content on the UI
 */
public class WellbeingHelper {
    /**
     * Gets the image associated with the way to wellbeing
     *
     * @param surveyResponseWayToWellbeing The way to wellbeing type
     * @return The image resource
     */
    public static int getImage(WaysToWellbeing surveyResponseWayToWellbeing) {
        switch(surveyResponseWayToWellbeing) {
            case CONNECT:
                return R.drawable.icon_way_to_wellbeing_connect;
            case KEEP_LEARNING:
                return R.drawable.icon_way_to_wellbeing_keep_learning;
            case TAKE_NOTICE:
                return R.drawable.icon_way_to_wellbeing_take_notice;
            case BE_ACTIVE:
                return R.drawable.icon_way_to_wellbeing_be_active;
            case GIVE:
                return R.drawable.icon_way_to_wellbeing_give;
            default:
                return R.drawable.icon_way_to_wellbeing_unassigned;
        }
    }

    /**
     * Get the way to wellbeing associated with the activity type
     *
     * @param activityType The activity type
     * @return The associated way to wellbeing
     */
    public static WaysToWellbeing getDefaultWayToWellbeingFromActivityType(String activityType) {
        WaysToWellbeing wayToWellbeing;
        switch (ActivityType.valueOf(activityType.toUpperCase())) {
            case HOBBY:
            case LEARNING:
            case WORK:
                wayToWellbeing = WaysToWellbeing.KEEP_LEARNING;
                break;
            case PET:
            case PEOPLE:
                wayToWellbeing = WaysToWellbeing.CONNECT;
                break;
            case SPORT:
            case EXERCISE:
                wayToWellbeing = WaysToWellbeing.BE_ACTIVE;
                break;
            case CHORES:
            case COOKING:
                wayToWellbeing = WaysToWellbeing.GIVE;
                break;
            case RELAXATION:
            case JOURNALING:
            case FAITH:
                wayToWellbeing = WaysToWellbeing.TAKE_NOTICE;
                break;
            case APP:
            default:
                wayToWellbeing = WaysToWellbeing.UNASSIGNED;
                break;
        }

        return wayToWellbeing;
    }

    /**
     * Convert the way to wellbeing from a string to an enum
     *
     * @param wayToWellbeingInput The way to wellbeing in string form
     * @return The associated way to wellbeing enum
     */
    public static WaysToWellbeing getWayToWellbeingFromString(String wayToWellbeingInput) {

        WaysToWellbeing wayToWellbeing = WaysToWellbeing.UNASSIGNED;
        switch (wayToWellbeingInput.toLowerCase()) {
            case "connect":
                wayToWellbeing = WaysToWellbeing.CONNECT;
                break;
            case "be active":
                wayToWellbeing = WaysToWellbeing.BE_ACTIVE;
                break;
            case "keep learning":
                wayToWellbeing = WaysToWellbeing.KEEP_LEARNING;
                break;
            case "take notice":
                wayToWellbeing = WaysToWellbeing.TAKE_NOTICE;
                break;
            case "give":
                wayToWellbeing = WaysToWellbeing.GIVE;
                break;
            default:
                wayToWellbeing = WaysToWellbeing.UNASSIGNED;
        }

        return wayToWellbeing;
    }

    /**
     * Convert the way to wellbeing enum to a string
     *
     * @param wayToWellbeingInput The way to wellbeing to convert
     * @return The string associated with the way to wellbeing
     */
    public static String getStringFromWayToWellbeing(WaysToWellbeing wayToWellbeingInput) {

        String wayToWellbeing = "None";
        switch (wayToWellbeingInput) {
            case CONNECT:
                wayToWellbeing = "Connect";
                break;
            case BE_ACTIVE:
                wayToWellbeing = "Be active";
                break;
            case KEEP_LEARNING:
                wayToWellbeing = "Keep learning";
                break;
            case TAKE_NOTICE:
                wayToWellbeing = "Take notice";
                break;
            case GIVE:
                wayToWellbeing = "Give";
                break;
            default:
                wayToWellbeing = "None";
        }

        return wayToWellbeing;
    }

    /**
     * Get the color associated with the way to wellbeing
     *
     * @param context The application context
     * @param wayToWellbeing The way to wellbeing
     * @return The associated color
     */
    public static int getColor(Context context, String wayToWellbeing) {
        WaysToWellbeing wayToWellbeingType = getWayToWellbeingFromString(wayToWellbeing.replace('_', ' '));

        switch (wayToWellbeingType) {
            case CONNECT:
                return context.getColor(R.color.way_to_wellbeing_connect);
            case BE_ACTIVE:
                return context.getColor(R.color.way_to_wellbeing_be_active);
            case KEEP_LEARNING:
                return context.getColor(R.color.way_to_wellbeing_keep_learning);
            case TAKE_NOTICE:
                return context.getColor(R.color.way_to_wellbeing_take_notice);
            case GIVE:
                return context.getColor(R.color.way_to_wellbeing_give);
            case UNASSIGNED:
            default:
                return context.getColor(R.color.colorSilver);
        }
    }

    /**
     * Get the wellbeing string resource
     *
     * @param wayToWellbeing The way to wellbeing
     * @return The string resource id
     */
    public static int getWellbeingStringResource(WaysToWellbeing wayToWellbeing) {
        switch(wayToWellbeing) {
            case CONNECT:
                return R.string.wellbeing_connect;
            case BE_ACTIVE:
                return R.string.wellbeing_be_active;
            case KEEP_LEARNING:
                return R.string.wellbeing_keep_learning;
            case TAKE_NOTICE:
                return R.string.wellbeing_take_notice;
            case GIVE:
                return R.string.wellbeing_give;
            case UNASSIGNED:
                return R.string.wellbeing_unassigned;
            default:
                return 0;
        }
    }
}
