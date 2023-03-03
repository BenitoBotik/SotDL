package com.example.example_project.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.example_project.R;
import com.example.example_project.ui.RegisterActivity;
import com.example.example_project.ui.main_page.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    private EditText emailEditText;
    private LoginPresenter presenter;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // open login when user click login button
        emailEditText = findViewById(R.id.editText_email);
        EditText passwordEditText = findViewById(R.id.editText_password);
        Button button_login = findViewById(R.id.button_login);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                presenter.loginClicked(email, password, intent);
            }
        });

        // open register activity when user click register textView
        TextView textView_register = findViewById(R.id.textView_clickable_register);
        textView_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // set presenter
        presenter = new LoginPresenter(this);
    }

    public void showEmail(String email) {
        emailEditText.setText(email);
    }
}