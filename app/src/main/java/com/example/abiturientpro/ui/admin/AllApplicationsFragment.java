package com.example.abiturientpro.ui.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.abiturientpro.R;
import com.example.abiturientpro.data.models.Application;
import com.example.abiturientpro.data.models.Specialty;
import com.example.abiturientpro.data.models.User;
import com.example.abiturientpro.data.repository.MockRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllApplicationsFragment extends Fragment {

    private String adminId;
    private Spinner spinnerSpecialty;
    private ListView listView;
    private TextView tvEmpty;
    private Button btnConfirmAdmin;
    private List<Application> allApps;
    private Application selectedApp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            adminId = getArguments().getString("user_id");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_applications, container, false);

        spinnerSpecialty = view.findViewById(R.id.spinnerSpecialty);
        listView = view.findViewById(R.id.listView);
        tvEmpty = view.findViewById(R.id.tvEmpty);
        btnConfirmAdmin = view.findViewById(R.id.btnConfirmAdmin);

        List<String> specialtyNames = new ArrayList<>();
        specialtyNames.add(getString(R.string.all_specialties));
        for (Specialty s : Specialty.ALL) {
            specialtyNames.add(s.toString());
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, specialtyNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSpecialty.setAdapter(spinnerAdapter);

        spinnerSpecialty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterApplications(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        btnConfirmAdmin.setOnClickListener(v -> {
            if (selectedApp != null) {
                if (selectedApp.isConfirmedByParent() || !needsParentConfirmation(selectedApp)) {
                    selectedApp.setConfirmedByAdmin(true);
                    MockRepository.getInstance().updateApplication(selectedApp);
                    Toast.makeText(getContext(), R.string.application_confirmed_by_admin, Toast.LENGTH_SHORT).show();
                    filterApplications(spinnerSpecialty.getSelectedItemPosition());
                } else {
                    Toast.makeText(getContext(), R.string.parent_confirmation_needed, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), R.string.select_application, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private boolean needsParentConfirmation(Application app) {
        String applicantId = app.getApplicantId();
        User applicant = MockRepository.getInstance().getUserById(applicantId);
        return applicant != null && applicant.isMinor();
    }

    private void filterApplications(int position) {
        allApps = MockRepository.getInstance().getAllApplications();
        List<Application> filtered = new ArrayList<>();
        if (position == 0) {
            filtered.addAll(allApps);
        } else {
            String selectedSpecialty = spinnerSpecialty.getItemAtPosition(position).toString();
            String code = selectedSpecialty.split(" ")[0];
            for (Application a : allApps) {
                if (a.getSpecialtyCode().equals(code)) {
                    filtered.add(a);
                }
            }
        }

        if (filtered.isEmpty()) {
            listView.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
            btnConfirmAdmin.setVisibility(View.GONE);
        } else {
            listView.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
            btnConfirmAdmin.setVisibility(View.VISIBLE);

            List<Map<String, String>> data = new ArrayList<>();
            for (Application a : filtered) {
                Map<String, String> map = new HashMap<>();
                User applicant = MockRepository.getInstance().getUserById(a.getApplicantId());
                String name = applicant != null ? applicant.getFullName() : "Неизвестно";
                map.put("applicant", name);
                map.put("specialty", a.getSpecialtyName());
                map.put("status", a.getStatus());
                data.add(map);
            }

            SimpleAdapter adapter = new SimpleAdapter(
                    requireContext(),
                    data,
                    R.layout.item_application_admin,
                    new String[]{"applicant", "specialty", "status"},
                    new int[]{R.id.tvApplicant, R.id.tvSpecialty, R.id.tvStatus}
            );
            listView.setAdapter(adapter);

            selectedApp = filtered.get(0);
        }
    }
}