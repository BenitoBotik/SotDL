package com.example.example_project.ui.character;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.example_project.R;
import com.example.example_project.ui.character.character_list.CharactersListActivity;
import com.example.example_project.ui.model.Character;

public class CharacterActivity extends AppCompatActivity {
    private CharacterPresenter presenter;
    private TextView nameTextView;
    private TextView strengthTextView;
    private TextView agilityTextView;
    private TextView intellectTextView;
    private TextView willTextView;
    private ImageView characterImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        SetViews();

        presenter = new CharacterPresenter(this);
    }

    private void SetViews() {
        nameTextView = findViewById(R.id.nameTextView);
        strengthTextView = findViewById(R.id.textview_strength);
        agilityTextView = findViewById(R.id.textview_agility);
        intellectTextView = findViewById(R.id.textview_intellect);
        willTextView = findViewById(R.id.textview_will);
        characterImageView = findViewById(R.id.character_imageview);
    }

    public void DeleteCharacter(View view) {
        presenter.DeleteButtonClicked();
        Intent intent = new Intent(this, CharactersListActivity.class);
        startActivity(intent);
        finish();
    }

    public void AttachCharacterToView(Character character){
        nameTextView.setText(character.getName());
        strengthTextView.setText(String.valueOf(character.getStrength()));
        agilityTextView.setText(String.valueOf(character.getAgility()));
        intellectTextView.setText(String.valueOf(character.getIntellect()));
        willTextView.setText(String.valueOf(character.getWill()));
        characterImageView.setImageResource(character.getIcon());
    }
}