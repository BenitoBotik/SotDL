package com.example.example_project.ui.character.character_list;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.util.Log;

import com.example.example_project.ui.model.Character;
import com.example.example_project.ui.character.CharacterActivity;
import com.example.example_project.ui.persistance.Repository;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class CharacterListPresenter implements Repository.LoadCharactersListener {
    private final CharactersListActivity view;
    private final FirebaseAuth firebaseAuth;
    private List<Character> characters = new ArrayList<>();

    public CharacterListPresenter(CharactersListActivity view) {
        this.view = view;

        view.SettingAdapter(characters);

        // Initialize firebase auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Initialize firebase user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        // Get the user's email
        String email = firebaseUser.getEmail();

        // get the list of characters from the database
        Repository.getInstance().setCharactersListener(this);
        Repository.getInstance().GetCharacters(email);
    }

    public void CharacterClicked(int position) {
        Character character = characters.get(position);
        Intent intent = new Intent(view, CharacterActivity.class);
        intent.putExtra("selected_character", character);
        view.startActivity(intent);
    }

    @Override
    public void updateCharacters(List<Character> characters) {
        this.characters = characters;
        view.ShowCharacter(characters);
    }
}
