package com.example.logintugas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etUsername,etPassword;
    private Button signInButton;

    private String usernameEditText= "haris";
    private String passwordEditText = "12345";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = findViewById(R.id.usernameEditText);
        etPassword = findViewById(R.id.passwordEditText);
        signInButton = findViewById(R.id.signInButton);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(etUsername.getText().toString().equalsIgnoreCase(usernameEditText) && etPassword.getText().toString().equalsIgnoreCase(passwordEditText)){
                   Intent Login = new Intent(MainActivity.this, Dashboard.class);
                   startActivity(Login);

                   Toast.makeText(MainActivity.this, "LOGIN BERHASIL!!!", Toast.LENGTH_SHORT).show();
               }else{
                   Toast.makeText(MainActivity.this, "Username atau Password anda salah!!!", Toast.LENGTH_SHORT).show();
               }
            }
        });
    }
}