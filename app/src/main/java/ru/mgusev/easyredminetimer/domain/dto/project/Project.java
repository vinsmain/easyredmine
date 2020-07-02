package ru.mgusev.easyredminetimer.domain.dto.project;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
public class Project implements Serializable {

    public static final String KEY_NAME = "name";
    public static final String KEY_SELECTED = "selected";

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("identifier")
    @Expose
    private String identifier;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("homepage")
    @Expose
    private String homepage;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("is_public")
    @Expose
    private Boolean isPublic;
    @SerializedName("easy_start_date")
    @Expose
    private String easyStartDate;
    @Ignore
    @SerializedName("author")
    @Expose
    private Author author;
    @SerializedName("sum_time_entries")
    @Expose
    private Double sumTimeEntries;
    @SerializedName("sum_estimated_hours")
    @Expose
    private Double sumEstimatedHours;
    @Ignore
    @SerializedName("custom_fields")
    @Expose
    private List<CustomField> customFields = null;
    @SerializedName("created_on")
    @Expose
    private String createdOn;
    @SerializedName("updated_on")
    @Expose
    private String updatedOn;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @Ignore
    @Expose
    private boolean selected;

    public Project() {
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

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getEasyStartDate() {
        return easyStartDate;
    }

    public void setEasyStartDate(String easyStartDate) {
        this.easyStartDate = easyStartDate;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Double getSumTimeEntries() {
        return sumTimeEntries;
    }

    public void setSumTimeEntries(Double sumTimeEntries) {
        this.sumTimeEntries = sumTimeEntries;
    }

    public Double getSumEstimatedHours() {
        return sumEstimatedHours;
    }

    public void setSumEstimatedHours(Double sumEstimatedHours) {
        this.sumEstimatedHours = sumEstimatedHours;
    }

    public List<CustomField> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(List<CustomField> customFields) {
        this.customFields = customFields;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
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
        return selected == project.selected &&
                Objects.equals(name, project.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, selected);
    }
}
