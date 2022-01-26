package com.store.healthsnap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ValueEventListener;

public class patient_home extends AppCompatActivity {

    RecyclerView recyclerView;
    billAdapter bAdapter;

    TextView pCount, totalFee;

    Button btnBack, btnAdd;

    private DatabaseReference nDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);

        pCount = findViewById(R.id.pCount);


        btnBack = findViewById(R.id.btnPatientBack2);
        btnAdd = findViewById(R.id.btnAddPatient);

        recyclerView = findViewById(R.id.rViewPatients);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        nDatabase = (DatabaseReference) FirebaseDatabase.getInstance().getReference("Billing").child("bill");
        nDatabase.keepSynced(true);


        nDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int sum = 0;

                if(dataSnapshot.exists())
                {
                    sum = (int) dataSnapshot.getChildrenCount();
                    pCount.setText(Integer.toString(sum) + "");


                }

                else
                {
                    pCount.setText("Wow such empty!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        FirebaseRecyclerOptions<billModel> options =
                new FirebaseRecyclerOptions.Builder<billModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Billing").child("bill"), billModel.class)
                        .build();

        bAdapter = new billAdapter(options);
        recyclerView.setAdapter(bAdapter);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                returnBack();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addPatient();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        bAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        bAdapter.stopListening();
    }

    private void returnBack()
    {
        Intent intent = new Intent(patient_home.this, Home_page.class);
        startActivity(intent);
    }

    private void addPatient()
    {
        Intent intent = new Intent(patient_home.this, Billing.class);
        startActivity(intent);
    }
}
