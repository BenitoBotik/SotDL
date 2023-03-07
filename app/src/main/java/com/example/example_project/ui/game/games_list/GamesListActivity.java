package com.example.example_project.ui.game.games_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.example_project.R;
import com.example.example_project.ui.character.character_list.CharactersListActivity;
import com.example.example_project.ui.game.Game;
import com.example.example_project.ui.game.GameAdapter;
import com.example.example_project.ui.game.GameCreationActivity;
import com.example.example_project.ui.login.LoginActivity;
import com.example.example_project.ui.main_page.MainActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class GamesListActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);

        // Initialize firebase auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Initialize firebase user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        // Initialize sign in client
        googleSignInClient = GoogleSignIn.getClient(GamesListActivity.this, GoogleSignInOptions.DEFAULT_SIGN_IN);

        ArrayList<Game> games = new ArrayList<>();
        for (int  i = 0; i < 20; i++){
            games.add(new Game( "Group" + i, "what's up dude?", "04:20", "4", "group_icon"));
        }

        RecyclerView recyclerView = findViewById(R.id.recycleview_chat);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        GameAdapter gameAdapter = new GameAdapter(games);
        recyclerView.setAdapter(gameAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                Intent intent = new Intent(GamesListActivity.this, MainActivity.class);
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
                            Intent intent2 = new Intent(GamesListActivity.this, LoginActivity.class);
                            startActivity(intent2);
                            // Finish activity
                            finish();
                        }
                    }
                });
                break;

            case R.id.menu_characters:
                Intent intent2 = new Intent(GamesListActivity.this, CharactersListActivity.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addGame(View view) {
        Intent intent = new Intent(GamesListActivity.this, GameCreationActivity.class);
        startActivity(intent);
    }
}