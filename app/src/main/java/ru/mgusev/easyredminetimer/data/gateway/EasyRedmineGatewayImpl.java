package ru.mgusev.easyredminetimer.data.gateway;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import ru.mgusev.easyredminetimer.app.ui._base.formatter.DateTimeFormatter;
import ru.mgusev.easyredminetimer.data.dto.time_entry.TimeEntry;
import ru.mgusev.easyredminetimer.data.dto.time_entry.TimeSpentEntryBody;
import ru.mgusev.easyredminetimer.data.local.database.AppDatabase;
import ru.mgusev.easyredminetimer.data.network.easy_redmine.EasyRedmineNetwork;
import ru.mgusev.easyredminetimer.domain.dto.project.Project;
import ru.mgusev.easyredminetimer.domain.dto.project.ProjectListHolder;
import ru.mgusev.easyredminetimer.domain.dto.task.Task;
import ru.mgusev.easyredminetimer.domain.dto.task.Time;
import ru.mgusev.easyredminetimer.domain.gateway.EasyRedmineGateway;
import timber.log.Timber;

public class EasyRedmineGatewayImpl implements EasyRedmineGateway {

    private final EasyRedmineNetwork network;
    private final AppDatabase database;

    public EasyRedmineGatewayImpl(EasyRedmineNetwork network, AppDatabase database) {
        this.network = network;
        this.database = database;
    }

    @Override
    public Single<ProjectListHolder> getProjectListHolder(Map<String, Object> params) {
        return network.getProjectList(params);
    }

    @Override
    public Completable insertSelectedProjectList(List<Project> list) {
        return database.projectDao().insertSelectedProjectList(list);
    }

    @Override
    public Flowable<List<Project>> getSelectedProjectList() {
        return database.projectDao().getSelectedProjectList();
    }

    @Override
    public Flowable<List<Task>> getTaskList() {
        return database.taskDao().getNotStoppedTaskList()
                .map(list -> {
                    for (Task taskItem : list) {
                        taskItem.setProjectName(database.projectDao().getProjectNameById(taskItem.getProjectId()));
                        taskItem.setActivityName(database.activityDao().getActivityNameById(taskItem.getActivityId()));
                        taskItem.setTimeList(database.timeDao().getTimeListByTaskId(taskItem.getId()));
                    }
                    return list;
                });
    }

//    @Override
//    public Flowable<List<Task>> getStoppedTaskList() {
//        return database.taskDao().getStoppedTaskList()
//                .map(list -> {
//                    for (Task taskItem : list) {
//                        taskItem.setProjectName(database.projectDao().getProjectNameById(taskItem.getProjectId()));
//                        taskItem.setActivityName(database.activityDao().getActivityNameById(taskItem.getActivityId()));
//                        taskItem.setTimeList(database.timeDao().getTimeListByTaskId(taskItem.getId()));
//                    }
//                    return list;
//                });
//    }

    @Override
    public Single<Task> insertTask(Task task) {
        return database.taskDao().insert(task)
                .flatMap(id -> database.taskDao().getTaskById(id))
                .map(taskItem -> {
                    taskItem.setProjectName(database.projectDao().getProjectNameById(taskItem.getProjectId()));
                    taskItem.setActivityName(database.activityDao().getActivityNameById(taskItem.getActivityId()));
                    taskItem.setTimeList(database.timeDao().getTimeListByTaskId(taskItem.getId()));
                    return taskItem;
                });
    }

    @Override
    public Completable updateTask(Task task) {
        return database.timeDao().insert(task.getTimeList()).andThen(database.taskDao().update(task));

    }

    @Override
    public Completable deleteTask(Task task) {
        return database.timeDao().delete(task.getTimeList()).andThen(database.taskDao().delete(task));
    }

    @Override
    public Completable createTimeSentEntry(String apiKey, List<Task> list) {
        return Observable.fromIterable(list)
                .flatMapCompletable(task -> network.createTimeSpentEntry(apiKey,
                        new TimeSpentEntryBody(new TimeEntry(task.getProjectId(),
                                task.getActivityId(),
                                task.getHours(),
                                task.getComments(),
                                task.getSpentOn()))));
    }

    @Override
    public Single<List<Task>> getTaskListByPeriod(ZonedDateTime dateFrom, ZonedDateTime dateTo) {
        return database.timeDao().getTimeListByPeriod(dateFrom, dateTo)
                .toObservable()
                .map(list1 -> {
                    Timber.d(String.valueOf(list1.size()));
                    return list1;
                })
                .flatMapIterable(list -> list)
                .flatMap(item -> database.taskDao().getStoppedTaskById(item.getTaskId())
                        .map(task -> {
                            Timber.d(task.getId() + " " + item.getDate());
                            task.setProjectName(database.projectDao().getProjectNameById(task.getProjectId()));
                            task.setActivityName(database.activityDao().getActivityNameById(task.getActivityId()));
                            List<Time> timeList = database.timeDao().getTimeListByTaskIdByPeriod(task.getId(), dateFrom, dateTo);
                            Timber.d(timeList.toString());
                            task.setTimeList(timeList);
                            long totalSec = 0L;
                            for (Time time : timeList) {
                                Timber.d("123 " + time.getTimeStart() + " " + time.getTimeStop());
                                if (time.getTimeStop() != null)
                                    totalSec += Duration.between(time.getTimeStart(), time.getTimeStop()).getSeconds();
                            }
                            task.setTime(totalSec);
                            task.setHours(DateTimeFormatter.getHours(totalSec));
                            return task;
                        }).toObservable())
                .toList();
    }

//    @Override
//    public Single<NewsList> getNewsList(Map<String, Object> params) {
//        return network.getNewsList(params);
//    }
//
//    @Override
//    public Single<NewsDetailsItem> getNewsDetailsItem(String url) {
//        return network.getNewsDetailsItem(url);
//    }
//

//
//    @Override
//    public Flowable<List<NewsItem>> getBookmarkByUrl(String url) {
//        return database.newsItemDao().getByUrl(url);
//    }
//

//
//    @Override
//    public Completable deleteBookmark(NewsItem bookmark) {
//        return database.newsItemDao().delete(bookmark);
//    }
//
//    @Override
//    public BehaviorSubject<AudioState> getAudioStateSubject() {
//        return audioStateSubject;
//    }
//
//    @Override
//    public BehaviorSubject<AudioState> getAudioProgressSubject() {
//        return audioProgressSubject;
//    }
}