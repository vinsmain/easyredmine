package ru.mgusev.easyredminetimer.data.dto.time_entry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TimeSpentEntryBody implements Serializable {

    @SerializedName("time_entry")
    @Expose
    private TimeEntry timeEntry;

    public TimeSpentEntryBody() {
    }

    public TimeSpentEntryBody(TimeEntry timeEntry) {
        this.timeEntry = timeEntry;
    }

    public TimeEntry getTimeEntry() {
        return timeEntry;
    }

    public void setTimeEntry(TimeEntry timeEntry) {
        this.timeEntry = timeEntry;
    }
}