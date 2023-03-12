package com.example.example_project.ui.character;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.example_project.R;
import com.example.example_project.ui.character.Character;
import com.example.example_project.ui.character.character_list.CharactersListActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CharacterActivity extends AppCompatActivity {
    private TextView nameTextView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference docRef;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        Character character = (Character) getIntent().getSerializableExtra("selected_character");

        docRef = db.collection("characters").document(character.getId());

        ViewToId();

        nameTextView.setText(character.getName());
    }

    private void ViewToId() {
        nameTextView = findViewById(R.id.nameTextView);
    }

    public void DeleteCharacter(View view) {
        docRef.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Document successfully deleted
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Log the error message
                        Log.w(TAG, "Error deleting document", e);
                    }
                });

        Intent intent = new Intent(this, CharactersListActivity.class);
        startActivity(intent);
        finish();
    }
}