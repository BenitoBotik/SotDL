package com.example.example_project.ui.model;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;


import com.example.example_project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class NotificationWorker extends Worker {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String email = mAuth.getCurrentUser().getEmail();
    private String CHANNEL_ID = "my_channel_01";

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
                            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException error) {
                                Log.w(TAG, "Event happened!");
                                if (error != null) {
                                    Log.w(TAG, "Listen failed.", error);
                                    return;
                                }

                                for (DocumentChange dc : snapshots.getDocumentChanges()) {
                                    switch (dc.getType()) {
                                        case ADDED:
                                            Log.d(TAG, "New game: " + dc.getDocument().getData());
                                            break;
                                        case MODIFIED:
                                            String name = dc.getDocument().getData().get("name").toString();
                                            Log.d(TAG, "Modified game: " + name);
                                             SendNotification(name);
                                            break;
                                        case REMOVED:
                                            Log.d(TAG, "Removed game: " + dc.getDocument().getData());
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }
                        });
    }

    @SuppressLint("MissingPermission")
    private void SendNotification(String name) {
        Log.w(TAG, "entered SendNotification!");

        int notificationId = 1;

        // create a notification channel
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "channel", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("channel description");
            NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this.getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.avatar)
                .setContentTitle(name + " Has Been Updated!")
                .setContentText("Go and check it out!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this.getApplicationContext());

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, builder.build());
    }
}
