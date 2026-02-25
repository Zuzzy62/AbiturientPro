package com.example.abiturientpro.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatDelegate;

public class ThemeHelper {

    private static final String SELECTED_THEME = "theme";

    public static void applyTheme(String theme) {
        switch (theme) {
            case "dark":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case "light":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            default:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
    }

    public static void saveTheme(Context context, String theme) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString(SELECTED_THEME, theme).apply();
        applyTheme(theme);
    }

    public static String getSavedTheme(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(SELECTED_THEME, "light");
    }
}