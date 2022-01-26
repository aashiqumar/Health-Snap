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

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class view_stocks extends AppCompatActivity {

    RecyclerView recyclerView1;
    StockAdapter stockAdapter;

    Button btnAdd,btnBack;
    billAdapter BillAdapter;

    TextView txtStockCount;

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stocks);

        txtStockCount = findViewById(R.id.stockCount);

        btnAdd = findViewById(R.id.btnAddStock);
        btnBack = findViewById(R.id.btnBackStock);

        recyclerView1 = findViewById(R.id.rViewStock);

        recyclerView1.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<StockModel> options =
                new FirebaseRecyclerOptions.Builder<StockModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Stock").child("Medical"), StockModel.class)
                        .build();

      
        stockAdapter = new StockAdapter(options);

        recyclerView1.setAdapter(stockAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addStock();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                backHome();

            }
        });


        reference = FirebaseDatabase.getInstance().getReference("Stock").child("Medical");
        reference.keepSynced(true);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int count = 0;

                if (dataSnapshot.exists())
                {
                    count = (int) dataSnapshot.getChildrenCount();
                    txtStockCount.setText(Integer.toString(count) + "");
                }

                else
                {
                    txtStockCount.setText("Wow! Such Empty.");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        stockAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        stockAdapter.stopListening();
    }

    private void addStock()
    {
        Intent intent = new Intent(view_stocks.this, StockAdd.class);
        startActivity(intent);
    }

    private void backHome()
    {
        Intent intent = new Intent(view_stocks.this, Home_page.class);
        startActivity(intent);

    }
}
