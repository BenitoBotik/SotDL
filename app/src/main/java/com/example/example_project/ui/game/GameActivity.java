package com.example.example_project.ui.game;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.example_project.R;
import com.example.example_project.ui.model.Game;
import com.example.example_project.ui.model.Icon;
import com.example.example_project.ui.model.TouchHandler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    private TextView idTextView;
    private ConstraintLayout gameLayout;
    private ImageView addIcon;
    private ImageView bucket;
    private FirebaseFirestore db;
    private DocumentReference docRef;
    private String email;
    private String gm;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Game game = (Game) getIntent().getSerializableExtra("selected_game");

        db = FirebaseFirestore.getInstance();
        docRef = db.collection("games").document(game.getId());

        // Initialize firebase auth
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        // Initialize firebase user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        email = firebaseUser.getEmail();
        gm = game.getGm();

        // show game id in textview
        idTextView = findViewById(R.id.id_textview);
        idTextView.setText(game.getId());

        // get game layout
        gameLayout = findViewById(R.id.my_parent_layout);
        addIcon = findViewById(R.id.imageview_add_icon);
        bucket = findViewById(R.id.imageview_bucket);

        ArrayList<Icon> icons = game.getIcons();

        // loop through list of image objects and create ImageView for each one
        if (icons != null) {
            for (Icon icon : icons) {
                ImageView currentIcon = new ImageView(GameActivity.this);
                int resourceId = getResources().getIdentifier(icon.getImage(), "drawable", getPackageName());
                currentIcon.setImageResource(resourceId);
                currentIcon.setLayoutParams(new ViewGroup.LayoutParams(64, 64));

                // position icon in the correct location
                currentIcon.setX(icon.getX());
                currentIcon.setY(icon.getY());

                // Set the ImageView's id
                currentIcon.setId(View.generateViewId()); // generate a unique id for the ImageView

                // add the game icon to the main layout
                gameLayout.addView(currentIcon);

                if (email.equals(gm)) {
                    // Set an OnTouchListener to handle touch events on the ImageView
                    currentIcon.setOnTouchListener(new TouchHandler(findViewById(R.id.my_parent_layout), currentIcon, bucket));
                }
            }

            if (email.equals(gm)) {
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
                        currentIcon.setOnTouchListener(new TouchHandler(findViewById(R.id.my_parent_layout), currentIcon, bucket));
                    }
                });
            }

            //update button is clicked
            // Set up the button
            Button button = findViewById(R.id.button_update);
            if (email.equals(gm)) {
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

                        // update game icons
                        game.setIcons(newIcons);

                        // update the game in the database
                        docRef.set(game);
                    }
                });
            }
        }
    }
}
