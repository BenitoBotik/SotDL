package com.example.example_project.ui.character_list;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.example_project.ui.games_list.GamesListActivity;
import com.example.example_project.R;
import com.example.example_project.ui.character.CharacterActivity;
import com.example.example_project.ui.character.CharacterAdapter;
import com.example.example_project.ui.character_creation.CharacterCreationActivity;
import com.example.example_project.ui.login.LoginActivity;
import com.example.example_project.ui.main_page.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CharactersListActivity extends AppCompatActivity {
    private FirebaseFirestore db;

    ImageView addButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_sheets_list);

        db = FirebaseFirestore.getInstance();

        ArrayList<CharacterActivity> characters = new ArrayList<>();
//        for (int i = 0; i < 20; i++){
//            characters.add(new CharacterActivity("Name" + i, "4", "icon" + (i%6), "1", "2", "3", "4"));
//        }

        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
        
        Assaign();

        RecyclerView recyclerView = findViewById(R.id.recycleview_charactersheets);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        CharacterAdapter characterSheetAdapter = new CharacterAdapter(characters);
        recyclerView.setAdapter(characterSheetAdapter);
    }

    private void Assaign() {
        addButton = findViewById(R.id.imageview_add_button);
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
                Intent intent = new Intent(CharactersListActivity.this, GamesListActivity.class);
                startActivity(intent);
                break;

            case R.id.menu_logout:
                Intent intent1 = new Intent(CharactersListActivity.this, LoginActivity.class);
                startActivity(intent1);
                break;

            case R.id.menu_home:
                Intent intent2 = new Intent(CharactersListActivity.this, MainActivity.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void press(View view) {
        Intent intent = new Intent(CharactersListActivity.this, CharacterCreationActivity.class);
        startActivity(intent);
    }
}