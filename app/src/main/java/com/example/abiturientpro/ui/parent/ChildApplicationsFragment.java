package com.example.abiturientpro.ui.parent;

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
import com.example.abiturientpro.data.models.User;
import com.example.abiturientpro.data.repository.MockRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChildApplicationsFragment extends Fragment {

    private String parentId;
    private Spinner spinnerChildren;
    private ListView listView;
    private TextView tvEmpty;
    private Button btnConfirm;
    private List<User> children;
    private User selectedChild;
    private List<Application> currentApps;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            parentId = getArguments().getString("user_id");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_child_applications, container, false);

        spinnerChildren = view.findViewById(R.id.spinnerChildren);
        listView = view.findViewById(R.id.listView);
        tvEmpty = view.findViewById(R.id.tvEmpty);
        btnConfirm = view.findViewById(R.id.btnConfirm);

        children = MockRepository.getInstance().getChildrenByParentId(parentId);
        if (children.isEmpty()) {
            tvEmpty.setText(R.string.no_children);
            tvEmpty.setVisibility(View.VISIBLE);
            spinnerChildren.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);
            btnConfirm.setVisibility(View.GONE);
            return view;
        }

        ArrayAdapter<User> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, children);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChildren.setAdapter(adapter);

        spinnerChildren.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedChild = children.get(position);
                loadApplicationsForChild();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        btnConfirm.setOnClickListener(v -> {
            if (selectedChild == null) return;
            if (currentApps != null && !currentApps.isEmpty()) {
                Application app = currentApps.get(0);
                if (!app.isConfirmedByParent()) {
                    app.setConfirmedByParent(true);
                    MockRepository.getInstance().updateApplication(app);
                    Toast.makeText(getContext(), R.string.application_confirmed, Toast.LENGTH_SHORT).show();
                    loadApplicationsForChild();
                } else {
                    Toast.makeText(getContext(), R.string.already_confirmed, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void loadApplicationsForChild() {
        if (selectedChild == null) return;
        currentApps = MockRepository.getInstance().getApplicationsByApplicantId(selectedChild.getId());
        if (currentApps.isEmpty()) {
            listView.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
            tvEmpty.setText(R.string.no_applications);
            btnConfirm.setVisibility(View.GONE);
        } else {
            listView.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
            btnConfirm.setVisibility(View.VISIBLE);

            List<Map<String, String>> data = new ArrayList<>();
            for (Application a : currentApps) {
                Map<String, String> map = new HashMap<>();
                map.put("specialty", a.getSpecialtyName());
                map.put("status", a.getStatus());
                data.add(map);
            }

            SimpleAdapter adapter = new SimpleAdapter(
                    requireContext(),
                    data,
                    R.layout.item_application,
                    new String[]{"specialty", "status"},
                    new int[]{R.id.tvSpecialty, R.id.tvStatus}
            );
            listView.setAdapter(adapter);
        }
    }
}