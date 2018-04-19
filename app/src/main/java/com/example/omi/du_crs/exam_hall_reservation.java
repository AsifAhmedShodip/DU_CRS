package com.example.omi.du_crs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.asif.du_crs.R;
import com.example.asif.du_crs.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class exam_hall_reservation extends AppCompatActivity {

    List<ExamHallSlot> all_bookings=new ArrayList<>();
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Exam_Hall").child(FunctionList.exam_hall_search.hall_name);
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_hall_reservation);
        getSupportActionBar().setTitle("Reservations");
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Exam_Hall").child(FunctionList.exam_hall_search.hall_name);
        //check

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                all_bookings.clear();
                for (DataSnapshot users : dataSnapshot.getChildren()) {
                    ExamHallSlot temp = new ExamHallSlot();
                    temp = users.getValue(ExamHallSlot.class);
                    if(temp.reserverId.equals(User.getCurrent().getDeptName()) && temp.rdate.equals(ExamHallCalendar.mdate))all_bookings.add(temp);
                }
                adapter=new exam_hall_resrvation_adapter(all_bookings);
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

        adapter=new exam_hall_resrvation_adapter(all_bookings);
        recyclerView.setAdapter(adapter);

    }
}
