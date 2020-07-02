package ru.mgusev.easyredminetimer.domain.throwable;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

public class ResponseThrowable extends Throwable {

    private String message;
    private String error;
    @SerializedName("error_description")
    private String errorDescription;
    private int code;

    public ResponseThrowable(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public ResponseThrowable(String error, String errorDescription) {
        this.error = error;
        this.errorDescription = errorDescription;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    @Override
    public String getMessage() {
        return !TextUtils.isEmpty(message) ? message : errorDescription;
    }
}
