package com.example.abiturientpro.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import java.util.Locale;

public class LocaleHelper {

    private static final String SELECTED_LANGUAGE = "Locale.Helper.Selected.Language";

    public static void setLocale(Context context, String languageCode) {
        persistLanguage(context, languageCode);
        updateResources(context, languageCode);
    }

    private static void persistLanguage(Context context, String languageCode) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString(SELECTED_LANGUAGE, languageCode).apply();
    }

    public static String getSavedLanguage(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(SELECTED_LANGUAGE, "ru");
    }

    private static void updateResources(Context context, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        config.setLocale(locale);
        res.updateConfiguration(config, res.getDisplayMetrics());
    }
}