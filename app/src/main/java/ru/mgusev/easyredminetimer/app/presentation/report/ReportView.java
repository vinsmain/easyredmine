package ru.mgusev.easyredminetimer.app.presentation.report;

import java.util.Calendar;
import java.util.List;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;
import ru.mgusev.easyredminetimer.app.presentation.base.BaseView;
import ru.mgusev.easyredminetimer.domain.dto.task.Task;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface ReportView extends BaseView {

    void showList(List<Task> list);

    void invalidateListItem(Task item);

    void setPreselectedDates(Calendar dateFrom, Calendar dateTo);
}