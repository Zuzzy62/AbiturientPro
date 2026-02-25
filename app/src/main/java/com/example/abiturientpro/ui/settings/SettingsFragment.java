package com.example.abiturientpro.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.abiturientpro.R;
import com.example.abiturientpro.ui.main.MainActivity;
import com.example.abiturientpro.utils.LocaleHelper;
import com.example.abiturientpro.utils.ThemeHelper;

public class SettingsFragment extends Fragment {

    private RadioGroup rgTheme, rgLanguage;
    private RadioButton rbLight, rbDark, rbSystem, rbRu, rbEn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        rgTheme = view.findViewById(R.id.rgTheme);
        rgLanguage = view.findViewById(R.id.rgLanguage);
        rbLight = view.findViewById(R.id.rbLight);
        rbDark = view.findViewById(R.id.rbDark);
        rbSystem = view.findViewById(R.id.rbSystem);
        rbRu = view.findViewById(R.id.rbRu);
        rbEn = view.findViewById(R.id.rbEn);

        String currentTheme = ThemeHelper.getSavedTheme(requireContext());
        if (currentTheme.equals("light")) rbLight.setChecked(true);
        else if (currentTheme.equals("dark")) rbDark.setChecked(true);
        else rbSystem.setChecked(true);

        String currentLang = LocaleHelper.getSavedLanguage(requireContext());
        if (currentLang.equals("ru")) rbRu.setChecked(true);
        else rbEn.setChecked(true);

        rgTheme.setOnCheckedChangeListener((group, checkedId) -> {
            String theme;
            if (checkedId == R.id.rbLight) theme = "light";
            else if (checkedId == R.id.rbDark) theme = "dark";
            else theme = "system";
            ThemeHelper.saveTheme(requireContext(), theme);
            recreateActivity();
        });

        rgLanguage.setOnCheckedChangeListener((group, checkedId) -> {
            String lang;
            if (checkedId == R.id.rbRu) lang = "ru";
            else lang = "en";
            LocaleHelper.setLocale(requireContext(), lang);
            recreateActivity();
        });

        return view;
    }

    private void recreateActivity() {
        Intent intent = new Intent(requireContext(), MainActivity.class);
        intent.putExtra("user_id", getArguments() != null ? getArguments().getString("user_id") : "");
        startActivity(intent);
        requireActivity().finish();
    }
}