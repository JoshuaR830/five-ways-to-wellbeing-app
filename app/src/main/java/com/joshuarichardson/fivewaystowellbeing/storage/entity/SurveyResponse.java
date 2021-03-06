package com.joshuarichardson.fivewaystowellbeing.storage.entity;

import com.joshuarichardson.fivewaystowellbeing.WaysToWellbeing;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import static com.joshuarichardson.fivewaystowellbeing.storage.WaysToWellbeingContract.SURVEY_RESPONSE_DESCRIPTION;
import static com.joshuarichardson.fivewaystowellbeing.storage.WaysToWellbeingContract.SURVEY_RESPONSE_ID;
import static com.joshuarichardson.fivewaystowellbeing.storage.WaysToWellbeingContract.SURVEY_RESPONSE_TABLE_NAME;
import static com.joshuarichardson.fivewaystowellbeing.storage.WaysToWellbeingContract.SURVEY_RESPONSE_TIMESTAMP;
import static com.joshuarichardson.fivewaystowellbeing.storage.WaysToWellbeingContract.SURVEY_RESPONSE_TITLE;
import static com.joshuarichardson.fivewaystowellbeing.storage.WaysToWellbeingContract.SURVEY_RESPONSE_WAY_TO_WELLBEING;

/**
 * The entity representing the survey response table
 * The table is used to hold all the high level survey information
 */
@Entity(tableName = SURVEY_RESPONSE_TABLE_NAME)
public class SurveyResponse {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = SURVEY_RESPONSE_ID)
    private long surveyResponseId;

    @NonNull
    @ColumnInfo(name = SURVEY_RESPONSE_TIMESTAMP)
    private long surveyResponseTimestamp;

    @ColumnInfo(name = SURVEY_RESPONSE_WAY_TO_WELLBEING)
    private String surveyResponseWayToWellbeing;

    @ColumnInfo(name = SURVEY_RESPONSE_TITLE)
    private String title;

    @ColumnInfo(name = SURVEY_RESPONSE_DESCRIPTION)
    private String description;

    public SurveyResponse(long surveyResponseTimestamp, String surveyResponseWayToWellbeing, String title, String description) {
        this.setSurveyResponseTimestamp(surveyResponseTimestamp);
        this.setSurveyResponseWayToWellbeing(surveyResponseWayToWellbeing);
        this.setTitle(title);
        this.setDescription(description);
    }

    public SurveyResponse(long surveyResponseTimestamp, WaysToWellbeing surveyResponseWayToWellbeing, String title, String description) {
        this.setSurveyResponseTimestamp(surveyResponseTimestamp);
        this.setSurveyResponseWayToWellbeing(surveyResponseWayToWellbeing.name());
        this.setTitle(title);
        this.setDescription(description);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setSurveyResponseId(long surveyResponseId) {
        this.surveyResponseId = surveyResponseId;
    }

    public void setSurveyResponseTimestamp(long surveyResponseTimestamp) {
        this.surveyResponseTimestamp = surveyResponseTimestamp;
    }

    public void setSurveyResponseWayToWellbeing(String surveyResponseWayToWellbeing) {
        this.surveyResponseWayToWellbeing = surveyResponseWayToWellbeing;
    }

    public long getSurveyResponseId() {
        return this.surveyResponseId;
    }

    public long getSurveyResponseTimestamp() {
        return this.surveyResponseTimestamp;
    }

    public String getSurveyResponseWayToWellbeing() {
        return this.surveyResponseWayToWellbeing;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }
}
