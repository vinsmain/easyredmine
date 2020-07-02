package ru.mgusev.easyredminetimer.app.presentation.request_token;

import moxy.InjectViewState;
import ru.mgusev.easyredminetimer.app.navigation.Screens;
import ru.mgusev.easyredminetimer.app.presentation.base.BasePresenter;
import ru.mgusev.easyredminetimer.app.presentation.base.ResourceManager;
import ru.mgusev.easyredminetimer.data.local.pref.LocalStorage;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class RequestTokenPresenter extends BasePresenter<RequestTokenView> {

    /**
     * 1555c02cbc52fca31c50383f437bad3cdb73b2ee
     */

    private LocalStorage localStorage;

    public RequestTokenPresenter(Router router, ResourceManager resourceManager, LocalStorage localStorage) {
        super(router, resourceManager);
        this.localStorage = localStorage;
    }

    @Override
    protected void onFirstViewAttach() {

    }

    public void onBtnSaveOnClick(String apiToken) {
        if (!apiToken.trim().equals("")) {
            localStorage.saveApiToken(apiToken.trim());
            router.replaceScreen(new Screens.ProjectListScreen());
        }
    }
}
