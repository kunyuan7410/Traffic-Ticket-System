package com.example.trafficticketsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
public class AboutUsActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        drawerLayout = findViewById(R.id.drawer_layout);
    }
    public void ClickMenu(View view){
        UserActivity.openDrawer(drawerLayout);
    }
    public void ClickLogo(View view){
        UserActivity.closeDrawer(drawerLayout);
    }
    public void ClickHome(View view){
        UserActivity.redirectActivity(this,UserActivity.class);
    }
    public void ClickProfile(View view){
        UserActivity.redirectActivity(this,ProfileActivity.class);
    }
    public void ClickAboutUs(View view){
        recreate();
    }
    public void Logout(View view){
        Intent intent = new Intent(AboutUsActivity.this,MainActivity.class);
        startActivity(intent);
        preferences.clearData(this);
        finish();
    }
    protected void onPause(){
        super.onPause();
        UserActivity.closeDrawer(drawerLayout);
    }
}