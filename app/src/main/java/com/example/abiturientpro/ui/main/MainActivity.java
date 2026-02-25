package com.example.abiturientpro.ui.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.example.abiturientpro.R;
import com.example.abiturientpro.data.models.User;
import com.example.abiturientpro.data.repository.MockRepository;
import com.example.abiturientpro.ui.admin.AdminHomeFragment;
import com.example.abiturientpro.ui.admin.AllApplicationsFragment;
import com.example.abiturientpro.ui.applicant.ApplicantHomeFragment;
import com.example.abiturientpro.ui.applicant.MyApplicationsFragment;
import com.example.abiturientpro.ui.applicant.SubmitApplicationFragment;
import com.example.abiturientpro.ui.base.BaseActivity;
import com.example.abiturientpro.ui.parent.ChildApplicationsFragment;
import com.example.abiturientpro.ui.parent.ParentHomeFragment;
import com.example.abiturientpro.ui.settings.SettingsFragment;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private String currentUserId;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        } else {
            Log.e("MainActivity", "Toolbar is null!");
        }

        currentUserId = getIntent().getStringExtra("user_id");
        if (currentUserId == null) {
            finish();
            return;
        }
        currentUser = MockRepository.getInstance().getUserById(currentUserId);
        if (currentUser == null) {
            finish();
            return;
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        View headerView = navigationView.getHeaderView(0);
        TextView tvUserName = headerView.findViewById(R.id.tvUserName);
        TextView tvUserRole = headerView.findViewById(R.id.tvUserRole);
        tvUserName.setText(currentUser.getFullName());
        String role = "";
        switch (currentUser.getRole()) {
            case "applicant": role = "Абитуриент"; break;
            case "parent": role = "Родитель"; break;
            case "admin": role = "Администратор"; break;
        }
        tvUserRole.setText(role);

        hideMenuItemsByRole();

        if (savedInstanceState == null) {
            Fragment startFragment;
            if (currentUser.getRole().equals("applicant")) {
                startFragment = new ApplicantHomeFragment();
            } else if (currentUser.getRole().equals("parent")) {
                startFragment = new ParentHomeFragment();
            } else {
                startFragment = new AdminHomeFragment();
            }
            Bundle args = new Bundle();
            args.putString("user_id", currentUserId);
            startFragment.setArguments(args);
            loadFragment(startFragment);
        }
    }

    private void hideMenuItemsByRole() {
        Menu menu = navigationView.getMenu();
        if (menu == null) return;

        MenuItem groupApplicant = menu.findItem(R.id.nav_group_applicant);
        MenuItem groupParent = menu.findItem(R.id.nav_group_parent);
        MenuItem groupAdmin = menu.findItem(R.id.nav_group_admin);

        if (currentUser.getRole().equals("applicant")) {
            if (groupParent != null) groupParent.setVisible(false);
            if (groupAdmin != null) groupAdmin.setVisible(false);
        } else if (currentUser.getRole().equals("parent")) {
            if (groupApplicant != null) groupApplicant.setVisible(false);
            if (groupAdmin != null) groupAdmin.setVisible(false);
        } else if (currentUser.getRole().equals("admin")) {
            if (groupApplicant != null) groupApplicant.setVisible(false);
            if (groupParent != null) groupParent.setVisible(false);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            if (currentUser.getRole().equals("applicant")) {
                fragment = new ApplicantHomeFragment();
            } else if (currentUser.getRole().equals("parent")) {
                fragment = new ParentHomeFragment();
            } else {
                fragment = new AdminHomeFragment();
            }
        } else if (id == R.id.nav_submit_application) {
            fragment = new SubmitApplicationFragment();
        } else if (id == R.id.nav_my_applications) {
            fragment = new MyApplicationsFragment();
        } else if (id == R.id.nav_child_applications) {
            fragment = new ChildApplicationsFragment();
        } else if (id == R.id.nav_all_applications) {
            fragment = new AllApplicationsFragment();
        } else if (id == R.id.nav_settings) {
            fragment = new SettingsFragment();
        } else if (id == R.id.nav_logout) {
            finish();
            return true;
        }

        if (fragment != null) {
            Bundle args = new Bundle();
            args.putString("user_id", currentUserId);
            fragment.setArguments(args);
            loadFragment(fragment);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
    }

    @SuppressLint("GestureBackNavigation")
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}