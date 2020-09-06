package ru.mgusev.easyredminetimer.domain.dto.task;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "tasks")
public class Task implements Serializable {

    public static final String KEY_PROJECT_NAME = "project_name";
    public static final String KEY_ACTIVITY_NAME = "activity_name";
    public static final String KEY_COMMENTS = "comments";
    public static final String KEY_TIME = "time";
    public static final String KEY_TIME_LIST = "time_list";
    public static final String KEY_STATUS = "status";

    @PrimaryKey(autoGenerate = true)
    private long id;
    private int timeEntryId;
    private int projectId;
    private int activityId;
    private double hours;
    private String comments;
    private String spentOn;
    private int status;
    private long time;
    @Ignore
    private String projectName;
    @Ignore
    private String activityName;
    @Ignore
    private List<Time> timeList;

    public Task() {
    }

    @Ignore
    public Task(long id, int timeEntryId, int projectId, int activityId, double hours, String comments, String spentOn, int status) {
        this.id = id;
        this.timeEntryId = timeEntryId;
        this.projectId = projectId;
        this.activityId = activityId;
        this.hours = hours;
        this.comments = comments;
        this.spentOn = spentOn;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTimeEntryId() {
        return timeEntryId;
    }

    public void setTimeEntryId(int timeEntryId) {
        this.timeEntryId = timeEntryId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getSpentOn() {
        return spentOn;
    }

    public void setSpentOn(String spentOn) {
        this.spentOn = spentOn;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public List<Time> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<Time> timeList) {
        this.timeList = timeList;
    }
}