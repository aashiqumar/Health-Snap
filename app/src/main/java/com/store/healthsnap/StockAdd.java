package com.store.healthsnap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class StockAdd extends AppCompatActivity {

    EditText txtId, txtName, txtMDate, txtEDate, txtQuantity, txtBPrice, txtSPrice;
    TextView txtTBPrice, txtTSPrice, txtProfit, txtStockCount;

    Button btnSave, btnBack;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medics);

        txtId = findViewById(R.id.txtM_Id);
        txtName = findViewById(R.id.txtM_Name);
        txtEDate = findViewById(R.id.txtM_ExpDate);
        txtMDate = findViewById(R.id.txtM_ManDate);
        txtQuantity = findViewById(R.id.txtStockQuantity);
        txtBPrice = findViewById(R.id.txtM_Bprice);
        txtTBPrice = findViewById(R.id.txtM_TotalBprice);
        txtSPrice = findViewById(R.id.txtM_Sprice);
        txtTSPrice = findViewById(R.id.txtM_TotalSprice);
        txtProfit = findViewById(R.id.txtM_Profit);


        btnSave = findViewById(R.id.btnSaveStock);
        btnBack = findViewById(R.id.btnStockBack);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goToStockHome();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addToFirebase();
                clearAll();
            }
        });

        txtMDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDate();
            }
        });

        txtEDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                eDate();
            }
        });

        txtTBPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 bpTotal();
            }
        });

        txtTSPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                spTotal();
            }
        });

        txtProfit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                profit();
            }
        });



    }

    private void goToStockHome()
    {
        Intent intent = new Intent(StockAdd.this, view_stocks.class);
        startActivity(intent);
    }

    private void mDate()
    {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

                txtMDate.setText(simpleDateFormat.format(calendar.getTime()));


            }
        };

        new DatePickerDialog(StockAdd.this, dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void eDate()
    {
        Calendar calendar1 = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                calendar1.set(Calendar.YEAR,year);
                calendar1.set(Calendar.MONTH,month);
                calendar1.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

                txtMDate.setText(simpleDateFormat.format(calendar1.getTime()));


            }
        };

        new DatePickerDialog(StockAdd.this, dateSetListener,
                calendar1.get(Calendar.YEAR),
                calendar1.get(Calendar.MONTH),
                calendar1.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void bpTotal()
    {
        double buyingPrice = 0, quantity = 0, totalBP = 0;

        buyingPrice = Integer.parseInt(txtBPrice.getText().toString());
        quantity = Integer.parseInt(txtQuantity.getText().toString());

        totalBP = quantity * buyingPrice;

        txtTBPrice.setText(String.valueOf(totalBP));
    }

    private void spTotal()
    {
        double sellingPrice = 0, quantity = 0, totalSP = 0;

        sellingPrice = Integer.parseInt(txtSPrice.getText().toString());
        quantity = Integer.parseInt(txtQuantity.getText().toString());

        totalSP = quantity * sellingPrice;

        txtTSPrice.setText(String.valueOf(totalSP));
    }

    private void profit()
    {
        double totalBP = 0, totalSP = 0, profit = 0, buyingPrice = 0, sellingPrice = 0, quantity = 0;

        buyingPrice = Integer.parseInt(txtBPrice.getText().toString());
        sellingPrice = Integer.parseInt(txtSPrice.getText().toString());
        quantity = Integer.parseInt(txtQuantity.getText().toString());

        totalBP = buyingPrice * quantity;
        totalSP = sellingPrice * quantity;

        profit = totalSP - totalBP;

        txtProfit.setText(String.valueOf(profit));
    }

    private void addToFirebase()
    {

                Map<String, Object> map = new HashMap<>();

                map.put("Name", txtName.getText().toString());
                map.put("Mdate", txtMDate.getText().toString());
                map.put("Edate", txtEDate.getText().toString());
                map.put("Id", txtId.getText().toString());
                map.put("BPtotal", txtTBPrice.getText().toString());
                map.put("Profit", txtProfit.getText().toString());
                map.put("Quantity", txtQuantity.getText().toString());
                map.put("SP", txtSPrice.getText().toString());
                map.put("BP", txtBPrice.getText().toString());
                map.put("SPtotal", txtTSPrice.getText().toString());

                FirebaseDatabase.getInstance().getReference("Stock").child("Medical").push()
                        .setValue(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(StockAdd.this, "Stock Added Sucessfully!", Toast.LENGTH_SHORT).show();

                            }
                        })

                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {

                                Toast.makeText(StockAdd.this, "Stock Failed to Add!", Toast.LENGTH_SHORT).show();

                            }
                        });


    }

    private void clearAll()
    {
        txtId.setText("");
        txtName.setText("");
        txtEDate.setText("");
        txtMDate.setText("");
        txtQuantity.setText("");
        txtBPrice.setText("");
        txtTBPrice.setText("");
        txtSPrice.setText("");
        txtTSPrice.setText("");
        txtProfit.setText("");

    }



}
