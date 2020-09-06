package ru.mgusev.easyredminetimer.domain.dto.task;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity(tableName = "times")
public class Time implements Serializable {

    public static final String KEY_PROJECT_NAME = "project_name";
    public static final String KEY_ACTIVITY_NAME = "activity_name";
    public static final String KEY_COMMENTS = "comments";
    public static final String KEY_TIME = "time";
    public static final String KEY_STATUS = "status";

    @PrimaryKey(autoGenerate = true)
    private long id;
    private long taskId;
    private ZonedDateTime timeStart;
    private ZonedDateTime timeStop;

    public Time() {
    }

    @Ignore
    public Time(long taskId, ZonedDateTime timeStart) {
        this.taskId = taskId;
        this.timeStart = timeStart;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public ZonedDateTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(ZonedDateTime timeStart) {
        this.timeStart = timeStart;
    }

    public ZonedDateTime getTimeStop() {
        return timeStop;
    }

    public void setTimeStop(ZonedDateTime timeStop) {
        this.timeStop = timeStop;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Time time = (Time) o;
        return id == time.id &&
                taskId == time.taskId &&
                Objects.equals(timeStart, time.timeStart) &&
                Objects.equals(timeStop, time.timeStop);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, taskId, timeStart, timeStop);
    }
}