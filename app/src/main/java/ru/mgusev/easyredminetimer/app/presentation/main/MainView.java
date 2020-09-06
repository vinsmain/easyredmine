package ru.mgusev.easyredminetimer.app.presentation.main;

import java.util.List;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.OneExecutionStateStrategy;
import moxy.viewstate.strategy.StateStrategyType;
import ru.mgusev.easyredminetimer.app.presentation.base.BaseView;
import ru.mgusev.easyredminetimer.domain.dto.task.Task;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MainView extends BaseView {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void sendCommandToStopwatchService(Task task, int status);

    void showTaskList(List<Task> list);

    void invalidateTaskItem(Task task);
}