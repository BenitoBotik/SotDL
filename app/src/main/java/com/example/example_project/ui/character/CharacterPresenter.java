package com.example.example_project.ui.character;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CharacterPresenter {
    CharacterActivity view;
    private DocumentReference docRef;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public CharacterPresenter(CharacterActivity view) {
        this.view = view;
    }

    public void GetDocument(Character character){
        docRef = db.collection("characters").document(character.getId());
    }

    public void DeleteButtonClicked(){
        docRef.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Document successfully deleted
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Log the error message
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }
}
