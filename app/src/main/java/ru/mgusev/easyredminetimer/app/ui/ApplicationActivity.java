package ru.mgusev.easyredminetimer.app.ui;

import androidx.fragment.app.Fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

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
import ru.mgusev.easyredminetimer.app.ui.main.StopwatchService;
import ru.mgusev.easyredminetimer.data.local.pref.LocalStorage;
import ru.mgusev.easyredminetimer.domain.interactor.task.GetTaskListUseCase;
import ru.mgusev.easyredminetimer.domain.interactor.task.UpdateTaskUseCase;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;
import timber.log.Timber;

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
    @Inject
    UpdateTaskUseCase updateTaskUseCase;
    @Inject
    GetTaskListUseCase getTaskListUseCase;

    private ServiceConnection connection;
    private StopwatchService service;
    private boolean bound;

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
        connectToStopwatchService();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (connection == null) {
            connectToStopwatchService();
        }
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

    private void connectToStopwatchService() {
        Intent intent = new Intent(this, StopwatchService.class);
        startService(intent);

        connection = new ServiceConnection() {
            public void onServiceConnected(ComponentName name, IBinder binder) {
                Timber.d("ApplicationActivity onServiceConnected");
                service = ((StopwatchService.MyBinder) binder).getService();
                service.setUpdateTaskUseCase(updateTaskUseCase);
                service.setGetTaskListUseCase(getTaskListUseCase);
                bound = true;
            }

            public void onServiceDisconnected(ComponentName name) {
                Timber.d("ApplicationActivity onServiceDisconnected");
                bound = false;
                connection = null;
            }
        };
        bindService(intent, connection, 0);
    }

    public StopwatchService getService() {
        return service;
    }

    @Override
    public void onDestroy() {
        Timber.d("ON DESTROY");
        if (connection != null) {
            unbindService(connection);
            bound = false;
        }
        super.onDestroy();
    }
}