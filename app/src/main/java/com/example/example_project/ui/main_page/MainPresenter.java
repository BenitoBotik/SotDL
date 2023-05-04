package com.example.example_project.ui.main_page;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.example_project.ui.login.LoginActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainPresenter {
    private MainActivity view;
    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleSignInClient;

    public MainPresenter(MainActivity view) {
        this.view = view;

        // Initialize firebase auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Initialize firebase user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        // Initialize sign in client
        googleSignInClient = GoogleSignIn.getClient(view, GoogleSignInOptions.DEFAULT_SIGN_IN);

        view.WelcomeText(firebaseUser);
    }

    public void LogOutClicked(){
        firebaseAuth.signOut();
        googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(view, "Signed out successfully", Toast.LENGTH_SHORT).show();
                view.startActivity(new Intent(view, LoginActivity.class));
                view.finish();
            }
        });
    }
}
