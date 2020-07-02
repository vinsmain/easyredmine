package ru.mgusev.easyredminetimer.data.local.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ru.mgusev.easyredminetimer.domain.dto.project.Project;

@Database(entities = {Project.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProjectDao projectDao();
}
