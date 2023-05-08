package com.example.example_project.ui.game;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private FirebaseFirestore db;
    private DocumentReference docRef;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Game game = (Game) getIntent().getSerializableExtra("selected_game");

        db = FirebaseFirestore.getInstance();
        docRef = db.collection("games").document(game.getId());

        // show game id in textview
        idTextView = findViewById(R.id.id_textview);
        idTextView.setText(game.getId());

        // get game layout
        gameLayout = findViewById(R.id.my_parent_layout);
        addIcon = findViewById(R.id.imageview_add_icon);
        bucket = findViewById(R.id.imageview_bucket);

        ArrayList<Icon> icons = game.getIcons();

        // loop through list of image objects and create ImageView for each one
        for (Icon icon : icons) {
            ImageView imageView = new ImageView(this);
            int resourceId = getResources().getIdentifier(icon.getImage(), "drawable", getPackageName());
            imageView.setImageResource(resourceId);

            // set layout params for image view
            ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams.horizontalBias = icon.getX() / ConstraintLayout.LayoutParams.MATCH_PARENT;
            layoutParams.verticalBias = icon.getY() / ConstraintLayout.LayoutParams.MATCH_PARENT;

            // add image view to layout
            ConstraintLayout layout = findViewById(R.id.my_parent_layout);
            layout.addView(imageView, layoutParams);
        }

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

                // add the game icon to the main layout
                gameLayout.addView(currentIcon);

                // Center the ImageView in the parent ConstraintLayout
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(gameLayout);
                constraintSet.centerHorizontally(currentIcon.getId(), ConstraintSet.PARENT_ID);
                constraintSet.centerVertically(currentIcon.getId(), ConstraintSet.PARENT_ID);
                constraintSet.applyTo(gameLayout);

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

                                // Get the boundaries of the parent ConstraintLayout
                                ConstraintLayout parentLayout = findViewById(R.id.my_parent_layout);
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
        });

        //update button is clicked
        // Set up the button
        Button button = findViewById(R.id.button_update);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the parent layout
                ConstraintLayout parentLayout = findViewById(R.id.my_parent_layout);

                // Create the list to hold the icons
                ArrayList<Icon> newIcons = new ArrayList<>();

                // Loop over all the child views
                for (int i = 0; i < parentLayout.getChildCount(); i++) {
                    View childView = parentLayout.getChildAt(i);

                    // Skip the two imageviews with the same ids and positions
                    if (childView.getId() == R.id.imageview_bucket || childView.getId() == R.id.imageview_add_icon || childView.getId() == R.id.id_textview || childView.getId() == R.id.button_update) {
                        continue;
                    }

                    // Get the position of the child view
                    ConstraintSet constraintSet = new ConstraintSet();
                    constraintSet.clone(parentLayout);
                    constraintSet.constrainWidth(childView.getId(), childView.getWidth());
                    constraintSet.constrainHeight(childView.getId(), childView.getHeight());
                    constraintSet.connect(childView.getId(), ConstraintSet.TOP, parentLayout.getId(), ConstraintSet.TOP, 0);
                    constraintSet.connect(childView.getId(), ConstraintSet.LEFT, parentLayout.getId(), ConstraintSet.LEFT, 0);
                    constraintSet.applyTo(parentLayout);

                    float x = childView.getX();
                    float y = childView.getY();

                    // Create an icon and add it to the list
                    Icon icon = new Icon("icon3", x, y);
                    newIcons.add(icon);
                }

                // Do something with the list of icons
                Game newGame = new Game(game.getName(), game.getGm(), game.getMap(), game.getPlayers(), game.getId(), newIcons);

                //update the game in the database
                docRef.set(newGame);
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
