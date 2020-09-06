package ru.mgusev.easyredminetimer.app.presentation;

import android.content.Intent;

import moxy.InjectViewState;

import ru.mgusev.easyredminetimer.app.navigation.Screens;
import ru.mgusev.easyredminetimer.app.presentation.base.BasePresenter;
import ru.mgusev.easyredminetimer.app.presentation.base.BaseView;
import ru.mgusev.easyredminetimer.app.presentation.base.ResourceManager;
import ru.mgusev.easyredminetimer.data.local.pref.LocalStorage;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class ApplicationPresenter extends BasePresenter<BaseView> {

    private Intent intent;
    private LocalStorage localStorage;

    public ApplicationPresenter(Router router, ResourceManager resourceManager, Intent intent, LocalStorage localStorage) {
        super(router, resourceManager);
        this.intent = intent;
        this.localStorage = localStorage;
    }

    @Override
    protected void onFirstViewAttach() {
        if (localStorage.hasApiToken())
            router.replaceScreen(new Screens.MainScreen());
        else
            router.replaceScreen(new Screens.RequestTokenScreen());
    }
}