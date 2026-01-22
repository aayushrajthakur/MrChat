package com.example.mrchat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mrchat.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AuthenticationActivity extends AppCompatActivity {

    private EditText loginEmail, loginPassword;
    private EditText registerUsername, registerEmail, registerPassword;
    private Button buttonLogin, buttonRegister;
    private TextView textToggle;

    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;

    private boolean isLoginMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_authentication);


        mAuth = FirebaseAuth.getInstance();
        usersRef = FirebaseDatabase.getInstance().getReference("users");

        // Bind views
        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        registerUsername = findViewById(R.id.registerUsername);
        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);
        textToggle = findViewById(R.id.textToggle);

        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // User already signed in → skip auth screen
            startActivity(new Intent(AuthenticationActivity.this, MainActivity.class));
            finish();
        }

        // Toggle between login and register
        textToggle.setOnClickListener(v -> {
            if (isLoginMode) {
                isLoginMode = false;
                findViewById(R.id.loginCard).setVisibility(android.view.View.GONE);
                findViewById(R.id.registerCard).setVisibility(android.view.View.VISIBLE);
                textToggle.setText("Already have an account? Login");
            } else {
                isLoginMode = true;
                findViewById(R.id.loginCard).setVisibility(android.view.View.VISIBLE);
                findViewById(R.id.registerCard).setVisibility(android.view.View.GONE);
                textToggle.setText("Don't have an account? Register");
            }
        });

        // Login
        buttonLogin.setOnClickListener(v -> {
            String email = loginEmail.getText().toString().trim();
            String password = loginPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                            // Go to MainActivity
                            startActivity(new Intent(AuthenticationActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        // Register
        buttonRegister.setOnClickListener(v -> {
            String username = registerUsername.getText().toString().trim();
            String email = registerEmail.getText().toString().trim();
            String password = registerPassword.getText().toString().trim();

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            if (firebaseUser != null) {
                                String uid = firebaseUser.getUid();

                                // Store user info in Realtime Database
                                User newUser = new User(username, uid, email);
                                usersRef.child(uid).setValue(newUser);




                                startActivity(new Intent(AuthenticationActivity.this, MainActivity.class));
                                finish();
                            }
                        } else {
                            Toast.makeText(this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}
