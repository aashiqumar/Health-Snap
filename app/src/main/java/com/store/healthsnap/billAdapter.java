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

public class billAdapter extends FirebaseRecyclerAdapter <billModel,billAdapter.myViewHolder> {

    public billAdapter(@NonNull FirebaseRecyclerOptions<billModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull billModel billModel) {

        holder.txtName.setText(billModel.getpName());
        holder.txtDate.setText(billModel.getpDate());
        holder.txtTime.setText(billModel.getpTime());
        holder.txtCost.setText(billModel.getpFee());
//        holder.txtDescription.setText(billModel.getpDescription());
        holder.txt_pNumber.setText(billModel.getpNumber());


        Glide.with(holder.txtName.getContext())
        .load(billModel.pName);





        holder.btnViewPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.txtName.getContext())
                        .setContentHolder(new ViewHolder(R.layout.activity_billing))
                        .setExpanded(true, 800)
                        .create();

                View view = dialogPlus.getHolderView();

                EditText name, date, time, number, description, fee;

                name = view.findViewById(R.id.txtPBillName1);
                date = view.findViewById(R.id.txtPBillDate);
                time = view.findViewById(R.id.txtPBillTime);
                number = view.findViewById(R.id.txtPBillNumber);
                description = view.findViewById(R.id.txtPBillDescription);
                fee = view.findViewById(R.id.txtPBillFee);

                Button btnUpdate = view.findViewById(R.id.btnSaveBill);

                name.setText(billModel.getpName());
                date.setText(billModel.getpDate());
                time.setText(billModel.getpTime());
                number.setText(billModel.getpNumber());
                description.setText(billModel.getpDescription());
                fee.setText(billModel.getpFee());

                dialogPlus.show();


                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Map<String, Object> map = new HashMap<>();

                        map.put("pName", name.getText().toString());
                        map.put("pDate", date.getText().toString());
                        map.put("pTime", time.getText().toString());
                        map.put("pNumber", number.getText().toString());
                        map.put("pFee", fee.getText().toString());
                        map.put("pDescription", description.getText().toString());

                        FirebaseDatabase.getInstance().getReference("Billing").child("bill")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        Toast.makeText(holder.txtName.getContext(), "Updated Sucessfully!", Toast.LENGTH_SHORT).show();

                                        dialogPlus.dismiss();
                                    }
                                })

                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {

                                        Toast.makeText(holder.txtName.getContext(), "Update Failed!", Toast.LENGTH_SHORT).show();

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

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_view_patients, parent, false);
        return new myViewHolder(view);
    }



    class myViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtName, txtDate, txtTime, txtCost, txt_pNumber, txtDescription, viewFee;
        Button btnSavePatient, btnViewPatient;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtPatientName);
            txtTime = itemView.findViewById(R.id.txtPatientTime);
            txtCost = itemView.findViewById(R.id.txtM_SelingPrice);
            txtDate = itemView.findViewById(R.id.txtPatientDate);
            txt_pNumber = itemView.findViewById(R.id.txtPatientNumber);
            txtDescription = itemView.findViewById(R.id.txtPBillDescription);

            btnSavePatient = itemView.findViewById(R.id.btnSaveStock);
            btnViewPatient = itemView.findViewById(R.id.btnViewPatient);





        }
    }


}
