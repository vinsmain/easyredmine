package ru.mgusev.easyredminetimer.app.ui.main;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

import ru.mgusev.easyredminetimer.R;
import ru.mgusev.easyredminetimer.app.navigation.RouterProvider;
import ru.mgusev.easyredminetimer.app.presentation.base.ResourceManager;
import ru.mgusev.easyredminetimer.app.presentation.main.MainPresenter;
import ru.mgusev.easyredminetimer.app.presentation.main.MainView;
import ru.mgusev.easyredminetimer.app.ui._base.BaseFragment;
import ru.mgusev.easyredminetimer.app.ui._base.Layout;
import ru.terrakok.cicerone.Router;

@Layout(id = R.layout.fragment_main)
public class MainFragment extends BaseFragment implements MainView, RouterProvider {

    @BindView(R.id.mainFragmentContainer)
    FrameLayout fragmentContainer;

    @Inject
    ResourceManager resourceManager;

    @InjectPresenter
    MainPresenter presenter;

    @Inject
    Router router;

    @ProvidePresenter
    MainPresenter providePresenter() {
        return new MainPresenter(router, resourceManager);
    }

    public static MainFragment getInstance() {
        return new MainFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //bottomNavigationView.setOnNavigationItemSelectedListener(this);
//        if (getChildFragmentManager().findFragmentById(R.id.mainFragmentContainer) == null) {
//            currentTab = TAB_NEWS;
//
//        } else {
//            bottomNavigationView.getMenu().getItem(0).setChecked(true);
//        }
        //bottomNavigationView.setSelectedItemId(bottomNavigationView.getMenu().getItem(1).getItemId());
    }

    private void initTabFragment(int position) {
        FragmentManager fm = getChildFragmentManager();
        Fragment currentFragment = null;
        List<Fragment> fragments = fm.getFragments();
        if (fragments != null) {
            for (Fragment f : fragments) {
                if (f.isVisible()) {
                    currentFragment = f;
                    break;
                }
            }
        }
        Fragment newFragment = fm.findFragmentByTag(String.valueOf(position));

        if (currentFragment != null && newFragment != null && currentFragment == newFragment)
            return;

        FragmentTransaction transaction = fm.beginTransaction();
//        if (newFragment == null) {
//            Fragment fragment = new Screens.TabScreen(position, new Category(resourceManager.getString(R.string.apiCategoryPodcasts))).getFragment();
//            if (fragment != null) {
//                transaction.add(R.id.mainFragmentContainer, fragment, String.valueOf(position));
//            }
//        }

        if (currentFragment != null) {
            ((BaseFragment) currentFragment).onHide();
            transaction.hide(currentFragment);
        }

        if (newFragment != null) {
            ((BaseFragment) newFragment).onShow();
            transaction.show(newFragment);
        }
        transaction.commitNow();
    }

//    @Override
//    public boolean onBackPressed() {
//        FragmentManager fm = getChildFragmentManager();
//        Fragment fragment = null;
//        List<Fragment> fragments = fm.getFragments();
//        if (fragments != null) {
//            for (Fragment f : fragments) {
//                if (f.isVisible()) {
//                    fragment = f;
//                    break;
//                }
//            }
//        }
//        if (fragment != null
//                && fragment instanceof BackButtonListener
//                && ((BackButtonListener) fragment).onBackPressed()) {
//            return true;
//        } else {
//            return false;
//        }
//    }

    @Override
    public Router getRouter() {
        return router;
    }

}
