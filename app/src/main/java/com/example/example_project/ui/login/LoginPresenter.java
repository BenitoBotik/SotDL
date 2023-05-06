package com.example.example_project.ui.login;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPresenter {
    private LoginActivity view;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth firebaseAuth;

    public LoginPresenter(LoginActivity view) {
        this.view = view;


    }
}
