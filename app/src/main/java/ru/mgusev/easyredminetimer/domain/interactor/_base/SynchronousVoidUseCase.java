package ru.mgusev.easyredminetimer.domain.interactor._base;

public abstract class SynchronousVoidUseCase<T extends BaseUseCaseParams> extends BaseReactiveUseCase {

    public SynchronousVoidUseCase(SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
    }

    public abstract void execute(T params);
}
