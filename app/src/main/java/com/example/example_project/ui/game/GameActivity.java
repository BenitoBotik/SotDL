package com.example.example_project.ui.game;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.example_project.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameActivity extends AppCompatActivity {
    private TextView idTextView;
    private ConstraintLayout gameLayout;
    private ImageView addIcon;
    private ImageView bucket;
    private ArrayList<Icon> icons;
    private FirebaseFirestore db;
    private DocumentReference docRef;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Game game = (Game) getIntent().getSerializableExtra("selected_game");

        icons = game.getIcons();

        db = FirebaseFirestore.getInstance();
        docRef = db.collection("games").document(game.getId());

        // show game id in textview
        idTextView = findViewById(R.id.id_textview);
        idTextView.setText(game.getId());

        // get game layout
        gameLayout = findViewById(R.id.textview_id);
        addIcon = findViewById(R.id.imageview_add_icon);
        bucket = findViewById(R.id.imageview_bucket);

        // add a movable icon to game layout
        addIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create a new game icon of size 64x64
                ImageView currentIcon = new ImageView(GameActivity.this);
                currentIcon.setImageResource(R.drawable.icon3);
                currentIcon.setLayoutParams(new ViewGroup.LayoutParams(64, 64));

                // Set the ImageView's id
                currentIcon.setId(View.generateViewId()); // generate a unique id for the ImageView

                if (icons == null) {
                    icons = new ArrayList<>();
                }
                icons.add(new Icon("icon3", 0, 0));

                // add the game icon to the main layout
                gameLayout.addView(currentIcon);

                // Center the ImageView in the parent ConstraintLayout
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(gameLayout);
                constraintSet.centerHorizontally(currentIcon.getId(), ConstraintSet.PARENT_ID);
                constraintSet.centerVertically(currentIcon.getId(), ConstraintSet.PARENT_ID);
                constraintSet.applyTo(gameLayout);

                for (Icon icon : icons) {
                    // Set an OnTouchListener to handle touch events on the ImageView
                    currentIcon.setOnTouchListener(new View.OnTouchListener() {
                        float dX, dY;

                        @Override
                        public boolean onTouch(View view, MotionEvent event) {
                            switch (event.getAction()) {
                                case MotionEvent.ACTION_DOWN:
                                    // Get the initial touch position
                                    dX = view.getX() - event.getRawX();
                                    dY = view.getY() - event.getRawY();
                                    break;
                                case MotionEvent.ACTION_MOVE:
                                    // Update the position of the ImageView based on touch events
                                    float newX = event.getRawX() + dX;
                                    float newY = event.getRawY() + dY;

                                    // Update the position in FireStore
                                    Icon movedIcon = new Icon("icon3", newX, newY);
                                    Game data = new Game(game.getName(), game.getGm(), game.getMap(), game.getPlayers(), game.getId());
                                    data.setIcons(icons);
                                    docRef.set(data);

                                    // Get the boundaries of the parent ConstraintLayout
                                    ConstraintLayout parentLayout = findViewById(R.id.textview_id);
                                    int parentWidth = parentLayout.getWidth();
                                    int parentHeight = parentLayout.getHeight();

                                    // Adjust the position of the ImageView to stay within the boundaries
                                    newX = Math.max(0, Math.min(newX, parentWidth - view.getWidth()));
                                    newY = Math.max(0, Math.min(newY, parentHeight - view.getHeight()));

                                    view.animate()
                                            .x(newX)
                                            .y(newY)
                                            .setDuration(0)
                                            .start();

                                    // Check if the ImageView is overlapping the Bucket view
                                    if (isViewOverlapping(currentIcon, bucket)) {
                                        // If the ImageView is overlapping, change the Bucket to an open bucket
                                        bucket.setImageResource(R.drawable.bucket_opened);
                                    } else {
                                        // Otherwise, change the Bucket back to a closed bucket
                                        bucket.setImageResource(R.drawable.bucket_closed);
                                    }
                                    break;
                                case MotionEvent.ACTION_UP:
                                    // When the touch gesture is released, check if the ImageView is over the Bucket view
                                    if (isViewOverlapping(currentIcon, bucket)) {
                                        // If it is, delete the ImageView and change the Bucket back to a closed bucket
                                        ((ViewGroup) currentIcon.getParent()).removeView(currentIcon);
                                        bucket.setImageResource(R.drawable.bucket_closed);
                                    }
                                    break;
                                default:
                                    return false;
                            }
                            return true;
                        }
                    });
                }
            }
        });
    }

    // Helper function to check if two views are overlapping
    public static boolean isViewOverlapping(View firstView, View secondView) {
        Rect firstRect = new Rect();
        firstView.getHitRect(firstRect);
        Rect secondRect = new Rect();
        secondView.getHitRect(secondRect);
        return Rect.intersects(firstRect, secondRect);
    }
}
