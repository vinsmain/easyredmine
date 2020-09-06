package ru.mgusev.easyredminetimer.domain.interactor.task;

import ru.mgusev.easyredminetimer.domain.dto.task.Task;
import ru.mgusev.easyredminetimer.domain.interactor._base.BaseUseCaseParams;

public class UpdateTaskParams extends BaseUseCaseParams {

    private Task task;

    public UpdateTaskParams(Task task) {
        this.task = task;
    }

    public Task getTask() {
        return task;
    }
}