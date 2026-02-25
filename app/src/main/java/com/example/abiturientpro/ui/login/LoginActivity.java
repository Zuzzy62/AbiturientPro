package com.example.abiturientpro.ui.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.abiturientpro.R;
import com.example.abiturientpro.data.models.User;
import com.example.abiturientpro.data.repository.MockRepository;
import com.example.abiturientpro.ui.base.BaseActivity;
import com.example.abiturientpro.ui.main.MainActivity;

public class LoginActivity extends BaseActivity {

    private EditText etLogin, etPassword;
    private Button btnLogin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLogin = findViewById(R.id.etLogin);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = etLogin.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (login.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                    return;
                }

                User user = MockRepository.getInstance().getUserByLogin(login);
                if (user != null && user.getPassword().equals(password)) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("user_id", user.getId());
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}