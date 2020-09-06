package ru.mgusev.easyredminetimer.domain.interactor.selected_project_list;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import ru.mgusev.easyredminetimer.domain.dto.project.Project;
import ru.mgusev.easyredminetimer.domain.gateway.EasyRedmineGateway;
import ru.mgusev.easyredminetimer.domain.interactor._base.BaseUseCaseParams;
import ru.mgusev.easyredminetimer.domain.interactor._base.FlowableUseCase;
import ru.mgusev.easyredminetimer.domain.interactor._base.SchedulerProvider;

public class GetSelectedProjectListUseCase extends FlowableUseCase<List<Project>, BaseUseCaseParams> {

    private final EasyRedmineGateway gateway;

    @Inject
    public GetSelectedProjectListUseCase(SchedulerProvider schedulerProvider, EasyRedmineGateway gateway) {
        super(schedulerProvider);
        this.gateway = gateway;
    }

    @Override
    protected Flowable<List<Project>> buildUseCaseFlowable(BaseUseCaseParams params) {
        return gateway.getSelectedProjectList();
    }
}