package ru.mgusev.easyredminetimer.data.local.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import ru.mgusev.easyredminetimer.domain.dto.project.Project;

@Dao
public interface ProjectDao {

    @Query("SELECT * FROM project")
    Flowable<List<Project>> getSelectedProjectList();
//
//    @Query("SELECT * FROM news_items WHERE url = :url")
//    Flowable<List<NewsItem>> getByUrl(String url);
//
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertSelectedProjectList(List<Project> list);
//
//    @Delete
//    Completable delete(NewsItem newsItem);
}