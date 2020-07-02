package ru.mgusev.easyredminetimer.app.ui._base.formatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeFormat extends SimpleDateFormat {

    private static DateTimeFormat formatter;

    private DateTimeFormat() {
        super("dd.MM.yyyy HH:mm");
    }

    public static DateTimeFormat instance() {
        if (formatter == null) formatter = new DateTimeFormat();

        return formatter;
    }

    public static String getTime(long publishTime) {
        long currentTime = new Date().getTime();
        long diffTime = (currentTime - publishTime) / 1000;
        if (diffTime < 60) {
            return getSecondText((int) diffTime);
        } else if (diffTime < 60 * 60) {
            return getMinuteText(Math.round(diffTime / 60f));
        } else if (diffTime < 60 * 60 * 24 && new Date(publishTime).getDay() == new Date(currentTime).getDay()) {
            return getHourText(Math.round(diffTime / (60 * 60f)));
        } else {
            return instance().format(publishTime);
        }
    }

    public static String getDate(long publishTime) {
        return instance().format(publishTime);
    }

    public static String getDuration(int durationInSeconds) {
        //Timber.d(String.valueOf(durationInSeconds));
        return String.format(Locale.getDefault(), "%d:%02d:%02d", durationInSeconds / 3600, (durationInSeconds % 3600) / 60, (durationInSeconds % 60));
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