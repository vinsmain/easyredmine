package ru.mgusev.easyredminetimer.app.ui.report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import ru.mgusev.easyredminetimer.R;
import ru.mgusev.easyredminetimer.app.presentation.base.ResourceManager;
import ru.mgusev.easyredminetimer.app.ui._base.recycler.adapter.BaseAdapter;
import ru.mgusev.easyredminetimer.domain.dto.task.Task;

public class ReportTaskListAdapter extends BaseAdapter<ReportTaskListViewHolder, Task> {

    private ResourceManager resourceManager;

    public ReportTaskListAdapter(String errorString, String noDataString, ResourceManager resourceManager) {
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

                return  oldItem.getProjectName().equals(newItem.getProjectName()) &&
                        oldItem.getActivityName().equals(newItem.getActivityName()) &&
                        oldItem.getComments().equals(newItem.getComments()) &&
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
                if (!oldItem.getComments().equals(newItem.getComments())) {
                    diffBundle.putString(Task.KEY_COMMENTS, newItem.getComments());
                }
                if (oldItem.getTime() != newItem.getTime()) {
                    diffBundle.putLong(Task.KEY_TIME, newItem.getTime());
                }
                if (diffBundle.size() == 0) return null;
                return diffBundle;
            }
        };
    }

    @Override
    public ReportTaskListViewHolder getItemViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReportTaskListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_report_item, parent, false), itemClick, resourceManager);
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