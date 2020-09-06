package ru.mgusev.easyredminetimer.domain.utils;

// https://gist.github.com/ianhanniballake/47617ec3488e0257325c

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.media.MediaMetadataCompat;

import androidx.core.app.NotificationCompat;

import ru.mgusev.easyredminetimer.R;
import ru.mgusev.easyredminetimer.app.ui._base.formatter.DateTimeFormatter;
import ru.mgusev.easyredminetimer.app.ui.main.StopwatchService;
import ru.mgusev.easyredminetimer.domain.dto.Const;
import ru.mgusev.easyredminetimer.domain.dto.task.Task;
import timber.log.Timber;


/**
 * Helper APIs for constructing MediaStyle notifications
 */
public class MediaStyleHelper {
    /**
     * Build a notification using the information from the given media session. Makes heavy use
     * of {@link MediaMetadataCompat#getDescription()} to extract the appropriate information.
     * @param context Context used to construct the notification.
     * @param mediaSession Media session to get information.
     * @return A pre-built notification with information from the given media session.
     */

    public static NotificationCompat.Builder builder;

    public static NotificationCompat.Builder from(Context context, Task task, PendingIntent intent) {

        builder = new NotificationCompat.Builder(context, StopwatchService.NOTIFICATION_DEFAULT_CHANNEL_ID);
        builder
                .setContentTitle(task.getProjectName())
                .setContentText(DateTimeFormatter.getDurationFormattedHM(task.getTime()))
                .setSubText(task.getActivityName())
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon))
                .setContentIntent(intent)
                //.setDeleteIntent(PendingIntent.getService(context, 100, new Intent(context, StopwatchService.class).putExtra("action", Const.ACTION_PAUSE), PendingIntent.FLAG_UPDATE_CURRENT))
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        return builder;
    }
}