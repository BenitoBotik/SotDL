package com.example.example_project.ui.character.character_list;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.example_project.R;
import com.example.example_project.ui.character.CharacterActivity;
import com.example.example_project.ui.character.Character;
import com.example.example_project.ui.character.character_creation.CharacterCreationActivity;
import com.example.example_project.ui.game.games_list.GamesListActivity;
import com.example.example_project.ui.login.LoginActivity;
import com.example.example_project.ui.main_page.MainActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class CharactersListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageView addButton;
    private FirebaseFirestore db;
    private final List<Character> characters = new ArrayList<>();
    private CharacterAdapter characterAdapter;
    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_list);

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

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

        // Initialize firebase auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Initialize firebase user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        // Get the user's email
        String email = firebaseUser.getEmail();

        // Initialize sign in client
        googleSignInClient = GoogleSignIn.getClient(CharactersListActivity.this, GoogleSignInOptions.DEFAULT_SIGN_IN);

        // click on the add button to go to the character creation activity
        addButton = findViewById(R.id.imageview_add_button);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, CharacterCreationActivity.class);
            startActivity(intent);
        });

        // set up the recycler view
        recyclerView = findViewById(R.id.recycleview_charactersheets);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // set up the adapter
        characterAdapter = new CharacterAdapter(characters);
        recyclerView.setAdapter(characterAdapter);

        // get the list of characters from the database
        db = FirebaseFirestore.getInstance();
        db.collection("characters")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        //get the list of documents
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            //add the document to the list
                            Character character = document.toObject(Character.class);
                            character.setId(document.getId());
                            characters.add(character);
                        }

                        //notify the adapter that the data has changed
                        characterAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.d(TAG, "Error getting documents: ", e);
                });

        // set up the click listener
        characterAdapter.setOnItemClickListener(new CharacterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Character character = characters.get(position);
                Intent intent = new Intent(CharactersListActivity.this, CharacterActivity.class);
                intent.putExtra("selected_character", character);
                startActivity(intent);
            }
        });
    }
}