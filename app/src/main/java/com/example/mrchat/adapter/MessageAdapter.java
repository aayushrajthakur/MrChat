package com.example.mrchat.adapter;

import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mrchat.R;
import com.example.mrchat.model.Message;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> messages;
    private String currentUserId;

    public MessageAdapter(List<Message> messages, String currentUserId) {
        this.messages = messages;
        this.currentUserId = currentUserId;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.textMessage.setText(message.getText());

        // Format timestamp
        String time = DateFormat.format("hh:mm a", message.getTimestamp()).toString();
        holder.textTimestamp.setText(time);

        // Align message depending on sender
        FrameLayout.LayoutParams params =
                (FrameLayout.LayoutParams) holder.container.getLayoutParams();

        if (message.getSenderId().equals(currentUserId)) {
            holder.container.setBackgroundResource(R.drawable.bg_message_sent);
            params.gravity = Gravity.END;
        } else {
            holder.container.setBackgroundResource(R.drawable.bg_message_received);
            params.gravity = Gravity.START;
        }
        holder.container.setLayoutParams(params);

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        LinearLayout container;
        TextView textMessage, textTimestamp;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.messageContainer);
            textMessage = itemView.findViewById(R.id.textMessage);
            textTimestamp = itemView.findViewById(R.id.textTimestamp);
        }
    }
}
