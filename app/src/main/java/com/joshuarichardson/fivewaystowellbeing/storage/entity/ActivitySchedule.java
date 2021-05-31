package com.joshuarichardson.fivewaystowellbeing.storage.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import static com.joshuarichardson.fivewaystowellbeing.storage.WaysToWellbeingContract.ACTIVITY_SCHEDULES_ID;
import static com.joshuarichardson.fivewaystowellbeing.storage.WaysToWellbeingContract.ACTIVITY_SCHEDULES_IMAGE;
import static com.joshuarichardson.fivewaystowellbeing.storage.WaysToWellbeingContract.ACTIVITY_SCHEDULES_NAME;
import static com.joshuarichardson.fivewaystowellbeing.storage.WaysToWellbeingContract.ACTIVITY_SCHEDULES_TABLE_NAME;

@Entity(tableName = ACTIVITY_SCHEDULES_TABLE_NAME)
public class ActivitySchedule {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ACTIVITY_SCHEDULES_ID)
    public long id;

    @NonNull
    @ColumnInfo(name = ACTIVITY_SCHEDULES_NAME)
    public String name;

    @NonNull
    @ColumnInfo(name = ACTIVITY_SCHEDULES_IMAGE)
    public String image;

    public ActivitySchedule(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
