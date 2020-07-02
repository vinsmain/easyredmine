package ru.mgusev.easyredminetimer.domain.interactor._base;

import io.reactivex.Scheduler;

public interface SchedulerProvider {

    Scheduler io();

    Scheduler computation();

    Scheduler ui();
}
