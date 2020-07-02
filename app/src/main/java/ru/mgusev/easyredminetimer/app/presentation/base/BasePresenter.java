package ru.mgusev.easyredminetimer.app.presentation.base;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import moxy.MvpPresenter;

import ru.mgusev.easyredminetimer.R;
import ru.mgusev.easyredminetimer.domain.interactor._base.BaseReactiveUseCase;
import ru.terrakok.cicerone.Router;
import timber.log.Timber;

public abstract class BasePresenter<T extends BaseView> extends MvpPresenter<T> {

    protected final Router router;
    protected final List<BaseReactiveUseCase> useCases;
    protected final ResourceManager resourceManager;

    public BasePresenter(Router router, ResourceManager resourceManager) {
        this.router = router;
        this.resourceManager = resourceManager;
        useCases = new ArrayList<>();
    }

    public void addUseCase(BaseReactiveUseCase useCase) {
        useCases.add(useCase);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (BaseReactiveUseCase useCase : useCases) {
            useCase.dispose();
        }
    }

    protected String parseThrowable(Throwable throwable) {
        Timber.e(throwable);
        if (throwable instanceof SocketTimeoutException) {
            return resourceManager.getString(R.string.error_connection_timeout);
        }

        return throwable.getLocalizedMessage() != null ? throwable.getLocalizedMessage() : resourceManager.getString(R.string.error_something_went_wrong);
    }

    protected void showError(Throwable throwable) {
        String error = parseThrowable(throwable);
        if (error != null) {
            getViewState().showMessage(error);
        }
    }
}