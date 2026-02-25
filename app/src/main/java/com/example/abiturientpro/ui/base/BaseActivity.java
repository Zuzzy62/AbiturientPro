package com.example.abiturientpro.ui.base;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.abiturientpro.utils.LocaleHelper;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LocaleHelper.setLocale(this, LocaleHelper.getSavedLanguage(this));
        super.onCreate(savedInstanceState);
    }
}