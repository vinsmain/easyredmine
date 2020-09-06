package ru.mgusev.easyredminetimer.app.presentation.report;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import moxy.InjectViewState;
import ru.mgusev.easyredminetimer.app.presentation.base.BasePresenter;
import ru.mgusev.easyredminetimer.app.presentation.base.ResourceManager;
import ru.mgusev.easyredminetimer.data.local.pref.LocalStorage;
import ru.mgusev.easyredminetimer.domain.dto.task.Task;
import ru.mgusev.easyredminetimer.domain.interactor.selected_project_list.GetSelectedProjectListParams;
import ru.mgusev.easyredminetimer.domain.interactor.task.CreateTimeSpentEntryParams;
import ru.mgusev.easyredminetimer.domain.interactor.task.CreateTimeSpentEntryUseCase;
import ru.mgusev.easyredminetimer.domain.interactor.task.GetStoppedTaskListUseCase;
import ru.terrakok.cicerone.Router;
import timber.log.Timber;

@InjectViewState
public class ReportPresenter extends BasePresenter<ReportView> {

    private final GetStoppedTaskListUseCase getStoppedTaskListUseCase;
    private final CreateTimeSpentEntryUseCase createTimeSpentEntryUseCase;
    private LocalStorage localStorage;

    private List<Task> taskList;

    public ReportPresenter(Router router,
                           ResourceManager resourceManager,
                           GetStoppedTaskListUseCase getStoppedTaskListUseCase,
                           CreateTimeSpentEntryUseCase createTimeSpentEntryUseCase,
                           LocalStorage localStorage) {
        super(router, resourceManager);
        this.getStoppedTaskListUseCase = getStoppedTaskListUseCase;
        this.createTimeSpentEntryUseCase = createTimeSpentEntryUseCase;
        this.localStorage = localStorage;

        addUseCase(this.getStoppedTaskListUseCase);
        addUseCase(this.createTimeSpentEntryUseCase);

        taskList = new ArrayList<>();
    }

    @Override
    protected void onFirstViewAttach() {
        setPreselectedDates();
        loadData();
    }

    private void setPreselectedDates() {
        getViewState().setPreselectedDates(Calendar.getInstance(), Calendar.getInstance());
    }

    public void loadData() {
        getStoppedTaskListUseCase.execute(new DisposableSingleObserver<List<Task>>() {

            @Override
            public void onSuccess(List<Task> list) {
                taskList.clear();
                taskList.addAll(list);
                getViewState().showList(taskList);
            }

            @Override
            public void onError(Throwable e) {
                Timber.d(e);
            }
        }, new GetSelectedProjectListParams(ZonedDateTime.of(LocalDate.now().atTime(LocalTime.MIN), ZoneId.systemDefault()), ZonedDateTime.of(LocalDate.now().atTime(LocalTime.MAX), ZoneId.systemDefault())));
    }

    public void sendReport() {
        createTimeSpentEntryUseCase.execute(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                Timber.d("COMPLETE");
            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e);
            }
        }, new CreateTimeSpentEntryParams(localStorage.getApiToken(), taskList));
    }
}