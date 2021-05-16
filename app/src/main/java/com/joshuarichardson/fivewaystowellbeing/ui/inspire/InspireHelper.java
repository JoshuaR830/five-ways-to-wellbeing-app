package com.joshuarichardson.fivewaystowellbeing.ui.inspire;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.joshuarichardson.fivewaystowellbeing.ActivityType;
import com.joshuarichardson.fivewaystowellbeing.WaysToWellbeing;
import com.joshuarichardson.fivewaystowellbeing.storage.entity.ActivityRecord;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class InspireHelper {
    public static List<InspireRecord> getInspirationList() {
        List<InspireRecord> inspirationList = Arrays.asList(
            new InspireRecord(1, "Message a friend", ActivityType.PEOPLE.toString(), WaysToWellbeing.CONNECT.toString(), 5, false),
            new InspireRecord(2, "Telephone call", ActivityType.PEOPLE.toString(), WaysToWellbeing.CONNECT.toString(), 5, false),
            new InspireRecord(3, "Talk to a friend", ActivityType.PEOPLE.toString(), WaysToWellbeing.CONNECT.toString(), 5, false),
            new InspireRecord(4, "BBQ", ActivityType.PEOPLE.toString(), WaysToWellbeing.CONNECT.toString(), 5, false),
            new InspireRecord(5, "Games night", ActivityType.PEOPLE.toString(), WaysToWellbeing.CONNECT.toString(), 5, false),
            new InspireRecord(6, "Meeting", ActivityType.PEOPLE.toString(), WaysToWellbeing.CONNECT.toString(), 5, false),
            new InspireRecord(7, "Socialising", ActivityType.PEOPLE.toString(), WaysToWellbeing.CONNECT.toString(), 5, false),
            new InspireRecord(8, "Family time", ActivityType.PEOPLE.toString(), WaysToWellbeing.CONNECT.toString(), 5, false),
            new InspireRecord(9, "Talk to a pet", ActivityType.PEOPLE.toString(), WaysToWellbeing.CONNECT.toString(), 5, false),

            new InspireRecord(10, "Walk", ActivityType.EXERCISE.toString(), WaysToWellbeing.BE_ACTIVE.toString(), 5, false),
            new InspireRecord(11, "Run", ActivityType.EXERCISE.toString(), WaysToWellbeing.BE_ACTIVE.toString(), 5, false),
            new InspireRecord(12, "Swim", ActivityType.SPORT.toString(), WaysToWellbeing.BE_ACTIVE.toString(), 5, false),
            new InspireRecord(13, "Gym", ActivityType.EXERCISE.toString(), WaysToWellbeing.BE_ACTIVE.toString(), 5, false),
            new InspireRecord(14, "Yoga", ActivityType.RELAXATION.toString(), WaysToWellbeing.BE_ACTIVE.toString(), 5, false),
            new InspireRecord(15, "Cycling", ActivityType.EXERCISE.toString(), WaysToWellbeing.BE_ACTIVE.toString(), 5, false),
            new InspireRecord(16, "Rowing", ActivityType.SPORT.toString(), WaysToWellbeing.BE_ACTIVE.toString(), 5, false),
            new InspireRecord(17, "Gardening", ActivityType.HOBBY.toString(), WaysToWellbeing.BE_ACTIVE.toString(), 5, false),
            new InspireRecord(18, "Football", ActivityType.SPORT.toString(), WaysToWellbeing.BE_ACTIVE.toString(), 5, false),
            new InspireRecord(19, "Walk the dog", ActivityType.PET.toString(), WaysToWellbeing.BE_ACTIVE.toString(), 5, false),

            new InspireRecord(20, "Studying", ActivityType.WORK.toString(), WaysToWellbeing.KEEP_LEARNING.toString(), 5, false),
            new InspireRecord(21, "Work", ActivityType.WORK.toString(), WaysToWellbeing.KEEP_LEARNING.toString(), 5, false),
            new InspireRecord(22, "Reading", ActivityType.HOBBY.toString(), WaysToWellbeing.KEEP_LEARNING.toString(), 5, false),
            new InspireRecord(23, "Programming", ActivityType.HOBBY.toString(), WaysToWellbeing.KEEP_LEARNING.toString(), 5, false),
            new InspireRecord(24, "Learn a language", ActivityType.HOBBY.toString(), WaysToWellbeing.KEEP_LEARNING.toString(), 5, false),
            new InspireRecord(25, "Learn an instrument", ActivityType.HOBBY.toString(), WaysToWellbeing.KEEP_LEARNING.toString(), 5, false),
            new InspireRecord(26, "Puzzle games", ActivityType.HOBBY.toString(), WaysToWellbeing.KEEP_LEARNING.toString(), 5, false),
            new InspireRecord(27, "Lecture", ActivityType.WORK.toString(), WaysToWellbeing.KEEP_LEARNING.toString(), 5, false),
            new InspireRecord(28, "Model making", ActivityType.HOBBY.toString(), WaysToWellbeing.KEEP_LEARNING.toString(), 5, false),
            new InspireRecord(29, "Lego", ActivityType.HOBBY.toString(), WaysToWellbeing.KEEP_LEARNING.toString(), 5, false),

            new InspireRecord(30, "Meditation", ActivityType.RELAXATION.toString(), WaysToWellbeing.TAKE_NOTICE.toString(), 5, false),
            new InspireRecord(31, "Prayer", ActivityType.FAITH.toString(), WaysToWellbeing.TAKE_NOTICE.toString(), 5, false),
            new InspireRecord(32, "Colouring", ActivityType.RELAXATION.toString(), WaysToWellbeing.TAKE_NOTICE.toString(), 5, false),
            new InspireRecord(33, "Diary", ActivityType.JOURNALING.toString(), WaysToWellbeing.TAKE_NOTICE.toString(), 5, false),
            new InspireRecord(34, "Journaling", ActivityType.JOURNALING.toString(), WaysToWellbeing.TAKE_NOTICE.toString(), 5, false),
            new InspireRecord(35, "Photography", ActivityType.HOBBY.toString(), WaysToWellbeing.TAKE_NOTICE.toString(), 5, false),
            new InspireRecord(36, "Exploring new places", ActivityType.HOBBY.toString(), WaysToWellbeing.TAKE_NOTICE.toString(), 5, false),
            new InspireRecord(37, "Adventure", ActivityType.EXERCISE.toString(), WaysToWellbeing.TAKE_NOTICE.toString(), 5, false),
            new InspireRecord(38, "Jigsaw", ActivityType.HOBBY.toString(), WaysToWellbeing.TAKE_NOTICE.toString(), 5, false),
            new InspireRecord(39, "Writing a letter", ActivityType.HOBBY.toString(), WaysToWellbeing.TAKE_NOTICE.toString(), 5, false),
            new InspireRecord(40, "Driving", ActivityType.RELAXATION.toString(), WaysToWellbeing.TAKE_NOTICE.toString(), 5, false),

            new InspireRecord(41, "Washing up", ActivityType.CHORES.toString(), WaysToWellbeing.GIVE.toString(), 5, false),
            new InspireRecord(42, "Cleaning", ActivityType.CHORES.toString(), WaysToWellbeing.GIVE.toString(), 5, false),
            new InspireRecord(43, "Cooking", ActivityType.COOKING.toString(), WaysToWellbeing.GIVE.toString(), 5, false),
            new InspireRecord(44, "Baking", ActivityType.COOKING.toString(), WaysToWellbeing.GIVE.toString(), 5, false),
            new InspireRecord(45, "Volunteering", ActivityType.PEOPLE.toString(), WaysToWellbeing.GIVE.toString(), 5, false),
            new InspireRecord(46, "Shopping for someone", ActivityType.CHORES.toString(), WaysToWellbeing.GIVE.toString(), 5, false),
            new InspireRecord(47, "Chores", ActivityType.CHORES.toString(), WaysToWellbeing.GIVE.toString(), 5, false),
            new InspireRecord(48, "Feed a pet", ActivityType.PET.toString(), WaysToWellbeing.GIVE.toString(), 5, false)
        );

        inspirationList.sort(new NameComparator());

        return inspirationList;
    }

    public static String getInspirationJson() {
        Gson gson = new Gson();
        return gson.toJson(getInspirationList());
    }

    public static List<InspireRecord> deserializeInspirationJson() {
        // ToDo - replace with a web request
        String jsonString = getInspirationJson();

        Gson gson = new Gson();

        // Reference: https://www.baeldung.com/gson-list
        Type inspireListType = new TypeToken<ArrayList<InspireRecord>>() {}.getType();

        return gson.fromJson(jsonString, inspireListType);
    }

    public static ActivityRecord convertInspireRecordToActivity(InspireRecord inspireRecord) {
        return new ActivityRecord(inspireRecord.getActivityName(), 0, Calendar.getInstance().getTimeInMillis(), inspireRecord.getActivityType(), inspireRecord.getActivityWayToWellbeing(), false, inspireRecord.getInspireId());
    }


}