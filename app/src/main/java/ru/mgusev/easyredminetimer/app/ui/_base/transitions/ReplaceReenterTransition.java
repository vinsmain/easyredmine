package ru.mgusev.easyredminetimer.app.ui._base.transitions;

import android.transition.Fade;

public class ReplaceReenterTransition extends Fade {

    public ReplaceReenterTransition() {
        setMode(MODE_IN);
        setStartDelay(150);
        setDuration(150);
    }
}
