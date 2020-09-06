package ru.mgusev.easyredminetimer.app.ui._base.formatter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Locale;

import timber.log.Timber;

public class DateTimeFormatter {

    public static String getDateFormatted(LocalDate date) {
        return java.time.format.DateTimeFormatter.ofPattern("YYYY-MM-dd").format(date);
    }

    public static String getDateFormatted(Calendar calendar) {
        LocalDate date = LocalDateTime.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId()).toLocalDate();
        return java.time.format.DateTimeFormatter.ofPattern("dd.MM.YYYY").format(date);
    }

    public static String getDurationFormatted(long durationInSec) {
        return String.format(Locale.getDefault(),  "%d:%02d:%02d", durationInSec / 3600, (durationInSec % 3600) / 60, durationInSec % 60);
    }

    public static String getDurationFormattedHM(long durationInSec) {
        if (durationInSec / 3600 == 0 && (durationInSec % 3600) / 60 == 0)
            return "< 1 м.";
        return String.format(Locale.getDefault(),  "%d ч. %02d м.", durationInSec / 3600, (durationInSec % 3600) / 60);
    }

    public static double getHours(long durationInSec) {
        BigDecimal result = new BigDecimal(Double.toString(durationInSec / 3600d));
        Timber.d(String.valueOf(result.setScale(2, RoundingMode.HALF_UP).doubleValue()));
        return result.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    private static String getSecondText(int seconds) {
        int preLastDigit = (seconds % 100 / 10);
        if (preLastDigit == 1) {
            return seconds + " секунд назад";
        }

        switch (seconds % 10) {
            case 1:
                return seconds + " секунду назад";
            case 2:
            case 3:
            case 4:
                return seconds + " секунды назад";
            default:
                return seconds + " секунд назад";
        }
    }

    private static String getMinuteText(int minutes) {
        int preLastDigit = (minutes % 100 / 10);
        if (preLastDigit == 1) {
            return minutes + " минут назад";
        }

        switch (minutes % 10) {
            case 1:
                return minutes + " минуту назад";
            case 2:
            case 3:
            case 4:
                return minutes + " минуты назад";
            default:
                return minutes + " минут назад";
        }
    }

    private static String getHourText(int hours) {
        int preLastDigit = (hours % 100 / 10);
        if (preLastDigit == 1) {
            return hours + " часов назад";
        }

        switch (hours % 10) {
            case 1:
                return hours + " час назад";
            case 2:
            case 3:
            case 4:
                return hours + " часа назад";
            default:
                return hours + " часов назад";
        }
    }
}