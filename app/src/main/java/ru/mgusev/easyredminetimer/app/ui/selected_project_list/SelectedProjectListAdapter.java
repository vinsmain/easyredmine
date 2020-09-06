package ru.mgusev.easyredminetimer.app.ui.selected_project_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import ru.mgusev.easyredminetimer.R;
import ru.mgusev.easyredminetimer.app.presentation.base.ResourceManager;
import ru.mgusev.easyredminetimer.app.ui._base.recycler.adapter.BaseAdapter;
import ru.mgusev.easyredminetimer.domain.dto.project.Project;


public class SelectedProjectListAdapter extends BaseAdapter<SelectedProjectListViewHolder, Project> {

    private ResourceManager resourceManager;

    public SelectedProjectListAdapter(String errorString, String noDataString, ResourceManager resourceManager) {
        super(errorString, noDataString);
        this.resourceManager = resourceManager;
    }

    @Override
    public DiffUtilCallback<Project> getDiffUtils(List<Project> oldItems, List<Project> newItems) {
        return new DiffUtilCallback<Project>(oldItems, newItems) {
            @Override
            public boolean areItemsTheSame(int i, int i1) {
                Project oldItem = oldItems.get(i);
                Project newItem = newItems.get(i1);

                return oldItem != null && newItem != null && oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(int i, int i1) {
                Project oldItem = oldItems.get(i);
                Project newItem = newItems.get(i1);

                return  oldItem.getName().equals(newItem.getName());
            }

            @Nullable
            @Override
            public Object getChangePayload(int i, int i1) {
                Project oldItem = oldItems.get(i);
                Project newItem = newItems.get(i1);

                Bundle diffBundle = new Bundle();

                if (oldItem.getId() != newItem.getId()) {
                    return null;
                }
                if (!oldItem.getName().equals(newItem.getName())) {
                    diffBundle.putString(Project.KEY_NAME, newItem.getName());
                }
                if (diffBundle.size() == 0) return null;
                return diffBundle;
            }
        };
    }

    @Override
    public SelectedProjectListViewHolder getItemViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SelectedProjectListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_selected_project_item, parent, false), itemClick, resourceManager);
    }

    public void invalidateItem(Project item) {
        int position = 0;
        for (Project project : items) {
            if (project.getId() == item.getId()) {
                position = items.indexOf(item);
            }
        }
        notifyItemChanged(position);
    }
}