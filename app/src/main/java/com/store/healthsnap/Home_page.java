package com.store.healthsnap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Home_page extends AppCompatActivity {

    Button btnBack, btnLogout, btnAppointment, btnBill, btnPatient, btnStock;
    FirebaseAuth nAuth;

    MainAdapter mainAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        nAuth = FirebaseAuth.getInstance();
        btnBack = findViewById(R.id.btnBack);
        btnLogout = findViewById(R.id.btnLogout);
        btnAppointment = findViewById(R.id.btnAppoint);
        btnPatient = findViewById(R.id.btnPatient);
        btnStock = findViewById(R.id.btnStock);

        recyclerView = findViewById(R.id.rViewPlus);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Appointments").child("save"), MainModel.class)
                        .build();

        mainAdapter = new MainAdapter(options);
        recyclerView.setAdapter(mainAdapter);
        //Task Buttons

        btnAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goToAppointment();

            }
        });

        btnPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goToPatients();
            }
        });



        //Browse Buttons


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nAuth.signOut();

                Intent intent = new Intent(Home_page.this, MainActivity.class);
                startActivity(intent);

            }
        });

        btnStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goToStock();
            }
        });

    }

    public void returnBtnBack()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goToAppointment ()
    {
        Intent intent = new Intent(this, appointment_home.class);
        startActivity(intent);
    }

    private void goToBilling ()
    {
        Intent intent = new Intent(this, Billing.class);
        startActivity(intent);
    }

    private void goToPatients ()
    {
        Intent intent = new Intent(this, patient_home.class);
        startActivity(intent);
    }

    private void goToStock()
    {
        Intent intent = new Intent(Home_page.this, view_stocks.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = nAuth.getCurrentUser();

        if(user == null)
        {
            Intent intent = new Intent(this, LoginPage.class);
            startActivity(intent);
        }

        mainAdapter.startListening();

    }


    @Override
    protected void onStop() {
        super.onStop();

        mainAdapter.stopListening();
    }
}
