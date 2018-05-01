package com.example.omi.du_crs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.example.asif.du_crs.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.example.rafi.du_crs.*;
public class AuditorioumCalendar extends AppCompatActivity {

    static ArrayList<String> choiclist=new ArrayList<>();
    static ArrayList<String> audilist=new ArrayList<>();
    Spinner choicspinner,audispinner;
    String choice,venue;
    ArrayAdapter<String> adapter;
    static ArrayList<mypair> range=new ArrayList<>();
    static mypair[] mpp=new mypair[1];
    com.applandeo.materialcalendarview.CalendarView calendarView;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auditorioum_calendar);
        mpp[0]=new mypair(0,0);
        audilist.add("Not Selected");
        choiclist.add("Free Slots");
        choiclist.add("Schedules");
        audispinner=findViewById(R.id.t1);
        choicspinner=findViewById(R.id.t2);
        calendarView=findViewById(R.id.t3);
        choice="Free Slots";
        venue="Not Selected";
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,choiclist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choicspinner.setAdapter(adapter);
        choicspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                choice=choiclist.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        databaseReference=FirebaseDatabase.getInstance().getReference().child("data").child("Auditorium");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                audilist.clear();
                range.clear();
                audilist.add("Not Selected");
                for (DataSnapshot users : dataSnapshot.getChildren()) {
                    detail temp = new detail();
                    temp = users.getValue(detail.class);
                    audilist.add(temp.getName());
                    int uu=FunctionList.getminute(temp.getStartTime());
                    int vv=FunctionList.getminute(temp.getEndTime());
                    range.add(new mypair(uu,vv));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,audilist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        audispinner.setAdapter(adapter);
        audispinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                venue=audilist.get(i);
                if(i!=0) mpp[0]=range.get(i-1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar calendar=eventDay.getCalendar();
                SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
                String s=format1.format(calendar.getTime()).toString();
                auditorioumfreeslots.rdate=s;
                if(choice.equals("Free Slots") && !venue.equals("Not Selected"))
                {
                    auditorioumfreeslots.venue=venue;
                    auditorioumfreeslots.needl=mpp[0].v1;
                    auditorioumfreeslots.needh=mpp[0].v2;
                    Intent intent = new Intent(AuditorioumCalendar.this,auditorioumfreeslots.class);
                    startActivity(intent);
                }
                else if(choice.equals("Schedules") && !venue.equals("Not Selected"))
                {
                    audischedule.rrdate=s;
                    audischedule.vvenue=venue;
                    Intent intent = new Intent(AuditorioumCalendar.this,audischedule.class);
                    startActivity(intent);
                }
            }
        });
    }
}
