package ru.mgusev.easyredminetimer.domain.dto.project;

import java.util.List;

public class ProjectListHolder {

    private List<Project> projects = null;
    private int totalCount;
    private int offset;
    private int limit;

    public ProjectListHolder(List<Project> projects, int totalCount, int offset, int limit) {
        this.projects = projects;
        this.totalCount = totalCount;
        this.offset = offset;
        this.limit = limit;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
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
}