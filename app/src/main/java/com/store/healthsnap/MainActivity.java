package com.store.healthsnap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnLogin, btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegister = findViewById(R.id.btnRegFire);
        btnLogin = findViewById(R.id.btnLogin);




        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openLoginMenu();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openRegistrationPage();
            }
        });

    }


    public void openLoginMenu()
    {
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
    }

    public void openRegistrationPage()
    {
        Intent intent = new Intent(this, RegistrationPage.class);
        startActivity(intent);
    }

}
