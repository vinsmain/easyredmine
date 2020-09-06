package ru.mgusev.easyredminetimer.data.local.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Single;
import ru.mgusev.easyredminetimer.domain.dto.task.Activity;

@Dao
public interface ActivityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertActivityList(List<Activity> list);

    @Query("SELECT * FROM activities WHERE id = :id")
    Single<Activity> getActivityById(int id);

    @Query("SELECT name FROM activities WHERE id = :id")
    String getActivityNameById(int id);
}