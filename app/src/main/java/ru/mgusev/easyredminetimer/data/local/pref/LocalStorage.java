package ru.mgusev.easyredminetimer.data.local.pref;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

import javax.inject.Inject;

@SuppressLint("ApplySharedPref")
public class LocalStorage {

    private final SharedPreferences prefs;

    @Inject
    public LocalStorage(SharedPreferences prefs) {
        this.prefs = prefs;
    }


    public void saveApiToken(String apiToken) {
        prefs.edit().putString("ApiToken", apiToken).commit();
    }

    public String getApiToken() {
        return prefs.getString("ApiToken", null);
    }

    public boolean hasApiToken() {
        return getApiToken() != null && !getApiToken().isEmpty();
    }

    public void saveRefreshToken(String refreshToken) {
        prefs.edit().putString("RefreshToken", refreshToken).commit();
    }

    public String getRefreshToken() {
        return prefs.getString("RefreshToken", null);
    }

    public void saveUserId(String userId) {
        prefs.edit().putString("UserId", userId).commit();
    }

    public String getUserId() {
        return prefs.getString("UserId", null);
    }

    public void saveSmsSendTime(long time) {
        prefs.edit().putLong("SmsSendTime", time).commit();
    }

    public long getSmsSendTime() {
        return prefs.getLong("SmsSendTime", 0);
    }

    public void clear() {
        prefs.edit().remove("UserId").commit();
        prefs.edit().remove("AccessToken").commit();
        prefs.edit().remove("RefreshToken").commit();
    }
}
