package com.example.omi.du_crs;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.asif.du_crs.R;
import com.example.asif.du_crs.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class book_exam_halls extends AppCompatActivity {

    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Exam_Hall").child(FunctionList.exam_hall_search.hall_name);
    DatabaseReference ds;
    EditText exd,exst,exet,excnt,exsub;
    Button booking_button;
    ArrayList<ExamHallSlot> all_bookings=new ArrayList<>();
    ArrayList<ExamHallSlot> temp_list=new ArrayList<>();
    private DatePickerDialog.OnDateSetListener mDateSetListener,mDateSetListener1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_exam_halls);
        exd=findViewById(R.id.exd);
        exst=findViewById(R.id.exst);
        exet=findViewById(R.id.exet);
        excnt=findViewById(R.id.excnt);
        exsub=findViewById(R.id.exsub);
        exd.setText(ExamHallCalendar.mdate);
        exst.setText(ExamHallCalendar.mstime);
        exet.setText(ExamHallCalendar.metime);
        excnt.setText(ExamHallCalendar.cnt);
        booking_button=findViewById(R.id.booking_button);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Exam_Hall").child(FunctionList.exam_hall_search.hall_name);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                {
                    all_bookings.clear();
                    for (DataSnapshot users : dataSnapshot.getChildren()) {
                        ExamHallSlot temp = new ExamHallSlot();
                        temp = users.getValue(ExamHallSlot.class);
                        all_bookings.add(temp);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        exst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(book_exam_halls.this, new TimePickerDialog.OnTimeSetListener() {
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

                        exst.setText(choosedHour + ":" + choosedMinute +" "+choosedTimeZone);
                    }
                }, hour, minute, false);
                mTimePicker.show();
            }
        });
        exet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(book_exam_halls.this, new TimePickerDialog.OnTimeSetListener() {
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

                        exet.setText(choosedHour + ":" + choosedMinute +" "+choosedTimeZone);
                    }
                }, hour, minute, false);
                mTimePicker.show();
            }
        });
        exd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                DatePickerDialog dialog = new DatePickerDialog(
                        book_exam_halls.this,
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
                exd.setText(date);
            }
        };
        booking_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(exd.getText().toString().equals("") || exst.getText().toString().equals("") || exet.getText().toString().equals("")
                        || excnt.getText().toString().equals("") || exsub.getText().toString().equals(""))
                {
                    Toast.makeText(book_exam_halls.this, "All Field Must be Filled", Toast.LENGTH_LONG).show();
                }
                else {
                    temp_list.clear();
                    String s = exst.getText().toString();
                    final int exam_st = FunctionList.getminute(s);
                    s = exet.getText().toString();
                    final int exam_et = FunctionList.getminute(s);
                    s = excnt.getText().toString();
                    final int cnt = Integer.parseInt(s);
                    s = exd.getText().toString();
                    for (int i = 0; i < all_bookings.size(); i++) {
                        if (all_bookings.get(i).rdate.equals(s)) temp_list.add(all_bookings.get(i));
                    }
                    boolean bool = FunctionList.isTheTimeSlotFree(exam_st, exam_et, (8 * 60) + 30, 17 * 60, 200, cnt, temp_list);
                    if (bool) {


                        final AlertDialog.Builder a_buider = new AlertDialog.Builder(book_exam_halls.this);

                        a_buider.setMessage("Do you want to book this slot?");
                        a_buider.setCancelable(false);
                        final String finalS = s;
                        a_buider.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ExamHallSlot temp = new ExamHallSlot();
                                ds = databaseReference.push();
                                temp.setReservetionId("" + ds.getKey());
                                temp.setRdate(finalS);
                                temp.setStartTime(exam_st);
                                temp.setEndTime(exam_et);
                                temp.setCancelled(exsub.getText().toString());
                                temp.setReserverId(User.getCurrent().getDeptName().toString());
                                temp.counter = cnt;
                                ds.setValue(temp);
                                Toast.makeText(book_exam_halls.this, "Booking is Done", Toast.LENGTH_LONG).show();
                                dialogInterface.cancel();
                            }
                        });
                        a_buider.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        AlertDialog alert = a_buider.create();
                        alert.setTitle("Confirmation");
                        alert.show();
                        
                    } else {
                        Toast.makeText(book_exam_halls.this, "Not available", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

}
