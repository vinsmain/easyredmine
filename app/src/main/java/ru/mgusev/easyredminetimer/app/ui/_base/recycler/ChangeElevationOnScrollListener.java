package ru.mgusev.easyredminetimer.app.ui._base.recycler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;

public class ChangeElevationOnScrollListener extends RecyclerView.OnScrollListener {

    private final AppBarLayout appBarLayout;

    public ChangeElevationOnScrollListener(AppBarLayout appBarLayout) {
        this.appBarLayout = appBarLayout;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (recyclerView.canScrollVertically(-1)) {
            appBarLayout.setElevation(4);
        } else {
            appBarLayout.setElevation(0);
        }

    }
}
