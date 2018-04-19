package com.example.omi.du_crs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.asif.du_crs.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class exam_hall_available_list extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<free_slots> list=new ArrayList<>();
    List<ExamHallSlot> all_bookings=new ArrayList<>();
    ArrayList<ExamHallSlot> temp_list=new ArrayList<>();
    List<free_slots> temp_list1=new ArrayList<>();
    Button book_button;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Exam_Hall").child(FunctionList.exam_hall_search.hall_name);
    DatabaseReference ds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_hall_available_list);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Exam_Hall").child(FunctionList.exam_hall_search.hall_name);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                synchronized (all_bookings) {
                    all_bookings.clear();
                    for (DataSnapshot users : dataSnapshot.getChildren()) {
                        ExamHallSlot temp = new ExamHallSlot();
                        temp = users.getValue(ExamHallSlot.class);
                        all_bookings.add(temp);
                    }
                }
                node ctd=node.stringtoclass(FunctionList.exam_hall_search.start_date);
                node etd=node.stringtoclass(FunctionList.exam_hall_search.end_date);
                while (true)
                {
                    if(ctd.isGreater(etd)) break;
                    temp_list.clear();
                    temp_list1.clear();
                    String stdctd=node.classtostring(ctd);
                    for(int i=0;i<all_bookings.size();i++)
                    {
                        if(all_bookings.get(i).rdate.equals(stdctd))
                        {
                            temp_list.add(all_bookings.get(i));
                        }
                    }
                    temp_list1=FunctionList.EmptySlots(FunctionList.exam_hall_search.count,(8*60)+30,17*60,200,temp_list,stdctd);
                    for(int i=0;i<temp_list1.size();i++)
                    {
                        list.add(temp_list1.get(i));
                    }
                    ctd=FunctionList.nextday(ctd);
                }
                adapter=new exam_hall_adapter(list,getApplicationContext());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        for(int i=0;i<0;i++)
        {
            ExamHallSlot temp=new ExamHallSlot();
            ds=databaseReference.push();
            temp.setReservetionId(""+ds.getKey());
            temp.setStartTime(0);
            temp.setEndTime(0);
            ds.setValue(temp);
        }
        book_button=findViewById(R.id.book_button);
        book_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(exam_hall_available_list.this,book_exam_halls.class);
                startActivity(intent);
            }
        });
        //list.add(new free_slots("Omi","Omi","Omi"));

        //list.add(new free_slots("Oni","Oni","Oni"));
        recyclerView=(RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);

        adapter=new exam_hall_adapter(list,this);
        recyclerView.setAdapter(adapter);
    }
}
