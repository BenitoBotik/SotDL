package com.example.example_project.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.example_project.R;
import com.example.example_project.ui.character.Character;

public class CharacterActivity extends AppCompatActivity {
    private TextView nameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        Character character = (Character) getIntent().getSerializableExtra("selected_character");

        ViewToId();

        nameTextView.setText(character.getName());
    }

    private void ViewToId() {
        nameTextView = findViewById(R.id.nameTextView);
    }
}