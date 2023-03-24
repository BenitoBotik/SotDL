package com.example.example_project.ui.game;

import android.annotation.SuppressLint;
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

public class GameActivity extends AppCompatActivity {
    private TextView idTextView;
    private ConstraintLayout gameLayout;

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

        // create a new game icon of size 64x64
        ImageView icon = new ImageView(this);
        icon.setImageResource(R.drawable.icon0);
        icon.setLayoutParams(new ViewGroup.LayoutParams(64, 64));

        // Set the ImageView's id
        icon.setId(View.generateViewId()); // generate a unique id for the ImageView

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
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });

    }
}