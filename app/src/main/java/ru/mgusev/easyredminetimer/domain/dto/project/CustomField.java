package ru.mgusev.easyredminetimer.domain.dto.project;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CustomField implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("internal_name")
    @Expose
    private Object internalName;
    @SerializedName("field_format")
    @Expose
    private String fieldFormat;
    @SerializedName("value")
    @Expose
    private String value;

    public CustomField() {
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

    public Object getInternalName() {
        return internalName;
    }

    public void setInternalName(Object internalName) {
        this.internalName = internalName;
    }

    public String getFieldFormat() {
        return fieldFormat;
    }

    public void setFieldFormat(String fieldFormat) {
        this.fieldFormat = fieldFormat;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
