package com.example.example_project;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CharacterSheetCreationActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private EditText name;
    private EditText level;
    private EditText icon;
    private EditText strength;
    private EditText agility;
    private EditText intellect;
    private EditText will;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_sheet_creation);

        db = FirebaseFirestore.getInstance();
    }

    public void save(View view) {

        // Create a new user with a first and last name
        Map<String, Object> character = new HashMap<>();
        Character character1 = new Character("Test", "Test", "Test", "Test", "Test", "Test", "Test");
        character.put("Name", character1);

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