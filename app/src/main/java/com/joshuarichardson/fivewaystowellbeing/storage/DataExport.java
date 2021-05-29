package com.joshuarichardson.fivewaystowellbeing.storage;

import com.joshuarichardson.fivewaystowellbeing.storage.entity.ActivityRecord;
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

    public DataExport(List<ActivityRecord> activityRecords, List<SurveyResponse> surveyResponses, List<SurveyResponseActivityRecord> surveyActivityRecords, List<WellbeingQuestion> wellbeingQuestions, List<WellbeingRecord> wellbeingRecords, List<WellbeingResult> wellbeingResults) {
        this.activityRecords = activityRecords;
        this.surveyResponses = surveyResponses;
        this.surveyActivityRecords = surveyActivityRecords;
        this.wellbeingQuestions = wellbeingQuestions;
        this.wellbeingRecords = wellbeingRecords;
        this.wellbeingResults = wellbeingResults;
    }
}
