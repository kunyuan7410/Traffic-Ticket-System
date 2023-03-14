package com.example.trafficticketsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class AddPoliceAccount extends AppCompatActivity {
    private TextInputLayout P_Name, P_username, P_password, P_ic, P_phoneno;
    private TextView as;
    private Button btnAdd,btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_police_account);
        P_Name = findViewById(R.id.name1);
        P_username = findViewById(R.id.username1);
        P_password = findViewById(R.id.password1);
        P_ic = findViewById(R.id.IC1);
        P_phoneno = findViewById(R.id.phoneNo1);
        as = findViewById(R.id.as);

        btnAdd = findViewById(R.id.btnAdd);
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddPoliceAccount.this, AdminActivity.class));
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               AddPolice();
            }
        });
    }

    private void AddPolice() {
        Map<String,Object> map = new HashMap<>();
        String username = P_username.getEditText().getText().toString();
        map.put("name",P_Name.getEditText().getText().toString());
        map.put("username",P_username.getEditText().getText().toString());
        map.put("password",P_password.getEditText().getText().toString());
        map.put("ic",P_ic.getEditText().getText().toString());
        map.put("phoneNo",P_phoneno.getEditText().getText().toString());
        map.put("as",as.getText().toString());
        FirebaseDatabase.getInstance().getReference().child("police").child(username).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                P_Name.getEditText().setText("");
                P_username.getEditText().setText("");
                P_password.getEditText().setText("");
                P_ic.getEditText().setText("");
                P_phoneno.getEditText().setText("");

                Toast.makeText(getApplicationContext(),"Inserted Successfully",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Could not insert",Toast.LENGTH_LONG).show();
            }
        });
    }
}