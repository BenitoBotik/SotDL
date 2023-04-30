package com.example.example_project.ui.character.character_creation;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.example_project.ui.character.Character;
import com.example.example_project.ui.character.character_list.CharactersListActivity;
import com.example.example_project.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CharacterCreationActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private EditText editText_name;
    private EditText editText_level;
    private EditText editText_strength;
    private EditText editText_agility;
    private EditText editText_intellect;
    private EditText editText_will;
    private String email;
    private ImageView iconImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_creation);

        db = FirebaseFirestore.getInstance();

        // Initialize firebase auth
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        // Initialize firebase user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        this.email = firebaseUser.getEmail();

        ViewToId();

        iconImageView = findViewById(R.id.character_icon_imageview);
    }

    private void ViewToId() {
        editText_name = findViewById(R.id.editText_name);
        editText_level = findViewById(R.id.editText_level);
        editText_strength = findViewById(R.id.editText_strength);
        editText_agility = findViewById(R.id.editText_agility);
        editText_intellect = findViewById(R.id.editText_intellect);
        editText_will = findViewById(R.id.editText_will);
    }

    public void save(View view) {
        String name = editText_name.getText().toString();
        String level = editText_level.getText().toString();
        String strength = editText_strength.getText().toString();
        String agility = editText_agility.getText().toString();
        String intellect = editText_intellect.getText().toString();
        String will = editText_will.getText().toString();
        String icon = iconImageView.toString();

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

        Intent intent = new Intent(CharacterCreationActivity.this, CharactersListActivity.class);
        startActivity(intent);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // If the request code is 1 and the result code is OK
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Get the data from the intent
            iconImageView.setImageURI(data.getData());
        }
    }
}