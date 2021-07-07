package com.joshuarichardson.fivewaystowellbeing.storage;

import com.joshuarichardson.fivewaystowellbeing.storage.entity.ActivityRecord;
import com.joshuarichardson.fivewaystowellbeing.storage.entity.ActivityRecordActivitySchedule;
import com.joshuarichardson.fivewaystowellbeing.storage.entity.ActivitySchedule;
import com.joshuarichardson.fivewaystowellbeing.storage.entity.SurveyResponse;
import com.joshuarichardson.fivewaystowellbeing.storage.entity.SurveyResponseActivityRecord;
import com.joshuarichardson.fivewaystowellbeing.storage.entity.WellbeingQuestion;
import com.joshuarichardson.fivewaystowellbeing.storage.entity.WellbeingRecord;
import com.joshuarichardson.fivewaystowellbeing.storage.entity.WellbeingResult;

import java.util.List;

public class DataExport {
    List<ActivityRecord> activityRecords;
    List<SurveyResponse> surveyResponses;
    List<SurveyResponseActivityRecord> surveyActivityRecords;
    List<WellbeingQuestion> wellbeingQuestions;
    List<WellbeingRecord> wellbeingRecords;
    List<WellbeingResult> wellbeingResults;
    List<ActivityRecordActivitySchedule> activityScheduleLinks;
    List<ActivitySchedule> activitySchedules;

    public DataExport(List<ActivityRecord> activityRecords, List<SurveyResponse> surveyResponses, List<SurveyResponseActivityRecord> surveyActivityRecords, List<WellbeingQuestion> wellbeingQuestions, List<WellbeingRecord> wellbeingRecords, List<WellbeingResult> wellbeingResults, List<ActivityRecordActivitySchedule> activityScheduleLinks, List<ActivitySchedule> activitySchedules) {
        this.activityRecords = activityRecords;
        this.surveyResponses = surveyResponses;
        this.surveyActivityRecords = surveyActivityRecords;
        this.wellbeingQuestions = wellbeingQuestions;
        this.wellbeingRecords = wellbeingRecords;
        this.wellbeingResults = wellbeingResults;
        this.activityScheduleLinks = activityScheduleLinks;
        this.activitySchedules = activitySchedules;
    }

    public List<ActivityRecord> getActivityRecords() {
        return activityRecords;
    }

    public List<SurveyResponse> getSurveyResponses() {
        return surveyResponses;
    }

    public List<SurveyResponseActivityRecord> getSurveyActivityRecords() {
        return surveyActivityRecords;
    }

    public List<WellbeingQuestion> getWellbeingQuestions() {
        return wellbeingQuestions;
    }

    public List<WellbeingRecord> getWellbeingRecords() {
        return wellbeingRecords;
    }

    public List<WellbeingResult> getWellbeingResults() {
        return wellbeingResults;
    }

    public List<ActivityRecordActivitySchedule> getActivityScheduleLinks() {
        return activityScheduleLinks;
    }

    public List<ActivitySchedule> getActivitySchedules() {
        return activitySchedules;
    }
}