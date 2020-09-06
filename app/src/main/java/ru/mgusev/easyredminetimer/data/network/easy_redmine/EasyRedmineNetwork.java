package ru.mgusev.easyredminetimer.data.network.easy_redmine;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.mgusev.easyredminetimer.data.dto.time_entry.TimeSpentEntryBody;
import ru.mgusev.easyredminetimer.data.dto.project.ProjectListApiResponse;
import ru.mgusev.easyredminetimer.data.network._base.BaseNetwork;
import ru.mgusev.easyredminetimer.domain.dto.project.ProjectListHolder;

public class EasyRedmineNetwork extends BaseNetwork {

    private final EasyRedmineApi api;

    @Inject
    public EasyRedmineNetwork(Gson gson, EasyRedmineApi api) {
        super(gson);
        this.api = api;
    }

    public Single<ProjectListHolder> getProjectList(Map<String, Object> params) {
        return api.getProjectList(params)
                .flatMap(response -> parseResponse(response, new TypeToken<ProjectListApiResponse>() {
                }))
                .map(ProjectListApiResponse::getProjectListHolder);
    }

    public Completable createTimeSpentEntry(String apiKey, TimeSpentEntryBody body) {
        return api.createTimeSpentEntry(apiKey, body);
    }
//
//    public Single<NewsDetailsItem> getNewsDetailsItem(String url) {
//        return api.getNewsDetails(url)
//                .flatMap(response -> parseResponse(response, new TypeToken<NewsDetailsApiResponse>() {
//                }))
//                .map(NewsDetailsApiResponse::getNewsDetailsItem)
//                .map(newsDetailsItem -> {
//                    newsDetailsItem.getContent().setBody("<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" />" + newsDetailsItem.getContent().getBody().replace("src=\"/", "src=\"" + BuildConfig.SiteUrl + "/"));
//                    newsDetailsItem.setDateTime(newsDetailsItem.getDateTime() * 1000L);
//                    return newsDetailsItem;
//                });
//    }
}