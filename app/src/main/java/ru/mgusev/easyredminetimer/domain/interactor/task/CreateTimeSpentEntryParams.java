package ru.mgusev.easyredminetimer.domain.interactor.task;

import java.util.List;

import ru.mgusev.easyredminetimer.domain.dto.task.Task;
import ru.mgusev.easyredminetimer.domain.interactor._base.BaseUseCaseParams;

public class CreateTimeSpentEntryParams extends BaseUseCaseParams {

    private String apiKey;
    private List<Task> taskList;

    public CreateTimeSpentEntryParams(String apiKey, List<Task> taskList) {
        this.apiKey = apiKey;
        this.taskList = taskList;
    }

    public String getApiKey() {
        return apiKey;
    }

    public List<Task> getTaskList() {
        return taskList;
    }
}