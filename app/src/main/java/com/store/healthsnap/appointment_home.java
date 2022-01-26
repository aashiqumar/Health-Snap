package com.store.healthsnap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class appointment_home extends AppCompatActivity {

    RecyclerView recyclerView;
    MainAdapter mainAdapter;

    Button btnAddAppointment, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_home);

        btnAddAppointment = findViewById(R.id.btnAdd);
        btnBack = findViewById(R.id.btnBack);

        recyclerView = (RecyclerView) findViewById(R.id.rViewPlus);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Appointments").child("save"), MainModel.class)
                        .build();

        mainAdapter = new MainAdapter(options);

        recyclerView.setAdapter(mainAdapter);


        //BUTTON TO ADD APPOINTMENTS
        btnAddAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addAppointments();
            }
        });

        //BUTTON TO RETURN TO HOMEPAGE
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                returnBack();
            }
        });

    }

    private void addAppointments ()
    {
        Intent intent = new Intent(this, Appointment_write.class);
        startActivity(intent);
    }

    private void returnBack ()
    {
        Intent intent = new Intent(this, Home_page.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }
}
