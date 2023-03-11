package com.example.example_project.ui.game.games_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.example_project.R;
import com.example.example_project.ui.game.Game;

import java.util.ArrayList;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ChatViewHolder> {

    private ArrayList<Game> games;

    public GameAdapter(ArrayList<Game> games) {
        this.games = games;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View chatView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleitem_game, parent, false);
        return new ChatViewHolder(chatView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Game currentGame = games.get(position);
        holder.nameTextView.setText(currentGame.getName());
        holder.messageTextView.setText(currentGame.getGm());
        holder.timeTextView.setText(currentGame.getPlayerOne());
        holder.unreadTextView.setText(currentGame.getPlayerTwo());
        holder.iconImageView.setImageResource(holder.nameTextView.getResources().getIdentifier(currentGame.getIcon(), "drawable", holder.nameTextView.getContext().getPackageName()));
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder{

        public TextView nameTextView;
        public TextView messageTextView;
        public TextView timeTextView;
        public TextView unreadTextView;
        public ImageView iconImageView;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textview_group_name);
            messageTextView = itemView.findViewById(R.id.textview_last_message);
            timeTextView = itemView.findViewById(R.id.textview_time);
            unreadTextView = itemView.findViewById(R.id.textview_unread);
            iconImageView = itemView.findViewById(R.id.imageview_group_icon);
        }
    }
}
