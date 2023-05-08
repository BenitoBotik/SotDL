package com.example.example_project.ui.game.games_list;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.example_project.ui.game.Game;
import com.example.example_project.ui.game.GameActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameListPresenter {
    private GamesListActivity view;
    private final List<Game> games = new ArrayList<>();
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private String email;

    public GameListPresenter(GamesListActivity view) {
        this.view = view;

        view.SettingAdapter(games);

        // Initialize firebase auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Initialize firebase user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        email = firebaseUser.getEmail();

        // get the list of characters from the database
        db = FirebaseFirestore.getInstance();
        db.collection("games")
                .whereArrayContains("players", email)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        //get the list of documents
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            //add the document to the list
                                Game game = document.toObject(Game.class); //this line bugs the code
                                game.setId(document.getId());
                                games.add(game);
                        }

                        //notify the adapter that the data has changed
                        view.ShowGame(games);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.d(TAG, "Error getting documents: ", e);
                });
    }

    public void JoinButtonClicked(String name){
        //get document reference
        DocumentReference docRef = db.collection("games").document(name);

        // get document and update it
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //get the list of players
                        ArrayList<String> players = (ArrayList<String>) document.get("players");
                        //add the current player to the list
                        players.add(email);
                        //update the document
                        Map<String, Object> updates = new HashMap<>();
                        updates.put("players", players);
                        docRef.update(updates)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(view.getApplicationContext(), "Player successfully added to the game!", Toast.LENGTH_SHORT).show();
                                    //add the game to the list
                                    Game game = document.toObject(Game.class);
                                    game.setId(document.getId());
                                    games.add(game);
                                    //notify the adapter that the data has changed
                                    view.ShowGame(games);
                                })
                                .addOnFailureListener(e -> Toast.makeText(view.getApplicationContext(), "Error adding player", Toast.LENGTH_SHORT).show());
                    } else {
                        Toast.makeText(view.getApplicationContext(), "the game doesn't exist!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    public void CreateButtonClicked(String name){
        ArrayList<String> players = new ArrayList<>();
        players.add(email);

        Game game = new Game(name, email, "Test Icon", players, null, null);

        // Add a new document with a generated ID
        db.collection("games")
                .add(game)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        game.setId(documentReference.getId());
                        games.add(game);
                        //notify the adapter that the data has changed
                        view.ShowGame(games);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public void GameClicked(int position){
        Game game = games.get(position);
        Intent intent = new Intent(view, GameActivity.class);
        intent.putExtra("selected_game", game);
        view.startActivity(intent);
    }
}
