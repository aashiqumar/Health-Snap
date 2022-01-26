package com.store.healthsnap;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel, MainAdapter.myViewHolder> {

    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull MainModel model) {

        holder.title.setText(model.getTitle());
        holder.date.setText(model.getDate());
        holder.time.setText(model.getTime());

        Glide.with(holder.title.getContext())
                .load(model.getTitle());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.title.getContext())
                        .setContentHolder(new ViewHolder(R.layout.item))
                        .setExpanded(true, 610)
                        .create();


                EditText title1, date, time, description;

                View view = dialogPlus.getHolderView();

                title1 = view.findViewById(R.id.txtTitle);
                date = view.findViewById(R.id.txtDate);
                time = view.findViewById(R.id.txtTime);
                description = view.findViewById(R.id.txtDes);

                Button btnUpdate = view.findViewById(R.id.btnUpdate);

                title1.setText(model.getTitle());
                date.setText(model.getDate());
                time.setText(model.getTime());
                description.setText(model.getDescription());

                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Map<String,Object> map = new HashMap<>();
                        map.put("title", title1.getText().toString());
                        map.put("date", date.getText().toString());
                        map.put("time", time.getText().toString());
                        map.put("description", description.getText().toString());

                        FirebaseDatabase.getInstance().getReference("Appointments").child("save")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        Toast.makeText(holder.title.getContext(), "Updated Sucesffully!", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })

                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {

                                        Toast.makeText(holder.title.getContext(), "Update Failed! Try Again", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });


                    }
                });


            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(holder.title.getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("Deleted Apointments Can't Undo!");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        FirebaseDatabase.getInstance().getReference("Appointments").child("save")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(holder.title.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();

                    }
                });

                builder.show();
            }
        });




    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_view_appointments,parent, false);


        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        TextView title, date, time, description;

        Button btnEdit, btnDelete;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.txtTitle);
            date = itemView.findViewById(R.id.txtDate);
            time = itemView.findViewById(R.id.txtTime);
            description = itemView.findViewById(R.id.txtDes);

            btnEdit = (Button)itemView.findViewById(R.id.btnEditAppointment);
            btnDelete = (Button)itemView.findViewById(R.id.btnDeleteAppointment);
        }
    }
}
