package ru.mgusev.easyredminetimer.domain.dto.task;

import java.time.ZonedDateTime;

public class TaskToDate {

    private long taskId;
    private ZonedDateTime date;

    public TaskToDate() {
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }
}
