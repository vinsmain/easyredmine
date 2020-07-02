package ru.mgusev.easyredminetimer.app.presentation.base;

import android.content.Context;

import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import javax.inject.Inject;

public class ResourceManager {

    private final Context context;

    @Inject
    public ResourceManager(Context context) {
        this.context = context;
    }

    public String getString(@StringRes int stringRes) {
        return context.getResources().getString(stringRes);
    }

    public String getString(@StringRes int stringRes, Object... objects) {
        return context.getResources().getString(stringRes, objects);
    }

    public int getColor(@ColorRes int colorRes) {
        return ContextCompat.getColor(context, colorRes);
    }

    public float getDimen(@DimenRes int dimenRes) {
        return context.getResources().getDimension(dimenRes);
    }
}