package ru.mgusev.easyredminetimer.app.presentation.main;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.subscribers.DisposableSubscriber;
import moxy.InjectViewState;
import ru.mgusev.easyredminetimer.app.navigation.Screens;
import ru.mgusev.easyredminetimer.app.presentation.base.BasePresenter;
import ru.mgusev.easyredminetimer.app.presentation.base.ResourceManager;
import ru.mgusev.easyredminetimer.app.ui._base.formatter.DateTimeFormatter;
import ru.mgusev.easyredminetimer.domain.dto.Const;
import ru.mgusev.easyredminetimer.domain.dto.project.SelectedProjectHolder;
import ru.mgusev.easyredminetimer.domain.dto.task.Task;
import ru.mgusev.easyredminetimer.domain.interactor.task.CreateTaskParams;
import ru.mgusev.easyredminetimer.domain.interactor.task.CreateTaskUseCase;
import ru.mgusev.easyredminetimer.domain.interactor.task.GetTaskListUseCase;
import ru.terrakok.cicerone.Router;
import timber.log.Timber;

@InjectViewState
public class MainPresenter extends BasePresenter<MainView> {

    private final SelectedProjectHolder selectedProjectHolder;
    private final CreateTaskUseCase createTaskUseCase;
    private final GetTaskListUseCase getTaskListUseCase;

    public MainPresenter(Router router, ResourceManager resourceManager, SelectedProjectHolder selectedProjectHolder, CreateTaskUseCase createTaskUseCase, GetTaskListUseCase getTaskListUseCase) {
        super(router, resourceManager);
        this.selectedProjectHolder = selectedProjectHolder;
        this.createTaskUseCase = createTaskUseCase;
        this.getTaskListUseCase = getTaskListUseCase;

        addUseCase(createTaskUseCase);
        addUseCase(getTaskListUseCase);
    }

    @Override
    protected void onFirstViewAttach() {
        loadData();
    }

    private void loadData() {
        getTaskListUseCase.execute(new DisposableSubscriber<List<Task>>() {
            @Override
            public void onNext(List<Task> tasks) {
                getViewState().showTaskList(tasks);
            }

            @Override
            public void onError(Throwable t) {
                Timber.e(t);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void createTask(Task task) {
        createTaskUseCase.execute(new DisposableSingleObserver<Task>() {
            @Override
            public void onSuccess(Task task) {
                Timber.d("ID " + task.getId());
                getViewState().sendCommandToStopwatchService(task, Const.STATUS_START);
                selectedProjectHolder.setProject(null);
            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e);
            }
        }, new CreateTaskParams(task));
    }

    public void onAddTaskClicked() {
        router.navigateTo(new Screens.SelectedProjectListScreen());
    }

    public void checkCreateTaskRequest() {
        if (selectedProjectHolder.getProject() != null) {
            //getViewState().sendCommandToStopwatchService(new Task(0, 0, selectedProjectHolder.getProject().getId(), 1, 0, "", new Date().getTime(), 0, 0), Const.STATUS_START);
            createTask(new Task(0, 0, selectedProjectHolder.getProject().getId(), 1, 0, "", DateTimeFormatter.getDateFormatted(LocalDate.now()),-1));
        }
    }

    public void onReportClicked() {
        router.navigateTo(new Screens.ReportScreen());
    }
}
