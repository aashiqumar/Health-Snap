package com.store.healthsnap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class Appointment_write extends AppCompatActivity {


    EditText txtTitle, txtDate, txtTime, txtDescription;
    Button btnSave, btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_write);

        txtTitle = findViewById(R.id.txtTitle);
        txtDate = findViewById(R.id.txtDate);
        txtTime = findViewById(R.id.txtTime);
        txtDescription = findViewById(R.id.txtDes);

        btnSave = findViewById(R.id.btnSave);
        btnReturn = findViewById(R.id.btnBack);

        //THE DATE PICKER
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dateDialog();

            }
        });

        //THE TIME PICKER
        txtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timeDialog();

            }
        });

        //THE RETURN BUTTON
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                returnBack();

            }
        });

        //THE ADD APPOINTMENT BUTTON
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                addAppointment();
                clearAll();
            }
        });


    }

    private void timeDialog()
    {
        Calendar calendar = Calendar.getInstance();

        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendar.set(Calendar.MINUTE,minute);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH-mm");

                txtTime.setText(simpleDateFormat.format(calendar.getTime()));

            }
        };

        new TimePickerDialog(Appointment_write.this, timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), false).show();
    }

    //TO MAKE THE EDIT TEXT SHOW DATE PICKER DIALOG
    private void dateDialog()
    {
        Calendar calendar =  Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

                txtDate.setText(simpleDateFormat.format(calendar.getTime()));

            }
        };

        new DatePickerDialog(Appointment_write.this, dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }


    //TO RETURN TO APPPOINTMENT HOMEPAGE
    private void returnBack ()
    {
        Intent intent = new Intent(this, appointment_home.class);
        startActivity(intent);
    }


    //TO WRITE THE APPOINTMENETS TO FIREBASE
    private void addAppointment ()
    {
        Map<String, Object> map = new HashMap<>();
        map.put("title", txtTitle.getText().toString());
        map.put("date", txtDate.getText().toString());
        map.put("time", txtTime.getText().toString());
        map.put("description", txtDescription.getText().toString());

        FirebaseDatabase.getInstance().getReference("Appointments").child("save").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(Appointment_write.this, "Appointmentd Saved Sucessfully!", Toast.LENGTH_SHORT).show();
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(Appointment_write.this, "Appointment Save Failed!", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void clearAll()
    {
        txtTime.setText("");
        txtDate.setText("");
        txtTitle.setText("");
        txtDescription.setText("");
    }

}
