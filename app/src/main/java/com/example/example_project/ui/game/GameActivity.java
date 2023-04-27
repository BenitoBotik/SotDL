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

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {
    private TextView idTextView;
    private ConstraintLayout gameLayout;
    private ImageView addIcon;
    private ImageView bucket;
    private List<Icon> icons = new ArrayList<>();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Game game = (Game) getIntent().getSerializableExtra("selected_game");

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
                ImageView icon = new ImageView(GameActivity.this);
                icon.setImageResource(R.drawable.icon3);
                icon.setLayoutParams(new ViewGroup.LayoutParams(64, 64));

                // Set the ImageView's id
                icon.setId(View.generateViewId()); // generate a unique id for the ImageView
                icons.add(new Icon(icon.getId(), 0, 0));

                // add the game icon to the main layout
                gameLayout.addView(icon);

                // Center the ImageView in the parent ConstraintLayout
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(gameLayout);
                constraintSet.centerHorizontally(icon.getId(), ConstraintSet.PARENT_ID);
                constraintSet.centerVertically(icon.getId(), ConstraintSet.PARENT_ID);
                constraintSet.applyTo(gameLayout);

                // Set an OnTouchListener to handle touch events on the ImageView
                icon.setOnTouchListener(new View.OnTouchListener() {
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
                                icons.get(icons.indexOf(new Icon(view.getId(), 0, 0))).setX(newX);
                                icons.get(icons.indexOf(new Icon(view.getId(), 0, 0))).setY(newY);

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
                                if (isViewOverlapping(icon, bucket)) {
                                    // If the ImageView is overlapping, change the Bucket to an open bucket
                                    bucket.setImageResource(R.drawable.bucket_opened);
                                }
                                else {
                                    // Otherwise, change the Bucket back to a closed bucket
                                    bucket.setImageResource(R.drawable.bucket_closed);
                                }
                                break;
                            case MotionEvent.ACTION_UP:
                                // When the touch gesture is released, check if the ImageView is over the Bucket view
                                if (isViewOverlapping(icon, bucket)) {
                                    // If it is, delete the ImageView and change the Bucket back to a closed bucket
                                    ((ViewGroup)icon.getParent()).removeView(icon);
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
