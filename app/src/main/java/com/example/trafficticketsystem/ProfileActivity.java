package com.example.trafficticketsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    TextInputLayout pName, p_ic, plate_no, p_email, phone_no, password1, p_username;
//    String user_username, user_password, user_name, user_ic, user_plateno, user_phoneno, user_email, user_address;
    DatabaseReference reference;
    private Button btnProfile;
    private ImageView imgProfile;
    private Uri pickedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        reference = FirebaseDatabase.getInstance().getReference("users");
        password1 = findViewById(R.id.Password1);
        pName = findViewById(R.id.name1);
        p_ic = findViewById(R.id.IC1);
        plate_no = findViewById(R.id.plateNo1);
        p_email = findViewById(R.id.email1);
        phone_no = findViewById(R.id.phoneNo1);
        p_username = findViewById(R.id.username2);
        btnProfile = findViewById(R.id.buttonProfile);
        imgProfile = findViewById(R.id.profilePic);



        drawerLayout = findViewById(R.id.drawer_layout);
        preferences preferences = new preferences(this);
        HashMap<String, String> usersDetails = preferences.getUsersDetailFromSession();

        String name = usersDetails.get(preferences.KEY_NAME);
        String password = usersDetails.get(preferences.KEY_PASSWORD);
        String ic = usersDetails.get(preferences.KEY_IC);
        String plate = usersDetails.get(preferences.KEY_PLATE);
        String email = usersDetails.get(preferences.KEY_EMAIL);
        String phone = usersDetails.get(preferences.KEY_PHONE);
        String username = usersDetails.get(preferences.KEY_USERNAME);
        String profile = usersDetails.get(preferences.KEY_PROFILE);
        Glide.with(ProfileActivity.this).load(profile).into(imgProfile);

        pName.getEditText().setText(name);
        password1.getEditText().setText(password);
        p_ic.getEditText().setText(ic);
        plate_no.getEditText().setText(plate);
        p_email.getEditText().setText(email);
        phone_no.getEditText().setText(phone);
        p_username.getEditText().setText(username);

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileActivity.this,""+profile,Toast.LENGTH_SHORT).show();
            }
        });






//        showAllUserData();
    }
//    private void showAllUserData(){
//        Intent intent = getIntent();
//        user_username = intent.getStringExtra("username");
//        user_password = intent.getStringExtra("password");
//        user_name = intent.getStringExtra("name");
//        user_ic = intent.getStringExtra("ic");
//        user_plateno = intent.getStringExtra("plateno");
//        user_phoneno = intent.getStringExtra("phoneno");
//        user_email = intent.getStringExtra("email");
//        user_address = intent.getStringExtra("address");
//
//        pName.getEditText().setText(user_name);
//        p_ic.getEditText().setText(user_ic);
//        plate_no.getEditText().setText(user_plateno);
//        p_email.getEditText().setText(user_email);
//        phone_no.getEditText().setText(user_phoneno);
//        home_address.getEditText().setText(user_address);
//        password.getEditText().setText(user_password);
//    }
    public void btnUpdate(View view){
        String sName = pName.getEditText().getText().toString();
        String sPassword = password1.getEditText().getText().toString();
        String sEmail = p_email.getEditText().getText().toString();
        String sIC = p_ic.getEditText().getText().toString();
        String sPhone = phone_no.getEditText().getText().toString();
        String sPlate = plate_no.getEditText().getText().toString();
        String sUsername = p_username.getEditText().getText().toString();
        String sProfile = "";


        if (!sName.isEmpty() && !sEmail.isEmpty() && !sIC.isEmpty() && !sPhone.isEmpty() && !sPlate.isEmpty() && !sPassword.isEmpty()) {

            reference.child(sPlate).child("name").setValue(pName.getEditText().getText().toString());

            reference.child(sPlate).child("email").setValue(p_email.getEditText().getText().toString());

            reference.child(sPlate).child("icNo").setValue(p_ic.getEditText().getText().toString());

            reference.child(sPlate).child("phoneNo").setValue(phone_no.getEditText().getText().toString());

            reference.child(sPlate).child("plateNo").setValue(plate_no.getEditText().getText().toString());

            reference.child(sPlate).child("password").setValue(password1.getEditText().getText().toString());



            String _name = pName.getEditText().getText().toString();
            String _password = password1.getEditText().getText().toString();
            String _email = p_email.getEditText().getText().toString();
            String _phone = phone_no.getEditText().getText().toString();
            String _plate = plate_no.getEditText().getText().toString();
            String _username = p_username.getEditText().getText().toString();
            String _icNo = p_ic.getEditText().getText().toString();
            String _profile = "";

            preferences preferences = new preferences(ProfileActivity.this);
            preferences.createLoginSession(_username, _password, _name, _email, _phone, _plate, _icNo, _profile);


            Toast.makeText(this,"Data has been saved",Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this,"Field cannot be empty",Toast.LENGTH_LONG).show();


        }





    }

