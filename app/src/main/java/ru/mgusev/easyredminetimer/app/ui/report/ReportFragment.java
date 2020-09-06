package ru.mgusev.easyredminetimer.app.ui.report;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.archit.calendardaterangepicker.customviews.CalendarListener;
import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.mgusev.easyredminetimer.R;
import ru.mgusev.easyredminetimer.app.navigation.RouterProvider;
import ru.mgusev.easyredminetimer.app.presentation.base.ResourceManager;
import ru.mgusev.easyredminetimer.app.presentation.report.ReportPresenter;
import ru.mgusev.easyredminetimer.app.presentation.report.ReportView;
import ru.mgusev.easyredminetimer.app.ui._base.BaseFragment;
import ru.mgusev.easyredminetimer.app.ui._base.Layout;
import ru.mgusev.easyredminetimer.app.ui._base.formatter.DateTimeFormatter;
import ru.mgusev.easyredminetimer.data.local.pref.LocalStorage;
import ru.mgusev.easyredminetimer.domain.dto.task.Task;
import ru.mgusev.easyredminetimer.domain.interactor.task.CreateTimeSpentEntryUseCase;
import ru.mgusev.easyredminetimer.domain.interactor.task.GetStoppedTaskListUseCase;
import ru.terrakok.cicerone.Router;
import timber.log.Timber;

@Layout(id = R.layout.fragment_report)
public class ReportFragment extends BaseFragment implements ReportView, RouterProvider, CalendarListener {

    @BindView(R.id.rvTaskList)
    RecyclerView rvTaskList;
    @BindView(R.id.dateRangePicker)
    DateRangeCalendarView dateRangePicker;
    @BindView(R.id.textDateFrom)
    AppCompatTextView textDateFrom;
    @BindView(R.id.textDateTo)
    AppCompatTextView textDateTo;

    @Inject
    ResourceManager resourceManager;

    @InjectPresenter
    ReportPresenter presenter;

    @Inject
    Router router;

    @Inject
    GetStoppedTaskListUseCase getStoppedTaskListUseCase;

    @Inject
    CreateTimeSpentEntryUseCase createTimeSpentEntryUseCase;

    @Inject
    LocalStorage localStorage;

    @ProvidePresenter
    ReportPresenter providePresenter() {
        return new ReportPresenter(router, resourceManager, getStoppedTaskListUseCase, createTimeSpentEntryUseCase, localStorage);
    }

    public static ReportFragment getInstance() {
        return new ReportFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rvTaskList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvTaskList.setHasFixedSize(true);
        rvTaskList.setAdapter(new ReportTaskListAdapter(
                getString(R.string.adapter_base_action_retry),
                getString(R.string.adapter_base_text_no_data),
                resourceManager));
        //((ReportTaskListAdapter) rvTaskList.getAdapter()).setItemClick(item -> presenter.onItemClicked(item));

        dateRangePicker.setCalendarListener(this);
    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.selected_project_list_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit_selected_projects:
                presenter.sendReport();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showList(List<Task> list) {
        ((ReportTaskListAdapter) rvTaskList.getAdapter()).setItems(list);
    }

    @Override
    public void invalidateListItem(Task item) {
        ((ReportTaskListAdapter) rvTaskList.getAdapter()).invalidateItem(item);
    }


    @Override
    public Router getRouter() {
        return router;
    }

    @Override
    public void onDateRangeSelected(@NotNull Calendar calendar, @NotNull Calendar calendar1) {
        textDateFrom.setText(DateTimeFormatter.getDateFormatted(calendar));
        textDateTo.setText(DateTimeFormatter.getDateFormatted(calendar1));
    }

    @Override
    public void onFirstDateSelected(@NotNull Calendar calendar) {
        textDateTo.setText("");
        textDateFrom.setText(DateTimeFormatter.getDateFormatted(calendar));
    }

    @Override
    public void setPreselectedDates(Calendar dateFrom, Calendar dateTo) {
        dateRangePicker.setSelectedDateRange(dateFrom, dateTo);
        textDateFrom.setText(DateTimeFormatter.getDateFormatted(dateFrom));
        textDateTo.setText(DateTimeFormatter.getDateFormatted(dateTo));
    }
}