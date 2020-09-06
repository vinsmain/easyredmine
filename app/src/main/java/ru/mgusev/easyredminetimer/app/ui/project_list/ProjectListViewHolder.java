package ru.mgusev.easyredminetimer.app.ui.project_list;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.SwitchCompat;

import java.util.List;

import butterknife.BindView;
import ru.mgusev.easyredminetimer.R;
import ru.mgusev.easyredminetimer.app.presentation.base.ResourceManager;
import ru.mgusev.easyredminetimer.app.ui._base.recycler.adapter.BaseAdapter;
import ru.mgusev.easyredminetimer.app.ui._base.recycler.adapter.BaseViewHolder;
import ru.mgusev.easyredminetimer.domain.dto.project.Project;
import timber.log.Timber;

public class ProjectListViewHolder extends BaseViewHolder<Project> {

    @BindView(R.id.switchProject)
    SwitchCompat switchProject;

    @BindView(R.id.viewItemClick)
    View viewItemClick;

    private ResourceManager resourceManager;

    public ProjectListViewHolder(View itemView, BaseAdapter.OnItemClick<Project> itemClick, ResourceManager resourceManager) {
        super(itemView, itemClick);
        this.resourceManager = resourceManager;
    }

    @Override
    public void fill(Project item, boolean isLast) {
        if (itemClick != null) {
            viewItemClick.setOnClickListener(v -> itemClick.onItemClick(item));
        }
        if (itemClick != null) {
            switchProject.setOnClickListener(v -> itemClick.onItemClick(item));
        }
        switchProject.setText(item.getName());
        switchProject.setChecked(item.isSelected());
    }

    @Override
    public void fill(Project item, boolean isLast, List<Object> payloads) {
        if (payloads.isEmpty()) {
            fill(item, isLast);
        } else {
            Timber.d(String.valueOf(payloads.size()));
            for (int i = 0; i < payloads.size(); i++) {
                Bundle bundle = (Bundle) payloads.get(i);
                for (String key : bundle.keySet()) {
                    switch (key) {
                        case Project.KEY_NAME:
                            switchProject.setText(item.getName());
                            break;
                        case Project.KEY_SELECTED:
                            switchProject.setChecked(item.isSelected());
                            break;
                    }
                }
            }
        }
    }

    @Override
    public void clear() {

    }
}