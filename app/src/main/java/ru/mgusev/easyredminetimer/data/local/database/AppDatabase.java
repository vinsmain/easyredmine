package ru.mgusev.easyredminetimer.data.local.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import ru.mgusev.easyredminetimer.domain.dto.project.Project;
import ru.mgusev.easyredminetimer.domain.dto.task.Activity;
import ru.mgusev.easyredminetimer.domain.dto.task.Task;
import ru.mgusev.easyredminetimer.domain.dto.task.Time;

@Database(entities = {Project.class, Task.class, Activity.class, Time.class}, version = 1)
@TypeConverters(LocalDateTimeConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProjectDao projectDao();
    public abstract TaskDao taskDao();
    public abstract ActivityDao activityDao();
    public abstract TimeDao timeDao();
}