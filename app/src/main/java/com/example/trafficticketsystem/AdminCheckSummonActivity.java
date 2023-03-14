package com.example.trafficticketsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class AdminCheckSummonActivity extends AppCompatActivity {

    RecyclerView AdminRecycler;
    AdminCheckSummonAdapter adminCheckSummonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_check_summon);
        Toolbar toolbar3 = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar3);
        setTitle("Search here..");

        AdminRecycler=(RecyclerView)findViewById(R.id.AdminRecycler);
        AdminRecycler.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<UploadSummon_Admin> options =
                new FirebaseRecyclerOptions.Builder<UploadSummon_Admin>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("admin").child("Summon"), UploadSummon_Admin.class)
                        .build();

        adminCheckSummonAdapter = new AdminCheckSummonAdapter(options);
        AdminRecycler.setAdapter(adminCheckSummonAdapter);

    }
    @Override
    protected void onStart() {
        super.onStart();
        adminCheckSummonAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
       adminCheckSummonAdapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.searchmenu,menu);

        MenuItem item = menu.findItem(R.id.search);

        SearchView searchView=(SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String s) {

                processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processsearch(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.logout){
            Toast.makeText(getApplicationContext(),"Logout Successfully",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AdminCheckSummonActivity.this,MainActivity.class));
            preferences.clearData(this);
            finish();

        }else if(id == R.id.Summons){
            Toast.makeText(getApplicationContext(),"View Summons",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AdminCheckSummonActivity.this,AdminCheckSummonActivity.class));

        }else if(id == R.id.AddPolice){
            Toast.makeText(getApplicationContext(),"Add Police Acount",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AdminCheckSummonActivity.this,AdminActivity.class));
        }
        return true;
    }

    private void processsearch(String s)
    {
        FirebaseRecyclerOptions<UploadSummon_Admin> options =
                new FirebaseRecyclerOptions.Builder<UploadSummon_Admin>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("admin").child("Summon").orderByChild("summonNo").startAt(s).endAt(s+"\uf8ff"), UploadSummon_Admin.class)
                        .build();

        adminCheckSummonAdapter=new AdminCheckSummonAdapter(options);
       adminCheckSummonAdapter.startListening();
        AdminRecycler.setAdapter(adminCheckSummonAdapter);

    }
}