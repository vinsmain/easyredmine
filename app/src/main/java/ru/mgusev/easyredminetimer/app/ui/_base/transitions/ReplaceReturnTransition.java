package ru.mgusev.easyredminetimer.app.ui._base.transitions;

import android.transition.Fade;

public class ReplaceReturnTransition extends Fade {

    public ReplaceReturnTransition() {
        setDuration(150);
        setMode(MODE_OUT);
    }
}
