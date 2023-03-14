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

public class PoliceCheckHistoryAdapter extends FirebaseRecyclerAdapter<UploadSummon_Police,PoliceCheckHistoryAdapter.myviewholder> {
    public PoliceCheckHistoryAdapter(@NonNull FirebaseRecyclerOptions<UploadSummon_Police> options)
    {

        super(options);

    }
    protected void onBindViewHolder(@NonNull final PoliceCheckHistoryAdapter.myviewholder holder, final int position, @NonNull final UploadSummon_Police uploadSummon_police){
        holder.sSNo.setText(uploadSummon_police.getSummonNo());
        holder.sPlate.setText(uploadSummon_police.getPlateNo());
        holder.sDate.setText(uploadSummon_police.getDate());
        holder.sTime.setText(uploadSummon_police.getTime());
        holder.sLocation.setText(uploadSummon_police.getLocation());
        holder.sAmount.setText(uploadSummon_police.getSummonAmount());
        holder.sType.setText(uploadSummon_police.getTypeSummon());
        Glide.with(holder.sImage.getContext()).load(uploadSummon_police.getImageURL()).into(holder.sImage);

        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _PlateNo = uploadSummon_police.getPlateNo();
                String _NO = uploadSummon_police.getSummonNo();
                String _TYPE = uploadSummon_police.getTypeSummon();
                String _LOCATION = uploadSummon_police.getLocation();
                String _DATE = uploadSummon_police.getDate();
                String _TIME = uploadSummon_police.getTime();
                String _AMOUNT = uploadSummon_police.getSummonAmount();
                String _IMAGE = uploadSummon_police.getImageURL();
                Context context = v.getContext();

                preference3 preferences = new preference3(context);
                preferences.CheckPoliceSession(_PlateNo, _NO, _TYPE, _LOCATION, _DATE, _TIME, _AMOUNT, _IMAGE);
                Intent intent = new Intent(context,PoliceRecordInformationActivity.class);
                ((Activity)context).startActivity(intent);

            }
        });

    }
    @NonNull
    @Override
    public PoliceCheckHistoryAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.policehistorylistbox,parent,false);
        return new PoliceCheckHistoryAdapter.myviewholder(view);
    }




    class myviewholder extends RecyclerView.ViewHolder
    {


        TextView sSNo, sPlate, sDate, sTime, sLocation, sAmount, sType;
        ImageView check,sImage;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            sSNo = (TextView)itemView.findViewById(R.id.sSNo_txt);
            sPlate =(TextView)itemView.findViewById(R.id.sPlate_txt);
            sDate = (TextView)itemView.findViewById(R.id.sDate_txt);
            sTime = (TextView)itemView.findViewById(R.id.sTime_txt);
            sLocation = (TextView)itemView.findViewById(R.id.sLocation_txt);
            sAmount = (TextView)itemView.findViewById(R.id.sAmount_txt);
            sType = (TextView)itemView.findViewById(R.id.sType_txt);
            sImage = (ImageView)itemView.findViewById(R.id.sImage_Summon);



            check=(ImageView)itemView.findViewById(R.id.check);

        }
    }
}
