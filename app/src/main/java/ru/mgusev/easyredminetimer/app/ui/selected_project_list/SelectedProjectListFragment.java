package ru.mgusev.easyredminetimer.app.ui.selected_project_list;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.mgusev.easyredminetimer.R;
import ru.mgusev.easyredminetimer.app.navigation.RouterProvider;
import ru.mgusev.easyredminetimer.app.presentation.base.ResourceManager;
import ru.mgusev.easyredminetimer.app.presentation.project_list.ProjectListPresenter;
import ru.mgusev.easyredminetimer.app.presentation.project_list.ProjectListView;
import ru.mgusev.easyredminetimer.app.ui._base.BaseFragment;
import ru.mgusev.easyredminetimer.app.ui._base.Layout;
import ru.mgusev.easyredminetimer.app.ui.project_list.ProjectListAdapter;
import ru.mgusev.easyredminetimer.data.local.pref.LocalStorage;
import ru.mgusev.easyredminetimer.domain.dto.project.Project;
import ru.mgusev.easyredminetimer.domain.interactor.project_list.GetProjectListUseCase;
import ru.mgusev.easyredminetimer.domain.interactor.project_list.SaveSelectedProjectListUseCase;
import ru.terrakok.cicerone.Router;

@Layout(id = R.layout.fragment_project_list)
public class SelectedProjectListFragment extends BaseFragment implements ProjectListView, RouterProvider {

    @BindView(R.id.rvProjectList)
    RecyclerView rvProjectList;

    @Inject
    ResourceManager resourceManager;

    @InjectPresenter
    ProjectListPresenter presenter;

    @Inject
    Router router;

    @Inject
    LocalStorage localStorage;

    @Inject
    GetProjectListUseCase getProjectListUseCase;

    @Inject
    SaveSelectedProjectListUseCase saveSelectedProjectListUseCase;

    @ProvidePresenter
    ProjectListPresenter providePresenter() {
        return new ProjectListPresenter(router, resourceManager, localStorage, getProjectListUseCase, saveSelectedProjectListUseCase);
    }

    public static SelectedProjectListFragment getInstance() {
        return new SelectedProjectListFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rvProjectList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvProjectList.setHasFixedSize(true);
        rvProjectList.setAdapter(new ProjectListAdapter(
                getString(R.string.adapter_base_action_retry),
                getString(R.string.adapter_base_text_no_data),
                resourceManager));
        ((ProjectListAdapter) rvProjectList.getAdapter()).setItemClick(item -> presenter.onCardItemClicked(item));
    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.project_list_menu, menu);
        //addToBookmarks = menu.getItem(0);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save_selected_projects:
                presenter.onSaveIconClicked();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showProjectList(List<Project> list) {
        ((ProjectListAdapter) rvProjectList.getAdapter()).setItems(list);
    }

    @Override
    public void invalidateProjectItem(Project project) {
        ((ProjectListAdapter) rvProjectList.getAdapter()).invalidateItem(project);
    }


    @Override
    public Router getRouter() {
        return router;
    }

}