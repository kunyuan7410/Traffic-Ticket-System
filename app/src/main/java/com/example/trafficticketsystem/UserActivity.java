package com.example.trafficticketsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

public class UserActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        drawerLayout = findViewById(R.id.drawer_layout);

        textView = findViewById(R.id.text1);
        preferences preferences = new preferences(this);
        HashMap<String, String> summonDetails = preferences.getUsersDetailFromSession();
        String plate = summonDetails.get(preferences.KEY_PLATE_NO);
        String summonNo = summonDetails.get(preferences.KEY_NO);
        String summonType = summonDetails.get(preferences.KEY_TYPE);
        String ssLocation = summonDetails.get(preferences.KEY_LOCATION);
        String ssDate = summonDetails.get(preferences.KEY_DATE);
        String ssTime = summonDetails.get(preferences.KEY_TIME);
        String ssAmount = summonDetails.get(preferences.KEY_AMOUNT);
        String ssStatus = summonDetails.get(preferences.KEY_STATUS);
        String ssImage = summonDetails.get(preferences.KEY_IMAGE);
  


//        preferences preferences = new preferences(this);
//        HashMap<String, String> usersDetails = preferences.getUsersDetailFromSession();
//        String name = usersDetails.get(preferences.KEY_NAME);
//        String password = usersDetails.get(preferences.KEY_PASSWORD);
//        textView.setText(name + "\n" + password);
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
        recreate();
    }
    public void ClickProfile(View view){
        redirectActivity(this,ProfileActivity.class);
    }
    public void ClickSummonList(View view){
        redirectActivity(this,UserSummonActivity.class);
    }
    public void Logout(View view){
        Intent intent = new Intent(UserActivity.this,MainActivity.class);
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