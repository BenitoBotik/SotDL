package com.example.example_project.ui.game.games_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.example_project.R;
import com.example.example_project.ui.character.Character;
import com.example.example_project.ui.game.Game;

import java.util.ArrayList;
import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {

    private static GameAdapter.OnItemClickListener mListener;
    private List<Game> games;

    public GameAdapter(List<Game> games) {
        this.games = games;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View chatView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleitem_game, parent, false);
        return new GameViewHolder(chatView);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        Game currentGame = games.get(position);
        holder.nameTextView.setText(currentGame.getName());
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class GameViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView;
        public ImageView iconImageView;

        public GameViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textview_group_name);
            iconImageView = itemView.findViewById(R.id.imageview_group_icon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public void SetGames(List<Game> games){
        this.games = games;
    }
}
