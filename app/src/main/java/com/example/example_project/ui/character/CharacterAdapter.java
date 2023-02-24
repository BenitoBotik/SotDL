package com.example.example_project.ui.character;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.example_project.R;

import java.util.ArrayList;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterSheetViewHolder> {
    private ArrayList<CharacterActivity> characters;

    public CharacterAdapter(ArrayList<CharacterActivity> characters) {
        this.characters = characters;
    }

    @NonNull
    @Override
    public CharacterSheetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View characterSheetsView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleitem_character_sheets, parent, false);
        return new CharacterSheetViewHolder(characterSheetsView);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterSheetViewHolder holder, int position) {
        CharacterActivity currentCharacter = characters.get(position);
        holder.nameTextView.setText(currentCharacter.getName());
        holder.levelTextView.setText(currentCharacter.getLevel());
        holder.iconImageView.setImageResource(holder.nameTextView.getResources().getIdentifier(currentCharacter.getIcon(), "drawable", holder.nameTextView.getContext().getPackageName()));
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    public static class CharacterSheetViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView levelTextView;
        public ImageView iconImageView;
        public CharacterSheetViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textview_name);
            levelTextView = itemView.findViewById(R.id.textview_level);
            iconImageView = itemView.findViewById(R.id.imageview_icon);
        }
    }
}