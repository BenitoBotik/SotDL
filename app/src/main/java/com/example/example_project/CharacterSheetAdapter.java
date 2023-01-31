package com.example.example_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CharacterSheetAdapter extends RecyclerView.Adapter<CharacterSheetAdapter.CharacterSheetViewHolder> {
    private ArrayList<CharacterSheet> characterSheets;

    public CharacterSheetAdapter(ArrayList<CharacterSheet> characterSheets) {
        this.characterSheets = characterSheets;
    }

    @NonNull
    @Override
    public CharacterSheetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View characterSheetsView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleitem_character_sheets, parent, false);
        return new CharacterSheetViewHolder(characterSheetsView);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterSheetViewHolder holder, int position) {
        CharacterSheet currentCharacterSheet = characterSheets.get(position);
        holder.nameTextView.setText(currentCharacterSheet.getName());
        holder.groupTextView.setText(currentCharacterSheet.getGroup());
        holder.levelTextView.setText(currentCharacterSheet.getLevel());
        holder.iconImageView.setImageResource(holder.nameTextView.getResources().getIdentifier(currentCharacterSheet.getIcon(), "drawable", holder.nameTextView.getContext().getPackageName()));
    }

    @Override
    public int getItemCount() {
        return characterSheets.size();
    }

    public static class CharacterSheetViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView groupTextView;
        public TextView levelTextView;
        public ImageView iconImageView;
        public CharacterSheetViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textview_name);
            groupTextView = itemView.findViewById(R.id.textview_group);
            levelTextView = itemView.findViewById(R.id.textview_level);
            iconImageView = itemView.findViewById(R.id.imageview_icon);
        }
    }
}
