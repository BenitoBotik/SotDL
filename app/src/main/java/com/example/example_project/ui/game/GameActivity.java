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
    GamePresenter presenter;
    private TextView idTextView;
    private ConstraintLayout gameLayout;
    private ImageView addIcon;
    private ImageView bucket;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        presenter = new GamePresenter(this);

        SetViews();
        }

    private void SetViews() {
        // show game id in textview
        idTextView = findViewById(R.id.id_textview);
        idTextView.setText("Click for Private Code");
        idTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.CopyToClipboard(GameActivity.this);
            }
        });

        // get game layout
        gameLayout = findViewById(R.id.my_parent_layout);
        addIcon = findViewById(R.id.imageview_add_icon);
        bucket = findViewById(R.id.imageview_bucket);

        presenter.AddAllIcons(gameLayout, bucket);

        // add icon button is clicked
        if (presenter.EmailEqualsGm()) {
            // add a movable icon to game layout
            addIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.AddButtonClicked(gameLayout, bucket);
                }
            });
        }

        //update button is clicked
        // Set up the button
        Button button = findViewById(R.id.button_update);
        if (presenter.EmailEqualsGm()) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.UpdateButtonClicked();
                }
            });
        }
    }
}
