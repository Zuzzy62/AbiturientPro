package com.example.abiturientpro.ui.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.abiturientpro.R;
import com.example.abiturientpro.data.models.User;
import com.example.abiturientpro.data.repository.MockRepository;

public class AdminHomeFragment extends Fragment {

    private String userId;

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
        View view = inflater.inflate(R.layout.fragment_admin_home, container, false);
        TextView tvWelcome = view.findViewById(R.id.tvWelcome);
        User user = MockRepository.getInstance().getUserById(userId);
        if (user != null) {
            tvWelcome.setText(getString(R.string.welcome_admin, user.getFullName()));
        }
        return view;
    }
}