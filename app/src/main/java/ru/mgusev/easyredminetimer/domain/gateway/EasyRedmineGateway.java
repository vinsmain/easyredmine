package ru.mgusev.easyredminetimer.domain.gateway;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import ru.mgusev.easyredminetimer.domain.dto.project.Project;
import ru.mgusev.easyredminetimer.domain.dto.project.ProjectListHolder;
import ru.mgusev.easyredminetimer.domain.dto.task.Task;

public interface EasyRedmineGateway {

    Single<ProjectListHolder> getProjectListHolder(Map<String, Object> params);

    Completable insertSelectedProjectList(List<Project> list);

    Flowable<List<Project>> getSelectedProjectList();

    Flowable<List<Task>> getTaskList();

    Single<Task> insertTask(Task task);

    Completable updateTask(Task task);

    Completable deleteTask(Task task);

    Completable createTimeSentEntry(String apiKey, List<Task> list);

    Single<List<Task>> getTaskListByPeriod(ZonedDateTime dateFrom, ZonedDateTime dateTo);
//
//    Single<NewsDetailsItem> getNewsDetailsItem(String url);
//
//    Flowable<List<NewsItem>> getBookmarkList();
//
//    Flowable<List<NewsItem>> getBookmarkByUrl(String url);
//
//    Completable insertBookmark(NewsItem newsItem);
//
//    Completable deleteBookmark(NewsItem newsItem);
//
//    BehaviorSubject<AudioState> getAudioStateSubject();
//
//    BehaviorSubject<AudioState> getAudioProgressSubject();
}
