package ru.mgusev.easyredminetimer.app.presentation.main;

import moxy.InjectViewState;
import ru.mgusev.easyredminetimer.app.presentation.base.BasePresenter;
import ru.mgusev.easyredminetimer.app.presentation.base.ResourceManager;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class MainPresenter extends BasePresenter<MainView> {

    public MainPresenter(Router router, ResourceManager resourceManager) {
        super(router, resourceManager);
    }

    @Override
    protected void onFirstViewAttach() {

    }
}
