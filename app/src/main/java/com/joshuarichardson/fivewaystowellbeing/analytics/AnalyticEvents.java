package com.joshuarichardson.fivewaystowellbeing.analytics;

/**
 * Class containing analytics tracking information
 */
public class AnalyticEvents {

    /**
     * Class containing analytics event names
     * Use these when logging events with Firebase
     */
    public static class Events {
        public static final String CREATE_ACTIVITY = "create_activity";
        public static final String CREATE_SURVEY = "create_survey";
        public static final String ACHIEVED_WAY_TO_WELLBEING = "achieved_way_to_wellbeing";
        public static final String CHECKED_WAY_TO_WELLBEING = "checked_way_to_wellbeing_checkbox";
        public static final String UNCHECKED_WAY_TO_WELLBEING = "unchecked_way_to_wellbeing_checkbox";
        public static final String ACTIVITY_WAY_TO_WELLBEING = "activity_way_to_wellbeing";
        public static final String AUTOMATIC_WAY_TO_WELLBEING = "automatic_activity_way_to_wellbeing";
    }

    /**
     * Class containing analytics parameter names
     */
    public static class Param {
        public static final String SURVEY = "survey";
        public static final String WAY_TO_WELLBEING = "way_to_wellbeing";
    }
}
