package ru.mgusev.easyredminetimer.app.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.List;

import ru.mgusev.easyredminetimer.R;
import ru.mgusev.easyredminetimer.app.presentation.base.ResourceManager;
import ru.mgusev.easyredminetimer.app.ui._base.recycler.adapter.BaseAdapter;
import ru.mgusev.easyredminetimer.domain.dto.task.Task;


public class TaskListAdapter extends BaseAdapter<TaskListViewHolder, Task> {

    private ResourceManager resourceManager;

    public TaskListAdapter(String errorString, String noDataString, ResourceManager resourceManager) {
        super(errorString, noDataString);
        this.resourceManager = resourceManager;
    }

    @Override
    public DiffUtilCallback<Task> getDiffUtils(List<Task> oldItems, List<Task> newItems) {
        return new DiffUtilCallback<Task>(oldItems, newItems) {
            @Override
            public boolean areItemsTheSame(int i, int i1) {
                Task oldItem = oldItems.get(i);
                Task newItem = newItems.get(i1);
                return oldItem != null && newItem != null && oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(int i, int i1) {
                Task oldItem = oldItems.get(i);
                Task newItem = newItems.get(i1);
                return  oldItem.getProjectId() == newItem.getProjectId() &&
                        oldItem.getActivityId() == newItem.getActivityId() &&
                        oldItem.getStatus() == newItem.getStatus() &&
                        oldItem.getTime() == newItem.getTime();
            }

            @Nullable
            @Override
            public Object getChangePayload(int i, int i1) {
                Task oldItem = oldItems.get(i);
                Task newItem = newItems.get(i1);

                Bundle diffBundle = new Bundle();

                if (oldItem.getId() != newItem.getId()) {
                    return null;
                }
                if (!oldItem.getProjectName().equals(newItem.getProjectName())) {
                    diffBundle.putString(Task.KEY_PROJECT_NAME, newItem.getProjectName());
                }
                if (!oldItem.getActivityName().equals(newItem.getActivityName())) {
                    diffBundle.putString(Task.KEY_ACTIVITY_NAME, newItem.getActivityName());
                }
                if (oldItem.getTime() != newItem.getTime()) {
                    diffBundle.putLong(Task.KEY_TIME, newItem.getTime());
                }
                if (oldItem.getStatus() != newItem.getStatus()) {
                    diffBundle.putInt(Task.KEY_STATUS, newItem.getStatus());
                }
                if (diffBundle.size() == 0) return null;
                return diffBundle;
            }
        };
    }

    @Override
    public TaskListViewHolder getItemViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_task_item, parent, false), itemClick, itemViewClick, resourceManager);
    }

    public void invalidateItem(Task item) {
        int position = 0;
        for (Task task : items) {
            if (task.getId() == item.getId()) {
                position = items.indexOf(item);
            }
        }
        notifyItemChanged(position);
    }
}