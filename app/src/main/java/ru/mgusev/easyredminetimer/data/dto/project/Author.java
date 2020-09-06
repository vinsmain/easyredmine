package ru.mgusev.easyredminetimer.data.dto.project;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Author implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("easy_external_id")
    @Expose
    private Object easyExternalId;

    public Author() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getEasyExternalId() {
        return easyExternalId;
    }

    public void setEasyExternalId(Object easyExternalId) {
        this.easyExternalId = easyExternalId;
    }
}
