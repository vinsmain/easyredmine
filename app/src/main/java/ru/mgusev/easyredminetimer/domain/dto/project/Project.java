package ru.mgusev.easyredminetimer.domain.dto.project;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity(tableName = "projects")
public class Project implements Serializable {

    public static final String KEY_NAME = "name";
    public static final String KEY_SELECTED = "selected";

    @PrimaryKey
    private int id;
    private String name;
    private boolean enabled;
    @Ignore
    private boolean selected;

    public Project() {
    }

    @Ignore
    public Project(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return id == project.id &&
                enabled == project.enabled &&
                selected == project.selected &&
                Objects.equals(name, project.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, enabled, selected);
    }
}