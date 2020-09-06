package ru.mgusev.easyredminetimer.domain.interactor.task;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import ru.mgusev.easyredminetimer.domain.dto.task.Task;
import ru.mgusev.easyredminetimer.domain.gateway.EasyRedmineGateway;
import ru.mgusev.easyredminetimer.domain.interactor._base.SchedulerProvider;
import ru.mgusev.easyredminetimer.domain.interactor._base.SingleUseCase;
import ru.mgusev.easyredminetimer.domain.interactor.selected_project_list.GetSelectedProjectListParams;

public class GetStoppedTaskListUseCase extends SingleUseCase<List<Task>, GetSelectedProjectListParams> {

    private final EasyRedmineGateway gateway;

    @Inject
    public GetStoppedTaskListUseCase(SchedulerProvider schedulerProvider, EasyRedmineGateway gateway) {
        super(schedulerProvider);
        this.gateway = gateway;
    }

    @Override
    protected Single<List<Task>> buildUseCaseSingle(GetSelectedProjectListParams params) {
        return gateway.getTaskListByPeriod(params.getDateFrom(), params.getDateTo());
    }
}