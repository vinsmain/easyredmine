package ru.mgusev.easyredminetimer.app.ui.main;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import org.apache.commons.lang3.SerializationUtils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.subscribers.DisposableSubscriber;
import ru.mgusev.easyredminetimer.R;
import ru.mgusev.easyredminetimer.app.ui.ApplicationActivity;
import ru.mgusev.easyredminetimer.app.ui._base.formatter.DateTimeFormatter;
import ru.mgusev.easyredminetimer.domain.dto.Const;
import ru.mgusev.easyredminetimer.domain.dto.task.Task;
import ru.mgusev.easyredminetimer.domain.dto.task.Time;
import ru.mgusev.easyredminetimer.domain.interactor.task.GetTaskListUseCase;
import ru.mgusev.easyredminetimer.domain.interactor.task.UpdateTaskParams;
import ru.mgusev.easyredminetimer.domain.interactor.task.UpdateTaskUseCase;
import ru.mgusev.easyredminetimer.domain.utils.MediaStyleHelper;
import timber.log.Timber;

public class StopwatchService extends Service {

    public final static int NOTIFICATION_ID = 404;
    public final static String NOTIFICATION_DEFAULT_CHANNEL_ID = "redmine_channel";
    public final static String NOTIFICATION_CHANNEL_NAME = "Redmine Stopwatch";


    public class MyBinder extends Binder {
        public StopwatchService getService() {
            return StopwatchService.this;
        }
    }

    private MyBinder binder = new MyBinder();

