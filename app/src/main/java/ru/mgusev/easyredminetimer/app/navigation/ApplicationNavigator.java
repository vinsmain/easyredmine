package ru.mgusev.easyredminetimer.app.navigation;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import ru.mgusev.easyredminetimer.app.ui._base.transitions.ForwardEnterTransition;
import ru.mgusev.easyredminetimer.app.ui._base.transitions.ForwardExitTransition;
import ru.mgusev.easyredminetimer.app.ui._base.transitions.ForwardReenterTransition;
import ru.mgusev.easyredminetimer.app.ui._base.transitions.ForwardReturnTransition;
import ru.mgusev.easyredminetimer.app.ui._base.transitions.ReplaceEnterTransition;
import ru.mgusev.easyredminetimer.app.ui._base.transitions.ReplaceExitTransition;
import ru.mgusev.easyredminetimer.app.ui._base.transitions.ReplaceReenterTransition;
import ru.mgusev.easyredminetimer.app.ui._base.transitions.ReplaceReturnTransition;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Forward;
import ru.terrakok.cicerone.commands.Replace;

public class ApplicationNavigator extends SupportAppNavigator {

    public ApplicationNavigator(FragmentActivity activity, FragmentManager fragmentManager, int containerId) {
        super(activity, fragmentManager, containerId);
    }

    @Override
    protected void setupFragmentTransaction(Command command, Fragment currentFragment, Fragment nextFragment, FragmentTransaction fragmentTransaction) {
        if (command instanceof Replace) {
            initReplaceAnimation(currentFragment, nextFragment);
        } else if (command instanceof Forward) {
            initForwardAnimation(currentFragment, nextFragment);
        }
    }

    private void initReplaceAnimation(Fragment currentFragment, Fragment nextFragment) {
        if (currentFragment != null) {
            currentFragment.setExitTransition(new ReplaceExitTransition());
            currentFragment.setReenterTransition(new ReplaceReenterTransition());
        }
        nextFragment.setEnterTransition(new ReplaceEnterTransition());
        nextFragment.setReturnTransition(new ReplaceReturnTransition());
    }

    private void initForwardAnimation(Fragment currentFragment, Fragment nextFragment) {
        if (currentFragment != null) {
            currentFragment.setExitTransition(new ForwardExitTransition());
            currentFragment.setReenterTransition(new ForwardReenterTransition());
        }
        nextFragment.setEnterTransition(new ForwardEnterTransition());
        nextFragment.setReturnTransition(new ForwardReturnTransition());
    }
}