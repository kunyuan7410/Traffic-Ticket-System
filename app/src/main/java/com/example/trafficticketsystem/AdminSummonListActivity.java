package com.example.trafficticketsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.HashMap;

public class AdminSummonListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_summon_list);
        TextView plateNo = findViewById(R.id.pPlate);
        TextView typeSummon = findViewById(R.id.pViolation);
        TextView date = findViewById(R.id.pDate);
        TextView time = findViewById(R.id.pTime);
        TextView SummonAmount = findViewById(R.id.pAmount);
        TextView location = findViewById(R.id.pLocation);
        TextView SummonNo = findViewById(R.id.pSummonNo);
        TextView SummonStatus = findViewById(R.id.pStatus);
        TextView SummonPolice = findViewById(R.id.pPolice);
        ImageView summonImage = findViewById(R.id.imageSummon);
        final ImageButton btnBack = findViewById(R.id.uBack);

        preferences2 preferences2 = new preferences2(this);
        HashMap<String, String> summonDetails = preferences2.getAdminCheckSession();
        String plate = summonDetails.get(preferences2.KEY_PLATE_NO);
        String summonNo = summonDetails.get(preferences2.KEY_NO);
        String summonType = summonDetails.get(preferences2.KEY_TYPE);
        String ssLocation = summonDetails.get(preferences2.KEY_LOCATION);
        String ssDate = summonDetails.get(preferences2.KEY_DATE);
        String ssTime = summonDetails.get(preferences2.KEY_TIME);
        String ssAmount = summonDetails.get(preferences2.KEY_AMOUNT);
        String ssStatus = summonDetails.get(preferences2.KEY_STATUS);
        String ssImage = summonDetails.get(preferences2.KEY_IMAGE);
        String ssPolice= summonDetails.get(preferences2.KEY_POLICE);

        SummonNo.setText(summonNo);
        SummonStatus.setText(ssStatus);
        SummonPolice.setText(ssPolice);
        Glide.with(AdminSummonListActivity.this).load(ssImage).into(summonImage);
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
                startActivity(new Intent(AdminSummonListActivity.this,AdminCheckSummonActivity.class));
            }
        });
    }
}