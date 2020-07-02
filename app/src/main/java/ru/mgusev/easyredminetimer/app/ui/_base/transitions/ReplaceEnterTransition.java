package ru.mgusev.easyredminetimer.app.ui._base.transitions;

import android.transition.Fade;

public class ReplaceEnterTransition extends Fade {

    public ReplaceEnterTransition() {
        setMode(MODE_IN);
        setStartDelay(150);
        setDuration(150);
    }
}
