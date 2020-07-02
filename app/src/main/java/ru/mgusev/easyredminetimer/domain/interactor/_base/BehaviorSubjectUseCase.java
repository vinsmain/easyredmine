package ru.mgusev.easyredminetimer.domain.interactor._base;

import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.BehaviorSubject;

public abstract class BehaviorSubjectUseCase<R, T extends BaseUseCaseParams> extends BaseReactiveUseCase {

    private BehaviorSubject<R> subject;

    public BehaviorSubjectUseCase(SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
    }

    protected abstract BehaviorSubject<R> buildUseCaseBehaviorSubject(T params);

    public void execute(DisposableObserver<R> observer, T params) {
        subject = buildUseCaseWithSchedulers(params);
        addDisposable(subject.subscribeWith(observer));
    }

    private BehaviorSubject<R> buildUseCaseWithSchedulers(T params) {
        return buildUseCaseBehaviorSubject(params);
    }

    public void publish(R r) {
        if (subject != null)
            subject.onNext(r);
    }
}
