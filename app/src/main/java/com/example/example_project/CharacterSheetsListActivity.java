package com.example.example_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.example_project.ui.login.LoginActivity;

import java.util.ArrayList;

public class CharacterSheetsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_sheets_list);

        ArrayList<CharacterSheet> characterSheets = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            characterSheets.add(new CharacterSheet("Name" + i, "Group" + i, "4", "icon" + (i%6)));
        }

        RecyclerView recyclerView = findViewById(R.id.recycleview_charactersheets);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        CharacterSheetAdapter characterSheetAdapter = new CharacterSheetAdapter(characterSheets);
        recyclerView.setAdapter(characterSheetAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_game:
                Intent intent = new Intent(CharacterSheetsListActivity.this, GamePage.class);
                startActivity(intent);
                break;

            case R.id.menu_logout:
                Intent intent1 = new Intent(CharacterSheetsListActivity.this, LoginActivity.class);
                startActivity(intent1);
                break;

            case R.id.menu_home:
                Intent intent2 = new Intent(CharacterSheetsListActivity.this, MainPage.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}