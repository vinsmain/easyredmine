package ru.mgusev.easyredminetimer.app.ui._base.transitions;

import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionSet;
import android.transition.Visibility;
import android.view.Gravity;
import android.view.animation.AccelerateInterpolator;

public class ForwardReenterTransition extends TransitionSet {

    public ForwardReenterTransition() {
        Fade fade = new Fade();
        fade.setMode(Visibility.MODE_IN);
        fade.setInterpolator(new AccelerateInterpolator());
        fade.setDuration(300);
        addTransition(fade);

        Slide slide = new Slide();
        slide.setMode(Visibility.MODE_IN);
        slide.setSlideEdge(Gravity.START);
        slide.setDuration(300);
        addTransition(slide);

        setOrdering(ORDERING_TOGETHER);
    }
}
