package com.example.mrchat;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mrchat.model.Message;
import com.example.mrchat.adapter.MessageAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private TextView chatTitle;
    private RecyclerView recyclerViewMessages;
    private EditText editTextMessage;
    private MaterialButton buttonSend;

    private MessageAdapter adapter;
    private List<Message> messages = new ArrayList<>();

    private DatabaseReference chatRef;
    private String senderUid, receiverUid, receiverName, chatRoomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Bind views
        chatTitle = findViewById(R.id.chatTitle);
        recyclerViewMessages = findViewById(R.id.recyclerViewMessages);
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.buttonSend);

        // Setup RecyclerView
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MessageAdapter(messages, FirebaseAuth.getInstance().getCurrentUser().getUid());
        recyclerViewMessages.setAdapter(adapter);

        // Get sender and receiver info
        senderUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        receiverUid = getIntent().getStringExtra("receiverUid");
        receiverName = getIntent().getStringExtra("receiverName");

        chatTitle.setText(receiverName);

        // Generate chat room ID (same for both users)
        chatRoomId = senderUid.compareTo(receiverUid) < 0
                ? senderUid + "_" + receiverUid
                : receiverUid + "_" + senderUid;

        chatRef = FirebaseDatabase.getInstance().getReference("chats")
                .child(chatRoomId).child("messages");

        // Listen for incoming messages
        chatRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, String prevChildKey) {
                Message msg = snapshot.getValue(Message.class);
                if (msg != null) {
                    messages.add(msg);
                    adapter.notifyItemInserted(messages.size() - 1);
                    recyclerViewMessages.scrollToPosition(messages.size() - 1);
                }
            }
            @Override public void onChildChanged(@NonNull DataSnapshot snapshot, String prevChildKey) {}
            @Override public void onChildRemoved(@NonNull DataSnapshot snapshot) {}
            @Override public void onChildMoved(@NonNull DataSnapshot snapshot, String prevChildKey) {}
            @Override public void onCancelled(@NonNull DatabaseError error) {}
        });

        // Send message
        buttonSend.setOnClickListener(v -> {
            String text = editTextMessage.getText().toString().trim();
            if (!TextUtils.isEmpty(text)) {
                Message message = new Message(senderUid, receiverUid, text);
                chatRef.push().setValue(message);
                editTextMessage.setText("");
            }
        });
    }
}
