package ru.mgusev.easyredminetimer.app.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.Room;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.mgusev.easyredminetimer.BuildConfig;
import ru.mgusev.easyredminetimer.data.gateway.EasyRedmineGatewayImpl;
import ru.mgusev.easyredminetimer.data.local.database.AppDatabase;
import ru.mgusev.easyredminetimer.data.network.news.EasyRedmineApi;
import ru.mgusev.easyredminetimer.data.network.news.EasyRedmineNetwork;
import ru.mgusev.easyredminetimer.domain.gateway.EasyRedmineGateway;
import ru.mgusev.easyredminetimer.domain.interactor._base.SchedulerProvider;
import ru.mgusev.easyredminetimer.domain.utils.UserAgentInterceptor;

@Module(includes = {ContextModule.class, NavigationModule.class, LocalNavigationModule.class})
public class ApplicationModule {

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .create();
    }

    @Provides
    @Singleton
    SchedulerProvider provideSchedulerProvider() {
        return new SchedulerProvider() {
            @Override
            public Scheduler io() {
                return Schedulers.io();
            }

            @Override
            public Scheduler ui() {
                return AndroidSchedulers.mainThread();
            }

            @Override
            public Scheduler computation() {
                return Schedulers.computation();
            }
        };
    }

    @Provides
    @Singleton
    SharedPreferences providePrefs(Context context) {
        return context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    EasyRedmineGateway provideNewsGateway(EasyRedmineNetwork network, AppDatabase database) {
        return new EasyRedmineGatewayImpl(network, database);
    }

    @Provides
    @Singleton
    EasyRedmineApi provideNewsApi(OkHttpClient client) {
        return createService(client, EasyRedmineApi.class);
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "database").build();
    }

    @Provides
    @Singleton
    OkHttpClient provideLoginClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new UserAgentInterceptor(BuildConfig.UserAgent));
        builder.connectTimeout(30, TimeUnit.SECONDS); // connect timeout
        builder.readTimeout(30, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG)
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));

        return builder.build();
    }

    private <S> S createService(OkHttpClient client, Class<S> serviceClass) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .baseUrl(BuildConfig.ApiBaseUrl)
                .callbackExecutor(Executors.newFixedThreadPool(3))
                .build()
                .create(serviceClass);
    }
}
