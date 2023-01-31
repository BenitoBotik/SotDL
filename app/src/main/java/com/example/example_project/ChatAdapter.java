package com.example.example_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private ArrayList<Chat> chats;

    public ChatAdapter(ArrayList<Chat> chats) {
        this.chats = chats;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View chatView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleitem_chat, parent, false);
        return new ChatViewHolder(chatView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Chat currentChat = chats.get(position);
        holder.nameTextView.setText(currentChat.getName());
        holder.messageTextView.setText(currentChat.getMessage());
        holder.timeTextView.setText(currentChat.getTime());
        holder.unreadTextView.setText(currentChat.getUnread());
        holder.iconImageView.setImageResource(holder.nameTextView.getResources().getIdentifier(currentChat.getIcon(), "drawable", holder.nameTextView.getContext().getPackageName()));
    }

    @Override
    public int getItemCount() {
        return chats.size();
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
