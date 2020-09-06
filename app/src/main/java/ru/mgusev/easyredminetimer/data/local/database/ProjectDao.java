package ru.mgusev.easyredminetimer.data.local.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import ru.mgusev.easyredminetimer.domain.dto.project.Project;

@Dao
public interface ProjectDao {

    @Query("SELECT * FROM projects")
    Flowable<List<Project>> getSelectedProjectList();

    @Query("SELECT * FROM projects WHERE id = :id")
    Single<Project> getProjectById(int id);

    @Query("SELECT name FROM projects WHERE id = :id")
    String getProjectNameById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertSelectedProjectList(List<Project> list);
//
//    @Delete
//    Completable delete(NewsItem newsItem);
}