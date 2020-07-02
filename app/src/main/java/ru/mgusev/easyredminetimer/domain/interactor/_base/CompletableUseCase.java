package ru.mgusev.easyredminetimer.domain.interactor._base;

import io.reactivex.Completable;
import io.reactivex.observers.DisposableCompletableObserver;

public abstract class CompletableUseCase<T extends BaseUseCaseParams> extends BaseReactiveUseCase {

    public CompletableUseCase(SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
    }

    protected abstract Completable buildUseCaseCompletable(T params);

    public void execute(DisposableCompletableObserver observer, T params) {
        Completable completable = buildUseCaseWithSchedulers(params);
        addDisposable(completable.subscribeWith(observer));
    }

    public void execute(DisposableCompletableObserver observer) {
        Completable completable = buildUseCaseWithSchedulers(null);
        addDisposable(completable.subscribeWith(observer));
    }

    public void execute() {
        Completable completable = buildUseCaseWithSchedulers(null);
        addDisposable(completable.subscribeWith(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
        }));
    }

    public void execute(T params) {
        Completable completable = buildUseCaseWithSchedulers(params);
        addDisposable(completable.subscribeWith(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
        }));
    }

    private Completable buildUseCaseWithSchedulers(T params) {
        return buildUseCaseCompletable(params)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui());
    }
}
