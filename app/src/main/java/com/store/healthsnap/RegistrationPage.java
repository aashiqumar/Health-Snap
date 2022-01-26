package com.store.healthsnap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationPage extends AppCompatActivity {

    EditText txtName, txtEmail, txtPassword;
    Button btnBack, btnRegister, btnLogin;
    FirebaseAuth rAuth;
    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        btnBack = findViewById(R.id.btnBack);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);

        txtEmail = findViewById(R.id.txtEmail);
        txtName = findViewById(R.id.txtName);
        txtPassword = findViewById(R.id.txtPassword);

        rAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginUser();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RegistrationPage.this, MainActivity.class);
                startActivity(intent);

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registerUser();

            }
        });


    }

    private void loginUser()
    {
        Intent intent = new Intent(RegistrationPage.this, LoginPage.class);
        startActivity(intent);
    }

    private void registerUser()
    {
        String name, email, password;

        name = txtName.getText().toString();
        email = txtEmail.getText().toString();
        password = txtPassword.getText().toString();


        if (TextUtils.isEmpty(email))
        {
            txtEmail.setError("Email Required! Cannot be empty");
            txtEmail.requestFocus();
        }

        else if (TextUtils.isEmpty(password))
        {
            txtPassword.setError("Password Required! Cannot be empty");
            txtPassword.requestFocus();
        }

        else
        {
            rAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful())
                    {
                        Toast.makeText(RegistrationPage.this, "Sucessfully Registered!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(RegistrationPage.this, LoginPage.class);
                        startActivity(intent);
                    }

                    else
                    {
                        Toast.makeText(RegistrationPage.this, "Registration Failed! Try again" +
                                task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
}
