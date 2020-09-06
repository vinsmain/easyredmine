package ru.mgusev.easyredminetimer.app.ui.report;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

import java.util.List;

import butterknife.BindView;
import ru.mgusev.easyredminetimer.R;
import ru.mgusev.easyredminetimer.app.presentation.base.ResourceManager;
import ru.mgusev.easyredminetimer.app.ui._base.recycler.adapter.BaseAdapter;
import ru.mgusev.easyredminetimer.app.ui._base.recycler.adapter.BaseViewHolder;
import ru.mgusev.easyredminetimer.domain.dto.task.Task;
import timber.log.Timber;

public class ReportTaskListViewHolder extends BaseViewHolder<Task> {

    @BindView(R.id.textProjectName)
    AppCompatTextView textProjectName;
    @BindView(R.id.textActivityName)
    AppCompatTextView textActivityName;
    @BindView(R.id.textComment)
    AppCompatTextView textComment;
    @BindView(R.id.textTime)
    AppCompatTextView textTime;

    @BindView(R.id.viewItemClick)
    View viewItemClick;

    private ResourceManager resourceManager;

    public ReportTaskListViewHolder(View itemView, BaseAdapter.OnItemClick<Task> itemClick, ResourceManager resourceManager) {
        super(itemView, itemClick);
        this.resourceManager = resourceManager;
    }

    @Override
    public void fill(Task item, boolean isLast) {
        if (itemClick != null) {
            viewItemClick.setOnClickListener(v -> itemClick.onItemClick(item));
        }
        textProjectName.setText(item.getProjectName());
        textActivityName.setText(item.getActivityName());
        setComment(item.getComments());
        textTime.setText(String.format("%s ч.", item.getHours()));
    }

    @Override
    public void fill(Task item, boolean isLast, List<Object> payloads) {
        if (payloads.isEmpty()) {
            fill(item, isLast);
        } else {
            Timber.d(String.valueOf(payloads.size()));
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
                        case Task.KEY_COMMENTS:
                            setComment(item.getComments());
                            break;
                        case Task.KEY_TIME:
                            textTime.setText(String.format("%s ч.", item.getHours()));
                            break;
                    }
                }
            }
        }
    }

    @Override
    public void clear() {

    }

    private void setComment(String comment) {
        textComment.setVisibility(comment.equals("") ? View.GONE : View.VISIBLE);
        textComment.setText(comment);
    }
}