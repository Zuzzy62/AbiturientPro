package com.example.abiturientpro;

import android.app.Application;

import com.example.abiturientpro.utils.LocaleHelper;
import com.example.abiturientpro.utils.ThemeHelper;

public class AbiturientProApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        String theme = ThemeHelper.getSavedTheme(this);
        ThemeHelper.applyTheme(theme);
        String lang = LocaleHelper.getSavedLanguage(this);
        LocaleHelper.setLocale(this, lang);
    }
}