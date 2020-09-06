package ru.mgusev.easyredminetimer.data.dto.time_entry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TimeEntry implements Serializable {

    @SerializedName("project_id")
    @Expose
    private int projectId;
    @SerializedName("activity_id")
    @Expose
    private int activityId;
    @Expose
    private double hours;
    @Expose
    private String comments;
    @SerializedName("spent_on")
    @Expose
    private String spentOn;

    public TimeEntry() {
    }

    public TimeEntry(int projectId, int activityId, double hours, String comments, String spentOn) {
        this.projectId = projectId;
        this.activityId = activityId;
        this.hours = hours;
        this.comments = comments;
        this.spentOn = spentOn;
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
}