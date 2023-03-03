package com.example.example_project.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class LoginPresenter {
    SharedPreferences sharedPreferences;
    private final LoginActivity view;

    public LoginPresenter(LoginActivity view) {
        this.view = view;
        sharedPreferences = view.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "guest");
        //view.showEmail(email);
    }

    public void loginClicked(String email, String password, Intent intent) {
        if (password.equals("123456")) {
            sharedPreferences = view.getSharedPreferences("preferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("email", email);
            editor.putString("password", password);
            editor.apply();
            view.startActivity(intent);
            view.finish();
        }
    }
}
