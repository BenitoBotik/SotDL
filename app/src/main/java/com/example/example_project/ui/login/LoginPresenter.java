package com.example.example_project.ui.login;

import android.content.Context;
import android.content.SharedPreferences;

public class LoginPresenter {
    private LoginActivity view;
    SharedPreferences sharedPreferences;
    public LoginPresenter(LoginActivity view) {
        this.view = view;
        sharedPreferences = view.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "guest");
        view.showEmail(email);
    }
}
