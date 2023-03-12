package com.example.example_project.ui.game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.example_project.R;

public class GameActivity extends AppCompatActivity {
    private TextView IdTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Game game = (Game) getIntent().getSerializableExtra("selected_game");

        ViewToId();

        IdTextView.setText(game.getId());
    }

    private void ViewToId() {
        IdTextView = findViewById(R.id.id_textview);
    }
}