package com.example.example_project.ui.persistance;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.MotionEffect;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.example_project.ui.model.Character;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    private static Repository instance;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    LoadCharactersListener charactersListener;

    private Repository() {
    }

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    public interface LoadCharactersListener {
        void updateCharacters(List<Character> characters);
    }

    public void setCharactersListener(LoadCharactersListener listener) {
        this.charactersListener = listener;
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

    public void AddCharacter(Character character) {
        db.collection("characters")
                .add(character)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
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
}
