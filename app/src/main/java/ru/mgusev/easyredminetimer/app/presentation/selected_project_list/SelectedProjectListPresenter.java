package ru.mgusev.easyredminetimer.app.presentation.selected_project_list;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.subscribers.DisposableSubscriber;
import moxy.InjectViewState;
import ru.mgusev.easyredminetimer.app.presentation.base.BasePresenter;
import ru.mgusev.easyredminetimer.app.presentation.base.ResourceManager;
import ru.mgusev.easyredminetimer.data.local.pref.LocalStorage;
import ru.mgusev.easyredminetimer.domain.dto.project.Project;
import ru.mgusev.easyredminetimer.domain.dto.project.SelectedProjectHolder;
import ru.mgusev.easyredminetimer.domain.interactor.selected_project_list.GetSelectedProjectListUseCase;
import ru.terrakok.cicerone.Router;
import timber.log.Timber;

@InjectViewState
public class SelectedProjectListPresenter extends BasePresenter<SelectedProjectListView> {

    private LocalStorage localStorage;
    private final SelectedProjectHolder selectedProjectHolder;
    private final GetSelectedProjectListUseCase getSelectedProjectListUseCase;

    private List<Project> projectList;

    public SelectedProjectListPresenter(Router router, ResourceManager resourceManager, LocalStorage localStorage, SelectedProjectHolder selectedProjectHolder, GetSelectedProjectListUseCase getSelectedProjectListUseCase) {
        super(router, resourceManager);
        this.localStorage = localStorage;
        this.selectedProjectHolder = selectedProjectHolder;
        this.getSelectedProjectListUseCase = getSelectedProjectListUseCase;

        projectList = new ArrayList<>();

        addUseCase(this.getSelectedProjectListUseCase);
    }

    @Override
    protected void onFirstViewAttach() {
        loadData();
    }

    public void loadData() {
        getSelectedProjectListUseCase.execute(new DisposableSubscriber<List<Project>>() {

            @Override
            public void onNext(List<Project> projects) {
                projectList.addAll(projects);
                getViewState().showProjectList(projectList);
            }

            @Override
            public void onError(Throwable e) {
                Timber.d(e);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void onCardItemClicked(Project item) {
        selectedProjectHolder.setProject(item);
        router.exit();
    }

    public void onSaveIconClicked() {
        //saveSelectedProjectListUseCase.execute(new SaveSelectedProjectListParams(getSelectedProjectList()));
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