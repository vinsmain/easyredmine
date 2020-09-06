package ru.mgusev.easyredminetimer.domain.interactor.task;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import ru.mgusev.easyredminetimer.domain.dto.task.Task;
import ru.mgusev.easyredminetimer.domain.gateway.EasyRedmineGateway;
import ru.mgusev.easyredminetimer.domain.interactor._base.BaseUseCaseParams;
import ru.mgusev.easyredminetimer.domain.interactor._base.FlowableUseCase;
import ru.mgusev.easyredminetimer.domain.interactor._base.SchedulerProvider;

public class GetTaskListUseCase extends FlowableUseCase<List<Task>, BaseUseCaseParams> {

    private final EasyRedmineGateway gateway;

    @Inject
    public GetTaskListUseCase(SchedulerProvider schedulerProvider, EasyRedmineGateway gateway) {
        super(schedulerProvider);
        this.gateway = gateway;
    }

    @Override
    protected Flowable<List<Task>> buildUseCaseFlowable(BaseUseCaseParams params) {
        return gateway.getTaskList();
    }
}