package ru.mgusev.easyredminetimer.domain.interactor.project_list;

import javax.inject.Inject;

import io.reactivex.Single;
import ru.mgusev.easyredminetimer.domain.dto.project.ProjectListHolder;
import ru.mgusev.easyredminetimer.domain.gateway.EasyRedmineGateway;
import ru.mgusev.easyredminetimer.domain.interactor._base.SchedulerProvider;
import ru.mgusev.easyredminetimer.domain.interactor._base.SingleUseCase;


public class GetProjectListUseCase extends SingleUseCase<ProjectListHolder, GetProjectListParams> {

    private final EasyRedmineGateway gateway;

    @Inject
    public GetProjectListUseCase(SchedulerProvider schedulerProvider, EasyRedmineGateway gateway) {
        super(schedulerProvider);
        this.gateway = gateway;
    }

    @Override
    protected Single<ProjectListHolder> buildUseCaseSingle(GetProjectListParams params) {
        return gateway.getProjectListHolder(params.toParams());
    }
}