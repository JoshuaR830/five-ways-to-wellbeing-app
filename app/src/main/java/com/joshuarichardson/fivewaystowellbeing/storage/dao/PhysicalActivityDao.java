package com.joshuarichardson.fivewaystowellbeing.storage.dao;

import com.joshuarichardson.fivewaystowellbeing.storage.entity.PhysicalActivity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface PhysicalActivityDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(PhysicalActivity physicalActivity);

    @Query("UPDATE physical_activity SET start_time = :startTime WHERE activity_type = :activityType")
    void updateStartTime(String activityType, long startTime);

    @Query("UPDATE physical_activity SET end_time = :endTime WHERE activity_type = :activityType")
    void updateEndTime(String activityType, long endTime);

    @Query("SELECT * FROM physical_activity WHERE activity_type = :activityType AND activity_id > 0")
    PhysicalActivity getPhysicalActivityByTypeWithAssociatedActivity(String activityType);

    @Query("SELECT * FROM physical_activity WHERE activity_id > 0 AND name IS NOT NULL ORDER BY name ASC")
    List<PhysicalActivity> getAllPhysicalActivitiesWithNamesAndAssociatedActivities();

    @Query("SELECT * FROM physical_activity WHERE name IS NOT NULL ORDER BY name ASC")
    List<PhysicalActivity> getAllPhysicalActivitiesWithNames();

    @Query("UPDATE physical_activity SET activity_id = :activityId WHERE activity_type = :activityType")
    void updateActivityId(String activityType, long activityId);

    @Query("UPDATE physical_activity SET is_pending = :isPending WHERE activity_type = :activityType")
    void updateIsPendingStatus(String activityType, boolean isPending);

    @Query("SELECT * FROM physical_activity WHERE is_pending = 1 ORDER BY start_time")
    List<PhysicalActivity> getPending();

    @Query("UPDATE physical_activity SET is_notification_confirmed = :isConfirmed WHERE activity_type = :activityType")
    void updateIsNotificationConfirmedStatus(String activityType, boolean isConfirmed);
}