package com.example.omi.du_crs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.asif.du_crs.R;
import com.example.rafi.du_crs.detail;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class auditorioumfreeslots extends AppCompatActivity {
    static String venue,rdate;
    static int needl,needh;
    ArrayList<mypair> list=new ArrayList<>();
    ArrayList<AuditorioumDetails> bookings=new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auditorioumfreeslots);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Auditorioum bookings");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bookings.clear();
                for (DataSnapshot users : dataSnapshot.getChildren()) {

                    AuditorioumDetails temp = new AuditorioumDetails();
                    temp = users.getValue(AuditorioumDetails.class);
                    if(temp.venue.equals(venue) && temp.rdate.equals(rdate) && temp.getStatus()<2)
                        bookings.add(temp);

                }
                list.clear();
                list=AuditorioumDetails.audfreeslots(bookings,needl,needh);
                adapter=new audi_free_adapter(list,auditorioumfreeslots.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        recyclerView=(RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);

        adapter=new audi_free_adapter(list,auditorioumfreeslots.this);
        recyclerView.setAdapter(adapter);
    }

}
