package ru.mgusev.easyredminetimer.domain.interactor.task;

import ru.mgusev.easyredminetimer.domain.dto.task.Task;
import ru.mgusev.easyredminetimer.domain.interactor._base.BaseUseCaseParams;

public class CreateTaskParams extends BaseUseCaseParams {

    private Task task;

    public CreateTaskParams(Task task) {
        this.task = task;
    }

    public Task getTask() {
        return task;
    }
}