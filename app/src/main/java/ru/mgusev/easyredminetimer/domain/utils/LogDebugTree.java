package ru.mgusev.meduza.domain.utils;

import org.jetbrains.annotations.NotNull;

import timber.log.Timber;

public class LogDebugTree extends Timber.DebugTree {
    @Override
    protected String createStackElementTag(@NotNull StackTraceElement element) {
        return String.format("(%s:%s)#%s",
                element.getFileName(),
                element.getLineNumber(),
                element.getMethodName());
    }
}
