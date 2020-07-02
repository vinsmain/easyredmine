package ru.mgusev.easyredminetimer.app.presentation.project_list;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.observers.DisposableSingleObserver;
import moxy.InjectViewState;
import ru.mgusev.easyredminetimer.app.presentation.base.BasePresenter;
import ru.mgusev.easyredminetimer.app.presentation.base.ResourceManager;
import ru.mgusev.easyredminetimer.data.local.pref.LocalStorage;
import ru.mgusev.easyredminetimer.domain.dto.project.Project;
import ru.mgusev.easyredminetimer.domain.dto.project.ProjectListHolder;
import ru.mgusev.easyredminetimer.domain.interactor.project_list.GetProjectListParams;
import ru.mgusev.easyredminetimer.domain.interactor.project_list.GetProjectListUseCase;
import ru.mgusev.easyredminetimer.domain.interactor.project_list.SaveSelectedProjectListParams;
import ru.mgusev.easyredminetimer.domain.interactor.project_list.SaveSelectedProjectListUseCase;
import ru.terrakok.cicerone.Router;
import timber.log.Timber;

@InjectViewState
public class ProjectListPresenter extends BasePresenter<ProjectListView> {

    private LocalStorage localStorage;
    private GetProjectListUseCase getProjectListUseCase;
    private SaveSelectedProjectListUseCase saveSelectedProjectListUseCase;
    private List<Project> projectList;
    private int limit = 20;

    public ProjectListPresenter(Router router, ResourceManager resourceManager, LocalStorage localStorage, GetProjectListUseCase getProjectListUseCase, SaveSelectedProjectListUseCase saveSelectedProjectListUseCase) {
        super(router, resourceManager);
        this.localStorage = localStorage;
        this.getProjectListUseCase = getProjectListUseCase;
        this.saveSelectedProjectListUseCase = saveSelectedProjectListUseCase;

        projectList = new ArrayList<>();

        addUseCase(this.getProjectListUseCase);
        addUseCase(this.saveSelectedProjectListUseCase);
    }

    @Override
    protected void onFirstViewAttach() {
        loadData(new GetProjectListParams(0, limit, localStorage.getApiToken()));
    }

    public void loadData(GetProjectListParams params) {
        getProjectListUseCase.execute(new DisposableSingleObserver<ProjectListHolder>() {

            @Override
            public void onSuccess(ProjectListHolder projectListHolder) {
                projectList.addAll(projectListHolder.getProjects());
                if (projectList.size() == projectListHolder.getTotalCount())
                    getViewState().showProjectList(projectList);
                else
                    loadData(new GetProjectListParams(projectListHolder.getOffset() + limit, limit, localStorage.getApiToken()));
            }

            @Override
            public void onError(Throwable e) {
                Timber.d(e);
            }
        }, params);
    }

    public void onCardItemClicked(Project item) {
        for (Project project : projectList) {
            if (project.getId() == item.getId()) {
                project.setSelected(!project.isSelected());
            }
        }
        getViewState().showProjectList(projectList);
    }

    public void onSaveIconClicked() {
        saveSelectedProjectListUseCase.execute(new SaveSelectedProjectListParams(getSelectedProjectList()));
    }

    private List<Project> getSelectedProjectList() {
        List<Project> selectedProjectList = new ArrayList<>();
        for (Project project : projectList){
            if (project.isSelected())
                selectedProjectList.add(project);
        }
        return selectedProjectList;
    }
}