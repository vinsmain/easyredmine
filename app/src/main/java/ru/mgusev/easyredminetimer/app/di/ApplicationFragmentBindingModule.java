package ru.mgusev.easyredminetimer.app.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ru.mgusev.easyredminetimer.app.ui.main.MainFragment;
import ru.mgusev.easyredminetimer.app.ui.project_list.ProjectListFragment;
import ru.mgusev.easyredminetimer.app.ui.report.ReportFragment;
import ru.mgusev.easyredminetimer.app.ui.request_token.RequestTokenFragment;
import ru.mgusev.easyredminetimer.app.ui.selected_project_list.SelectedProjectListFragment;


@Module
public abstract class ApplicationFragmentBindingModule {

    @ContributesAndroidInjector
    abstract MainFragment mainFragment();

    @ContributesAndroidInjector
    abstract RequestTokenFragment requestTokenFragment();

    @ContributesAndroidInjector
    abstract ProjectListFragment projectListFragment();

    @ContributesAndroidInjector
    abstract SelectedProjectListFragment selectedProjectListFragment();

    @ContributesAndroidInjector
    abstract ReportFragment reportFragment();
}