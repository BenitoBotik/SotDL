package com.example.example_project.ui.character;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.example_project.ui.model.Character;
import com.example.example_project.ui.persistance.Repository;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CharacterPresenter {
    private CharacterActivity view;
    private Character character;

    public CharacterPresenter(CharacterActivity view) {
        this.view = view;

        character = (Character) view.getIntent().getSerializableExtra("selected_character");

        view.AttachCharacterToView(character);
    }

    public void DeleteButtonClicked(){
        Repository.getInstance().Delete(character);
    }
}
