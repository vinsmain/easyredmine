package ru.mgusev.easyredminetimer.app.ui;

import androidx.fragment.app.Fragment;
import android.os.Bundle;

import javax.inject.Inject;

import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.mgusev.easyredminetimer.R;
import ru.mgusev.easyredminetimer.app.navigation.ApplicationNavigator;
import ru.mgusev.easyredminetimer.app.navigation.BackButtonListener;
import ru.mgusev.easyredminetimer.app.navigation.RouterProvider;
import ru.mgusev.easyredminetimer.app.presentation.ApplicationPresenter;
import ru.mgusev.easyredminetimer.app.presentation.base.ResourceManager;
import ru.mgusev.easyredminetimer.app.ui._base.BaseActivity;
import ru.mgusev.easyredminetimer.app.ui._base.Layout;
import ru.mgusev.easyredminetimer.data.local.pref.LocalStorage;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

@Layout(id = R.layout.activity_application)
public class ApplicationActivity extends BaseActivity implements RouterProvider {

    @Inject
    NavigatorHolder holder;
    @Inject
    Router router;
    @Inject
    ResourceManager resourceManager;
    @Inject
    LocalStorage localStorage;

    private Navigator navigator;

    @InjectPresenter
    ApplicationPresenter presenter;

    @ProvidePresenter
    ApplicationPresenter providePresenter() {
        return new ApplicationPresenter(router, resourceManager, getIntent(), localStorage);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        navigator = new ApplicationNavigator(this, getSupportFragmentManager(), R.id.fragmentContainer);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        holder.setNavigator(navigator);
    }

    @Override
    protected void onPause() {
        holder.removeNavigator();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if (!(fragment instanceof BackButtonListener) || !((BackButtonListener) fragment).onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    public Router getRouter() {
        return router;
    }
}