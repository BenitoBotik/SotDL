package com.example.example_project.ui.character;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.example_project.R;
import com.example.example_project.ui.character.character_list.CharactersListActivity;
import com.example.example_project.ui.model.Character;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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

        // download image from url and create a bitmap
        StorageReference filestore = FirebaseStorage.getInstance().getReference();
        StorageReference fileRef = filestore.child("icons/"+character.getId());
        fileRef.getBytes(1024*1024).addOnSuccessListener(bytes -> {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            characterImageView.setImageBitmap(bitmap);
        });
    }
}