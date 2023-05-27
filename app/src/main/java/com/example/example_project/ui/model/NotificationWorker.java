package com.example.example_project.ui.model;

import static android.content.ContentValues.TAG;
import static androidx.core.app.ActivityCompat.requestPermissions;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class NotificationWorker extends Worker {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String email = mAuth.getCurrentUser().getEmail();
    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.w(TAG, "got to doWork!");
        CheckPlayers();

        return Result.success();
    }

    @Override
    public void onStopped() {
        super.onStopped();
    }

    private void CheckPlayers() {
        Log.w(TAG, "entered CheckPlayers!");
        db.collection("games")
                .whereArrayContains("players", email)
                .addSnapshotListener(
                        new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                Log.w(TAG, "Event happened!");
                                if (error != null) {
                                    Log.w(TAG, "Listen failed.", error);
                                    return;
                                }

                                for (DocumentChange dc : value.getDocumentChanges()) {
                                    switch (dc.getType()) {
                                        case ADDED:
                                            Log.d(TAG, "New city: " + dc.getDocument().getData());
                                            break;
                                        case MODIFIED:
                                            Log.d(TAG, "Modified city: " + dc.getDocument().getData());
                                            break;
                                        case REMOVED:
                                            Log.d(TAG, "Removed city: " + dc.getDocument().getData());
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }
                        });
    }

    private void SendNotification() {

    }
}
