package ru.mgusev.easyredminetimer.domain.interactor.project_list;

import java.util.List;

import ru.mgusev.easyredminetimer.domain.dto.project.Project;
import ru.mgusev.easyredminetimer.domain.interactor._base.BaseUseCaseParams;

public class SaveSelectedProjectListParams extends BaseUseCaseParams {

    private List<Project> list;

    public SaveSelectedProjectListParams(List<Project> list) {
        this.list = list;
    }

    public List<Project> getBookmark() {
        return list;
    }
}