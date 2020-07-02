package ru.mgusev.easyredminetimer.domain.interactor._base;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

public abstract class ObservableUseCase<R, T extends BaseUseCaseParams> extends BaseReactiveUseCase {

    public ObservableUseCase(SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
    }

    protected abstract Observable<R> buildUseCaseObservable(T params);

    public void execute(DisposableObserver<R> observer, T params) {
        Observable<R> single = buildUseCaseWithSchedulers(params);
        addDisposable(single.subscribeWith(observer));
    }

    private Observable<R> buildUseCaseWithSchedulers(T params) {
        return buildUseCaseObservable(params)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui());
    }
}
