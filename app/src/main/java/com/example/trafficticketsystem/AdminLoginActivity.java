package com.example.trafficticketsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminLoginActivity extends AppCompatActivity {
    private TextInputLayout username, password;
    private Button login;
    CheckBox active;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        Toolbar toolbar1 = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        active = findViewById(R.id.active);
        progressBar = findViewById(R.id.Progress);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("admin").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String input1 = username.getEditText().getText().toString().replaceAll("\\s+", "");
                        String input2 = password.getEditText().getText().toString().replaceAll("\\s+", "");

                        if(!input1.isEmpty()&&!input2.isEmpty()) {

                            if (dataSnapshot.child(input1).exists()) {
                                if (dataSnapshot.child(input1).child("password").getValue(String.class).equals(input2)) {
                                    if (active.isChecked()) {
                                        if (dataSnapshot.child(input1).child("as").getValue(String.class).equals("admin")) {
                                            preferences.setDataLogin(AdminLoginActivity.this, true);
                                            preferences.setDataAs(AdminLoginActivity.this, "admin");
                                            startActivity(new Intent(AdminLoginActivity.this, AdminActivity.class));
                                            progressBar.setVisibility(View.GONE);
//                                        } else if (dataSnapshot.child(input1).child("as").getValue(String.class).equals("user")) {
//                                            preferences.setDataLogin(AdminLoginActivity.this, true);
//                                            preferences.setDataAs(AdminLoginActivity.this, "user");
//                                            String _name = dataSnapshot.child(input1).child("name").getValue(String.class);
//                                            String _password = dataSnapshot.child(input1).child("password").getValue(String.class);
//                                            String _email = dataSnapshot.child(input1).child("email").getValue(String.class);
//                                            String _phone = dataSnapshot.child(input1).child("phoneNo").getValue(String.class);
//                                            String _plate = dataSnapshot.child(input1).child("plateNo").getValue(String.class);
//                                            String _username = dataSnapshot.child(input1).child("username").getValue(String.class);
//                                            String _icNo = dataSnapshot.child(input1).child("icNo").getValue(String.class);
//                                            progressBar.setVisibility(View.GONE);
//
//                                            preferences preferences = new preferences(AdminLoginActivity.this);
//                                            preferences.createLoginSession(_username, _password, _name, _email, _phone, _plate, _icNo);
//
//                                            startActivity(new Intent(getApplicationContext(), UserActivity.class));
//                                        } else if (dataSnapshot.child(input1).child("as").getValue(String.class).equals("police")) {
//                                            preferences.setDataLogin(AdminLoginActivity.this, true);
//                                            preferences.setDataAs(AdminLoginActivity.this, "police");
//                                            startActivity(new Intent(AdminLoginActivity.this, PoliceActivity.class));
//                                            progressBar.setVisibility(View.GONE);
                                        }
                                    } else {
                                        if (dataSnapshot.child(input1).child("as").getValue(String.class).equals("admin")) {
                                            preferences.setDataLogin(AdminLoginActivity.this, false);
                                            startActivity(new Intent(AdminLoginActivity.this, AdminActivity.class));
                                            progressBar.setVisibility(View.GONE);

//                                        } else if (dataSnapshot.child(input1).child("as").getValue(String.class).equals("user")) {
//                                            preferences.setDataLogin(AdminLoginActivity.this, false);
//                                            String _name = dataSnapshot.child(input1).child("name").getValue(String.class);
//                                            String _password = dataSnapshot.child(input1).child("password").getValue(String.class);
//                                            String _email = dataSnapshot.child(input1).child("email").getValue(String.class);
//                                            String _phone = dataSnapshot.child(input1).child("phoneNo").getValue(String.class);
//                                            String _plate = dataSnapshot.child(input1).child("plateNo").getValue(String.class);
//                                            String _username = dataSnapshot.child(input1).child("username").getValue(String.class);
//                                            String _icNo = dataSnapshot.child(input1).child("icNo").getValue(String.class);
//                                            progressBar.setVisibility(View.GONE);
//
//
//                                            preferences preferences = new preferences(AdminLoginActivity.this);
//                                            preferences.createLoginSession(_username, _password, _name, _email, _phone, _plate, _icNo);
//                                            startActivity(new Intent(AdminLoginActivity.this, UserActivity.class));
//
//                                        } else if (dataSnapshot.child(input1).child("as").getValue(String.class).equals("police")) {
//                                            preferences.setDataLogin(AdminLoginActivity.this, false);
//                                            startActivity(new Intent(AdminLoginActivity.this, PoliceActivity.class));
//
//                                            progressBar.setVisibility(View.GONE);
                                        }
                                    }

                                } else {
                                    Toast.makeText(AdminLoginActivity.this, "Password incorrect! Please try again", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            } else {
                                Toast.makeText(AdminLoginActivity.this, "Username does not exist", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }else{
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(AdminLoginActivity.this, "Username/Password cannot be empty", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.Police){
            Toast.makeText(getApplicationContext(),"Police Login Page",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AdminLoginActivity.this,PoliceLoginActivity.class));
        }else if(id == R.id.User){
            Toast.makeText(getApplicationContext(),"User Login Page",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AdminLoginActivity.this,MainActivity.class));

        }else if(id == R.id.Admin){
            Toast.makeText(getApplicationContext(),"Admin Login Page",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AdminLoginActivity.this,AdminLoginActivity.class));

        }
        return true;
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (preferences.getDataLogin(this)) {
            if (preferences.getDataAs(this).equals("admin")) {
                startActivity(new Intent(this, AdminActivity.class));
                progressBar.setVisibility(View.GONE);
                finish();
//            } else if (preferences.getDataAs(this).equals("user")) {
//                startActivity(new Intent(this, UserActivity.class));
//                progressBar.setVisibility(View.GONE);
//                finish();
//            } else {
//                startActivity(new Intent(this, AdminActivity.class));
//                progressBar.setVisibility(View.GONE);
//                finish();
            }
        }
    }
}