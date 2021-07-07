package com.joshuarichardson.fivewaystowellbeing.storage.dao;

import com.joshuarichardson.fivewaystowellbeing.storage.ActivityRecordWrapper;
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

    @Query("SELECT activity_record_activity_schedule_link.id as linkId, activity_records.id as activityRecordId, activity_records.name as activityName, activity_records.duration as activityDuration, activity_records.timestamp as activityTimestamp, activity_records.type as activityType, activity_records.way_to_wellbeing as activityWayToWellbeing, activity_records.is_hidden as isHidden, activity_records.inspire_id as inspireId FROM activity_records INNER JOIN activity_record_activity_schedule_link ON activity_records.id = activity_record_activity_schedule_link.activity_id WHERE activity_record_activity_schedule_link.schedule_id = :scheduleId")
    Flow<List<ActivityRecordWrapper>> getActivitiesByScheduleId(long scheduleId);

    @Query("SELECT activity_record_activity_schedule_link.id as linkId, activity_records.id as activityRecordId, activity_records.name as activityName, activity_records.duration as activityDuration, activity_records.timestamp as activityTimestamp, activity_records.type as activityType, activity_records.way_to_wellbeing as activityWayToWellbeing, activity_records.is_hidden as isHidden, activity_records.inspire_id as inspireId FROM activity_records INNER JOIN activity_record_activity_schedule_link ON activity_records.id = activity_record_activity_schedule_link.activity_id WHERE activity_record_activity_schedule_link.schedule_id = :scheduleId")
    List<ActivityRecordWrapper> getActivitiesByScheduleIdNotLive(long scheduleId);

    @Query("DELETE FROM activity_record_activity_schedule_link WHERE id = :linkId")
    void unlink(long linkId);

    @Query("INSERT INTO activity_record_activity_schedule_link (id, activity_id, schedule_id) VALUES (:id, :activityId, :scheduleId) ON CONFLICT DO NOTHING")
    void insertData(long id, long activityId, long scheduleId);

    @Query("SELECT * FROM activity_record_activity_schedule_link")
    List<ActivityRecordActivitySchedule> getAllActivityScheduleLinks();
}
