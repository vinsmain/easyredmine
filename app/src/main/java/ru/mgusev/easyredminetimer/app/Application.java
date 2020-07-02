package ru.mgusev.easyredminetimer.app;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import ru.mgusev.easyredminetimer.BuildConfig;
import ru.mgusev.easyredminetimer.app.di.ApplicationComponent;

import ru.mgusev.easyredminetimer.app.di.DaggerApplicationComponent;
import ru.mgusev.meduza.domain.utils.LogDebugTree;
import timber.log.Timber;

public class Application extends DaggerApplication {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        ApplicationComponent component = DaggerApplicationComponent.builder().application(this).build();
        component.inject(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new LogDebugTree());
        }

        return component;
    }
}