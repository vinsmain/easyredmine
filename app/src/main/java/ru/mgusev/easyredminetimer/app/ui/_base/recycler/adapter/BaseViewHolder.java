package ru.mgusev.easyredminetimer.app.ui._base.recycler.adapter;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    protected BaseAdapter.OnItemClick<T> itemClick;

    public BaseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public BaseViewHolder(View itemView, BaseAdapter.OnItemClick<T> itemClick) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.itemClick = itemClick;
    }

    public abstract void fill(T item, boolean isLast);

    public abstract void fill(T item, boolean isLast, List<Object> payloads);

    public abstract void clear();
}
