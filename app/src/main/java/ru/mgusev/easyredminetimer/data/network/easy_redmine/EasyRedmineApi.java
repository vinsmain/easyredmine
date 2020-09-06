package ru.mgusev.easyredminetimer.data.network.easy_redmine;

import com.google.gson.JsonObject;

import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import ru.mgusev.easyredminetimer.data.dto.time_entry.TimeSpentEntryBody;

public interface EasyRedmineApi {

    @GET("/api/w5/{url}")
    Single<Response<JsonObject>> getNewsDetails(@Path("url") String newsUrl);

    @GET("/projects.json")
    Single<Response<JsonObject>> getProjectList(@QueryMap Map<String, Object> params);

    @POST("/time_entries.json")
    Completable createTimeSpentEntry(@Query("key") String key, @Body TimeSpentEntryBody body);
}
