package com.example.asif.du_crs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Classroom_book extends AppCompatActivity implements View.OnClickListener {

    TextView   sTime,eTime;
    Button book;
    EditText detail;
    String date,room;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom);

        sTime = (TextView) findViewById(R.id.sTime2);
        eTime = (TextView) findViewById(R.id.eTime2);
        detail = (EditText ) findViewById(R.id.detail);
        book = (Button) findViewById(R.id.finalbook);

        Bundle b = getIntent().getExtras();
        if(b!=null){
            date = b.getString("date");
            room = b.getString("room");
        }

        sTime.setOnClickListener(this);
        eTime.setOnClickListener(this);
        book.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sTime2:
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Classroom_book.this, AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int selectedMinute) {
                        String status = "AM";

                        if(hourOfDay > 11)
                        {
                            status = "PM";
                        }
                        int hour_of_12_hour_format;

                        if(hourOfDay > 11){
                            hour_of_12_hour_format = hourOfDay - 12;
                        }
                        else {
                            hour_of_12_hour_format = hourOfDay;
                        }

                        sTime.setText(hour_of_12_hour_format + " : " + selectedMinute + " " + status);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
                break;
            case R.id.eTime2:
                Calendar mcurrentTime2 = Calendar.getInstance();
                int hour2 = mcurrentTime2.get(Calendar.HOUR_OF_DAY);
                int minute2 = mcurrentTime2.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker2;
                mTimePicker = new TimePickerDialog(Classroom_book.this,AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int selectedMinute) {
                        String status = "AM";

                        if(hourOfDay > 11)
                        {
                            status = "PM";
                        }
                        int hour_of_12_hour_format;

                        if(hourOfDay > 11){
                            hour_of_12_hour_format = hourOfDay - 12;
                        }
                        else {
                            hour_of_12_hour_format = hourOfDay;
                        }

                        eTime.setText(hour_of_12_hour_format + " : " + selectedMinute + " " + status);
                    }
                }, hour2, minute2, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
                break;

            case R.id.finalbook:
                Classroom_Object classroom_object = new Classroom_Object();
                classroom_object.setDetail(detail.getText().toString());
                classroom_object.setRoom(room);
                classroom_object.setsTiem(sTime.getText().toString());
                classroom_object.seteTime(eTime.getText().toString());
                classroom_object.setBookedBy(User.getCurrent().getEmail());

                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Classroom").child(User.getCurrent().getDeptName()).child(date);
                String key = FirebaseDatabase.getInstance().getReference().push().getKey();
                mDatabase.child(key).setValue(classroom_object);

                Toast.makeText(this,"DONE",
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
