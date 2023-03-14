package com.example.trafficticketsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class PoliceHistoryActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    RecyclerView PoliceRecycler;
    PoliceCheckHistoryAdapter policeCheckHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_history);
        drawerLayout = findViewById(R.id.drawer_layout);

        PoliceRecycler=(RecyclerView)findViewById(R.id.PoliceRecycler);
        PoliceRecycler.setLayoutManager(new LinearLayoutManager(this));
        preferences preferences = new preferences(PoliceHistoryActivity.this);
        HashMap<String, String> policeDetails = preferences.getUsersDetailFromSession();
        String policeUsername = policeDetails.get(preferences.KEY_USERNAME);

        FirebaseRecyclerOptions<UploadSummon_Police> options =
                new FirebaseRecyclerOptions.Builder<UploadSummon_Police>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("police").child(policeUsername).child("RecordHistory"), UploadSummon_Police.class)
                        .build();

        policeCheckHistoryAdapter = new PoliceCheckHistoryAdapter(options);
        PoliceRecycler.setAdapter(policeCheckHistoryAdapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        policeCheckHistoryAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        policeCheckHistoryAdapter.stopListening();
    }
    public void ClickMenu(View view) {
        PoliceActivity.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view) {
        PoliceActivity.closeDrawer(drawerLayout);
    }

//    public void ClickHome(View view) {
//        PoliceActivity.redirectActivity(this, PoliceActivity.class);
//    }

    public void ClickRecord(View view) {
        PoliceActivity.redirectActivity(this, RecordActivity.class);
    }

    public void ClickHistory(View view) {
        PoliceActivity.redirectActivity(this, PoliceHistoryActivity.class);
    }

    public void Logout(View view) {
        Intent intent = new Intent(PoliceHistoryActivity.this, MainActivity.class);
        startActivity(intent);
        preferences.clearData(this);
        finish();
    }

    protected void onPause() {
        super.onPause();
        PoliceActivity.closeDrawer(drawerLayout);
    }
}