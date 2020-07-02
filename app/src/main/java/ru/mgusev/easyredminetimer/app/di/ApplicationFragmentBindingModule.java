package ru.mgusev.easyredminetimer.app.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ru.mgusev.easyredminetimer.app.ui.main.MainFragment;
import ru.mgusev.easyredminetimer.app.ui.project_list.ProjectListFragment;
import ru.mgusev.easyredminetimer.app.ui.request_token.RequestTokenFragment;


@Module
public abstract class ApplicationFragmentBindingModule {

    @ContributesAndroidInjector
    abstract MainFragment mainFragment();

    @ContributesAndroidInjector
    abstract RequestTokenFragment requestTokenFragment();

    @ContributesAndroidInjector
    abstract ProjectListFragment projectListFragment();

}