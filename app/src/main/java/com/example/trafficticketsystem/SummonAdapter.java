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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.DialogPlusBuilder;
import com.orhanobut.dialogplus.ViewHolder;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.logging.Handler;

import de.hdodenhof.circleimageview.CircleImageView;

public class SummonAdapter extends FirebaseRecyclerAdapter<summonModel,SummonAdapter.myviewholder> {




    public SummonAdapter(@NonNull FirebaseRecyclerOptions<summonModel> options)
    {

        super(options);

    }


    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull final summonModel summonModel)
    {
        holder.plateNo.setText(summonModel.getPlateNo());
        holder.typeSummon.setText(summonModel.getTypeSummon());
        holder.date.setText(summonModel.getDate());
        holder.time.setText(summonModel.getTime());
        holder.SummonAmount.setText(summonModel.getSummonAmount());
        holder.location.setText(summonModel.getLocation());
//        holder.imageURL.setTag(summonModel.getImageURL());
        holder.status.setText(summonModel.getPaymentStatus());
        Glide.with(holder.imageURL.getContext()).load(summonModel.getImageURL()).into(holder.imageURL);




        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _PlateNo = summonModel.getPlateNo();
                String _NO = summonModel.getSummonNo();
                String _TYPE = summonModel.getTypeSummon();
                String _LOCATION = summonModel.getLocation();
                String _DATE = summonModel.getDate();
                String _TIME = summonModel.getTime();
                String _AMOUNT = summonModel.getSummonAmount();

                String _IMAGE = summonModel.getImageURL();
                String _STATUS = summonModel.getPaymentStatus();
                Context context = v.getContext();




                preferences preferences = new preferences(context);
                preferences.CheckSession(_PlateNo, _NO, _TYPE, _LOCATION, _DATE, _TIME, _AMOUNT, _STATUS, _IMAGE);

                Intent intent = new Intent(context,PaySummonActivity.class);
                ((Activity)context).startActivity(intent);



//                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.imageURL.getContext())
//                        .setContentHolder(new ViewHolder(R.layout.userchecksummon)).setExpanded(true,2000).create();

//                View summon_view = dialogPlus.getHolderView();
//                final TextView plateNo = summon_view.findViewById(R.id.pPlate);
//                final TextView typeSummon = summon_view.findViewById(R.id.pViolation);
//                final TextView date = summon_view.findViewById(R.id.pDate);
//                final TextView time = summon_view.findViewById(R.id.pTime);
//                final TextView SummonAmount = summon_view.findViewById(R.id.pAmount);
//                final TextView location = summon_view.findViewById(R.id.pLocation);


//                Button btnPay =summon_view.findViewById(R.id.uPay);
//                ImageButton btnBack = summon_view.findViewById(R.id.uBack);
//
//                plateNo.setText(summonModel.getPlateNo());
//                typeSummon.setText(summonModel.getTypeSummon());
//                date.setText(summonModel.getDate());
//                time.setText(summonModel.getTime());
//                SummonAmount.setText(summonModel.getSummonAmount());
//                location.setText(summonModel.getLocation());
//                Context context = v.getContext();
//                Intent intent = new Intent(context,PayPalService.class);
//                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
//                ((Activity)context).startService(intent);
//
//
//
//                dialogPlus.show();

//                btnPay.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Map<String,Object> mapSummon = new HashMap<>();
//                        String plate = plateNo.getText().toString();
//                        mapSummon.put("plateNo",plateNo.getText().toString());
//                        mapSummon.put("typeSummon",typeSummon.getText().toString());
//                        mapSummon.put("date",date.getText().toString());
//                        mapSummon.put("time",time.getText().toString());
//                        mapSummon.put("SummonAmount",SummonAmount.getText().toString());
//                        mapSummon.put("location",location.getText().toString());
//                        String amount = SummonAmount.getText().toString();
//
//                        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)),"RM",
//                                "Payment for Summon",PayPalPayment.PAYMENT_INTENT_SALE);
//                        Context context = v.getContext();
//                        Intent intent = new Intent(context, PaymentActivity.class);
//                        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
//                        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
//                        ((Activity)context).startActivityForResult(intent,PAYPAL_REQUEST_CODE);
//
//                        FirebaseDatabase.getInstance().getReference().child("users").child(plate).child("Summons").child(getRef(position).getKey()).updateChildren(mapSummon)
//                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void aVoid) {
//                                        dialogPlus.dismiss();
//                                    }
//                                }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                dialogPlus.dismiss();
//                            }
//                        });
//                    }
//                });
//
//                btnBack.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialogPlus.dismiss();
//                    }
//                });

            }
        });






    }



    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.summonlist,parent,false);
        return new myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView imageURL;
        ImageView check;

        TextView plateNo, typeSummon, date, time, SummonAmount, location, status ;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            imageURL=(ImageView) itemView.findViewById(R.id.img123);
            plateNo=(TextView)itemView.findViewById(R.id.platetxt);
            typeSummon=(TextView)itemView.findViewById(R.id.violationtxt);
            date=(TextView)itemView.findViewById(R.id.Datetxt);
            time=(TextView)itemView.findViewById(R.id.Timetxt);
            SummonAmount=(TextView)itemView.findViewById(R.id.Amounttxt);
            location=(TextView)itemView.findViewById(R.id.Locationtxt);
            status=(TextView)itemView.findViewById(R.id.statustxt);



            check=(ImageView)itemView.findViewById(R.id.check);

        }
    }
}
