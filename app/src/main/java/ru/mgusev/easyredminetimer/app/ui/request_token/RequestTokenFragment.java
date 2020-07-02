package ru.mgusev.easyredminetimer.app.ui.request_token;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.mgusev.easyredminetimer.R;
import ru.mgusev.easyredminetimer.app.navigation.RouterProvider;
import ru.mgusev.easyredminetimer.app.presentation.base.ResourceManager;
import ru.mgusev.easyredminetimer.app.presentation.request_token.RequestTokenPresenter;
import ru.mgusev.easyredminetimer.app.presentation.request_token.RequestTokenView;
import ru.mgusev.easyredminetimer.app.ui._base.BaseFragment;
import ru.mgusev.easyredminetimer.app.ui._base.Layout;
import ru.mgusev.easyredminetimer.data.local.pref.LocalStorage;
import ru.terrakok.cicerone.Router;

@Layout(id = R.layout.fragment_request_token)
public class RequestTokenFragment extends BaseFragment implements RequestTokenView, RouterProvider {

    @BindView(R.id.inputApiToken)
    AppCompatEditText inputApiToken;

    @Inject
    ResourceManager resourceManager;

    @InjectPresenter
    RequestTokenPresenter presenter;

    @Inject
    Router router;

    @Inject
    LocalStorage localStorage;

    @ProvidePresenter
    RequestTokenPresenter providePresenter() {
        return new RequestTokenPresenter(router, resourceManager, localStorage);
    }

    public static RequestTokenFragment getInstance() {
        return new RequestTokenFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @OnClick(R.id.btnSaveApiToken)
    public void onClick() {
        presenter.onBtnSaveOnClick(String.valueOf(inputApiToken.getText()));
    }

    @Override
    public Router getRouter() {
        return router;
    }

}