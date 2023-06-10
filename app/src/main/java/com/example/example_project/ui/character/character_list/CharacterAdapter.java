package com.example.example_project.ui.character.character_list;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.example_project.R;
import com.example.example_project.ui.model.Character;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder> {
    private static OnItemClickListener mListener;
    private List<Character> characters;

    public CharacterAdapter(List<Character> characters) {
        this.characters = characters;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View charactersView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleitem_character, parent, false);
        return new CharacterViewHolder(charactersView);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        Character currentCharacter = characters.get(position);
        holder.nameTextView.setText(currentCharacter.getName());
        holder.levelTextView.setText(currentCharacter.getLevel());

        // download image from url and create a bitmap
        StorageReference filestore = FirebaseStorage.getInstance().getReference();
        StorageReference fileRef = filestore.child("icons/"+currentCharacter.getId());
        fileRef.getBytes(1024*1024).addOnSuccessListener(bytes -> {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            holder.iconImageView.setImageBitmap(bitmap);
        });
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class CharacterViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView levelTextView;
        public ImageView iconImageView;

        public CharacterViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textview_name);
            levelTextView = itemView.findViewById(R.id.textview_level);
            iconImageView = itemView.findViewById(R.id.imageview_icon);

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
    public void SetCharacters(List<Character> characters){
        this.characters = characters;
    }
}
