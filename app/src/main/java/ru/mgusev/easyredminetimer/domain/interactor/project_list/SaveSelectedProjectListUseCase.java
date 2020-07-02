package ru.mgusev.easyredminetimer.domain.interactor.project_list;

import javax.inject.Inject;

import io.reactivex.Completable;
import ru.mgusev.easyredminetimer.domain.gateway.EasyRedmineGateway;
import ru.mgusev.easyredminetimer.domain.interactor._base.CompletableUseCase;
import ru.mgusev.easyredminetimer.domain.interactor._base.SchedulerProvider;

public class SaveSelectedProjectListUseCase extends CompletableUseCase<SaveSelectedProjectListParams> {

    private final EasyRedmineGateway gateway;

    @Inject
    public SaveSelectedProjectListUseCase(SchedulerProvider schedulerProvider, EasyRedmineGateway gateway) {
        super(schedulerProvider);
        this.gateway = gateway;
    }

    @Override
    protected Completable buildUseCaseCompletable(SaveSelectedProjectListParams params) {
        return gateway.insertSelectedProjectList(params.getBookmark());
    }
}