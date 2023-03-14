package com.example.trafficticketsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UserSummonActivity extends AppCompatActivity {
    RecyclerView SummonRecyclerView;
    SummonAdapter summonAdapter;
    DrawerLayout drawerLayout;
    TextView No_SUMMON;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_summon);
        preferences preferences = new preferences(this);
        HashMap<String,String> userDetails = preferences.getUsersDetailFromSession();
        String name = userDetails.get(preferences.KEY_NAME);
        String password = userDetails.get(preferences.KEY_PASSWORD);
        String ic = userDetails.get(preferences.KEY_IC);
        String plate = userDetails.get(preferences.KEY_PLATE);
        String email = userDetails.get(preferences.KEY_EMAIL);
        String phone = userDetails.get(preferences.KEY_PHONE);
        String username = userDetails.get(preferences.KEY_USERNAME);
        drawerLayout = findViewById(R.id.drawer_layout);
//        No_SUMMON = findViewById(R.id.NO_SUMMON);


        SummonRecyclerView = findViewById(R.id.SummonReview);
        SummonRecyclerView.setLayoutManager(new LinearLayoutManager(this));



        FirebaseRecyclerOptions<summonModel> options = new FirebaseRecyclerOptions.Builder<summonModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("users").child(plate).child("Summons"),summonModel.class).build();
        summonAdapter = new SummonAdapter(options);
        SummonRecyclerView.setAdapter(summonAdapter);


//            if(summonAdapter.getItemCount()>0){
////                No_SUMMON.setVisibility(View.VISIBLE);
//                No_SUMMON.setVisibility(View.GONE);
//        }
//            else{
//                No_SUMMON.setVisibility(View.GONE);
//            }

//        if(summonAdapter.getItemCount()>0){
//            No_SUMMON.setVisibility(View.GONE);
//            SummonRecyclerView.setVisibility(View.VISIBLE);
//        }else if(summonAdapter.getItemCount()<0) {
//            No_SUMMON.setVisibility(View.VISIBLE);
//            SummonRecyclerView.setVisibility(View.GONE);
//        }



//        if(summonAdapter.getItemCount()==0){
//            No_SUMMON.setVisibility(View.GONE);
//
//        }else{
//            No_SUMMON.setVisibility(View.VISIBLE);
//        }


    }
    @Override
    protected void onStart() {
        super.onStart();
        summonAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        summonAdapter.stopListening();
    }

    public void ClickMenu(View view){
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);

    }
    public void ClickLogo(View view){
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    public void ClickHome(View view){
        redirectActivity(this,UserActivity.class);
    }
    public void ClickProfile(View view){
        redirectActivity(this,ProfileActivity.class);
    }
    public void ClickSummonList(View view){ recreate(); }
    public void Logout(View view){
        Intent intent = new Intent(UserSummonActivity.this,MainActivity.class);
        startActivity(intent);
        preferences.clearData(this);
        finish();

    }


    public static void redirectActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity,aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);

    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }

}