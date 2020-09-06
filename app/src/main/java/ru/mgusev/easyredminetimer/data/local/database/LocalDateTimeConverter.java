package ru.mgusev.easyredminetimer.data.local.database;

import androidx.room.TypeConverter;

import java.time.ZonedDateTime;

import timber.log.Timber;

public class LocalDateTimeConverter {

    @TypeConverter
    public static String toString(ZonedDateTime date) {
        if (date == null) {
            Timber.d("toString " + date);
            return null;
        } else {
            Timber.d("toString " + date.toOffsetDateTime().toString());
            return date.toOffsetDateTime().toString();
        }
    }

    @TypeConverter
    public static ZonedDateTime fromString(String date) {
        Timber.d("fromString " + date);
        if (date == null) {
            return null;
        } else {
            Timber.d("fromString " + date);
            return ZonedDateTime.parse(date);
        }
    }

}