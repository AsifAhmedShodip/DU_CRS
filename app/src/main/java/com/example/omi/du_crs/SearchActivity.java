package com.example.omi.du_crs;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.asif.du_crs.R;
import com.example.asif.du_crs.sign_in;

import java.util.ArrayList;
import java.util.Calendar;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class SearchActivity extends AppCompatActivity {
    Spinner search_category,dept,auditorioum,ground,E_Hall,hall_choice;
    String final_choice,final_dept,final_audi,final_ground,final_hall="Exam Hall",final_hall_choice;
    EditText stb,etb,sdb,edb,room_no,count_no;
    Button searchb;
    ArrayList<String> choice=new ArrayList<>();
    ArrayList<String> audis=new ArrayList<>();
    ArrayList<String> depts=new ArrayList<>();
    ArrayList<String> grounds=new ArrayList<>();
    ArrayList<String> Hall=new ArrayList<>();
    ArrayList<String> Hall_choice_list=new ArrayList<>();
    private DatePickerDialog.OnDateSetListener mDateSetListener,mDateSetListener1;
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
        E_Hall.setVisibility(View.GONE);
        count_no.setVisibility(View.GONE);
        hall_choice.setVisibility(View.GONE);
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
        E_Hall.setVisibility(View.VISIBLE);
        count_no.setVisibility(View.VISIBLE);
        hall_choice.setVisibility(View.VISIBLE);
    }
    void init_lab_class()
    {
        set_all();
        auditorioum.setVisibility(View.GONE);
        ground.setVisibility(View.GONE);
        E_Hall.setVisibility(View.GONE);
        count_no.setVisibility(View.GONE);
        hall_choice.setVisibility(View.GONE);
    }
    void init_ground()
    {
        set_all();
        dept.setVisibility(View.GONE);
        room_no.setVisibility(View.GONE);
        auditorioum.setVisibility(View.GONE);
        E_Hall.setVisibility(View.GONE);
        count_no.setVisibility(View.GONE);
        hall_choice.setVisibility(View.GONE);
    }
    void init_audi()
    {
        set_all();
        dept.setVisibility(View.GONE);
        room_no.setVisibility(View.GONE);
        ground.setVisibility(View.GONE);
        E_Hall.setVisibility(View.GONE);
        count_no.setVisibility(View.GONE);
        hall_choice.setVisibility(View.GONE);
    }
    void init_exam_hall()
    {
        set_all();
        stb.setVisibility(View.GONE);
        etb.setVisibility(View.GONE);
        dept.setVisibility(View.GONE);
        auditorioum.setVisibility(View.GONE);
        room_no.setVisibility(View.GONE);
        ground.setVisibility(View.GONE);

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
        Hall.add("Karjon Hall");
        Hall_choice_list.add("Free Slots");
        Hall_choice_list.add("My Reservations");
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
        E_Hall=findViewById(R.id.ExamHall);
        searchb=findViewById(R.id.searchb);
        count_no=findViewById(R.id.count_no);
        hall_choice=findViewById(R.id.hall_choice);
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
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Hall);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        E_Hall.setAdapter(adapter);
        E_Hall.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                final_hall=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Hall_choice_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hall_choice.setAdapter(adapter);
        hall_choice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                final_hall_choice=adapterView.getItemAtPosition(i).toString();
                if(final_hall_choice.equals("My Reservations"))
                {
                    init_all();
                    hall_choice.setVisibility(View.VISIBLE);

                    E_Hall.setVisibility(View.VISIBLE);
                }
                else
                {
                    init_exam_hall();
                    E_Hall.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        searchb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(final_choice.equals("Exam Hall") && !final_hall_choice.equals("My Reservations"))
                {
                    String temp=count_no.getText().toString();
                    String temp1=sdb.getText().toString();
                    String temp2=edb.getText().toString();

                    int tp=Integer.parseInt(temp);
                    FunctionList.exam_hall_search=new search_hall_date_range(temp1,temp2,final_hall,tp);
                    Intent intent = new Intent(SearchActivity.this,exam_hall_available_list.class);
                    startActivity(intent);
                }
                if(final_choice.equals("Exam Hall") && final_hall_choice.equals("My Reservations"))
                {

                    FunctionList.exam_hall_search.hall_name=final_hall;
                    Intent intent = new Intent(SearchActivity.this,exam_hall_reservation.class);
                    startActivity(intent);
                }
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

                        stb.setText(choosedHour + ":" + choosedMinute +" "+choosedTimeZone);
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

                        etb.setText(choosedHour + ":" + choosedMinute +" "+choosedTimeZone);
                    }
                }, hour, minute, false);
                mTimePicker.show();
            }
        });





        sdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                DatePickerDialog dialog = new DatePickerDialog(
                        SearchActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog,
                        mDateSetListener,
                        mYear, mMonth, mDay);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month++;
                String date = day + "/" + month + "/" + year;
                date="";
                if(day<10) date+="0"+day+"/";
                else date+=day+"/";
                if(month<10) date+="0"+month+"/";
                else date+=month+"/";
                date+=year;
                sdb.setText(date);
            }
        };



        edb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                DatePickerDialog dialog = new DatePickerDialog(
                        SearchActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog,
                        mDateSetListener1,
                        mYear, mMonth, mDay);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month++;
                String date = day + "/" + month + "/" + year;
                date="";
                if(day<10) date+="0"+day+"/";
                else date+=day+"/";
                if(month<10) date+="0"+month+"/";
                else date+=month+"/";
                date+=year;
                edb.setText(date);
            }
        };






    }
    //
}
