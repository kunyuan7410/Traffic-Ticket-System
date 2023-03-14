package com.example.trafficticketsystem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.HashMap;

public class PaySummonActivity extends AppCompatActivity {

    private static final int PAYPAL_REQUEST_CODE = 7171;
    private static PayPalConfiguration config = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)
            .clientId(Config.PAYPAL_CLIENT_ID);
    DatabaseReference reference, reference2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_summon);
         TextView plateNo = findViewById(R.id.pPlate);
         TextView typeSummon = findViewById(R.id.pViolation);
       TextView date = findViewById(R.id.pDate);
       TextView time = findViewById(R.id.pTime);
       TextView SummonAmount = findViewById(R.id.pAmount);
       TextView location = findViewById(R.id.pLocation);
      ImageView summonImage = findViewById(R.id.imageSummon);

      Intent intent = new Intent(PaySummonActivity.this,PayPalService.class);
      intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
      startService(intent);
        reference = FirebaseDatabase.getInstance().getReference("users");
        reference2 = FirebaseDatabase.getInstance().getReference("admin");


        final Button btnPay = findViewById(R.id.uPay);
        final ImageButton btnBack = findViewById(R.id.uBack);

        preferences preferences = new preferences(this);
        HashMap<String, String> summonDetails = preferences.getCheckSession();
        String plate = summonDetails.get(preferences.KEY_PLATE_NO);
        String summonNo = summonDetails.get(preferences.KEY_NO);
        String summonType = summonDetails.get(preferences.KEY_TYPE);
        String ssLocation = summonDetails.get(preferences.KEY_LOCATION);
        String ssDate = summonDetails.get(preferences.KEY_DATE);
        String ssTime = summonDetails.get(preferences.KEY_TIME);
        String ssAmount = summonDetails.get(preferences.KEY_AMOUNT);
        String ssStatus = summonDetails.get(preferences.KEY_STATUS);
        String ssImage = summonDetails.get(preferences.KEY_IMAGE);


        plateNo.setText(plate);
        typeSummon.setText(summonType);
        date.setText(ssDate);
        time.setText(ssTime);
        location.setText(ssLocation);
        SummonAmount.setText(ssAmount);
        if(ssStatus.equals("Paid")){
            btnPay.setVisibility(View.GONE);
        }else{
            btnPay.setVisibility(View.VISIBLE);
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PaySummonActivity.this,UserSummonActivity.class));
            }
        });

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processPaypal();

            }
        });


    }

    private void processPaypal() {
        preferences preferences = new preferences(this);
        HashMap<String, String> summonDetails = preferences.getCheckSession();
        String ssAmount = summonDetails.get(preferences.KEY_AMOUNT);
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(ssAmount)),"MYR",
                "Pay for summon",PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(PaySummonActivity.this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(intent,PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(PaySummonActivity.this,PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK)
            {
                        preferences preferences = new preferences(this);
                        HashMap<String, String> summonDetails = preferences.getCheckSession();
                        String summonNo = summonDetails.get(preferences.KEY_NO);
                        String sPlate = summonDetails.get(preferences.KEY_PLATE_NO);
                        reference.child(sPlate).child("Summons").child(summonNo).child("paymentStatus").setValue("Paid");
                        reference2.child("Summon").child(summonNo).child("paymentStatus").setValue("Paid");
                        Toast.makeText(PaySummonActivity.this,"Payment Successfully",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(PaySummonActivity.this,UserSummonActivity.class));


                }else{
                Toast.makeText(PaySummonActivity.this,"Failed to Payment",Toast.LENGTH_LONG).show();
            }
            }
//            else if(resultCode == Activity.RESULT_CANCELED)
//
//                Toast.makeText(PaySummonActivity.this,"Cancel",Toast.LENGTH_SHORT).show();
//
//        }else if(resultCode==PaymentActivity.RESULT_EXTRAS_INVALID)
//            Toast.makeText(PaySummonActivity.this,"Invalid",Toast.LENGTH_SHORT).show();

    }
}