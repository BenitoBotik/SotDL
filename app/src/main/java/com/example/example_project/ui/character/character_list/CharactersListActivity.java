package com.example.example_project.ui.character.character_list;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.example_project.R;
import com.example.example_project.ui.character.CharacterActivity;
import com.example.example_project.ui.character.Character;
import com.example.example_project.ui.character.character_creation.CharacterCreationActivity;
import com.example.example_project.ui.game.games_list.GamesListActivity;
import com.example.example_project.ui.main_page.MainActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class CharactersListActivity extends AppCompatActivity {
    private CharacterListPresenter presenter;
    private RecyclerView recyclerView;
    private ImageView addButton;
    private FirebaseFirestore db;
    private CharacterAdapter characterAdapter;
    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleSignInClient;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_list);

        SetViews();

        presenter = new CharacterListPresenter(this);

        // set up the click listener
        characterAdapter.setOnItemClickListener(new CharacterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                presenter.CharacterClicked(position);
            }
        });
    }
    public void SetViews(){
        recyclerView = findViewById(R.id.recycleview_charactersheets);
        addButton = findViewById(R.id.imageview_add_button);
        bottomNavigationView=findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.menu_characters);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.menu_game:
                        startActivity(new Intent(getApplicationContext(),GamesListActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.menu_characters:
                        return true;
                    case R.id.menu_home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        // click on the add button to go to the character creation activity
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, CharacterCreationActivity.class);
            startActivity(intent);
        });

        // set up the recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    public void ShowCharacter(List<Character> characters){
        characterAdapter.SetCharacters(characters);
        characterAdapter.notifyDataSetChanged();
    }

    public void SettingAdapter(List<Character> characters){
        // set up the adapter
        characterAdapter = new CharacterAdapter(characters);
        recyclerView.setAdapter(characterAdapter);
    }
}