package com.example.trafficticketsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.appcompat.widget.Toolbar;
//import com.google.firebase.messaging.FirebaseMessaging;
//import com.google.firebase.messaging.FirebaseMessaging;



public class MainActivity extends AppCompatActivity{


    TextInputLayout lNumber, password;
    private Button login;
    CheckBox active;
    private ProgressBar progressBar;
    private Toolbar toolbar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        lNumber = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        active = findViewById(R.id.active);
        progressBar = findViewById(R.id.Progress);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String input1 = lNumber.getEditText().getText().toString().replaceAll("\\s+", "");
                        String input2 = password.getEditText().getText().toString().replaceAll("\\s+", "");

                        if(!input1.isEmpty()&&!input2.isEmpty()) {

                            if (dataSnapshot.child(input1).exists()) {
                                if (dataSnapshot.child(input1).child("password").getValue(String.class).equals(input2)) {
                                    if (active.isChecked()) {
                                        if (dataSnapshot.child(input1).child("as").getValue(String.class).equals("user")) {
                                            preferences.setDataLogin(MainActivity.this, true);
                                            preferences.setDataAs(MainActivity.this, "user");
                                            String _name = dataSnapshot.child(input1).child("name").getValue(String.class);
                                            String _password = dataSnapshot.child(input1).child("password").getValue(String.class);
                                            String _email = dataSnapshot.child(input1).child("email").getValue(String.class);
                                            String _phone = dataSnapshot.child(input1).child("phoneNo").getValue(String.class);
                                            String _plate = dataSnapshot.child(input1).child("plateNo").getValue(String.class);
                                            String _username = dataSnapshot.child(input1).child("username").getValue(String.class);
                                            String _icNo = dataSnapshot.child(input1).child("icNo").getValue(String.class);
                                            String _profile = dataSnapshot.child(input1).child("imageProfile").getValue(String.class);
                                            progressBar.setVisibility(View.GONE);

                                            preferences preferences = new preferences(MainActivity.this);
                                            preferences.createLoginSession(_username, _password, _name, _email, _phone, _plate, _icNo, _profile);

                                            startActivity(new Intent(getApplicationContext(), UserActivity.class));
                                        }

                                    } else {
                                        if (dataSnapshot.child(input1).child("as").getValue(String.class).equals("user")) {
                                            preferences.setDataLogin(MainActivity.this, false);
                                            String _name = dataSnapshot.child(input1).child("name").getValue(String.class);
                                            String _password = dataSnapshot.child(input1).child("password").getValue(String.class);
                                            String _email = dataSnapshot.child(input1).child("email").getValue(String.class);
                                            String _phone = dataSnapshot.child(input1).child("phoneNo").getValue(String.class);
                                            String _plate = dataSnapshot.child(input1).child("plateNo").getValue(String.class);
                                            String _username = dataSnapshot.child(input1).child("username").getValue(String.class);
                                            String _icNo = dataSnapshot.child(input1).child("icNo").getValue(String.class);
                                            String _profile = dataSnapshot.child(input1).child("imageProfile").getValue(String.class);
                                            progressBar.setVisibility(View.GONE);


                                            preferences preferences = new preferences(MainActivity.this);
                                            preferences.createLoginSession(_username, _password, _name, _email, _phone, _plate, _icNo, _profile);
                                            startActivity(new Intent(MainActivity.this, UserActivity.class));

                                        }
                                    }

                                } else {
                                    Toast.makeText(MainActivity.this, "Password incorrect! Please try again", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Username does not exist", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }else{
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(MainActivity.this, "Username/Password cannot be empty", Toast.LENGTH_SHORT).show();
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
            startActivity(new Intent(MainActivity.this,PoliceLoginActivity.class));
        }else if(id == R.id.Admin){
            Toast.makeText(getApplicationContext(),"Admin Login Page",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this,AdminLoginActivity.class));

        }else if(id == R.id.User){
            Toast.makeText(getApplicationContext(),"User Login Page",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this,MainActivity.class));

        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (preferences.getDataLogin(this)) {
            if (preferences.getDataAs(this).equals("user")) {
                startActivity(new Intent(this, UserActivity.class));
                progressBar.setVisibility(View.GONE);
                finish();
            }
            else if (preferences.getDataAs(this).equals("police")) {
                startActivity(new Intent(this, RecordActivity.class));
                progressBar.setVisibility(View.GONE);
                finish();
            }else if (preferences.getDataAs(this).equals("admin")) {
                startActivity(new Intent(this, AdminActivity.class));
                progressBar.setVisibility(View.GONE);
                finish();
            }
        }
    }
    public void cRegister(View view) {

        Intent intent=new Intent(MainActivity.this,CitizenRegister.class);
        startActivity(intent);



    }
    private void retrieveAndStoreToken(){
//        FirebaseMessaging.getInstance().getToken()
//                .addOnCompleteListener(new OnCompleteListener<String>() {
//                    @Override
//                    public void onComplete(@NonNull Task<String> task) {
//                        if (task.isSuccessful()) {
//                            String token = task.getResult();
//                            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//                            FirebaseDatabase.getInstance().getReference("tokens").child(userId).setValue(token);
//
//
//                        }
//
//
//                    }
//                });

    }
}