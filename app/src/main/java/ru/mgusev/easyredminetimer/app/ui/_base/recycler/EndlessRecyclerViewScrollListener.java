package ru.mgusev.meduza.app.ui._base.recycler;


import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import timber.log.Timber;

public class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
    RecyclerView.LayoutManager mLayoutManager;
    private int visibleThreshold = 5;
    private int currentPage = 0;
    private int previousTotalItemCount = 0;
    private boolean loading = true;
    private int startingPageIndex = 0;

    private final Runnable onLoadMoreRunnable;

    public EndlessRecyclerViewScrollListener(Runnable onLoadMoreRunnable) {

        this.onLoadMoreRunnable = onLoadMoreRunnable;
    }

    public EndlessRecyclerViewScrollListener(RecyclerView.LayoutManager layoutManager, Runnable onLoadMoreRunnable) {
        this.mLayoutManager = layoutManager;
        this.onLoadMoreRunnable = onLoadMoreRunnable;
    }

    public EndlessRecyclerViewScrollListener(LinearLayoutManager layoutManager, Runnable onLoadMoreRunnable) {
        this.mLayoutManager = layoutManager;
        this.onLoadMoreRunnable = onLoadMoreRunnable;
    }

    public EndlessRecyclerViewScrollListener(GridLayoutManager layoutManager, Runnable onLoadMoreRunnable) {
        this.mLayoutManager = layoutManager;
        visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
        this.onLoadMoreRunnable = onLoadMoreRunnable;
    }

    public EndlessRecyclerViewScrollListener(StaggeredGridLayoutManager layoutManager, Runnable onLoadMoreRunnable) {
        this.mLayoutManager = layoutManager;
        visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
        this.onLoadMoreRunnable = onLoadMoreRunnable;
    }

    public void setLayoutManager(RecyclerView.LayoutManager pLayoutManager) {
        mLayoutManager = pLayoutManager;
    }

    public void setLayoutManager(GridLayoutManager pLayoutManager) {
        mLayoutManager = pLayoutManager;
    }

    public int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }

    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        int lastVisibleItemPosition = 0;
        int totalItemCount = mLayoutManager.getItemCount();

        if (mLayoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) mLayoutManager).findLastVisibleItemPositions(null);
            lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);
        } else if (mLayoutManager instanceof GridLayoutManager) {
            lastVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        } else if (mLayoutManager instanceof LinearLayoutManager) {
            lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        }

        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.loading = true;
            }
        }

        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
        }

        if (currentPage == 0) {
            previousTotalItemCount = 0;
        }

        if (!loading && (lastVisibleItemPosition + visibleThreshold) > totalItemCount) { //Для случая, если один из элементов в JSON - nil
            currentPage++;
            onLoadMore();
            loading = true;
        }
    }

    public void resetState() {
        this.currentPage = this.startingPageIndex;
        this.previousTotalItemCount = 0;
        this.loading = true;
    }

    private void onLoadMore() {
        if (onLoadMoreRunnable != null) {
            onLoadMoreRunnable.run();
        }
    }

}
