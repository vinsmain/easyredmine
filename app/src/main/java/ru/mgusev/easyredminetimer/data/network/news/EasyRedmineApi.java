package ru.mgusev.easyredminetimer.data.network.news;

import com.google.gson.JsonObject;

import java.util.Map;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface EasyRedmineApi {

    @GET("/api/w5/{url}")
    Single<Response<JsonObject>> getNewsDetails(@Path("url") String newsUrl);

    @GET("/projects.json")
    Single<Response<JsonObject>> getProjectList(@QueryMap Map<String, Object> params);
}
