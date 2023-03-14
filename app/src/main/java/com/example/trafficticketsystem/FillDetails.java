package com.example.trafficticketsystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class FillDetails extends AppCompatActivity {

    TextInputLayout pName, p_ic, plate_no, p_email, address, password;
    private Button btnProfile;
    String user_username, user_password, user_name, user_ic, user_plateno, user_phoneno, user_email, user_as;
    DatabaseReference reference;
    private ProgressBar progressBar;
    static final int image_gallery = 100;
    private Bitmap imageBitmap;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private Uri pickedImage;
    private ImageView imgProfile;
    private String downloadImageUrl;

    private StorageReference ProductImagesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_details);
        reference = FirebaseDatabase.getInstance().getReference("users");
        password = findViewById(R.id.Password1);
        pName = findViewById(R.id.name1);
        p_ic = findViewById(R.id.IC1);
        plate_no = findViewById(R.id.plateNo1);
        p_email = findViewById(R.id.email1);
        address = findViewById(R.id.Address1);
        progressBar = findViewById(R.id.Progress);
        btnProfile = findViewById(R.id.buttonProfile);
        imgProfile = findViewById(R.id.profilePic);

        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("User Images");


        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImageGal();
            }
        });






        showAllUserData();
    }

    private void pickImageGal() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,image_gallery);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == image_gallery && resultCode == RESULT_OK) {
            pickedImage = data.getData();
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), pickedImage);

            } catch (IOException e) {
                e.printStackTrace();
            }
            imgProfile.getLayoutParams().height = 500;
            imgProfile.setImageURI(pickedImage);
            imgProfile.setVisibility(View.VISIBLE);
            uploadPicture();
        }
    }

    private void uploadPicture() {


        final String randomKey = UUID.randomUUID().toString();
        final StorageReference filePath = ProductImagesRef.child(pickedImage.getLastPathSegment() + randomKey + ".jpg");
        final UploadTask uploadTask = filePath.putFile(pickedImage);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(FillDetails.this, "Error: " + message, Toast.LENGTH_SHORT).show();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            downloadImageUrl = task.getResult().toString();


                        }
                    }
                });
            }
        });


    }


    private void showAllUserData() {
        Intent intent = getIntent();
        user_plateno = intent.getStringExtra("plateNo");
        user_password = intent.getStringExtra("password");
//        user_name = intent.getStringExtra("name");
//        user_ic = intent.getStringExtra("ic");
//        user_plateno = intent.getStringExtra("plateno");
//        user_phoneno = intent.getStringExtra("phoneno");
//        user_email = intent.getStringExtra("email");
        user_as = intent.getStringExtra("as");


//        pName.getEditText().setText(user_name);
//        p_ic.getEditText().setText(user_ic);
//        plate_no.getEditText().setText(user_plateno);
//        p_email.getEditText().setText(user_email);


    }



    public void btnSave(View view) {
        progressBar.setVisibility(View.VISIBLE);
        if (!NameValidation() | !EmailValidation() | !ICValidation()| !AddressValidation()) {
            progressBar.setVisibility(View.GONE);
            return;

        }
        String sPlate = user_plateno;
        String sPassword = user_password;
        String sName = pName.getEditText().getText().toString();
        String sEmail = p_email.getEditText().getText().toString();
        String sIC = p_ic.getEditText().getText().toString();
        String sAddress = address.getEditText().getText().toString();
        String imageProfile = downloadImageUrl;
        String AS = user_as;

        Intent intent = new Intent(getApplicationContext(),UserVerifyPhoneActivity.class);
        intent.putExtra("plateNo",sPlate);
        intent.putExtra("password",sPassword);
        intent.putExtra("name",sName);
        intent.putExtra("ic",sIC);
        intent.putExtra("email",sEmail);
        intent.putExtra("address",sAddress);
        intent.putExtra("imgURL",imageProfile);
        intent.putExtra("AS",AS);


        startActivity(intent);



//        if (!sName.isEmpty() && !sEmail.isEmpty() && !sIC.isEmpty() && !sPhone.isEmpty() ) {
//
//            reference.child(sUsername).child("plateNo").setValue(user_username);
//
//            reference.child(sUsername).child("password").setValue(user_password);
//
//            reference.child(sUsername).child("name").setValue(pName.getEditText().getText().toString());
//
//            reference.child(sUsername).child("email").setValue(p_email.getEditText().getText().toString());
//
//            reference.child(sUsername).child("icNo").setValue(p_ic.getEditText().getText().toString());
//
//            reference.child(sUsername).child("phoneNo").setValue(phone_no.getEditText().getText().toString());
//
//            reference.child(sUsername).child("imageProfile").setValue(imageProfile);
//
//            reference.child(sUsername).child("as").setValue(user_as);
//
//
//
//            Toast.makeText(this, "Register successful", Toast.LENGTH_LONG).show();
//
//            Intent intent=new Intent(FillDetails.this,MainActivity.class);
//            startActivity(intent);
//
//        } else {
//            Toast.makeText(this,"Field cannot be empty",Toast.LENGTH_LONG).show();
//            progressBar.setVisibility(View.GONE);
//
//
//        }
    }

    private boolean NameValidation() {
        String val = pName.getEditText().getText().toString();
        if (!val.isEmpty()) {

                pName.setError(null);
                pName.setErrorEnabled(false);


                return true;

        } else {
            pName.setError("Field cannot be empty");
            return false;
        }

    }



    private boolean EmailValidation() {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String val = p_email.getEditText().getText().toString();
        if (val.isEmpty()) {

            p_email.setError("Field cannot be empty");
            return false;

        }else if(!val.matches(emailPattern)){
            p_email.setError("Please enter a valid email.");
            return false;
        } else {

            p_email.setError(null);
            p_email.setErrorEnabled(false);


            return true;
        }

    }
    private boolean ICValidation() {
        String icFormat="(?=\\S+$)";
        int icLength = 12;
        String val = p_ic.getEditText().getText().toString();
        if (val.isEmpty()) {

            p_ic.setError("Field cannot be empty");
            return false;

        } else if(!(val.length() == icLength)){
            p_ic.setError("IC number should have 12 words");
            return false;
        }else {
            p_ic.setError(null);
            p_ic.setErrorEnabled(false);
            return true;

        }

    }
    private boolean AddressValidation() {
        String val = address.getEditText().getText().toString();
        if (!val.isEmpty()) {

            address.setError(null);
            address.setErrorEnabled(false);
            return true;

        } else {
            address.setError("Field cannot be empty");
            return false;
        }

    }
//        private boolean PlateUpdate() {
//        String val = plate_no.getEditText().getText().toString();
//        if (!val.isEmpty()) {
//
//            plate_no.setError(null);
//            plate_no.setErrorEnabled(false);
//
//            reference.child(user_username).child("plateNo").setValue(plate_no.getEditText().getText().toString());
//            user_plateno = plate_no.getEditText().getText().toString();
//            return true;
//
//        } else {
//            plate_no.setError("Field cannot be empty");
//            return false;
//        }
//
//    }

}