//    private boolean NameUpdate() {
//        String val = pName.getEditText().getText().toString();
//        if (!val.isEmpty()) {
//
//            pName.setError(null);
//            pName.setErrorEnabled(false);
//
//            reference.child(user_username).child("name").setValue(pName.getEditText().getText().toString());
//            user_name = pName.getEditText().getText().toString();
//            return true;
//
//
//        } else {
//            pName.setError("Field cannot be empty");
//            return false;
//        }
//
//    }
//
//    private boolean PasswordUpdate() {
//        if(!user_password.equals(password.getEditText().getText().toString())) {
//            reference.child(user_username).child("password").setValue(password.getEditText().getText().toString());
//            user_password = password.getEditText().getText().toString();
//            return true;
//        }else{
//            return false;
//        }
//
//    }
//    private boolean EmailUpdate() {
//        String val = p_email.getEditText().getText().toString();
//        if (!val.isEmpty()) {
//
//            p_email.setError(null);
//            p_email.setErrorEnabled(false);
//
//            reference.child(user_username).child("email").setValue(p_email.getEditText().getText().toString());
//            user_email = p_email.getEditText().getText().toString();
//            return true;
//
//        } else {
//            p_email.setError("Field cannot be empty");
//            return false;
//        }
//
//    }
//    private boolean ICUpdate() {
//        String val = p_ic.getEditText().getText().toString();
//        if (!val.isEmpty()) {
//
//            p_ic.setError(null);
//            p_ic.setErrorEnabled(false);
//
//            reference.child(user_ic).child("icNo").setValue(p_ic.getEditText().getText().toString());
//            user_ic = p_ic.getEditText().getText().toString();
//            return true;
//
//        } else {
//            p_ic.setError("Field cannot be empty");
//            return false;
//        }
//
//    }
//    private boolean PhoneUpdate() {
//        String val = phone_no.getEditText().getText().toString();
//        if (!val.isEmpty()) {
//
//            phone_no.setError(null);
//            phone_no.setErrorEnabled(false);
//
//            reference.child(user_phoneno).child("phoneNo").setValue(phone_no.getEditText().getText().toString());
//            user_phoneno = phone_no.getEditText().getText().toString();
//            return true;
//
//        } else {
//            phone_no.setError("Field cannot be empty");
//            return false;
//        }
//
//    }
//    private boolean PlateUpdate() {
//        String val = plate_no.getEditText().getText().toString();
//        if (!val.isEmpty()) {
//
//            plate_no.setError(null);
//            plate_no.setErrorEnabled(false);
//
//            reference.child(user_plateno).child("plateNo").setValue(plate_no.getEditText().getText().toString());
//            user_plateno = plate_no.getEditText().getText().toString();
//            return true;
//
//        } else {
//            plate_no.setError("Field cannot be empty");
//            return false;
//        }
//
//    }

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
        recreate();
    }
    public void ClickSummonList(View view){
        UserActivity.redirectActivity(this,UserSummonActivity.class);
    }
    public void Logout(View view){
        Intent intent = new Intent(ProfileActivity.this,MainActivity.class);
        startActivity(intent);
        preferences.clearData(this);
        finish();
    }
//    protected void onPause(){
//        super.onPause();
//        UserActivity.closeDrawer(drawerLayout);
//    }
}