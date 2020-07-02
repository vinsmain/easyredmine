package ru.mgusev.easyredminetimer.app.ui._base.recycler.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import ru.mgusev.easyredminetimer.R;

public abstract class BaseAdapter<VH extends BaseViewHolder, T extends Serializable> extends RecyclerView.Adapter<BaseViewHolder> {

    private final String errorString;
    private final String noDataString;
    protected List<T> items;
    protected OnItemClick<T> itemClick;
    protected OnErrorRetryButtonClickListener errorListener;
    private State state;

    private @LayoutRes
    int viewAdapterLoader;
    private @LayoutRes
    int viewAdapterError;
    private @LayoutRes
    int viewAdapterNoData;

    public BaseAdapter(String errorString, String noDataString) {
        this.errorString = errorString;
        this.noDataString = noDataString;
        this.items = new ArrayList<>();
        this.state = State.LOADING;

        viewAdapterLoader = R.layout.view_adapter_loader;
        viewAdapterError = R.layout.view_adapter_error;
        viewAdapterNoData = R.layout.view_adapter_no_data;
    }

    public void setViewAdapterNoData(int viewAdapterNoData) {
        this.viewAdapterNoData = viewAdapterNoData;
    }

    public void setItemClick(OnItemClick<T> itemClick) {
        this.itemClick = itemClick;
    }

    public void setErrorListener(OnErrorRetryButtonClickListener errorListener) {
        this.errorListener = errorListener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == State.LOADING.value) {
            return new LoadingViewHolder(inflater.inflate(viewAdapterLoader, parent, false));
        } else if (viewType == State.ERROR.value) {
            return new ErrorViewHolder(inflater.inflate(viewAdapterError, parent, false), errorListener);
        } else if (viewType == State.EMPTY.value) {
            return new EmptyViewHolder(inflater.inflate(viewAdapterNoData, parent, false));
        } else {
            return getItemViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if (getItemViewType(position) == State.ERROR.value || getItemViewType(position) == State.EMPTY.value) {
            holder.fill((String) item(position), position == getItemCount() - 1);
        } else if (getItemViewType(position) == State.LOADING.value) {
            holder.fill(null, position == getItemCount() - 1);
        } else holder.fill((T) item(position), position == getItemCount() - 1);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (getItemViewType(position) == State.ERROR.value || getItemViewType(position) == State.EMPTY.value) {
            holder.fill((String) item(position), position == getItemCount() - 1, payloads);
        } else if (getItemViewType(position) == State.LOADING.value) {
            holder.fill(null, position == getItemCount() - 1, payloads);
        } else holder.fill((T) item(position), position == getItemCount() - 1, payloads);
    }

    @Override
    public void onViewRecycled(@NonNull BaseViewHolder holder) {
        holder.clear();
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1 && state != State.DATA) {
            return state.value;
        } else {
            return State.DATA.value;
        }
    }

    @Override
    public int getItemCount() {
        if (state == State.DATA) {
            return items.size();
        } else {
            return items.size() + 1;
        }
    }

    protected Object item(int position) {
        if (state == State.DATA) {
            return items.get(position);
        } else if (getItemCount() - 1 == position) {
            if (state == State.EMPTY) {
                return noDataString;
            } else if (state == State.ERROR) {
                return errorString;
            } else {
                return null;
            }
        } else {
            return items.get(position);
        }
    }

    private void showError() {
        //if (state == State.LOADING) {
            state = State.ERROR;
            notifyItemChanged(getItemCount() - 1);
        //}
    }

    private void showEmpty() {
        if (state == State.LOADING) {
            state = State.EMPTY;
            notifyItemChanged(getItemCount() - 1);
        }
    }

    public void showLoading() {
        if (state == State.ERROR) {
            state = State.LOADING;
            notifyItemChanged(getItemCount() - 1);
        } else if (state == State.DATA) {
            state = State.LOADING;
            int position = getItemCount() - 1;
            notifyItemInserted(position);
        }
    }

    public void error() {
        if (state == State.LOADING) {
            state = State.ERROR;
            notifyItemChanged(getItemCount() - 1);
        }
    }

    public void refresh() {
        this.items.clear();
        state = State.LOADING;
        notifyDataSetChanged();
    }

    public void setItems(List<T> items) {
        if (items == null) {
            this.items.clear();
            notifyDataSetChanged(); //без этого ошибка Inconsistency detected. Invalid item position
            showError();
        } else if (items.isEmpty()) {
            if (this.items.size() == 0) {
                showEmpty();
            } else {
                int oldCount = this.items.size();
                this.items.clear();
                notifyItemRangeRemoved(0, oldCount);
                state = State.EMPTY;
                notifyItemInserted(0);
            }
        } else {
            if (state != State.DATA) {
                notifyItemRemoved(getItemCount() - 1);
            }
            state = State.DATA;

            DiffUtilCallback<T> callback = getDiffUtils(this.items, items);
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
            result.dispatchUpdatesTo(this);

            this.items.clear();
            for (T obj : items) {
                this.items.add(SerializationUtils.clone(obj));
            }
        }
    }

    public void onRefresh() {
        this.items.clear();
    }

    public abstract DiffUtilCallback<T> getDiffUtils(List<T> oldItems, List<T> newItems);

    public abstract VH getItemViewHolder(@NonNull ViewGroup parent, int viewType);

    public enum State {
        LOADING(0),
        ERROR(1),
        EMPTY(2),
        DATA(3);

        int value;

        State(int value) {
            this.value = value;
        }

        public int value() {
            return value;
        }
    }

    public interface OnItemClick<T> {
        void onItemClick(T item);
    }

    public interface OnErrorRetryButtonClickListener {
        void onErrorRetryClick();
    }

    public abstract static class DiffUtilCallback<T> extends DiffUtil.Callback {

        private final List<T> oldItems;
        private final List<T> newItems;

        public DiffUtilCallback(List<T> oldItems, List<T> newItems) {
            this.oldItems = oldItems;
            this.newItems = newItems;
        }

        @Override
        public int getOldListSize() {
            return oldItems.size();
        }

        @Override
        public int getNewListSize() {
            return newItems.size();
        }
    }

    public class LoadingViewHolder extends BaseViewHolder {

        public LoadingViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void fill(Object item, boolean isLast) {

        }

        @Override
        public void fill(Object item, boolean isLast, List payloads) {

        }

        @Override
        public void clear() {

        }
    }

    public class ErrorViewHolder extends BaseViewHolder<String> {

        @BindView(R.id.textError)
        AppCompatTextView textError;

        private OnErrorRetryButtonClickListener errorListener;

        public ErrorViewHolder(View itemView, OnErrorRetryButtonClickListener errorListener) {
            super(itemView);
            this.errorListener = errorListener;
        }

        @OnClick(R.id.buttonRetry)
        void onButtonRetryClick() {
            errorListener.onErrorRetryClick();
        }

        @Override
        public void fill(String item, boolean isLast) {
            textError.setText(item);
        }

        @Override
        public void fill(String item, boolean isLast, List<Object> payloads) {
            textError.setText(item);
        }

        @Override
        public void clear() {

        }
    }

    public class EmptyViewHolder extends BaseViewHolder<String> {

        @BindView(R.id.text)
        AppCompatTextView text;

        public EmptyViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void fill(String item, boolean isLast) {
            text.setText(item);
        }

        @Override
        public void fill(String item, boolean isLast, List<Object> payloads) {
            text.setText(item);
        }

        @Override
        public void clear() {

        }
    }
}