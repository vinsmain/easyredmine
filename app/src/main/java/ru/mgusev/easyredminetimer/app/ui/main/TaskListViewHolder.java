package ru.mgusev.easyredminetimer.app.ui.main;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import java.util.List;

import butterknife.BindView;
import ru.mgusev.easyredminetimer.R;
import ru.mgusev.easyredminetimer.app.presentation.base.ResourceManager;
import ru.mgusev.easyredminetimer.app.ui._base.formatter.DateTimeFormatter;
import ru.mgusev.easyredminetimer.app.ui._base.recycler.adapter.BaseAdapter;
import ru.mgusev.easyredminetimer.app.ui._base.recycler.adapter.BaseViewHolder;
import ru.mgusev.easyredminetimer.domain.dto.Const;
import ru.mgusev.easyredminetimer.domain.dto.task.Task;

public class TaskListViewHolder extends BaseViewHolder<Task> {

    @BindView(R.id.viewItemClick)
    View viewItemClick;
    @BindView(R.id.textProjectName)
    AppCompatTextView textProjectName;
    @BindView(R.id.textActivityName)
    AppCompatTextView textActivityName;
    @BindView(R.id.textTime)
    AppCompatTextView textTime;
    @BindView(R.id.btnStart)
    AppCompatImageView btnStart;
    @BindView(R.id.btnStop)
    AppCompatImageView btnStop;

    private ResourceManager resourceManager;

    public TaskListViewHolder(View itemView, BaseAdapter.OnItemClick<Task> itemClick, BaseAdapter.OnItemViewClick<Task> itemViewClick, ResourceManager resourceManager) {
        super(itemView, itemClick, itemViewClick);
        this.resourceManager = resourceManager;
    }

    @Override
    public void fill(Task item, boolean isLast) {
//        if (itemClick != null) {
//            viewItemClick.setOnClickListener(v -> itemClick.onItemClick(item));
//        }
        if (itemViewClick != null) {
            btnStart.setOnClickListener(v -> itemViewClick.onItemViewClick(item, btnStart));
            btnStop.setOnClickListener(v -> itemViewClick.onItemViewClick(item, btnStop));
        }
        textProjectName.setText(item.getProjectName());
        textActivityName.setText(item.getActivityName());
        textTime.setText(DateTimeFormatter.getDurationFormatted(item.getTime()));

        if (item.getStatus() == Const.STATUS_START)
            btnStart.setImageResource(R.drawable.pause);
        else
            btnStart.setImageResource(R.drawable.start);
    }

    @Override
    public void fill(Task item, boolean isLast, List<Object> payloads) {
        if (payloads.isEmpty()) {
            fill(item, isLast);
        } else {
            if (itemViewClick != null) {
                btnStart.setOnClickListener(null);
                btnStop.setOnClickListener(null);
                btnStart.setOnClickListener(v -> itemViewClick.onItemViewClick(item, btnStart));
                btnStop.setOnClickListener(v -> itemViewClick.onItemViewClick(item, btnStop));
            }
            for (int i = 0; i < payloads.size(); i++) {
                Bundle bundle = (Bundle) payloads.get(i);
                for (String key : bundle.keySet()) {
                    switch (key) {
                        case Task.KEY_PROJECT_NAME:
                            textProjectName.setText(item.getProjectName());
                            break;
                        case Task.KEY_ACTIVITY_NAME:
                            textActivityName.setText(item.getActivityName());
                            break;
                        case Task.KEY_TIME:
                            textTime.setText(DateTimeFormatter.getDurationFormatted(item.getTime()));
                            break;
                        case Task.KEY_STATUS:
                            if (item.getStatus() == Const.STATUS_START) {
                                btnStart.setImageResource(R.drawable.pause);
                            } else
                                btnStart.setImageResource(R.drawable.start);
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