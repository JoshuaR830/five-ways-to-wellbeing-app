package com.joshuarichardson.fivewaystowellbeing.storage.dao;

import com.joshuarichardson.fivewaystowellbeing.storage.entity.ActivitySchedule;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ActivityScheduleDao {
    @Insert
    void insert(ActivitySchedule activitySchedule);

    @Query("SELECT * FROM activity_schedules")
    List<ActivitySchedule> getAllSchedules();
}
