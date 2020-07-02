package ru.mgusev.easyredminetimer.app.ui._base.transitions;

import android.transition.Fade;
import android.transition.TransitionSet;
import android.transition.Visibility;
import android.view.animation.DecelerateInterpolator;

public class ForwardReturnTransition extends TransitionSet {

    public ForwardReturnTransition() {
        Fade fade = new Fade();
        fade.setMode(Visibility.MODE_OUT);
        fade.setInterpolator(new DecelerateInterpolator());
        fade.setDuration(150);
        addTransition(fade);

        setOrdering(ORDERING_TOGETHER);
    }
}
