package com.example.abiturientpro.ui.applicant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.abiturientpro.R;
import com.example.abiturientpro.data.models.Application;
import com.example.abiturientpro.data.models.Specialty;
import com.example.abiturientpro.data.models.User;
import com.example.abiturientpro.data.repository.MockRepository;

import java.util.List;
import java.util.UUID;

public class SubmitApplicationFragment extends Fragment {

    private String userId;
    private Spinner spinnerSpecialty;
    private Button btnSubmit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getString("user_id");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_submit_application, container, false);

        spinnerSpecialty = view.findViewById(R.id.spinnerSpecialty);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        List<Specialty> specialties = Specialty.ALL;
        ArrayAdapter<Specialty> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, specialties);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSpecialty.setAdapter(adapter);

        btnSubmit.setOnClickListener(v -> {
            Specialty selected = (Specialty) spinnerSpecialty.getSelectedItem();
            if (selected == null) return;

            User currentUser = MockRepository.getInstance().getUserById(userId);
            if (currentUser == null) return;

            Application app = new Application(
                    UUID.randomUUID().toString(),
                    userId,
                    selected.getCode(),
                    selected.getName()
            );

            MockRepository.getInstance().addApplication(app);
            Toast.makeText(getContext(), R.string.application_submitted, Toast.LENGTH_SHORT).show();
            requireActivity().onBackPressed();
        });

        return view;
    }
}