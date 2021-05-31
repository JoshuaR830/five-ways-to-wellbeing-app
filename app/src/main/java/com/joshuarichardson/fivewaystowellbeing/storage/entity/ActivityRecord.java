package com.joshuarichardson.fivewaystowellbeing.storage.entity;

import com.joshuarichardson.fivewaystowellbeing.ActivityType;
import com.joshuarichardson.fivewaystowellbeing.WaysToWellbeing;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static com.joshuarichardson.fivewaystowellbeing.storage.WaysToWellbeingContract.ACTIVITY_RECORD_DURATION;
import static com.joshuarichardson.fivewaystowellbeing.storage.WaysToWellbeingContract.ACTIVITY_RECORD_ID;
import static com.joshuarichardson.fivewaystowellbeing.storage.WaysToWellbeingContract.ACTIVITY_RECORD_INSPIRE_ID;
import static com.joshuarichardson.fivewaystowellbeing.storage.WaysToWellbeingContract.ACTIVITY_RECORD_IS_HIDDEN;
import static com.joshuarichardson.fivewaystowellbeing.storage.WaysToWellbeingContract.ACTIVITY_RECORD_NAME;
import static com.joshuarichardson.fivewaystowellbeing.storage.WaysToWellbeingContract.ACTIVITY_RECORD_SCHEDULE_ID;
import static com.joshuarichardson.fivewaystowellbeing.storage.WaysToWellbeingContract.ACTIVITY_RECORD_TABLE_NAME;
import static com.joshuarichardson.fivewaystowellbeing.storage.WaysToWellbeingContract.ACTIVITY_RECORD_TIMESTAMP;
import static com.joshuarichardson.fivewaystowellbeing.storage.WaysToWellbeingContract.ACTIVITY_RECORD_TYPE;
import static com.joshuarichardson.fivewaystowellbeing.storage.WaysToWellbeingContract.ACTIVITY_RECORD_WAY_TO_WELLBEING;

/**
 * The entity representing the activity record table
 * The table is used to hold all of the activities
 */
@Entity(tableName = ACTIVITY_RECORD_TABLE_NAME)
public class ActivityRecord {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ACTIVITY_RECORD_ID)
    private long activityRecordId;

    @NonNull
    @ColumnInfo(name = ACTIVITY_RECORD_NAME)
    private String activityName;

    @ColumnInfo(name = ACTIVITY_RECORD_TYPE)
    private String activityType;

    @NonNull
    @ColumnInfo(name = ACTIVITY_RECORD_TIMESTAMP)
    private long activityTimestamp;

    @NonNull
    @ColumnInfo(name = ACTIVITY_RECORD_DURATION)
    private long activityDuration;

    @ColumnInfo(name = ACTIVITY_RECORD_WAY_TO_WELLBEING)
    private String activityWayToWellbeing;

    @ColumnInfo(name = ACTIVITY_RECORD_IS_HIDDEN)
    private boolean isHidden;

    @ColumnInfo(name = ACTIVITY_RECORD_INSPIRE_ID)
    private long inspireId;

    @ColumnInfo(name = ACTIVITY_RECORD_SCHEDULE_ID)
    private long scheduleId;

    @Ignore
    public ActivityRecord(String activityName, long activityDuration, long activityTimestamp, String activityType, String activityWayToWellbeing, boolean isHidden, long inspireId) {
        initialiseActivityRecord(activityName, activityDuration, activityTimestamp, activityType, activityWayToWellbeing, isHidden, inspireId, 0);
    }

    @Ignore
    public ActivityRecord(String activityName, long activityDuration, long activityTimestamp, ActivityType activityType, WaysToWellbeing activityWayToWellbeing, boolean isHidden, long inspireId) {
        initialiseActivityRecord(activityName, activityDuration, activityTimestamp, activityType.toString(), activityWayToWellbeing.toString(), isHidden, inspireId, 0);
    }

    public ActivityRecord(String activityName, long activityDuration, long activityTimestamp, String activityType, String activityWayToWellbeing, boolean isHidden, long inspireId, long scheduleId) {
        initialiseActivityRecord(activityName, activityDuration, activityTimestamp, activityType, activityWayToWellbeing, isHidden, inspireId, scheduleId);
    }

    private void initialiseActivityRecord(String activityName, long activityDuration, long activityTimestamp, String activityType, String activityWayToWellbeing, boolean isHidden, long inspireId, long scheduleId) {
        this.setActivityName(activityName);
        this.setActivityDuration(activityDuration);
        this.setActivityTimestamp(activityTimestamp);
        this.setActivityType(activityType);
        this.setWayToWellbeing(activityWayToWellbeing);
        this.setIsHidden(isHidden);
        this.setInspireId(inspireId);
        this.setScheduleId(scheduleId);
    }

    private void setIsHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

    private void setWayToWellbeing(String wayToWellbeing) {
        this.activityWayToWellbeing = wayToWellbeing;
    }


    public void setActivityRecordId(@NonNull long recordId) {
        this.activityRecordId = recordId;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void setActivityType(String type) {
        this.activityType = type;
    }

    public void setActivityDuration(long duration) {
        this.activityDuration = duration;
    }

    public void setActivityTimestamp(long timestamp) {
        this.activityTimestamp = timestamp;
    }

    private void setInspireId(long inspireId) {
        this.inspireId = inspireId;
    }

    private void setScheduleId(long scheduleId) {
        this.scheduleId = scheduleId;
    }

    @NonNull
    public long getActivityRecordId() {
        return activityRecordId;
    }

    public String getActivityName() {
        return activityName;
    }

    public String getActivityType() {
        return activityType;
    }

    public long getActivityDuration() {
        return this.activityDuration;
    }

    public long getActivityTimestamp() {
        return this.activityTimestamp;
    }

    public String getActivityWayToWellbeing() {
        return this.activityWayToWellbeing;
    }

    public boolean getIsHidden() {
        return this.isHidden;
    }

    public long getInspireId() {
        return this.inspireId;
    }

    public long getScheduleId() {
        return this.scheduleId;
    }
}
