package ru.mgusev.easyredminetimer.data.gateway;

import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.mgusev.easyredminetimer.data.local.database.AppDatabase;
import ru.mgusev.easyredminetimer.data.network.news.EasyRedmineNetwork;
import ru.mgusev.easyredminetimer.domain.dto.project.Project;
import ru.mgusev.easyredminetimer.domain.dto.project.ProjectListHolder;
import ru.mgusev.easyredminetimer.domain.gateway.EasyRedmineGateway;

public class EasyRedmineGatewayImpl implements EasyRedmineGateway {

    private final EasyRedmineNetwork network;
    private final AppDatabase database;

    public EasyRedmineGatewayImpl(EasyRedmineNetwork network, AppDatabase database) {
        this.network = network;
        this.database = database;
    }

    @Override
    public Single<ProjectListHolder> getProjectListHolder(Map<String, Object> params) {
        return network.getNewsList(params);
    }

    @Override
    public Completable insertSelectedProjectList(List<Project> list) {
        return database.projectDao().insertSelectedProjectList(list);
    }

    @Override
    public Flowable<List<NewsItem>> getBookmarkList() {
        return database.newsItemDao().getAll();
    }

//    @Override
//    public Single<NewsList> getNewsList(Map<String, Object> params) {
//        return network.getNewsList(params);
//    }
//
//    @Override
//    public Single<NewsDetailsItem> getNewsDetailsItem(String url) {
//        return network.getNewsDetailsItem(url);
//    }
//

//
//    @Override
//    public Flowable<List<NewsItem>> getBookmarkByUrl(String url) {
//        return database.newsItemDao().getByUrl(url);
//    }
//

//
//    @Override
//    public Completable deleteBookmark(NewsItem bookmark) {
//        return database.newsItemDao().delete(bookmark);
//    }
//
//    @Override
//    public BehaviorSubject<AudioState> getAudioStateSubject() {
//        return audioStateSubject;
//    }
//
//    @Override
//    public BehaviorSubject<AudioState> getAudioProgressSubject() {
//        return audioProgressSubject;
//    }
}