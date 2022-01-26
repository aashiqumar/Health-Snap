package com.store.healthsnap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Billing extends AppCompatActivity {


    EditText txtName, txtNumber, txtDate, txtTime, txtCost, txtDescription;
    Button btnAddBill, btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);

        txtName = findViewById(R.id.txtPBillName1);
        txtNumber = findViewById(R.id.txtPBillNumber);
        txtDate = findViewById(R.id.txtPBillDate);
        txtTime = findViewById(R.id.txtPBillTime);
        txtCost = findViewById(R.id.txtPBillFee);
        txtDescription = findViewById(R.id.txtPBillDescription);

        btnAddBill = findViewById(R.id.btnSaveBill);
        btnBack = findViewById(R.id.btnPatientBack2);

        btnAddBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertDate();
                clearAll();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                returnBack();
            }
        });

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dateDialog();
            }
        });

        txtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timeDialog();

            }
        });


    }


    //TO SET DATE
    private void dateDialog()
    {
        Calendar calendar = Calendar.getInstance();

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

        new DatePickerDialog(Billing.this, dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    //TO SET TIME
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

        new TimePickerDialog(Billing.this, timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),false).show();
    }

    private void insertDate()
    {
        Map<String, Object> map = new HashMap<>();

        map.put("pName", txtName.getText().toString());
        map.put("pDate", txtDate.getText().toString());
        map.put("pTime", txtTime.getText().toString());
        map.put("pNumber", txtNumber.getText().toString());
        map.put("pFee", txtCost.getText().toString());
        map.put("pDescription", txtDescription.getText().toString());

        FirebaseDatabase.getInstance().getReference("Billing").child("bill").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(Billing.this, "Bill Added Sucessfully!", Toast.LENGTH_SHORT).show();
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(Billing.this, "Bill Fail to Add!", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void returnBack ()
    {
        Intent intent = new Intent(Billing.this, patient_home.class);
        startActivity(intent);
    }

    private void clearAll()
    {
        txtName.setText("");
        txtDate.setText("");
        txtTime.setText("");
        txtNumber.setText("");
        txtCost.setText("");
        txtDescription.setText("");
    }
}