    private UpdateTaskUseCase updateTaskUseCase;
    private GetTaskListUseCase getTaskListUseCase;
    private Task task;
    private Timer timer;
    private String durationFormattedHM = "";

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_DEFAULT_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManagerCompat.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            Objects.requireNonNull(notificationManager).createNotificationChannel(notificationChannel);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.hasExtra(Const.ACTION)) {
            switch (intent.getStringExtra(Const.ACTION)) {
                case Const.ACTION_START:
                    start();
                    break;
                case Const.ACTION_PAUSE:
                    pause();
                    break;
            }
        }
        return START_REDELIVER_INTENT;
    }

    public void sendCommand(Task task, int status) {

        if (this.task != null && this.task.getId() != task.getId() && status == Const.STATUS_STOP)
            return;

        if (this.task != null && this.task.getId() != task.getId())
            pause();

        this.task = SerializationUtils.clone(task);

        switch (status) {
            case Const.STATUS_START_OR_PAUSE:
                if (this.task.getStatus() == Const.STATUS_START)
                    pause();
                else
                    start();
                break;
            case Const.STATUS_START:
                start();
                break;
            case Const.STATUS_PAUSE:
                pause();
                break;
            case Const.STATUS_STOP:
                stop();
                break;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void setUpdateTaskUseCase(UpdateTaskUseCase updateTaskUseCase) {
        if (this.updateTaskUseCase != null)
            this.updateTaskUseCase.dispose();
        this.updateTaskUseCase = updateTaskUseCase;
    }

    public void setGetTaskListUseCase(GetTaskListUseCase getTaskListUseCase) {
        if (this.getTaskListUseCase != null)
            this.getTaskListUseCase.dispose();
        this.getTaskListUseCase = getTaskListUseCase;

        this.getTaskListUseCase.execute(new DisposableSubscriber<List<Task>>() {
            @Override
            public void onNext(List<Task> tasks) {
                for (Task task : tasks) {
                    if (StopwatchService.this.task != null && task.getId() == StopwatchService.this.task.getId()) {
                        StopwatchService.this.task = SerializationUtils.clone(task);
                    } else if (StopwatchService.this.task == null && task.getStatus() == Const.STATUS_START) {
                        StopwatchService.this.task = SerializationUtils.clone(task);
                        startTimer();
                    }
                }
            }

            @Override
            public void onError(Throwable t) {
                Timber.e(t);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void start() {
        Timber.d("START");

        task.setStatus(Const.STATUS_START);

        task.getTimeList().add(new Time(task.getId(), ZonedDateTime.now()));
        startTimer();
        refreshNotificationAndForegroundStatus(task.getStatus());
    }

    private void pause() {
        Timber.d("PAUSE");

        task.setStatus(Const.STATUS_PAUSE);
        task.getTimeList().get(task.getTimeList().size() - 1).setTimeStop(ZonedDateTime.now());
        stopTimer();
        update(task);
        refreshNotificationAndForegroundStatus(task.getStatus());
    }

    private void stop() {
        Timber.d("STOP");

        if (task.getStatus() == Const.STATUS_START) {
            task.getTimeList().get(task.getTimeList().size() - 1).setTimeStop(ZonedDateTime.now());
            //Timber.d(String.valueOf(Duration.between(LocalDateTime.parse(task.getTimeList().get(task.getTimeList().size() - 1).getTimeStop()), LocalDateTime.parse(task.getTimeList().get(task.getTimeList().size() - 1).getTimeStart())).getSeconds()));
        }
        task.setStatus(Const.STATUS_STOP);
        stopTimer();
        task.setHours(DateTimeFormatter.getHours(task.getTime()));
        update(task);
        refreshNotificationAndForegroundStatus(task.getStatus());
        task = null;
    }

    private void startTimer() {
        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                LocalDate date = task.getTimeList().get(task.getTimeList().size() - 1).getTimeStart().toLocalDate();
                if (date.isBefore(LocalDate.now())) {
                    task.getTimeList().get(task.getTimeList().size() - 1).setTimeStop(ZonedDateTime.of(date.atTime(LocalTime.MAX), ZoneId.systemDefault()));
                    task.getTimeList().add(new Time(task.getId(), ZonedDateTime.of(LocalDate.now().atTime(LocalTime.MIN), ZoneId.systemDefault())));
                }
                task.setTime(getTimeInSec(task.getTimeList()));
                update(task);
                if (!durationFormattedHM.equals(DateTimeFormatter.getDurationFormattedHM(task.getTime()))) {
                    durationFormattedHM = DateTimeFormatter.getDurationFormattedHM(task.getTime());
                    refreshNotificationAndForegroundStatus(task.getStatus());
                }
            }
        }, 0, 1000);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
        task.setTime(getTimeInSec(task.getTimeList()));
    }

    private long getTimeInSec(List<Time> list) {
        long totalSec = 0L;
        for (Time time : list) {
            if (time.getTimeStop() != null) {
                totalSec += Duration.between(time.getTimeStart(), time.getTimeStop()).getSeconds();
            } else {
                totalSec += Duration.between(time.getTimeStart(), ZonedDateTime.now()).getSeconds();
            }
        }
        //Timber.d("TOTAL " + totalSec);
        return totalSec;
    }

    private void update(Task task) {
        if (updateTaskUseCase != null)
            updateTaskUseCase.execute(new UpdateTaskParams(task));
    }

    private void refreshNotificationAndForegroundStatus(int playbackState) {
        switch (playbackState) {
            case Const.STATUS_START: {
                startForeground(NOTIFICATION_ID, getNotification(playbackState));
                break;
            }
            case Const.STATUS_PAUSE: {
                NotificationManagerCompat.from(StopwatchService.this).notify(NOTIFICATION_ID, getNotification(playbackState));
                stopForeground(false);
                break;
            }
            default: {
                stopForeground(true);
                break;
            }
        }
    }

    private Notification getNotification(int playbackState) {
        Intent resultIntent = new Intent(this, ApplicationActivity.class);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = MediaStyleHelper.from(this, task, resultPendingIntent);

        if (playbackState == Const.STATUS_START)
            builder.addAction(new NotificationCompat.Action(android.R.drawable.ic_media_pause, "Пауза", PendingIntent.getService(this, 100, new Intent(this, StopwatchService.class).putExtra(Const.ACTION, Const.ACTION_PAUSE), PendingIntent.FLAG_UPDATE_CURRENT)));
        else
            builder.addAction(new NotificationCompat.Action(android.R.drawable.ic_media_play, "Старт", PendingIntent.getService(this, 101, new Intent(this, StopwatchService.class).putExtra(Const.ACTION, Const.ACTION_START), PendingIntent.FLAG_UPDATE_CURRENT)));

        builder.setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                .setShowActionsInCompactView(0)
                .setShowCancelButton(true));
        //.setCancelButtonIntent(PendingIntent.getService(this, 100, new Intent(this, StopwatchService.class).putExtra(Const.ACTION, Const.ACTION_PAUSE), PendingIntent.FLAG_UPDATE_CURRENT)));
        //.setMediaSession(mediaSession.getSessionToken())); // setMediaSession требуется для Android Wear
        builder.setSmallIcon(R.drawable.start);
        builder.setColor(ContextCompat.getColor(this, R.color.colorPrimaryDark)); // The whole background (in MediaStyle), not just icon background
        builder.setShowWhen(false);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setOnlyAlertOnce(true);
        builder.setChannelId(NOTIFICATION_DEFAULT_CHANNEL_ID);

        return builder.build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.updateTaskUseCase != null)
            this.updateTaskUseCase.dispose();

        if (this.getTaskListUseCase != null)
            this.getTaskListUseCase.dispose();
    }
}