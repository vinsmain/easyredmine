package ru.mgusev.easyredminetimer.app.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
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
import ru.mgusev.easyredminetimer.data.network.easy_redmine.EasyRedmineApi;
import ru.mgusev.easyredminetimer.data.network.easy_redmine.EasyRedmineNetwork;
import ru.mgusev.easyredminetimer.domain.dto.project.SelectedProjectHolder;
import ru.mgusev.easyredminetimer.domain.dto.task.Activity;
import ru.mgusev.easyredminetimer.domain.gateway.EasyRedmineGateway;
import ru.mgusev.easyredminetimer.domain.interactor._base.SchedulerProvider;
import ru.mgusev.easyredminetimer.domain.utils.UserAgentInterceptor;

@Module(includes = {ContextModule.class, NavigationModule.class, LocalNavigationModule.class})
public class ApplicationModule {

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().create();
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
    SelectedProjectHolder provideSelectedProjectHolder() {
        return new SelectedProjectHolder();
    }

    @Provides
    @Singleton
    SharedPreferences providePrefs(Context context) {
        return context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    EasyRedmineGateway provideEasyRedmineGateway(EasyRedmineNetwork network, AppDatabase database) {
        return new EasyRedmineGatewayImpl(network, database);
    }

    @Provides
    @Singleton
    EasyRedmineApi provideEasyRedmineApi(OkHttpClient client) {
        return createService(client, EasyRedmineApi.class);
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "database")
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadScheduledExecutor().execute(() -> provideAppDatabase(context).activityDao().insertActivityList(getPrepopulateActivityList()));
                    }
                })
                .build();
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

    private List<Activity> getPrepopulateActivityList() {
        List<Activity> list = new ArrayList<>();
        list.add(new Activity(1, "Аналитика"));
        list.add(new Activity(28, "Дизайн"));
        list.add(new Activity(29, "Вёрстка"));
        list.add(new Activity(30, "Программирование"));
        list.add(new Activity(31, "Системное администрирование"));
        list.add(new Activity(32, "Тестирование"));
        list.add(new Activity(33, "Контент"));
        list.add(new Activity(34, "SMM"));
        return list;
    }
}
