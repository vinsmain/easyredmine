package ru.mgusev.easyredminetimer.domain.interactor.selected_project_list;

import java.time.ZonedDateTime;

import ru.mgusev.easyredminetimer.domain.interactor._base.BaseUseCaseParams;

public class GetSelectedProjectListParams extends BaseUseCaseParams {

    private ZonedDateTime dateFrom;
    private ZonedDateTime dateTo;

    public GetSelectedProjectListParams(ZonedDateTime dateFrom, ZonedDateTime dateTo) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public ZonedDateTime getDateFrom() {
        return dateFrom;
    }

    public ZonedDateTime getDateTo() {
        return dateTo;
    }
}