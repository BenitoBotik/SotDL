package com.example.example_project.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.example_project.MainPage;
import com.example.example_project.R;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    Button button_login;
    EditText emailEditText, editText_password;
    private LoginPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewById();
        presenter = new LoginPresenter(this);
    }

    private void ViewById() {
        button_login = findViewById(R.id.button_login);
        emailEditText = findViewById(R.id.editText_email);
        editText_password = findViewById(R.id.editText_password);
    }



    public void login(View view) {
        String email = emailEditText.getText().toString();
        String password = editText_password.getText().toString();
        if (password.equals("123456")) {
            sharedPreferences = getSharedPreferences("preferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("email", email);
            editor.putString("password", password);
            editor.apply();
            Intent intent = new Intent(LoginActivity.this, MainPage.class);
            startActivity(intent);
            finish();

        }
    }

    public void showEmail(String email) {
        emailEditText.setText(email);
    }
}