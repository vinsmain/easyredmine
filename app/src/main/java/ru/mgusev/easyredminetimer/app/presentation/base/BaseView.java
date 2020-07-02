package ru.mgusev.easyredminetimer.app.presentation.base;

import moxy.MvpView;
import moxy.viewstate.strategy.OneExecutionStateStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface BaseView extends MvpView {
    @StateStrategyType(OneExecutionStateStrategy.class)
    void showMessage(String message);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showSuccessToast(String message);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showErrorToast(String message);
}