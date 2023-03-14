package com.example.trafficticketsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.HashMap;

public class PoliceRecordInformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_record_information);
        TextView plateNo = findViewById(R.id.pPlate);
        TextView typeSummon = findViewById(R.id.pViolation);
        TextView date = findViewById(R.id.pDate);
        TextView time = findViewById(R.id.pTime);
        TextView SummonAmount = findViewById(R.id.pAmount);
        TextView location = findViewById(R.id.pLocation);
        TextView SummonNo = findViewById(R.id.pSummonNo);
        ImageView summonImage = findViewById(R.id.imageSummon);
        final ImageButton btnBack = findViewById(R.id.uBack);

        preference3 preference3 = new preference3(this);
        HashMap<String, String> HistoryDetails = preference3.getPoliceCheckSession();
        String plate = HistoryDetails.get(preferences2.KEY_PLATE_NO);
        String summonNo = HistoryDetails.get(preferences2.KEY_NO);
        String summonType = HistoryDetails.get(preferences2.KEY_TYPE);
        String ssLocation = HistoryDetails.get(preferences2.KEY_LOCATION);
        String ssDate = HistoryDetails.get(preferences2.KEY_DATE);
        String ssTime = HistoryDetails.get(preferences2.KEY_TIME);
        String ssAmount = HistoryDetails.get(preferences2.KEY_AMOUNT);
        String ssImage = HistoryDetails.get(preferences2.KEY_IMAGE);

        SummonNo.setText(summonNo);
        Glide.with(PoliceRecordInformationActivity.this).load(ssImage).into(summonImage);
        summonImage.getLayoutParams().height = 500;
        plateNo.setText(plate);
        typeSummon.setText(summonType);
        date.setText(ssDate);
        time.setText(ssTime);
        location.setText(ssLocation);
        SummonAmount.setText(ssAmount);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PoliceRecordInformationActivity.this,PoliceHistoryActivity.class));
            }
        });

    }
}