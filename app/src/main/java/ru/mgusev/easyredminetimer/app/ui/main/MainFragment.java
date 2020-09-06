package ru.mgusev.easyredminetimer.app.ui.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

import ru.mgusev.easyredminetimer.R;
import ru.mgusev.easyredminetimer.app.navigation.RouterProvider;
import ru.mgusev.easyredminetimer.app.presentation.base.ResourceManager;
import ru.mgusev.easyredminetimer.app.presentation.main.MainPresenter;
import ru.mgusev.easyredminetimer.app.presentation.main.MainView;
import ru.mgusev.easyredminetimer.app.ui.ApplicationActivity;
import ru.mgusev.easyredminetimer.app.ui._base.BaseFragment;
import ru.mgusev.easyredminetimer.app.ui._base.Layout;
import ru.mgusev.easyredminetimer.domain.dto.Const;
import ru.mgusev.easyredminetimer.domain.dto.project.SelectedProjectHolder;
import ru.mgusev.easyredminetimer.domain.dto.task.Task;
import ru.mgusev.easyredminetimer.domain.interactor.task.CreateTaskUseCase;
import ru.mgusev.easyredminetimer.domain.interactor.task.GetTaskListUseCase;
import ru.terrakok.cicerone.Router;
import timber.log.Timber;


@Layout(id = R.layout.fragment_main)
public class MainFragment extends BaseFragment implements MainView, RouterProvider {

    @BindView(R.id.rvTaskList)
    RecyclerView rvTaskList;

    @Inject
    ResourceManager resourceManager;
    @InjectPresenter
    MainPresenter presenter;
    @Inject
    Router router;
    @Inject
    SelectedProjectHolder selectedProjectHolder;
    @Inject
    CreateTaskUseCase createTaskUseCase;
    @Inject
    GetTaskListUseCase getTaskListUseCase;


    @ProvidePresenter
    MainPresenter providePresenter() {
        return new MainPresenter(router, resourceManager, selectedProjectHolder, createTaskUseCase, getTaskListUseCase);
    }

    public static MainFragment getInstance() {
        return new MainFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rvTaskList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvTaskList.setHasFixedSize(true);
        rvTaskList.setAdapter(new TaskListAdapter(
                getString(R.string.adapter_base_action_retry),
                getString(R.string.adapter_base_text_no_data),
                resourceManager));

        ((TaskListAdapter) rvTaskList.getAdapter()).setItemViewClick((item, view) -> {
            //Timber.d(String.valueOf(view.getId()) + " " + item.getId() + " " + item.getStatus());
            switch (view.getId()) {
                case R.id.btnStart:
                    Timber.d("Start clicked " + item.getProjectName() + " " + item.getStatus());
                    MainFragment.this.sendCommandToStopwatchService(item, Const.STATUS_START_OR_PAUSE);
                    break;
                case R.id.btnStop:
                    MainFragment.this.sendCommandToStopwatchService(item, Const.STATUS_STOP);
                    break;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.checkCreateTaskRequest();
    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                presenter.onAddTaskClicked();
                return true;
            case R.id.action_report:
                presenter.onReportClicked();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void sendCommandToStopwatchService(Task task, int status) {
        ((ApplicationActivity) getActivity()).getService().sendCommand(task, status);
    }

    @Override
    public void showTaskList(List<Task> list) {
        ((TaskListAdapter) rvTaskList.getAdapter()).setItems(list);
    }

    @Override
    public void invalidateTaskItem(Task task) {
        ((TaskListAdapter) rvTaskList.getAdapter()).invalidateItem(task);
    }

    @Override
    public Router getRouter() {
        return router;
    }
}