package com.store.healthsnap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class StockAdapter extends FirebaseRecyclerAdapter <StockModel, StockAdapter.myViewHolder> {

    public StockAdapter(@NonNull FirebaseRecyclerOptions<StockModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull StockModel stockModel) {

        holder.sName.setText(stockModel.getName());
        holder.mDate.setText(stockModel.getMdate());
        holder.eDate.setText(stockModel.getEdate());
        holder.sId.setText(stockModel.getId());
        holder.sPrice.setText(stockModel.getSP());
        holder.bPrice.setText(stockModel.getBP());
        holder.sQuantity.setText(stockModel.getQuantity());

        Glide.with(holder.sName.getContext())
                .load(stockModel.getName());



        holder.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.sName.getContext())
                        .setContentHolder(new ViewHolder(R.layout.activity_medics))
                        .setExpanded(true, 800)
                        .create();

                View view = dialogPlus.getHolderView();

                EditText sName, mDate, eDate, sId, sSellingPrice, sBuyingrice, sQuantity, bpTotal, spTotal, sProfit;

                sName = view.findViewById(R.id.txtM_Name);
                mDate = view.findViewById(R.id.txtM_ManDate);
                eDate = view.findViewById(R.id.txtM_ExpDate);
                sId = view.findViewById(R.id.txtM_Id);
                sSellingPrice = view.findViewById(R.id.txtM_Sprice);
                sBuyingrice = view.findViewById(R.id.txtM_Bprice);
                sQuantity = view.findViewById(R.id.txtStockQuantity);
                spTotal = view.findViewById(R.id.txtM_TotalSprice);
                bpTotal = view.findViewById(R.id.txtM_TotalBprice);
                sProfit = view.findViewById(R.id.txtM_Profit);



                sName.setText(stockModel.getName());
                mDate.setText(stockModel.getMdate());
                eDate.setText(stockModel.getEdate());
                sId.setText(stockModel.getId());
                sSellingPrice.setText(stockModel.getSP());
                sBuyingrice.setText(stockModel.getBP());
                sQuantity.setText(stockModel.getQuantity());
                spTotal.setText(stockModel.getSPtotal());
                bpTotal.setText(stockModel.getBPtotal());
                sProfit.setText(stockModel.getProfit());

                dialogPlus.show();

                Button btnUpdate = view.findViewById(R.id.btnSaveStock);


                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Map<String, Object> map = new HashMap<>();

                        map.put("Name", sName.getText().toString());
                        map.put("Mdate", mDate.getText().toString());
                        map.put("Edate", eDate.getText().toString());
                        map.put("Id", sId.getText().toString());
                        map.put("BPtotal", bpTotal.getText().toString());
                        map.put("Profit", sProfit.getText().toString());
                        map.put("Quantity", sQuantity.getText().toString());
                        map.put("SP", sSellingPrice.getText().toString());
                        map.put("BP", sBuyingrice.getText().toString());
                        map.put("SPtotal", spTotal.getText().toString());

                        FirebaseDatabase.getInstance().getReference("Stock").child("Medical")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        Toast.makeText(holder.sId.getContext(), "Updated Sucessfully!", Toast.LENGTH_SHORT).show();

                                        dialogPlus.dismiss();
                                    }
                                })

                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {

                                        Toast.makeText(holder.sId.getContext(), "Update Failed!", Toast.LENGTH_SHORT).show();

                                    }
                                });


                    }
                });



            }
        });


    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_stock_card, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder
    {
        TextView sName, mDate, eDate, sId, sPrice, bPrice, sQuantity, bpTotal, spTotal;

        Button btnSave, btnView;

        public myViewHolder(@NonNull View itemView)
        {
            super(itemView);

            sName = itemView.findViewById(R.id.StockName);
            mDate = itemView.findViewById(R.id.Stock_mDate);
            eDate = itemView.findViewById(R.id.Stock_eDate);
            sId= itemView.findViewById(R.id.stockID);
            sPrice = itemView.findViewById(R.id.StockSellingPrice);
            bPrice = itemView.findViewById(R.id.stockBuyingPrice);
            sQuantity = itemView.findViewById(R.id.StockQuantity);

            btnSave = itemView.findViewById(R.id.btnSaveStock);
            btnView = itemView.findViewById(R.id.btnViewStock);

        }
    }


}
