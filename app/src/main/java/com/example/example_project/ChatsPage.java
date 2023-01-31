package com.example.example_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.example_project.ui.login.LoginActivity;

import java.util.ArrayList;

public class ChatsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats_page);

        ArrayList<Chat> chats = new ArrayList<>();
        for (int  i = 0; i < 20; i++){
            chats.add(new Chat( "Group" + i, "what's up?", "04:20", "4", "group_icon"));
        }

        RecyclerView recyclerView = findViewById(R.id.recycleview_chat);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ChatAdapter chatAdapter = new ChatAdapter(chats);
        recyclerView.setAdapter(chatAdapter);
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
                Intent intent = new Intent(ChatsPage.this, MainPage.class);
                startActivity(intent);
                break;

            case R.id.menu_logout:
                Intent intent1 = new Intent(ChatsPage.this, LoginActivity.class);
                startActivity(intent1);
                break;

            case R.id.menu_characters:
                Intent intent2 = new Intent(ChatsPage.this, CharacterSheetsListActivity.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}