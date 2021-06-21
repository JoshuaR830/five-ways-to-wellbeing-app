package com.joshuarichardson.fivewaystowellbeing.storage.dao;

import com.joshuarichardson.fivewaystowellbeing.storage.entity.ActivityRecord;
import com.joshuarichardson.fivewaystowellbeing.storage.entity.ActivityRecordActivitySchedule;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import kotlinx.coroutines.flow.Flow;

@Dao
public interface ActivityRecordActivityScheduleLinkDao {
    @Insert
    long insert(ActivityRecordActivitySchedule activityRecordActivitySchedule);

    @Query("SELECT activity_records.id, activity_records.name, activity_records.duration, activity_records.timestamp, activity_records.type, activity_records.way_to_wellbeing, activity_records.is_hidden, activity_records.inspire_id FROM activity_records INNER JOIN activity_record_activity_schedule_link ON activity_records.id = activity_record_activity_schedule_link.activity_id WHERE activity_record_activity_schedule_link.schedule_id = :scheduleId")
    Flow<List<ActivityRecord>> getActivitiesByScheduleId(long scheduleId);

    @Query("SELECT activity_records.id, activity_records.name, activity_records.duration, activity_records.timestamp, activity_records.type, activity_records.way_to_wellbeing, activity_records.is_hidden, activity_records.inspire_id FROM activity_records INNER JOIN activity_record_activity_schedule_link ON activity_records.id = activity_record_activity_schedule_link.activity_id WHERE activity_record_activity_schedule_link.schedule_id = :scheduleId")
    List<ActivityRecord> getActivitiesByScheduleIdNotLive(long scheduleId);
}
