package com.joshuarichardson.fivewaystowellbeing.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.joshuarichardson.fivewaystowellbeing.storage.entity.ActivitySchedule
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityScheduleDao {
    @Insert
    fun insert(activitySchedule: ActivitySchedule)

    @Query("SELECT * FROM activity_schedules")
    fun getAllSchedules(): List<ActivitySchedule>

    @Query("SELECT * FROM activity_schedules")
    fun getAllLiveSchedules() : Flow<List<ActivitySchedule>>
}