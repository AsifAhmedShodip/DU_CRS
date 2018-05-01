package com.example.asif.du_crs;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class Upload_routine extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    TextView sDate, eDate, eTime, sTime, room;
    EditText course;
    Spinner spinner;
    Button reserve;
    String day;
    int sDay, sMonth, sYear, eDay, eMonth, eYear;
    SpinnerDialog spinnerDialog;
    ArrayList<String> classList = new ArrayList<>();
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_routine);

        init();
        spinnerInit();

        Fill_classList();
        selectClassroom();

        sDate.setOnClickListener(this);
        eDate.setOnClickListener(this);
        sTime.setOnClickListener(this);
        eTime.setOnClickListener(this);
        //spinner.setOnClickListener(this);
        reserve.setOnClickListener(this);

        room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerDialog.showSpinerDialog();
            }
        });

    }

    private void spinnerInit() {

        spinner.setOnItemSelectedListener(Upload_routine.this);
        List<String> categories = new ArrayList<String>();
        categories.add("Sunday");
        categories.add("Monday");
        categories.add("Tuesday");
        categories.add("Wednesday");
        categories.add("Thursday");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setSelection(0);
    }

    private void init() {
        sDate = findViewById(R.id.sDate);
        eDate = findViewById(R.id.eDate);
        sTime = findViewById(R.id.sTime);
        eTime = findViewById(R.id.eTime);
        spinner = findViewById(R.id.spinner);
        reserve = findViewById(R.id.reserve);
        room = findViewById(R.id.room);
        course = findViewById(R.id.course);
    }

    private void selectClassroom() {
        spinnerDialog = new SpinnerDialog(this, classList, "Select Classroom", R.style.DialogAnimations_SmileWindow, "Close");
        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                room.setText(item);
            }
        });
    }

    private void Fill_classList() {
        //classList.add("412");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("data").child("Class").child(User.getCurrent().getDeptName());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot users : dataSnapshot.getChildren()) {
                    classList.add(users.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.sDate: {
                DatePickerDialog datePickerDialog;
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int yr, int monthOfYear, int dayOfMonth) {
                                sDate.setText(Integer.toString(dayOfMonth) + "-" + Integer.toString(monthOfYear + 1) + "-" + Integer.toString(yr));
                                sYear = yr;
                                sMonth = monthOfYear + 1;
                                sDay = dayOfMonth;
                            }

                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            }

            case R.id.eDate: {
                DatePickerDialog datePickerDialog;
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int yr, int monthOfYear, int dayOfMonth) {
                                eDate.setText(Integer.toString(dayOfMonth) + "-" + Integer.toString(monthOfYear + 1) + "-" + Integer.toString(yr));
                                eYear = yr;
                                eMonth = monthOfYear + 1;
                                eDay = dayOfMonth;
                            }

                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            }

            case R.id.sTime: {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Upload_routine.this, AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int selectedMinute) {
                        String status = "AM";

                        if (hourOfDay > 11) {
                            status = "PM";
                        }
                        int hour_of_12_hour_format;

                        if (hourOfDay > 12) {
                            hour_of_12_hour_format = hourOfDay - 12;
                        } else {
                            hour_of_12_hour_format = hourOfDay;
                        }

                        if (selectedMinute < 10) {
                            sTime.setText(hour_of_12_hour_format + " :  0" + selectedMinute + " " + status);
                        } else {
                            sTime.setText(hour_of_12_hour_format + " :  " + selectedMinute + " " + status);
                        }
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
                break;
            }

            case R.id.eTime: {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Upload_routine.this, AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int selectedMinute) {
                        String status = "AM";

                        if (hourOfDay > 11) {
                            status = "PM";
                        }
                        int hour_of_12_hour_format;

                        if (hourOfDay > 12) {
                            hour_of_12_hour_format = hourOfDay - 12;
                        } else {
                            hour_of_12_hour_format = hourOfDay;
                        }

                        if (selectedMinute < 10) {
                            eTime.setText(hour_of_12_hour_format + " :  0" + selectedMinute + " " + status);
                        } else {
                            eTime.setText(hour_of_12_hour_format + " :  " + selectedMinute + " " + status);
                        }
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
                break;
            }

            case R.id.reserve: {
                reserveRoutine();
                break;
            }
        }
    }

    private void reserveRoutine() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = null, d2 = null;
        String s1 = sYear + "-" + sMonth + "-" + sDay;
        String s2 = eYear + "-" + eMonth + "-" + eDay;

        int dayofWeek = selectDay();

        Log.d("Date", s1 + "  " + s2);
        int count = 0;
        try {
            d1 = formatter.parse(s1);
            d2 = formatter.parse(s2);
            //count = saturdaysundaycount(d1,d2);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(d2);

        while (!c1.after(c2)) {
            if (c1.get(Calendar.DAY_OF_WEEK) == dayofWeek) {

                Classroom_Object classroom_object = new Classroom_Object();
                classroom_object.setDetail(course.getText().toString());
                classroom_object.setRoom(room.getText().toString());
                classroom_object.setsTiem(sTime.getText().toString());
                classroom_object.seteTime(eTime.getText().toString());
                classroom_object.setBookedBy(User.getCurrent().getEmail());

                int m = c1.get(Calendar.MONTH) + 1;
                int y = c1.get(Calendar.YEAR);
                int d = c1.get(Calendar.DAY_OF_MONTH);

                //pushing in Firebase
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Classroom")
                        .child(User.getCurrent().getDeptName()).child(d + "-" + m + "-" + y);
                String key = FirebaseDatabase.getInstance().getReference().push().getKey();
                mDatabase.child(key).setValue(classroom_object);

                Toast.makeText(this, "DONE",
                        Toast.LENGTH_SHORT).show();
            }

            c1.add(Calendar.DATE, 1);
        }
    }

    private int selectDay() {
        if (day.equals("Sunday"))
            return 1;
        else if (day.equals("Monday"))
            return 2;
        else if (day.equals("Tuesday"))
            return 3;
        else if (day.equals("Wednesday"))
            return 4;
        else if (day.equals("Thursday"))
            return 5;

        return 0;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        day = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
