package com.example.example_project.ui.game.games_list;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.example_project.R;
import com.example.example_project.ui.character.character_list.CharactersListActivity;
import com.example.example_project.ui.model.Game;
import com.example.example_project.ui.main_page.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class GamesListActivity extends AppCompatActivity {
    private GameListPresenter presenter;
    private GameAdapter gameAdapter;
    private RecyclerView recyclerView;
    private ImageView joinButton;
    private ImageView addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);

        SetViews();

        presenter = new GameListPresenter(this);

        gameAdapter.setOnItemClickListener(new GameAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                presenter.GameClicked(position);
            }
        });

        // join a game
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create and show the message box
                AlertDialog.Builder builder = new AlertDialog.Builder(GamesListActivity.this);
                builder.setTitle("Enter the group's code");

                // Set up the input
                final EditText input = new EditText(GamesListActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("Join", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = input.getText().toString();

                        presenter.JoinButtonClicked(name);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create and show the message box
                AlertDialog.Builder builder = new AlertDialog.Builder(GamesListActivity.this);
                builder.setTitle("Enter the group's name");

                // Set up the input
                final EditText input = new EditText(GamesListActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = input.getText().toString();

                        presenter.CreateButtonClicked(name);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void SetViews() {
        // Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        addButton = findViewById(R.id.imageview_add_game);

        joinButton = findViewById(R.id.imageview_join_game);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.menu_game);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menu_home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.menu_game:
                        return true;
                    case R.id.menu_characters:
                        startActivity(new Intent(getApplicationContext(), CharactersListActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        // set up the recycler view
        recyclerView = findViewById(R.id.recycleview_chat);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    public void ShowGame(List<Game> games) {
        gameAdapter.SetGames(games);
        gameAdapter.notifyDataSetChanged();
    }

    public void SettingAdapter(List<Game> games) {
        // set up the adapter
        gameAdapter = new GameAdapter(games);
        recyclerView.setAdapter(gameAdapter);
    }
}