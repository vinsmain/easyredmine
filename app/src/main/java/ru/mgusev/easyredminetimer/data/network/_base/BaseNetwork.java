package ru.mgusev.easyredminetimer.data.network._base;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import io.reactivex.Completable;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Response;
import ru.mgusev.easyredminetimer.domain.throwable.InternalServerError500Throwable;
import ru.mgusev.easyredminetimer.domain.throwable.NotFound404Throwable;
import ru.mgusev.easyredminetimer.domain.throwable.ResponseThrowable;
import ru.mgusev.easyredminetimer.domain.throwable.UnknownThrowable;


public abstract class BaseNetwork {

    protected final Gson gson;

    public BaseNetwork(Gson gson) {
        this.gson = gson;
    }

    protected <S> Single<S> parseResponse(Response<JsonObject> response, Class<S> cls) {
        JsonObject body = response.body();
        ResponseBody error = response.errorBody();

        if (response.code() == 200 && body != null)
            return Single.just(gson.fromJson(body.toString(), cls));
        else if (response.code() >= 500) {
            return Single.error(new InternalServerError500Throwable());
        } else if (response.code() == 404) {
            return Single.error(new NotFound404Throwable());
        } else if (error != null) {
            String errorStr = null;
            try {
                errorStr = error.string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ResponseThrowable responseThrowable = gson.fromJson(errorStr, ResponseThrowable.class);
            responseThrowable.setCode(response.code());
            return Single.error(responseThrowable);
        } else return Single.error(new UnknownThrowable());
    }

    protected <S> Single<S> parseResponse(Response<JsonObject> response, TypeToken<S> typeToken) {
        JsonObject body = response.body();
        ResponseBody error = response.errorBody();
        if (response.code() == 200 && body != null)
            return Single.just(gson.fromJson(body.toString(), typeToken.getType()));
        else if (response.code() >= 500) {
            return Single.error(new InternalServerError500Throwable());
        } else if (response.code() == 404) {
            return Single.error(new NotFound404Throwable());
        } else if (error != null) {
            String errorStr = null;
            try {
                errorStr = error.string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ResponseThrowable responseThrowable = gson.fromJson(errorStr, ResponseThrowable.class);
            responseThrowable.setCode(response.code());
            return Single.error(responseThrowable);
        } else return Single.error(new UnknownThrowable());
    }

    protected <S> Single<S> parseResponseArray(Response<JsonArray> response, TypeToken<S> typeToken) {
        JsonArray body = response.body();
        ResponseBody error = response.errorBody();

        if (response.code() == 200 && body != null)
            return Single.just(gson.fromJson(body.toString(), typeToken.getType()));
        else if (response.code() >= 500) {
            return Single.error(new InternalServerError500Throwable());
        } else if (response.code() == 404) {
            return Single.error(new NotFound404Throwable());
        } else if (error != null) {
            String errorStr = null;
            try {
                errorStr = error.string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ResponseThrowable responseThrowable = gson.fromJson(errorStr, ResponseThrowable.class);
            responseThrowable.setCode(response.code());
            return Single.error(responseThrowable);
        } else return Single.error(new UnknownThrowable());
    }


    protected Completable parseResponse(Response<JsonObject> response) {
        ResponseBody error = response.errorBody();

        if (response.code() == 200 || response.code() == 204) return Completable.complete();
        else if (response.code() >= 500)
            return Completable.error(new InternalServerError500Throwable());
        else if (error != null) {
            String errorStr = null;
            try {
                errorStr = error.string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ResponseThrowable responseThrowable = gson.fromJson(errorStr, ResponseThrowable.class);
            responseThrowable.setCode(response.code());
            return Completable.error(responseThrowable);
        } else return Completable.error(new UnknownThrowable());
    }

    protected Completable parseVoidResponse(Response<Void> response) {
        ResponseBody error = response.errorBody();

        if (response.code() == 200 || response.code() == 204) return Completable.complete();
        else if (response.code() >= 500)
            return Completable.error(new InternalServerError500Throwable());
        else if (error != null) {
            String errorStr = null;
            try {
                errorStr = error.string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ResponseThrowable responseThrowable = gson.fromJson(errorStr, ResponseThrowable.class);
            responseThrowable.setCode(response.code());
            return Completable.error(responseThrowable);
        } else return Completable.error(new UnknownThrowable());
    }
}
