package com.example.rafi.du_crs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.asif.du_crs.R;
import com.example.omi.du_crs.AuditorioumDetails;
import com.example.omi.du_crs.SearchActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class admin_main extends AppCompatActivity {
    RecyclerView recyclerView;
    Button changeMode;
    boolean showAudi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        getSupportActionBar().setTitle("Admin Panel");
        showAudi = true;
        changeMode = (Button) findViewById(R.id.bt_toggle);
        changeMode.setText("Show Ground Bookings");


        recyclerView = (RecyclerView) findViewById(R.id.pending_list);
        final ArrayList<AuditorioumDetails> pendingList = new ArrayList<>();
        final ArrayList<Ground_object> pendingGList = new ArrayList<>();
        final PendingBookingsAdapter adapter = new PendingBookingsAdapter(pendingList,this);
        final GroundBookinngAdapter adapter_g = new GroundBookinngAdapter(pendingGList,this);

        recyclerView.setLayoutManager(new LinearLayoutManager(admin_main.this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Auditorioum bookings");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pendingList.clear();
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    AuditorioumDetails temp = data.getValue(AuditorioumDetails.class);
                    if(temp.getStatus() == 0){
                        pendingList.add(temp);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        changeMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!showAudi){
                    showAudi = true;
                    setButton();
                    recyclerView.setLayoutManager(new LinearLayoutManager(admin_main.this,LinearLayoutManager.VERTICAL,false));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.hasFixedSize();
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Auditorioum bookings");
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            pendingList.clear();
                            for(DataSnapshot data: dataSnapshot.getChildren()){
                                AuditorioumDetails temp = data.getValue(AuditorioumDetails.class);
                                if(temp.getStatus() == 0){
                                    pendingList.add(temp);
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else {
                    showAudi = true;
                    setButton();

                    recyclerView.setLayoutManager(new LinearLayoutManager(admin_main.this,LinearLayoutManager.VERTICAL,false));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.hasFixedSize();
                    recyclerView.setAdapter(adapter_g);
                    adapter.notifyDataSetChanged();

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Ground booking");
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            pendingGList.clear();
                            for(DataSnapshot data: dataSnapshot.getChildren()){
                                Ground_object temp = new Ground_object();
                                temp = data.getValue(Ground_object.class);
                                if(temp.isBooked == 0){
                                    pendingGList.add(temp);
                                }
                            }
                            adapter_g.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_plus,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.plus:
                Intent intent = new Intent(getApplicationContext(),admin_add_option.class);
                startActivity(intent);
        }
        return true;
    }

    void setButton(){
        if(showAudi){
            changeMode.setText("Show Auditorium Bookings");
        }
        else {
            changeMode.setText("Show Ground Bookings");
        }
    }
}
