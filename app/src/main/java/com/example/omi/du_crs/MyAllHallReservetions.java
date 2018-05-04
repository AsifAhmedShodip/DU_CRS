package com.example.omi.du_crs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.asif.du_crs.ClassroomAdapter;
import com.example.asif.du_crs.Classroom_Object;
import com.example.asif.du_crs.R;
import com.example.asif.du_crs.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyAllHallReservetions extends AppCompatActivity {

    ArrayList<String> classroomDates = new ArrayList<>();
    List<Classroom_Object> classroom_objects = new ArrayList<>();
    Spinner search_category;
    List<String> choices=new ArrayList<>();
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Exam_Hall").child(FunctionList.exam_hall_search.hall_name);
    List<ExamHallSlot> list=new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    void gotoclass() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Classroom").child(User.getCurrent().getDeptName());
        //check

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                classroomDates.clear();
                for (DataSnapshot users : dataSnapshot.getChildren()) {
                    classroomDates.add(users.getKey().toString());
                }

                //.d("Class 2 ", String.valueOf(classroomDates.size()));
                fetchClassRoomData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void fetchClassRoomData() {

        //Collections.sort(list,new ExamResComp());
        adapter = new ClassroomAdapter(MyAllHallReservetions.this, classroom_objects);
        recyclerView.setAdapter(adapter);

        classroom_objects.clear();
        for (int i = 0; i < classroomDates.size(); i++) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Classroom")
                    .child(User.getCurrent().getDeptName()).child(classroomDates.get(i));

            final int finalI = i;
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //classroom_objects.clear();
                    for (DataSnapshot users : dataSnapshot.getChildren()) {
                        Classroom_Object temp = new Classroom_Object();
                        temp = users.getValue(Classroom_Object.class);
                        //Log.d("Class", temp.getDetail());
                        if (temp.getBookedBy().equals(User.getCurrent().getEmail())) {
                            classroom_objects.add(temp);
                            Log.d("Class 6 ", temp.getBookedBy());
                        }
                    }
                    Log.d("Class 10", String.valueOf(classroom_objects.size()));

                    adapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

        }


    }

    void gotoground() {

    }

    void gotoauditorioum() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_all_hall_reservetions);
        recyclerView = findViewById(R.id.recycler);

        choices.add("Exam Hall");
        node.mp.clear();
        node.mp.add("January");
        node.mp.add("February");
        node.mp.add("March");
        node.mp.add("April");
        node.mp.add("May");
        node.mp.add("June");
        node.mp.add("July");
        node.mp.add("August");
        node.mp.add("September");
        node.mp.add("October");
        node.mp.add("November");
        node.mp.add("December");

        search_category=findViewById(R.id.search_category);

        choices.add("Classroom/lab");
        choices.add("Ground");
        choices.add("Auditorioum");

        ArrayAdapter<String> adapters=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,choices);
        adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        search_category.setAdapter(adapters);
        search_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=0) {
                    if(i==1) gotoclass();
                    if(i==2) gotoground();
                    if(i==3) gotoauditorioum();
                } else
                    gotoExam();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Exam_Hall").child(FunctionList.exam_hall_search.hall_name);
        //check

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot users : dataSnapshot.getChildren()) {
                    ExamHallSlot temp = new ExamHallSlot();
                    temp = users.getValue(ExamHallSlot.class);

                    if (temp.reserverId.equals(User.getCurrent().getDeptName()))
                        list.add(temp);

                }

                Collections.sort(list,new ExamResComp());
                adapter=new MyAllExamAdapter(MyAllHallReservetions.this,list);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        Collections.sort(list,new ExamResComp());
        adapter=new MyAllExamAdapter(MyAllHallReservetions.this,list);
        recyclerView.setAdapter(adapter);
    }

    private void gotoExam() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Exam_Hall").child(FunctionList.exam_hall_search.hall_name);
        //check

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot users : dataSnapshot.getChildren()) {
                    ExamHallSlot temp = new ExamHallSlot();
                    temp = users.getValue(ExamHallSlot.class);

                    if (temp.reserverId.equals(User.getCurrent().getDeptName()))
                        list.add(temp);

                }

                Collections.sort(list, new ExamResComp());
                adapter = new MyAllExamAdapter(MyAllHallReservetions.this, list);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        Collections.sort(list, new ExamResComp());
        adapter = new MyAllExamAdapter(MyAllHallReservetions.this, list);
        recyclerView.setAdapter(adapter);
    }
}