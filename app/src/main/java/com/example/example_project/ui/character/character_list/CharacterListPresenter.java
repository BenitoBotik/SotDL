package com.example.example_project.ui.character.character_list;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.util.Log;

import com.example.example_project.ui.character.Character;
import com.example.example_project.ui.character.CharacterActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class CharacterListPresenter {
    private CharactersListActivity view;
    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleSignInClient;
    private final List<Character> characters = new ArrayList<>();
    public CharacterListPresenter(CharactersListActivity view) {
        this.view = view;

        view.SettingAdapter(characters);

        // Initialize firebase auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Initialize firebase user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        // Get the user's email
        String email = firebaseUser.getEmail();

        // Initialize sign in client
        googleSignInClient = GoogleSignIn.getClient(view, GoogleSignInOptions.DEFAULT_SIGN_IN);

        // get the list of characters from the database
        db = FirebaseFirestore.getInstance();
        db.collection("characters")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        //get the list of documents
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            //add the document to the list
                            Character character = document.toObject(Character.class);
                            character.setId(document.getId());
                            characters.add(character);
                        }

                        //notify the adapter that the data has changed
                        view.ShowCharacter(characters);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.d(TAG, "Error getting documents: ", e);
                });
    }
    public void CharacterClicked(int position){
        Character character = characters.get(position);
        Intent intent = new Intent(view, CharacterActivity.class);
        intent.putExtra("selected_character", character);
        view.startActivity(intent);
    }
}
