package com.example.example_project.ui.character.character_creation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.example_project.ui.character.character_list.CharactersListActivity;
import com.example.example_project.R;

public class CharacterCreationActivity extends AppCompatActivity {
    private CharacterCreationPresenter presenter;
    private EditText editText_name;
    private EditText editText_level;
    private EditText editText_strength;
    private EditText editText_agility;
    private EditText editText_intellect;
    private EditText editText_will;
    private ImageView characterImage;
    private int icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_creation);

        SetViews();

        presenter = new CharacterCreationPresenter(this);
    }

    private void SetViews() {
        editText_name = findViewById(R.id.editText_name);
        editText_level = findViewById(R.id.editText_level);
        editText_strength = findViewById(R.id.editText_strength);
        editText_agility = findViewById(R.id.editText_agility);
        editText_intellect = findViewById(R.id.editText_intellect);
        editText_will = findViewById(R.id.editText_will);
        characterImage = findViewById(R.id.character_icon_imageview);
        icon = R.drawable.icon0;

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String savedText = sharedPreferences.getString("textKey", "");
        editText_name.setText(savedText);

        editText_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Not needed for this example
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Not needed for this example
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editText_name.getText().toString();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("textKey", text);
                editor.apply();
            }
        });
    }

    public void changeCharacterIcon(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose a Character");

        final int[] iconIds = {R.drawable.icon0, R.drawable.icon1, R.drawable.icon2, R.drawable.icon3, R.drawable.icon4, R.drawable.icon5};
        CharSequence[] iconNames = {"Clockwork", "Dwarf", "Changeling", "Human", "Goblin", "Orc"};

        builder.setItems(iconNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                characterImage.setImageResource(iconIds[which]);
                icon = iconIds[which];
            }
        });

        builder.show();
    }

    public void save(View view) {
        String name = editText_name.getText().toString();
        String level = editText_level.getText().toString();
        String strength = editText_strength.getText().toString();
        String agility = editText_agility.getText().toString();
        String intellect = editText_intellect.getText().toString();
        String will = editText_will.getText().toString();

        presenter.SaveButtonClicked(name, level, icon, strength, agility, intellect, will);

        Intent intent = new Intent(CharacterCreationActivity.this, CharactersListActivity.class);
        startActivity(intent);
    }
}