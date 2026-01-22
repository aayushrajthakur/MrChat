package com.example.mrchat.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mrchat.R;
import com.example.mrchat.model.User;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<User> contacts = new ArrayList<>();
    private OnItemClickListener listener;

    // Interface for click events
    public interface OnItemClickListener {
        void onItemClick(User user);
    }

    // Allow MainActivity to set the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ContactAdapter(List<User> contacts) {
        if (contacts != null) {
            this.contacts = contacts;
        }
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        User user = contacts.get(position);
        holder.name.setText(user.getUsername());

        // Always show default avatar for now
        holder.avatar.setImageResource(R.drawable.user);

        // Handle click via listener
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts != null ? contacts.size() : 0;
    }

    // Helper method to refresh list dynamically
    public void updateContacts(List<User> newContacts) {
        contacts.clear();
        if (newContacts != null) {
            contacts.addAll(newContacts);
        }
        notifyDataSetChanged();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView avatar;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.contactName);
            avatar = itemView.findViewById(R.id.contactAvatar);
        }
    }
}
