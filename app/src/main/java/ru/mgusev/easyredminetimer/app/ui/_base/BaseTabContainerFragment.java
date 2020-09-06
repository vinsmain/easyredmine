package ru.mgusev.easyredminetimer.app.ui._base;

import androidx.fragment.app.Fragment;

import javax.inject.Inject;

import ru.mgusev.easyredminetimer.R;
import ru.mgusev.easyredminetimer.app.navigation.BackButtonListener;
import ru.mgusev.easyredminetimer.app.navigation.LocalCiceroneHolder;
import ru.mgusev.easyredminetimer.app.navigation.RouterProvider;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.Router;

public abstract class BaseTabContainerFragment extends BaseFragment implements BackButtonListener, RouterProvider {

    protected Navigator navigator;

    @Inject
    LocalCiceroneHolder ciceroneHolder;

    private Cicerone<Router> getCicerone() {
        return ciceroneHolder.getCicerone(getContainerName());
    }

    @Override
    public void onResume() {
        super.onResume();
        getCicerone().getNavigatorHolder().setNavigator(getNavigator());
    }

    @Override
    public void onPause() {
        getCicerone().getNavigatorHolder().removeNavigator();
        super.onPause();
    }

    @Override
    public Router getRouter() {
        return getCicerone().getRouter();
    }

    @Override
    public boolean onBackPressed() {
//        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.mainFragmentContainer);
//        if (fragment != null
//                && fragment instanceof BackButtonListener
//                && ((BackButtonListener) fragment).onBackPressed()) {
//            return true;
//        } else {
//            ((RouterProvider) getParentFragment()).getRouter().exit();
//            return true;
//        }
        return true;
    }

    protected abstract String getContainerName();

    protected abstract Navigator getNavigator();

//    protected boolean logoutIfNeed(Command command) {
//        return (command instanceof Replace && ((Replace) command).getScreen() instanceof Screens.LoginMainScreen);
//    }

    @Override
    public void onShow() {
        //BaseFragment fragment = (BaseFragment) getChildFragmentManager().findFragmentById(R.id.mainFragmentContainer);
        //if (fragment != null) fragment.onShow();
    }

    @Override
    public void onHide() {
//        BaseFragment fragment = (BaseFragment) getChildFragmentManager().findFragmentById(R.id.mainFragmentContainer);
//        if (fragment != null) fragment.onHide();
    }
}
