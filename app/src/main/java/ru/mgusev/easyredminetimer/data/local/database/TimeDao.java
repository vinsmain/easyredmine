package ru.mgusev.easyredminetimer.data.local.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.time.ZonedDateTime;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.mgusev.easyredminetimer.domain.dto.task.TaskToDate;
import ru.mgusev.easyredminetimer.domain.dto.task.Time;

@Dao
public interface TimeDao {

    @Query("SELECT * FROM times WHERE taskId = :id ")
    List<Time> getTimeListByTaskId(long id);

    @Query("SELECT * FROM times WHERE taskId = :id AND (datetime(timeStart) BETWEEN datetime(:dateFrom) AND datetime(:dateTo)) AND (datetime(timeStop) BETWEEN datetime(:dateFrom) AND datetime(:dateTo))")
    List<Time> getTimeListByTaskIdByPeriod(long id, ZonedDateTime dateFrom, ZonedDateTime dateTo);

    @Query("SELECT taskId, date(timeStart) FROM times WHERE (datetime(timeStart) BETWEEN datetime(:dateFrom) AND datetime(:dateTo)) AND (datetime(timeStop) BETWEEN datetime(:dateFrom) AND datetime(:dateTo)) GROUP BY date(timeStart)")
    Single<List<TaskToDate>> getTimeListByPeriod(ZonedDateTime dateFrom, ZonedDateTime dateTo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(List<Time> list);

    @Update
    Completable update(List<Time> list);

    @Delete
    Completable delete(List<Time> list);
}