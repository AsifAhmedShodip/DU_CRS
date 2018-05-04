package com.example.asif.du_crs;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class Book_Classroom extends AppCompatActivity implements View.OnClickListener{

    TextView   sTime,eTime , date2,room2;
    Button book;
    EditText detail;
    String date, room, start, end;
    DatabaseReference databaseReference;
    ArrayList<Double> sTimeSlots=new ArrayList<>();
    ArrayList<Double> eTimeSlots=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom_book);

        getSupportActionBar().setTitle("Reserve");

        sTime = findViewById(R.id.sTime2);
        date2 = findViewById(R.id.date2);
        room2 = findViewById(R.id.room2);
        eTime = findViewById(R.id.eTime2);
        detail = findViewById(R.id.detail);
        book = findViewById(R.id.book2);

        Bundle b = getIntent().getExtras();
        if(b!=null){
            date = b.getString("date");
            room = b.getString("room");
            start = b.getString("Start Time");
            end = b.getString("End Time");
        }

        date2.setText(date);
        room2.setText(room);
        if (!start.equals("")) {
            sTime.setText(start);
            eTime.setText(end);
        }

        sTime.setOnClickListener(this);
        eTime.setOnClickListener(this);
        book.setOnClickListener(this);

        loadNotAvailableTimeSlots();
    }

    private void loadNotAvailableTimeSlots() {
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Classroom").child(User.getCurrent().getDeptName()).child(date);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot users : dataSnapshot.getChildren()){

                    Classroom_Object classroom_object = users.getValue(Classroom_Object.class);
                    if(room.equals(classroom_object.getRoom())) {
                        String string = classroom_object.getsTiem();
                        String[] parts = string.split(" ");
                        string = parts[0]+"."+parts[2];
                        Double time = Double.parseDouble(string);
                        if(parts[3].equals("PM")){
                            time = time + 12.00;
                        }
                        sTimeSlots.add(time);

                        String string2 = classroom_object.geteTime();
                        String[] parts2 = string2.split(" ");
                        string2 = parts2[0]+"."+parts2[2];
                        Double time2 = Double.parseDouble(string2);
                        if(parts2[3].equals("PM")){
                            time2 = time2 + 12.00;
                        }
                        eTimeSlots.add(time2);

                        Log.d("Asif Ahmed",time  +"  -   "+time2);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sTime2: {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Book_Classroom.this, AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int selectedMinute) {
                        String status = "AM";

                        if(hourOfDay > 11)
                        {
                            status = "PM";
                        }
                        int hour_of_12_hour_format;

                        if (hourOfDay > 12) {
                            hour_of_12_hour_format = hourOfDay - 12;
                        }
                        else {
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


            case R.id.eTime2:
                Calendar mcurrentTime2 = Calendar.getInstance();
                int hour2 = mcurrentTime2.get(Calendar.HOUR_OF_DAY);
                int minute2 = mcurrentTime2.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker2;
                mTimePicker2 = new TimePickerDialog(Book_Classroom.this,AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int selectedMinute) {
                        String status = "AM";

                        if(hourOfDay > 11)
                        {
                            status = "PM";
                        }
                        int hour_of_12_hour_format;

                        if (hourOfDay > 12) {
                            hour_of_12_hour_format = hourOfDay - 12;
                        }
                        else {
                            hour_of_12_hour_format = hourOfDay;
                        }

                        if (selectedMinute < 10) {
                            eTime.setText(hour_of_12_hour_format + " :  0" + selectedMinute + " " + status);
                        } else {
                            eTime.setText(hour_of_12_hour_format + " :  " + selectedMinute + " " + status);
                        }
                    }
                }, hour2, minute2, false);//Yes 24 hour time
                mTimePicker2.setTitle("Select Time");
                mTimePicker2.show();
                break;

            case R.id.book2: {

                //boolean available = checkTimeSlots(sTime.getText().toString(), eTime.getText().toString());

                boolean flag = true;

                String string = sTime.getText().toString();
                String[] parts = string.split(" ");
                string = parts[0]+"."+parts[2];
                Double start = Double.parseDouble(string);
                if(parts[3].equals("PM")){
                    start = start + 12.00;
                }

                String string2 = eTime.getText().toString();
                String[] parts2 = string2.split(" ");
                string2 = parts2[0]+"."+parts2[2];
                Double end = Double.parseDouble(string2);
                if(parts2[3].equals("PM")){
                    end = end + 12.00;
                }

                for(int i=0;i<sTimeSlots.size();i++){
                    Double firstTime = sTimeSlots.get(i);
                    Double secondTime = eTimeSlots.get(i);

                    start = start + 00.10;
                    end = end - 00.10;

                    if(start >= firstTime && start <= secondTime){
                        flag = false;
                        break;
                    }

                    if (end >= firstTime && end <= secondTime){
                        flag = false;
                        break;
                    }
                }

                Log.d("Asif","i am here " + "flag");

                if (flag) {
                    Classroom_Object classroom_object = new Classroom_Object();
                    classroom_object.setDetail(detail.getText().toString());
                    classroom_object.setRoom(room);
                    classroom_object.setsTiem(sTime.getText().toString());
                    classroom_object.seteTime(eTime.getText().toString());
                    classroom_object.setBookedBy(User.getCurrent().getEmail());
                    classroom_object.setDate(date2.getText().toString());

                    //pushing in Firebase
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Classroom").child(User.getCurrent().getDeptName()).child(date);
                    String key = FirebaseDatabase.getInstance().getReference().push().getKey();
                    mDatabase.child(key).setValue(classroom_object);

                    Toast.makeText(this, "DONE",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Time Slots Are NOT Available !!!",
                            Toast.LENGTH_SHORT).show();
                }

                break;
            }
        }
    }

    private boolean checkTimeSlots(String sTime, String eTime) {

        boolean flag = true;
        Double start = Double.parseDouble(sTime);
        Double end = Double.parseDouble(eTime);

        for(int i=0;i<sTimeSlots.size();i++){
            Double firstTime = sTimeSlots.get(i);
            Double secondTime = eTimeSlots.get(i);

            firstTime = firstTime + 00.10;
            secondTime = secondTime - 00.10;

            if(start >= firstTime && start <= secondTime){
                flag = false;
                break;
            }

            if (end >= firstTime && end <= secondTime){
                flag = false;
                break;
            }
        }

        Log.d("Asif Ahmed","i am here " + flag);
        return flag;
    }
}

