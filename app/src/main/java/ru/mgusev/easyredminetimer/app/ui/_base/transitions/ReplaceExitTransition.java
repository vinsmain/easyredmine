package ru.mgusev.easyredminetimer.app.ui._base.transitions;

import android.transition.Fade;

public class ReplaceExitTransition extends Fade {

    public ReplaceExitTransition() {
        setMode(MODE_OUT);
        setDuration(150);
    }
}
