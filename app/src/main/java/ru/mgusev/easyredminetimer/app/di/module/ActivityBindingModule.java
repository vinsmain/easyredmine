package ru.mgusev.easyredminetimer.app.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ru.mgusev.easyredminetimer.app.di.ApplicationFragmentBindingModule;
import ru.mgusev.easyredminetimer.app.ui.ApplicationActivity;

@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = {ApplicationFragmentBindingModule.class})
    abstract ApplicationActivity bindApplication();
}
