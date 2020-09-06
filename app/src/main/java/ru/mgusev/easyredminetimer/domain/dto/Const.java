package ru.mgusev.easyredminetimer.domain.dto;

import ru.mgusev.easyredminetimer.BuildConfig;

public class Const {

    public static final String ACTION = "action";
    public static final String ACTION_START = BuildConfig.APPLICATION_ID + ".start";
    public static final String ACTION_PAUSE = BuildConfig.APPLICATION_ID + ".pause";

    public static final int STATUS_START_OR_PAUSE = 0;
    public static final int STATUS_START = 1;
    public static final int STATUS_PAUSE = 2;
    public static final int STATUS_STOP = 3;

}
