package ru.mgusev.easyredminetimer.app.presentation.selected_project_list;

import java.util.List;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;
import ru.mgusev.easyredminetimer.app.presentation.base.BaseView;
import ru.mgusev.easyredminetimer.domain.dto.project.Project;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface SelectedProjectListView extends BaseView {

    void showProjectList(List<Project> list);

    void invalidateProjectItem(Project project);
}