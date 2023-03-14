package com.example.trafficticketsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class CitizenRegister extends AppCompatActivity {

    TextInputLayout cLpNo, cPassword, cConfirmPassword;
    Button btnSignUp,btnGoback;
    TextView citizen, cName, cEmail, cPhone, cPlate, cIC;
    private ProgressBar progressBar;


    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen_register);
        citizen = findViewById(R.id.citizen);
        cName = findViewById(R.id.empty_name);
        cEmail = findViewById(R.id.empty_email);
        cPhone = findViewById(R.id.empty_phone);
        cPlate = findViewById(R.id.empty_plate);
        cIC = findViewById(R.id.empty_IC);
        cLpNo = findViewById(R.id.username1);
        cPassword = findViewById(R.id.password1);
        cConfirmPassword = findViewById(R.id.cpassword1);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnGoback = findViewById(R.id.btnGoBack);
        progressBar = findViewById(R.id.Progress);

        btnSignUp.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                progressBar.setVisibility(View.VISIBLE);
                if (!validateName() | !validatePassword() | !validatePasswordMatch()) {
                    progressBar.setVisibility(View.GONE);
                    return;

                }



                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");
                String un = cLpNo.getEditText().getText().toString();

                reference.orderByChild("plateNo").equalTo(un).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            cLpNo.setError("License Plate Number already exists");
                            progressBar.setVisibility(View.GONE);
                        }else{
                            cLpNo.setError(null);
                            cLpNo.setErrorEnabled(false);
//                            String username = cLpNo.getEditText().getText().toString();
//                            String password = cPassword.getEditText().getText().toString();
//                            String as = citizen.getText().toString();
//                            String name = cName.getText().toString();
//                            String email = cEmail.getText().toString();
//                            String phoneNo = cPhone.getText().toString();
//                            String plateNo = cPlate.getText().toString();
//                            String icNo = cIC.getText().toString();
//                            progressBar.setVisibility(View.GONE);
//                            User user = new User(username, password, name, email, phoneNo, plateNo, icNo, as);
//                            reference.child(plateNo).setValue(user);
                            Toast.makeText(CitizenRegister.this, "Please fill in your details", Toast.LENGTH_SHORT).show();
                            isUser();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




            }
        });


    }
    private void isUser() {
        String userEnteredUsername = cLpNo.getEditText().getText().toString().trim();
        String userEnteredPassword = cPassword.getEditText().getText().toString().trim();


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUser = reference.orderByChild("plateNo").equalTo(userEnteredUsername);

            String passwordDB = cPassword.getEditText().getText().toString().trim();
            String plateNoDB = cLpNo.getEditText().getText().toString().trim();
//            String nameDB = null;
//            String ICDB = null;
//            String platenoDB = null;
//            String phonenoDB = null;
//            String emailDB = null;
//            String hAddressDB = null;
            String as = citizen.getText().toString();
            Intent intent = new Intent(getApplicationContext(), FillDetails.class);
            intent.putExtra("as",as);
//            intent.putExtra("name", nameDB);
//            intent.putExtra("ic", ICDB);
//            intent.putExtra("plateno", platenoDB);
//            intent.putExtra("phoneno", phonenoDB);
//            intent.putExtra("email", emailDB);
//            intent.putExtra("address", hAddressDB);
            intent.putExtra("plateNo", plateNoDB);
            intent.putExtra("password", passwordDB);
            progressBar.setVisibility(View.GONE);

            startActivity(intent);



    }

    private Boolean validateName() {
        String noWhiteSpace="(?=\\S+$)";
        String val = cLpNo.getEditText().getText().toString();

        if (val.isEmpty()) {
            cLpNo.setError("Field cannot be empty");
            return false;
        }else if(val.length()>=15){
            cLpNo.setError("Username too long");
            return false;
        }else if(val.matches(noWhiteSpace)){
            cLpNo.setError("White Spaces are not allowed");
            return false;
        } else {
            cLpNo.setError(null);
            cLpNo.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validatePassword() {
        String val = cPassword.getEditText().getText().toString();
        String passwordVal = "^(?=.*[0-9])(?=\\S+$).{4,}$";
//        (?=.*[A-Z])(?=.*[@#$%^&+=!])

        if (val.isEmpty()) {
           cPassword.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
           cPassword.setError("Password is too weak");
            return false;
        } else {
            cPassword.setError(null);
            cPassword.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePasswordMatch() {
        String val = cPassword.getEditText().getText().toString();
        String val2 = cConfirmPassword.getEditText().getText().toString();


        if (val2.isEmpty()) {
            cConfirmPassword.setError("Field cannot be empty");
            return false;
        }  else if(!val.equals(val2)){
            cConfirmPassword.setError("The password and confirmation password do not match");
            return false;
        }else {
            cConfirmPassword.setError(null);
            cConfirmPassword.setErrorEnabled(false);
            return true;
        }
    }

    public void btnBack(View view) {
        Toast.makeText(CitizenRegister.this,"Back to Login",Toast.LENGTH_LONG).show();
        Intent intent=new Intent(CitizenRegister.this,MainActivity.class);
        startActivity(intent);


    }



//    public boolean onDataChange(DataSnapshot dataSnapshot) {
//        if (dataSnapshot.exists()) {
//            cUsername.setError("Username already exists");
//            return false;
//        } else {
//            cUsername.setError(null);
//            cUsername.setErrorEnabled(false);
//           return true;
//        }
//
//
//    }


}