package ru.mgusev.easyredminetimer.domain.interactor.task;

import javax.inject.Inject;

import io.reactivex.Completable;
import ru.mgusev.easyredminetimer.domain.gateway.EasyRedmineGateway;
import ru.mgusev.easyredminetimer.domain.interactor._base.CompletableUseCase;
import ru.mgusev.easyredminetimer.domain.interactor._base.SchedulerProvider;

public class UpdateTaskUseCase extends CompletableUseCase<UpdateTaskParams> {

    private final EasyRedmineGateway gateway;

    @Inject
    public UpdateTaskUseCase(SchedulerProvider schedulerProvider, EasyRedmineGateway gateway) {
        super(schedulerProvider);
        this.gateway = gateway;
    }

    @Override
    protected Completable buildUseCaseCompletable(UpdateTaskParams params) {
        return gateway.updateTask(params.getTask());
    }
}