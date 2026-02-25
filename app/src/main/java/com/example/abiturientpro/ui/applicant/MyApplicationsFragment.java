package com.example.abiturientpro.ui.applicant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.abiturientpro.R;
import com.example.abiturientpro.data.models.Application;
import com.example.abiturientpro.data.repository.MockRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyApplicationsFragment extends Fragment {

    private String userId;
    private ListView listView;
    private TextView tvEmpty;

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
        View view = inflater.inflate(R.layout.fragment_my_applications, container, false);
        listView = view.findViewById(R.id.listView);
        tvEmpty = view.findViewById(R.id.tvEmpty);
        loadApplications();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadApplications();
    }

    private void loadApplications() {
        List<Application> apps = MockRepository.getInstance().getApplicationsByApplicantId(userId);
        if (apps.isEmpty()) {
            listView.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            listView.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);

            List<Map<String, String>> data = new ArrayList<>();
            for (Application a : apps) {
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