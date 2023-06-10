package com.example.example_project.ui.persistance;

import static android.content.ContentValues.TAG;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.MotionEffect;

import com.example.example_project.ui.model.Game;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.example_project.ui.model.Character;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    private static Repository instance;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    LoadCharactersListener charactersListener;
    LoadGamesListener gamesListener;

    private Repository() {
    }

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    private void uploadImage(Uri imageUri, String characterId) {
        // upload image to file storage named after character name
        StorageReference filestore = FirebaseStorage.getInstance().getReference().child("icons").child(characterId);
        filestore.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                // get image url
                filestore.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d(MotionEffect.TAG, "Uploaded image for character: " + uri.toString());
                    }
                });
            }
        });
    }

    public interface LoadCharactersListener {
        void updateCharacters(List<Character> characters);
    }

    public interface LoadGamesListener {
        void updateGames(List<Game> games);
    }

    public void setCharactersListener(LoadCharactersListener listener) {
        this.charactersListener = listener;
    }

    public void setGamesListener(LoadGamesListener listener) {
        this.gamesListener = listener;
    }

    public void Delete(Character character){
        DocumentReference docRef = db.collection("characters").document(character.getId());

        docRef.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Document successfully deleted
                        Log.d(MotionEffect.TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Log the error message
                        Log.w(MotionEffect.TAG, "Error deleting document", e);
                    }
                });
    }

    public void AddCharacter(Character character, Uri imageUri) {
        db.collection("characters")
                .add(character)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        String id = documentReference.getId();
                        character.setId(id);
                        if (imageUri != null) {
                            uploadImage(imageUri, id);
                        }
                        Log.d(TAG, "DocumentSnapshot added with ID: " + id);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public void GetCharacters(String email){
        db.collection("characters")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        //get the list of documents
                        List<Character> characters = new ArrayList<>();
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            //add the document to the list
                            Character character = document.toObject(Character.class);
                            character.setId(document.getId());
                            characters.add(character);
                        }

                        charactersListener.updateCharacters(characters);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.d(TAG, "Error getting documents: ", e);
                });
    }

    public void GetGames(String email){
        db.collection("games")
                .whereArrayContains("players", email)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        List<Game> games = new ArrayList<>();
                        //get the list of documents
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            //add the document to the list
                            Game game = document.toObject(Game.class);
                            game.setId(document.getId());
                            games.add(game);
                        }

                        //notify the adapter that the data has changed
                        gamesListener.updateGames(games);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.d(TAG, "Error getting documents: ", e);
                });
    }

    public void UpdateGame(Game game){
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("games").document(game.getId());

        docRef.set(game);
    }

    public void AddGame(List<Game> games, Game game){
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
                        gamesListener.updateGames(games);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
}
