package com.example.example_project.ui.game;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import com.example.example_project.R;
import com.example.example_project.ui.model.Game;
import com.example.example_project.ui.model.Icon;
import com.example.example_project.ui.model.TouchHandler;
import com.example.example_project.ui.persistance.Repository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class GamePresenter {
    private final GameActivity view;
    private final String email;
    private final String gm;
    private final Game game;
    private int icon;

    public GamePresenter(GameActivity view) {
        this.view = view;

        game = (Game) view.getIntent().getSerializableExtra("selected_game");

        // Initialize firebase auth
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        // Initialize firebase user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        email = firebaseUser.getEmail();
        gm = game.getGm();
    }

    public void AddAllIcons(ConstraintLayout gameLayout, ImageView bucket){
        ArrayList<Icon> icons = game.getIcons();

        // loop through list of image objects and create ImageView for each one
        if (icons != null) {
            for (Icon icon : icons) {
                ImageView currentIcon = new ImageView(view);
                int resourceId = view.getResources().getIdentifier(icon.getImage(), "drawable", view.getPackageName());
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
                    currentIcon.setOnTouchListener(new TouchHandler(view.findViewById(R.id.my_parent_layout), currentIcon, bucket));
                }
            }
        }
    }

    public boolean EmailEqualsGm() {
        return email.equals(gm);
    }

    public void AddButtonClicked(ConstraintLayout gameLayout, ImageView bucket){
        // create a new game icon of size 64x64
        ImageView currentIcon = new ImageView(view);

        AlertDialog.Builder builder = new AlertDialog.Builder(view);
        builder.setTitle("Choose an icon");

        final int[] iconIds = {R.drawable.icon0, R.drawable.icon1, R.drawable.icon2, R.drawable.icon3, R.drawable.icon4, R.drawable.icon5};
        CharSequence[] iconNames = {"Clockwork", "Dwarf", "Changeling", "Human", "Goblin", "Orc"};

        builder.setItems(iconNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                currentIcon.setImageResource(iconIds[which]);
                icon = iconIds[which];
            }
        });

        builder.show();

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
        currentIcon.setOnTouchListener(new TouchHandler(view.findViewById(R.id.my_parent_layout), currentIcon, bucket));
    }

    public void UpdateButtonClicked() {
        // Get the parent layout
        ConstraintLayout parentLayout = view.findViewById(R.id.my_parent_layout);

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

            //check if the view is an imageview
            if (childView instanceof ImageView) {
                // Get the image resource id
                ImageView imageView = (ImageView) childView;

                if (imageView.getDrawable() != null) {
                    if (imageView.getDrawable().getConstantState().equals(ContextCompat.getDrawable(view, R.drawable.icon0).getConstantState())) {
                        // The ImageView is displaying image1
                        // Perform your desired actions for image1
                        // Create an icon and add it to the list
                        Icon icon = new Icon("icon0", x, y);
                        newIcons.add(icon);
                    } else if (imageView.getDrawable().getConstantState().equals(ContextCompat.getDrawable(view, R.drawable.icon1).getConstantState())) {
                        // The ImageView is displaying image2
                        // Perform your desired actions for image2
                        // Create an icon and add it to the list
                        Icon icon = new Icon("icon1", x, y);
                        newIcons.add(icon);
                    } else if (imageView.getDrawable().getConstantState().equals(ContextCompat.getDrawable(view, R.drawable.icon2).getConstantState())) {
                        // The ImageView is displaying image2
                        // Perform your desired actions for image2
                        // Create an icon and add it to the list
                        Icon icon = new Icon("icon2", x, y);
                        newIcons.add(icon);
                    }
                    else if (imageView.getDrawable().getConstantState().equals(ContextCompat.getDrawable(view, R.drawable.icon3).getConstantState())) {
                        // The ImageView is displaying image2
                        // Perform your desired actions for image2
                        // Create an icon and add it to the list
                        Icon icon = new Icon("icon3", x, y);
                        newIcons.add(icon);
                    }
                    else if (imageView.getDrawable().getConstantState().equals(ContextCompat.getDrawable(view, R.drawable.icon4).getConstantState())) {
                        // The ImageView is displaying image2
                        // Perform your desired actions for image2
                        // Create an icon and add it to the list
                        Icon icon = new Icon("icon4", x, y);
                        newIcons.add(icon);
                    }
                    else if (imageView.getDrawable().getConstantState().equals(ContextCompat.getDrawable(view, R.drawable.icon5).getConstantState())) {
                        // The ImageView is displaying image2
                        // Perform your desired actions for image2
                        // Create an icon and add it to the list
                        Icon icon = new Icon("icon5", x, y);
                        newIcons.add(icon);
                    }
                }
            }

            // update game icons
            game.setIcons(newIcons);

            // update the game in the database
            Repository.getInstance().UpdateGame(game);
        }
    }

    public void CopyToClipboard (Context context){
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("Copied Code", game.getId());
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(view, "Code copied to clipboard", Toast.LENGTH_SHORT).show();
    }
}
