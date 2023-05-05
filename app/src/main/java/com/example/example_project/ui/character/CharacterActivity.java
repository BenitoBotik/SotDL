package com.example.example_project.ui.character;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.example_project.R;
import com.example.example_project.ui.character.character_list.CharactersListActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CharacterActivity extends AppCompatActivity {
    private CharacterPresenter presenter;
    private TextView nameTextView;
    private TextView strengthTextView;
    private TextView agilityTextView;
    private TextView intellectTextView;
    private TextView willTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        presenter = new CharacterPresenter(this);

        SetViews();
    }

    private void SetViews() {
        nameTextView = findViewById(R.id.nameTextView);
        strengthTextView = findViewById(R.id.textview_strength);
        agilityTextView = findViewById(R.id.textview_agility);
        intellectTextView = findViewById(R.id.textview_intellect);
        willTextView = findViewById(R.id.textview_will);

        Character character = (Character) getIntent().getSerializableExtra("selected_character");

        nameTextView.setText(character.getName());
        strengthTextView.setText(String.valueOf(character.getStrength()));
        agilityTextView.setText(String.valueOf(character.getAgility()));
        intellectTextView.setText(String.valueOf(character.getIntellect()));
        willTextView.setText(String.valueOf(character.getWill()));

        presenter.GetDocument(character);
    }

    public void DeleteCharacter(View view) {
        presenter.DeleteButtonClicked();
        Intent intent = new Intent(this, CharactersListActivity.class);
        startActivity(intent);
        finish();
    }
}