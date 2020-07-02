package ru.mgusev.easyredminetimer.domain.gateway;

import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.mgusev.easyredminetimer.domain.dto.project.Project;
import ru.mgusev.easyredminetimer.domain.dto.project.ProjectListHolder;

public interface EasyRedmineGateway {

    Single<ProjectListHolder> getProjectListHolder(Map<String, Object> params);

    Completable insertSelectedProjectList(List<Project> list);
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
