package com.store.healthsnap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class viewAppointments extends AppCompatActivity {

    Button btnUpdate, btnDelete;
    TextView txtOption;
    CardView cView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointments);



        btnUpdate = findViewById(R.id.btnEditAppointment);
        btnDelete = findViewById(R.id.btnDeleteAppointment);

        cView = findViewById(R.id.cardView);


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateAppointment();
            }
        });

        cView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateAppointment();

            }
        });

    }

    private void updateAppointment ()
    {
        //Intent intent = new Intent(viewAppointments.this, .class);
        //startActivity(intent);

    }



}
