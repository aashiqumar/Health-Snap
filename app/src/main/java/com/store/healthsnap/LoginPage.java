package com.store.healthsnap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {

    EditText txtEmail, txtPassword;
    Button btnLogin, btnBack, btnForget, btnSignup;
    FirebaseAuth lAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        btnLogin = findViewById(R.id.btnLogin);
        btnBack = findViewById(R.id.btnBack);
        btnSignup = findViewById(R.id.btnSignup);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);

        lAuth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                singUp();

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goBack();

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginNow();

            }
        });

    }

    private void singUp()
    {
        Intent intent = new Intent(LoginPage.this, RegistrationPage.class);
        startActivity(intent);
    }

    public void goBack()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goToHomepage()
    {
        Intent intent = new Intent(this, Home_page.class);
        startActivity(intent);
    }

    public void loginNow()
    {
        String email, password;

        email = txtEmail.getText().toString();
        password = txtPassword.getText().toString();

        if(TextUtils.isEmpty(email))
        {
            txtEmail.setError("Email is Incorrect! Try again");
            txtEmail.requestFocus();
        }

        else if (TextUtils.isEmpty(password))
        {
            txtEmail.setError("Password is Incorrect! Try again");
            txtPassword.requestFocus();
        }

//        else if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password))
//        {
//            txtEmail.setError("Incorrect Login Info! Try Again");
//            txtEmail.requestFocus();
//        }

        else
        {
            lAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful())
                    {
                        Toast.makeText(LoginPage.this, "Login Sucessful!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(LoginPage.this, Home_page.class);
                        startActivity(intent);
                    }

                    else
                    {
                        Toast.makeText(LoginPage.this, "Login Failed!", Toast.LENGTH_SHORT).show();

                    }

                }
            });
        }
    }
}
