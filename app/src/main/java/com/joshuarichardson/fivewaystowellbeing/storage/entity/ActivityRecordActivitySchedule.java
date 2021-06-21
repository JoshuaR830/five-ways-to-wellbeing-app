package com.joshuarichardson.fivewaystowellbeing.storage.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;
import static com.joshuarichardson.fivewaystowellbeing.storage.WaysToWellbeingContract.ACTIVITY_RECORD_ACTIVITY_SCHEDULE_LINK_ACTIVITY_ID;
import static com.joshuarichardson.fivewaystowellbeing.storage.WaysToWellbeingContract.ACTIVITY_RECORD_ACTIVITY_SCHEDULE_LINK_ID;
import static com.joshuarichardson.fivewaystowellbeing.storage.WaysToWellbeingContract.ACTIVITY_RECORD_ACTIVITY_SCHEDULE_LINK_SCHEDULE_ID;
import static com.joshuarichardson.fivewaystowellbeing.storage.WaysToWellbeingContract.ACTIVITY_RECORD_ACTIVITY_SCHEDULE_LINK_TABLE_NAME;
import static com.joshuarichardson.fivewaystowellbeing.storage.WaysToWellbeingContract.ACTIVITY_RECORD_ID;
import static com.joshuarichardson.fivewaystowellbeing.storage.WaysToWellbeingContract.ACTIVITY_SCHEDULES_ID;

@Entity(tableName = ACTIVITY_RECORD_ACTIVITY_SCHEDULE_LINK_TABLE_NAME, foreignKeys = {
    @ForeignKey(entity = ActivityRecord.class, parentColumns = ACTIVITY_RECORD_ID, childColumns = ACTIVITY_RECORD_ACTIVITY_SCHEDULE_LINK_ACTIVITY_ID, onDelete = CASCADE),
    @ForeignKey(entity = ActivitySchedule.class, parentColumns = ACTIVITY_SCHEDULES_ID, childColumns = ACTIVITY_RECORD_ACTIVITY_SCHEDULE_LINK_SCHEDULE_ID, onDelete = CASCADE)
})
public class ActivityRecordActivitySchedule {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ACTIVITY_RECORD_ACTIVITY_SCHEDULE_LINK_ID)
    private long id;

    @ColumnInfo(name = ACTIVITY_RECORD_ACTIVITY_SCHEDULE_LINK_ACTIVITY_ID)
    private long activityId;

    @ColumnInfo(name = ACTIVITY_RECORD_ACTIVITY_SCHEDULE_LINK_SCHEDULE_ID)
    private long scheduleId;

    public ActivityRecordActivitySchedule(long activityId, long scheduleId) {
        this.activityId = activityId;
        this.scheduleId = scheduleId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }

    public long getScheduleId() {
        return this.scheduleId;
    }

    public long getActivityId() {
        return this.activityId;
    }
}
