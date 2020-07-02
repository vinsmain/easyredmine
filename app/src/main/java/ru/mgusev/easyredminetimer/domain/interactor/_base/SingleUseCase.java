package ru.mgusev.easyredminetimer.domain.interactor._base;

import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;

public abstract class SingleUseCase<R, T extends BaseUseCaseParams> extends BaseReactiveUseCase {

    public SingleUseCase(SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
    }

    protected abstract Single<R> buildUseCaseSingle(T params);

    public void execute(DisposableSingleObserver<R> observer, T params) {
        Single<R> single = buildUseCaseWithSchedulers(params);
        addDisposable(single.subscribeWith(observer));
    }

    public void execute(DisposableSingleObserver<R> observer) {
        Single<R> single = buildUseCaseWithSchedulers(null);
        addDisposable(single.subscribeWith(observer));
    }


    private Single<R> buildUseCaseWithSchedulers(T params) {
        return buildUseCaseSingle(params)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui());
    }
}
