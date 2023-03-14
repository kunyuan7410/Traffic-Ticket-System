package com.example.trafficticketsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trafficticketsystem.databinding.ActivityMainBinding;
import com.example.trafficticketsystem.databinding.ActivityUserVerifyPhoneBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class UserVerifyPhoneActivity extends AppCompatActivity {

    private ActivityUserVerifyPhoneBinding binding;
    private PhoneAuthProvider.ForceResendingToken forceResendingToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private static final String TAG = "MAIN_TAG";
    private FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    private ProgressDialog PD;



//    Button btnVerify;
//    TextInputLayout OTP_INPUT;
//    ProgressBar progressBar;
//    TextView btnResend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user_verify_phone);
        binding = ActivityUserVerifyPhoneBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        reference = FirebaseDatabase.getInstance().getReference("users");
        binding.phoneLl.setVisibility(View.VISIBLE);
        binding.codeLl.setVisibility(View.GONE);
//        btnResend = findViewById(R.id.resend_txt);
//        btnVerify = findViewById(R.id.Verify_Button);
//        OTP_INPUT = findViewById(R.id.OTP_CODE);
//        progressBar = findViewById(R.id.Progress);
//
//        progressBar.setVisibility(View.GONE);

        firebaseAuth = FirebaseAuth.getInstance();

        PD = new ProgressDialog(this);
        PD.setTitle("Please wait...");
        PD.setCanceledOnTouchOutside(false);




        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

                PD.dismiss();
                Toast.makeText(UserVerifyPhoneActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                super.onCodeSent(verificationId, forceResendingToken);

                Log.d(TAG,"onCodeSent"+verificationId);

                mVerificationId = verificationId;
                forceResendingToken = token;
                PD.dismiss();

                binding.phoneLl.setVisibility(View.GONE);
                binding.codeLl.setVisibility(View.VISIBLE);
                Toast.makeText(UserVerifyPhoneActivity.this,"Verification code sent...",Toast.LENGTH_SHORT).show();


                //binding/codeSentDescription.setText("Please type the verification code we sent \nto")+binding.phoneEt.getText()/toString().trim());


            }
        };

        binding.GetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = binding.phone.getEditText().getText().toString().trim();
                if(TextUtils.isEmpty(phone)){
                    Toast.makeText(UserVerifyPhoneActivity.this,"Please enter phone number...", Toast.LENGTH_SHORT).show();

                }else{
                    startPhoneNumberVerification(phone);
                }
            }
        });

        binding.resendtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = binding.phone.getEditText().getText().toString().trim();
                if(TextUtils.isEmpty(phone)){
                    Toast.makeText(UserVerifyPhoneActivity.this,"Please enter phone number...", Toast.LENGTH_SHORT).show();

                }else{
                    resendVerificationCode(phone, forceResendingToken);
                }
            }
        });

        binding.VerifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = binding.codeEt.getEditText().getText().toString().trim();
                if(TextUtils.isEmpty(code)){
                    Toast.makeText(UserVerifyPhoneActivity.this,"Please enter verification code...",Toast.LENGTH_SHORT).show();
                }else{
                    verifyPhoneNumberWithCode(mVerificationId, code);
                }
            }
        });







    }




    private void startPhoneNumberVerification(String phone) {
        PD.setMessage("Verifying Phone Number");
        PD.show();

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber("+6"+phone)
                .setTimeout(60L,TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallbacks)
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void resendVerificationCode(String phone, PhoneAuthProvider.ForceResendingToken token) {
        PD.setMessage("Resending Code");
        PD.show();

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber("+6"+phone)
                .setTimeout(60L,TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallbacks)
                .setForceResendingToken(token)
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        PD.setMessage("Verifying Code");
        PD.show();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        PD.setMessage("Register Successfully");

        firebaseAuth.signInWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                PD.dismiss();
                String plateNo = getIntent().getStringExtra("plateNo");
                String password = getIntent().getStringExtra("password");
                String name = getIntent().getStringExtra("name");
                String ic = getIntent().getStringExtra("ic");
                String email = getIntent().getStringExtra("email");
                String address = getIntent().getStringExtra("address");
                String imageProfile = getIntent().getStringExtra("imgURL");
                String as = getIntent().getStringExtra("AS");

                reference.child(plateNo).child("plateNo").setValue(plateNo);

                reference.child(plateNo).child("password").setValue(password);

                reference.child(plateNo).child("name").setValue(name);

                reference.child(plateNo).child("email").setValue(email);

                reference.child(plateNo).child("icNo").setValue(ic);

                reference.child(plateNo).child("address").setValue(address);

                reference.child(plateNo).child("imageProfile").setValue(imageProfile);

                reference.child(plateNo).child("as").setValue(as);

                reference.child(plateNo).child("phoneNo").setValue(binding.phone.getEditText().getText().toString());

                Toast.makeText(UserVerifyPhoneActivity.this,"Register Successfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(UserVerifyPhoneActivity.this,MainActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                PD.dismiss();
                Toast.makeText(UserVerifyPhoneActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }


}