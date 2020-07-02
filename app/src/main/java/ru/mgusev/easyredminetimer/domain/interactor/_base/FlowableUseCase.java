package ru.mgusev.easyredminetimer.domain.interactor._base;

import io.reactivex.Flowable;
import io.reactivex.subscribers.DisposableSubscriber;

public abstract class FlowableUseCase<R, T extends BaseUseCaseParams> extends BaseReactiveUseCase {

    public FlowableUseCase(SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
    }

    protected abstract Flowable<R> buildUseCaseFlowable(T params);

    public void execute(DisposableSubscriber<R> observer, T params) {
        Flowable<R> flowable = buildUseCaseWithSchedulers(params);
        addDisposable(flowable.subscribeWith(observer));
    }

    public void execute(DisposableSubscriber<R> observer) {
        Flowable<R> flowable = buildUseCaseWithSchedulers(null);
        addDisposable(flowable.subscribeWith(observer));
    }

    private Flowable<R> buildUseCaseWithSchedulers(T params) {
        return buildUseCaseFlowable(params)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui());
    }
}
