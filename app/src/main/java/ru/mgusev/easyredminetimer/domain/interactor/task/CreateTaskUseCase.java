package ru.mgusev.easyredminetimer.domain.interactor.task;

import javax.inject.Inject;

import io.reactivex.Single;
import ru.mgusev.easyredminetimer.domain.dto.task.Task;
import ru.mgusev.easyredminetimer.domain.gateway.EasyRedmineGateway;
import ru.mgusev.easyredminetimer.domain.interactor._base.SchedulerProvider;
import ru.mgusev.easyredminetimer.domain.interactor._base.SingleUseCase;

public class CreateTaskUseCase extends SingleUseCase<Task, CreateTaskParams> {

    private final EasyRedmineGateway gateway;

    @Inject
    public CreateTaskUseCase(SchedulerProvider schedulerProvider, EasyRedmineGateway gateway) {
        super(schedulerProvider);
        this.gateway = gateway;
    }

    @Override
    protected Single<Task> buildUseCaseSingle(CreateTaskParams params) {
        return gateway.insertTask(params.getTask());
    }
}