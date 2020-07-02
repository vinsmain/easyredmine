package ru.mgusev.easyredminetimer.domain.interactor._base;

public abstract class SynchronousUseCase<R, T extends BaseUseCaseParams> extends BaseReactiveUseCase {

    public SynchronousUseCase(SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
    }

    public abstract R execute(T params);

    public R execute() {
        return execute(null);
    }
}
