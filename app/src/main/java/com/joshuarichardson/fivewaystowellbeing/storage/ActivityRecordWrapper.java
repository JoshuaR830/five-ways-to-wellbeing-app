package com.joshuarichardson.fivewaystowellbeing.storage;

import com.joshuarichardson.fivewaystowellbeing.storage.entity.ActivityRecord;

import androidx.room.Ignore;

public class ActivityRecordWrapper {

    @Ignore
    public ActivityRecord record;

    public long linkId;
    private long activityRecordId;
    private String activityName;
    private long activityDuration;
    private long activityTimestamp;
    private String activityType;
    private String activityWayToWellbeing;
    private boolean isHidden;
    private long inspireId;

    public ActivityRecordWrapper(long linkId, long activityRecordId, String activityName, long activityDuration, long activityTimestamp, String activityType, String activityWayToWellbeing, boolean isHidden, long inspireId) {
        this.linkId = linkId;
        record = new ActivityRecord(activityName, activityDuration, activityTimestamp, activityType, activityWayToWellbeing, isHidden, inspireId);
        record.setActivityRecordId(activityRecordId);
    }

    public ActivityRecord getRecord() {
        return this.record;
    }
}
