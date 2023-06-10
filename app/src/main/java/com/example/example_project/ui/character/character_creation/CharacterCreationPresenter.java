package com.example.example_project.ui.character.character_creation;

import static android.content.ContentValues.TAG;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.example_project.ui.model.Character;
import com.example.example_project.ui.persistance.Repository;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CharacterCreationPresenter {
    private final CharacterCreationActivity view;
    private final String email;

    public CharacterCreationPresenter(CharacterCreationActivity view) {
        this.view = view;

        // Initialize firebase auth
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        // Initialize firebase user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        this.email = firebaseUser.getEmail();
    }

    public void SaveButtonClicked(String name, String level, int icon, Uri imageUri, String strength, String agility, String intellect, String will){
        Repository repository = Repository.getInstance();
        // Create a new character
        Character character = new Character(name, level, icon, strength, agility, intellect, will, this.email);
        repository.AddCharacter(character, imageUri);
    }
}
