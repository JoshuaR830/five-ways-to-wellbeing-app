package com.joshuarichardson.fivewaystowellbeing.storage.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import static com.joshuarichardson.fivewaystowellbeing.storage.WaysToWellbeingContract.WELLBEING_RESULT_BE_ACTIVE;
import static com.joshuarichardson.fivewaystowellbeing.storage.WaysToWellbeingContract.WELLBEING_RESULT_CONNECT;
import static com.joshuarichardson.fivewaystowellbeing.storage.WaysToWellbeingContract.WELLBEING_RESULT_GIVE;
import static com.joshuarichardson.fivewaystowellbeing.storage.WaysToWellbeingContract.WELLBEING_RESULT_ID;
import static com.joshuarichardson.fivewaystowellbeing.storage.WaysToWellbeingContract.WELLBEING_RESULT_KEEP_LEARNING;
import static com.joshuarichardson.fivewaystowellbeing.storage.WaysToWellbeingContract.WELLBEING_RESULT_TABLE_NAME;
import static com.joshuarichardson.fivewaystowellbeing.storage.WaysToWellbeingContract.WELLBEING_RESULT_TAKE_NOTICE;
import static com.joshuarichardson.fivewaystowellbeing.storage.WaysToWellbeingContract.WELLBEING_RESULT_TIMESTAMP;

@Entity(tableName = WELLBEING_RESULT_TABLE_NAME)
public class WellbeingResult {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = WELLBEING_RESULT_ID)
    private long id;

    @ColumnInfo(name = WELLBEING_RESULT_CONNECT)
    private int connectValue;

    @ColumnInfo(name = WELLBEING_RESULT_BE_ACTIVE)
    private int beActiveValue;

    @ColumnInfo(name = WELLBEING_RESULT_KEEP_LEARNING)
    private int keepLearningValue;

    @ColumnInfo(name = WELLBEING_RESULT_TAKE_NOTICE)
    private int takeNoticeValue;

    @ColumnInfo(name = WELLBEING_RESULT_GIVE)
    private int giveValue;

    @ColumnInfo(name = WELLBEING_RESULT_TIMESTAMP)
    private long timestamp;


    public WellbeingResult(long id, long timestamp, int connectValue, int beActiveValue, int keepLearningValue, int takeNoticeValue, int giveValue) {
        this.setId(id);
        this.setConnectValue(connectValue);
        this.setBeActiveValue(beActiveValue);
        this.setKeepLearningValue(keepLearningValue);
        this.setTakeNoticeValue(takeNoticeValue);
        this.setGiveValue(giveValue);
        this.setTimestamp(timestamp);
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setConnectValue(int connectValue) {
        this.connectValue = connectValue;
    }

    public void setBeActiveValue(int beActiveValue) {
        this.beActiveValue = beActiveValue;
    }

    public void setKeepLearningValue(int keepLearningValue) {
        this.keepLearningValue = keepLearningValue;
    }

    public void setTakeNoticeValue(int takeNoticeValue) {
        this.takeNoticeValue = takeNoticeValue;
    }

    public void setGiveValue(int giveValue) {
        this.giveValue = giveValue;
    }

    public int getConnectValue() {
        return this.connectValue;
    }

    public int getBeActiveValue() {
        return this.beActiveValue;
    }

    public int getKeepLearningValue() {
        return this.keepLearningValue;
    }

    public int getTakeNoticeValue() {
        return this.takeNoticeValue;
    }

    public int getGiveValue() {
        return this.giveValue;
    }

    public long getId() {
        return this.id;
    }

    public long getTimestamp() {
        return this.timestamp;
    }
}