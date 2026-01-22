package com.example.mrchat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mrchat.adapter.ContactAdapter;
import com.example.mrchat.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ContactAdapter adapter;
    private List<User> contacts = new ArrayList<>();
    private DatabaseReference database;
    private TextView textLoggedUser;
    private ImageButton buttonLogout;

    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewContacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ContactAdapter(contacts);
        recyclerView.setAdapter(adapter);

        textLoggedUser = findViewById(R.id.textLoggedUser);
        buttonLogout = findViewById(R.id.buttonLogout);

        database = FirebaseDatabase.getInstance().getReference("users");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // Load all users except the current one
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                contacts.clear();
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    if (user != null) {
                        if (!user.getUniqueId().equals(currentUser.getUid())) {
                            contacts.add(user);
                        } else {
                            // Show logged-in user's username in header
                            textLoggedUser.setText("Logged in as: " + user.getUsername());
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Failed to load contacts", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle contact clicks → open ChatActivity
        adapter.setOnItemClickListener(user -> {
            Intent intent = new Intent(MainActivity.this, ChatActivity.class);
            intent.putExtra("receiverUid", user.getUniqueId());
            intent.putExtra("receiverName", user.getUsername());
            startActivity(intent);
        });

        // Logout logic
        buttonLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this, AuthenticationActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
