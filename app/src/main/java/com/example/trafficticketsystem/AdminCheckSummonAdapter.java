package com.example.trafficticketsystem;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AdminCheckSummonAdapter extends FirebaseRecyclerAdapter<UploadSummon_Admin,AdminCheckSummonAdapter.myviewholder> {
    public AdminCheckSummonAdapter(@NonNull FirebaseRecyclerOptions<UploadSummon_Admin> options)
    {

        super(options);

    }

    protected void onBindViewHolder(@NonNull final AdminCheckSummonAdapter.myviewholder holder, final int position, @NonNull final UploadSummon_Admin uploadSummon_admin){
            holder.summon_No.setText(uploadSummon_admin.getSummonNo());
            holder.plate_No.setText(uploadSummon_admin.getPlateNo());
            holder.date.setText(uploadSummon_admin.getDate());
            holder.time.setText(uploadSummon_admin.getTime());
            holder.location.setText(uploadSummon_admin.getLocation());
            holder.payment_status.setText(uploadSummon_admin.getPaymentStatus());
            holder.police_name.setText(uploadSummon_admin.getPoliceName());
            holder.summon_Amount.setText(uploadSummon_admin.getSummonAmount());
            holder.summon_Type.setText(uploadSummon_admin.getTypeSummon());
            Glide.with(holder.image_summon.getContext()).load(uploadSummon_admin.getImageURL()).into(holder.image_summon);


            holder.check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String _PlateNo = uploadSummon_admin.getPlateNo();
                    String _NO = uploadSummon_admin.getSummonNo();
                    String _TYPE = uploadSummon_admin.getTypeSummon();
                    String _LOCATION = uploadSummon_admin.getLocation();
                    String _DATE = uploadSummon_admin.getDate();
                    String _TIME = uploadSummon_admin.getTime();
                    String _AMOUNT = uploadSummon_admin.getSummonAmount();
                    String _IMAGE = uploadSummon_admin.getImageURL();
                    String _STATUS = uploadSummon_admin.getPaymentStatus();
                    String _POLICE = uploadSummon_admin.getPoliceName();
                    Context context = v.getContext();

                    preferences2 preferences = new preferences2(context);
                    preferences.CheckAdminSession(_PlateNo, _NO, _TYPE, _LOCATION, _DATE, _TIME, _AMOUNT, _STATUS, _IMAGE,_POLICE);
                    Intent intent = new Intent(context,AdminSummonListActivity.class);
                    ((Activity)context).startActivity(intent);

                }
            });
    }

    @NonNull
    @Override
    public AdminCheckSummonAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adminsummonlist,parent,false);
        return new myviewholder(view);
    }




    class myviewholder extends RecyclerView.ViewHolder
    {


        TextView summon_No, plate_No, date, time, location, payment_status, police_name, summon_Amount, summon_Type;
        ImageView check,image_summon;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            summon_No = (TextView)itemView.findViewById(R.id.summonNo_txt);
            plate_No =(TextView)itemView.findViewById(R.id.plate_txt);
            date = (TextView)itemView.findViewById(R.id.date_txt);
            time = (TextView)itemView.findViewById(R.id.time_txt);
            location = (TextView)itemView.findViewById(R.id.location_txt);
            payment_status = (TextView)itemView.findViewById(R.id.status_txt);
            police_name = (TextView)itemView.findViewById(R.id.police_txt);
            summon_Amount = (TextView)itemView.findViewById(R.id.Amount_txt);
            summon_Type = (TextView)itemView.findViewById(R.id.type_txt);
            image_summon = (ImageView)itemView.findViewById(R.id.image_Summon);



            check=(ImageView)itemView.findViewById(R.id.check);

        }
    }
}
