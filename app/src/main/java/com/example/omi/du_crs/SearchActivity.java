package com.example.omi.du_crs;

import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.asif.du_crs.R;

import java.util.ArrayList;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class SearchActivity extends AppCompatActivity {
    EditText search_category,start_time,end_time,st_date,ed_date,department,room_no,ground;
    Button search_button;
    SpinnerDialog choice_dialogue;
    ArrayList<String> choice=new ArrayList<>();
    void init()
    {
        choice.add("Exam Hall");
        choice.add("Class Room");
        choice.add("Lab Room");
        choice.add("Ground");
        choice.add("Auditorioum");
    }
    void class_lab_init()
    {
        department.setVisibility(View.VISIBLE);
        room_no.setVisibility(View.VISIBLE);
        ground.setVisibility(View.GONE);
    }
    void ground_auditorioum_init()
    {
        department.setVisibility(View.GONE);
        room_no.setVisibility(View.GONE);
        ground.setVisibility(View.VISIBLE);
    }
    void exam_hall_init()
    {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
        search_category=findViewById(R.id.search_category);
        start_time=findViewById(R.id.start_time);
        end_time=findViewById(R.id.end_time);
        st_date=findViewById(R.id.lower_date);
        ed_date=findViewById(R.id.higher_date);
        department=findViewById(R.id.department);
        room_no=findViewById(R.id.room_no);
        ground=findViewById(R.id.ground);
        search_button=findViewById(R.id.search_buttons);

        choice_dialogue=new SpinnerDialog(getParent(),choice,"Enter Category");
        choice_dialogue.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                search_category.setText(s);
                if(i==0) exam_hall_init();
                if(i==1 || i==2) class_lab_init();
                if(i==3 || i==4) ground_auditorioum_init();
            }
        });
        search_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice_dialogue.showSpinerDialog();
            }
        });
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(search_category.getText().equals("Exam Hall"))
                {

                }
                if(search_category.getText().equals("Class Room"))
                {

                }
                if(search_category.getText().equals("Lab Room"))
                {

                }
                if(search_category.getText().equals("Ground"))
                {

                }
                if(search_category.getText().equals("Auditorioum"))
                {

                }
            }
        });
        start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        st_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        ed_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        start_time.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
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

                        start_time.append(" "+choosedHour + ":" + choosedMinute +" "+choosedTimeZone);
                    }
                }, hour, minute, false);
                mTimePicker.show();
            }
        });
        end_time.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
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

                        end_time.append(" "+choosedHour + ":" + choosedMinute +" "+choosedTimeZone);
                    }
                }, hour, minute, false);
                mTimePicker.show();
            }
        });

    }
}
