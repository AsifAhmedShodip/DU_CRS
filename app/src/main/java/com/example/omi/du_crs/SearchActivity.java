package com.example.omi.du_crs;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.asif.du_crs.R;

import java.util.ArrayList;
import java.util.Calendar;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class SearchActivity extends AppCompatActivity {
    Spinner search_category,dept,auditorioum,ground;
    String final_choice,final_dept,final_audi,final_ground;
    EditText stb,etb,sdb,edb,room_no;
    ArrayList<String> choice=new ArrayList<>();
    ArrayList<String> audis=new ArrayList<>();
    ArrayList<String> depts=new ArrayList<>();
    ArrayList<String> grounds=new ArrayList<>();
    void init_all()
    {
        stb.setVisibility(View.GONE);
        etb.setVisibility(View.GONE);
        sdb.setVisibility(View.GONE);
        edb.setVisibility(View.GONE);
        dept.setVisibility(View.GONE);
        auditorioum.setVisibility(View.GONE);
        room_no.setVisibility(View.GONE);
        ground.setVisibility(View.GONE);
    }
    void set_all()
    {
        stb.setVisibility(View.VISIBLE);
        etb.setVisibility(View.VISIBLE);
        sdb.setVisibility(View.VISIBLE);
        edb.setVisibility(View.VISIBLE);
        dept.setVisibility(View.VISIBLE);
        auditorioum.setVisibility(View.VISIBLE);
        room_no.setVisibility(View.VISIBLE);
        ground.setVisibility(View.VISIBLE);
    }
    void init_lab_class()
    {
        set_all();
        auditorioum.setVisibility(View.GONE);
        ground.setVisibility(View.GONE);
    }
    void init_ground()
    {
        set_all();
        dept.setVisibility(View.GONE);
        room_no.setVisibility(View.GONE);
        auditorioum.setVisibility(View.GONE);
    }
    void init_audi()
    {
        set_all();
        dept.setVisibility(View.GONE);
        room_no.setVisibility(View.GONE);
        ground.setVisibility(View.GONE);
    }
    void init_exam_hall()
    {
        set_all();
    }
    void init()
    {
        choice.add("Exam Hall");
        choice.add("Class Room");
        choice.add("Lab Room");
        choice.add("Ground");
        choice.add("Auditorioum");
        depts.add("CSE");
        depts.add("EEE");
        audis.add("TSC");
        audis.add("Cinet bhobon");
        grounds.add("Swimming pool");
        grounds.add("Hagi mohsin");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
        search_category=findViewById(R.id.search_category);
        dept=findViewById(R.id.deptName);
        auditorioum=findViewById(R.id.auditorioum);
        ground=findViewById(R.id.Ground);
        stb=findViewById(R.id.stb);
        etb=findViewById(R.id.etb);
        sdb=findViewById(R.id.sdb);
        edb=findViewById(R.id.edb);
        room_no=findViewById(R.id.room_no);
        init_exam_hall();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,choice);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        search_category.setAdapter(adapter);
        search_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                final_choice=adapterView.getItemAtPosition(i).toString();
                if(i==0) init_exam_hall();
                if(i==1 || i==2) init_lab_class();
                if(i==3) init_ground();
                if(i==4) init_audi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,depts);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dept.setAdapter(adapter);
        dept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                final_dept=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,audis);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        auditorioum.setAdapter(adapter);
        auditorioum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                final_audi=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,grounds);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ground.setAdapter(adapter);
        ground.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                final_ground=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        stb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(SearchActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String choosedHour = "";
                        String choosedMinute = "";
                        String choosedTimeZone = "";
                        if(selectedHour > 12){
                            choosedTimeZone = "PM";
                            selectedHour = selectedHour - 12;
                            if(selectedHour < 10){
                                choosedHour = "0"+selectedHour;
                            }else{
                                choosedHour = ""+selectedHour;
                            }
                        }else{
                            choosedTimeZone = "AM";
                            if(selectedHour < 10){
                                choosedHour = "0"+selectedHour;
                            }else{
                                choosedHour = ""+selectedHour;
                            }
                        }

                        if(selectedMinute < 10){
                            choosedMinute = "0"+selectedMinute;
                        }else{
                            choosedMinute= ""+selectedMinute;
                        }

                        stb.setText(" "+choosedHour + ":" + choosedMinute +" "+choosedTimeZone);
                    }
                }, hour, minute, false);
                mTimePicker.show();
            }
        });
        etb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(SearchActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String choosedHour = "";
                        String choosedMinute = "";
                        String choosedTimeZone = "";
                        if(selectedHour > 12){
                            choosedTimeZone = "PM";
                            selectedHour = selectedHour - 12;
                            if(selectedHour < 10){
                                choosedHour = "0"+selectedHour;
                            }else{
                                choosedHour = ""+selectedHour;
                            }
                        }else{
                            choosedTimeZone = "AM";
                            if(selectedHour < 10){
                                choosedHour = "0"+selectedHour;
                            }else{
                                choosedHour = ""+selectedHour;
                            }
                        }

                        if(selectedMinute < 10){
                            choosedMinute = "0"+selectedMinute;
                        }else{
                            choosedMinute= ""+selectedMinute;
                        }

                        etb.setText(" "+choosedHour + ":" + choosedMinute +" "+choosedTimeZone);
                    }
                }, hour, minute, false);
                mTimePicker.show();
            }
        });




    }
    //
}
