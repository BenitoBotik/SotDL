package com.example.example_project.ui.character.character_creation;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.example_project.ui.character.Character;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CharacterCreationPresenter {
    CharacterCreationActivity view;
    private FirebaseFirestore db;
    private String email;

    public CharacterCreationPresenter(CharacterCreationActivity view) {
        this.view = view;

        db = FirebaseFirestore.getInstance();

        // Initialize firebase auth
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        // Initialize firebase user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        this.email = firebaseUser.getEmail();
    }

    public void SaveButtonClicked(String name, String level, String icon, String strength, String agility, String intellect, String will){
        // Create a new character
        Character character = new Character(name, level, icon, strength, agility, intellect, will, this.email);

        // Add a new document with a generated ID
        db.collection("characters")
                .add(character)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
}
