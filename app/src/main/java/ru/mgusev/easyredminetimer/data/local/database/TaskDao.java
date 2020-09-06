package ru.mgusev.easyredminetimer.data.local.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import ru.mgusev.easyredminetimer.domain.dto.task.Task;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM tasks WHERE status != 3 ORDER BY id DESC")
    Flowable<List<Task>> getNotStoppedTaskList();

    @Query("SELECT * FROM tasks WHERE status = 3 ORDER BY id DESC")
    Flowable<List<Task>> getStoppedTaskList();

    @Query("SELECT * FROM tasks WHERE id = :id")
    Single<Task> getTaskById(long id);

    @Query("SELECT * FROM tasks WHERE id = :id AND status = 3")
    Single<Task> getStoppedTaskById(long id);

    @Query("UPDATE tasks SET status = 2 WHERE status = 1")
    Completable clearStatus();

    @Insert
    Single<Long> insert(Task task);

    @Update
    Completable update(Task task);

    @Delete
    Completable delete(Task task);

}