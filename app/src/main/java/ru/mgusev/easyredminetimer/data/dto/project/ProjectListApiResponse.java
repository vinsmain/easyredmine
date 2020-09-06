package ru.mgusev.easyredminetimer.data.dto.project;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ru.mgusev.easyredminetimer.domain.dto.project.Project;
import ru.mgusev.easyredminetimer.domain.dto.project.ProjectListHolder;

public class ProjectListApiResponse implements Serializable {

    @SerializedName("projects")
    @Expose
    private List<ProjectFromJson> projects = null;
    @SerializedName("total_count")
    @Expose
    private int totalCount;
    @SerializedName("offset")
    @Expose
    private int offset;
    @SerializedName("limit")
    @Expose
    private int limit;

    public List<ProjectFromJson> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectFromJson> projects) {
        this.projects = projects;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public ProjectListHolder getProjectListHolder() {
        List<Project> projectList = new ArrayList<>();
        for (ProjectFromJson project : projects) {
            projectList.add(new Project(project.getId(), project.getName()));
        }
        return new ProjectListHolder(projectList, totalCount, offset, limit);
    }
}