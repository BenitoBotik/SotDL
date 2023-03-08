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
import com.example.example_project.ui.CharacterActivity;
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

        // Initialize firebase auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Initialize firebase user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

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
                .whereEqualTo("email", "username@email.com")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        //get the list of documents
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            //add the document to the list
                            Character character = document.toObject(Character.class);
                            characters.add(character);
                        }

                        //notify the adapter that the data has changed
                        characterAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.d(TAG, "Error getting documents: ", e);
                });

        characterAdapter.setOnItemClickListener(new CharacterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(CharactersListActivity.this, CharacterActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
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
                // Sign out from google
                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Check condition
                        if (task.isSuccessful()) {
                            // When task is successful sign out from firebase
                            firebaseAuth.signOut();
                            // Display Toast
                            Toast.makeText(getApplicationContext(), "Logout successful", Toast.LENGTH_SHORT).show();
                            //Start a new activity
                            Intent intent2 = new Intent(CharactersListActivity.this, LoginActivity.class);
                            startActivity(intent2);
                            // Finish activity
                            finish();
                        }
                    }
                });
                break;

            case R.id.menu_home:
                Intent intent2 = new Intent(CharactersListActivity.this, MainActivity.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}