package ru.mgusev.easyredminetimer.app.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.mgusev.easyredminetimer.app.navigation.LocalCiceroneHolder;

@Module
public class LocalNavigationModule {

    @Provides
    @Singleton
    LocalCiceroneHolder provideLocalCiceroneHolder() {
        return new LocalCiceroneHolder();
    }
}
