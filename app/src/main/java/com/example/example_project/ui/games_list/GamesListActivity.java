package com.example.example_project.ui.games_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.example_project.R;
import com.example.example_project.ui.character_list.CharactersListActivity;
import com.example.example_project.ui.game.GameActivity;
import com.example.example_project.ui.game.GameAdapter;
import com.example.example_project.ui.login.LoginActivity;
import com.example.example_project.ui.main_page.MainActivity;

import java.util.ArrayList;

public class GamesListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);

        ArrayList<GameActivity> games = new ArrayList<>();
        for (int  i = 0; i < 20; i++){
            games.add(new GameActivity( "Group" + i, "what's up dude?", "04:20", "4", "group_icon"));
        }

        RecyclerView recyclerView = findViewById(R.id.recycleview_chat);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        GameAdapter gameAdapter = new GameAdapter(games);
        recyclerView.setAdapter(gameAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                Intent intent = new Intent(GamesListActivity.this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.menu_logout:
                Intent intent1 = new Intent(GamesListActivity.this, LoginActivity.class);
                startActivity(intent1);
                break;

            case R.id.menu_characters:
                Intent intent2 = new Intent(GamesListActivity.this, CharactersListActivity.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}