package com.example.asif.du_crs;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.asif.du_crs.signUp.signUpselect;
import com.example.asif.du_crs.signUp.signUpselect_2;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;


public class Classroom extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener{
    final ArrayList<String> availableClassroom = new ArrayList<>();
    Button check,book,list;
    TextView selectRoom, date, room_text, sTime_text, sTime, eTime_text, eTime;
    ScrollView sv;
    ArrayList<String> classList=new ArrayList<>();
    ArrayAdapter arrayAdapter, arrayAdapterRoom;
    ListView result;
    SpinnerDialog spinnerDialog;
    String year;
    String month;
    String day;
    DatabaseReference databaseReference, databaseReference2;
    boolean isRoomTypeSelected = false;
    ArrayList<String> NotAvailableClassroom = new ArrayList<>();
    RecyclerView rv;
    Classroom_booked_Adapter adapter;
    List<Classroom_Object> bookedClassroom = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom);
        getSupportActionBar().setTitle("Classroom");

        Bundle b = getIntent().getExtras();
        if (b != null) {
            year = b.getString("year");
            month = b.getString("month");
            day = b.getString("day");
        }

        initial();
        playWithVisibility();

        arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_listview, availableClassroom);
        arrayAdapterRoom = new ArrayAdapter<String>(this, R.layout.activity_listview_2, NotAvailableClassroom);

        result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0 && !isRoomTypeSelected) {
                    Intent intent = new Intent(Classroom.this, Book_Classroom.class);
                    intent.putExtra("date", date.getText().toString());
                    intent.putExtra("room", (String) adapterView.getItemAtPosition(i));
                    intent.putExtra("Start Time", sTime.getText().toString());
                    intent.putExtra("End Time", eTime.getText().toString());
                    startActivity(intent);
                }
            }
        });


        Spinner spinner = findViewById(R.id.select_type);
        spinner.setOnItemSelectedListener(Classroom.this);
        List<String> categories = new ArrayList<String>();
        categories.add("BY ROOM");
        categories.add("BY TIME");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setSelection(1);

        date.setText(day+"-"+month+"-"+year);

        Fill_classList();
        selectClassroom();


        selectRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerDialog.showSpinerDialog();
            }
        });


        check.setOnClickListener(this);
        date.setOnClickListener(this);
        book.setOnClickListener(this);
        sTime.setOnClickListener(this);
        eTime.setOnClickListener(this);
        list.setOnClickListener(this);
    }

    private void playWithVisibility() {
        result.setVisibility(View.GONE);
        book.setVisibility(View.GONE);
        list.setVisibility(View.GONE);

        sTime_text.setVisibility(View.VISIBLE);
        sTime.setVisibility(View.VISIBLE);
        eTime_text.setVisibility(View.VISIBLE);
        eTime.setVisibility(View.VISIBLE);

        room_text.setVisibility(View.GONE);
        selectRoom.setVisibility(View.GONE);


    }

    public void initial() {
        check = findViewById(R.id.check);
        book = findViewById(R.id.book);
        list = findViewById(R.id.list);
        selectRoom = findViewById(R.id.select_room);
        result = findViewById(R.id.result);
        date = findViewById(R.id.date);
        sTime_text = findViewById(R.id.stime_text);
        sTime = findViewById(R.id.select_start);
        eTime_text = findViewById(R.id.etime_text);
        eTime = findViewById(R.id.etime);
        room_text = findViewById(R.id.room_text);
        rv = findViewById(R.id.recycler);

        sv = findViewById(R.id.scrollView);
    }

    private void selectClassroom() {
        spinnerDialog=new SpinnerDialog(this,classList,"Select Classroom",R.style.DialogAnimations_SmileWindow,"Close");
        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                selectRoom.setText(item);
            }
        });
    }

    private void Fill_classList() {
        //classList.add("412");
        databaseReference= FirebaseDatabase.getInstance().getReference().child("data").child("Class").child(User.getCurrent().getDeptName());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot users : dataSnapshot.getChildren()){
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

        switch (view.getId()){


            case R.id.check:{
                rv.setVisibility(View.GONE);
                list.setVisibility(View.GONE);
                if(isRoomTypeSelected){
                    checkForRoomType();
                }
                else{
                    checkForTimeType();
                }
                break;
            }


            case R.id.date:{
                DatePickerDialog datePickerDialog;
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int yr, int monthOfYear, int dayOfMonth) {
                                date.setText(Integer.toString(dayOfMonth)+"-"+Integer.toString(monthOfYear+1)+"-"+Integer.toString(yr));
                                year = Integer.toString(yr);
                                month  = Integer.toString(monthOfYear);
                                day = Integer.toString(dayOfMonth);
                            }

                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            }


            case R.id.book:{
                Intent intent = new Intent(this, Book_Classroom.class);
                intent.putExtra("date",date.getText().toString());
                intent.putExtra("room",selectRoom.getText().toString());
                intent.putExtra("Start Time", "");
                intent.putExtra("End Time", "");
                startActivity(intent);
                break;
            }

            case R.id.select_start:{
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Classroom.this, AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
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
                            sTime.setText(hour_of_12_hour_format + " : 0" + selectedMinute + " " + status);
                        } else {
                            sTime.setText(hour_of_12_hour_format + " : " + selectedMinute + " " + status);
                        }
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
                break;
            }


            case R.id.etime:{
                Calendar mcurrentTime2 = Calendar.getInstance();
                int hour2 = mcurrentTime2.get(Calendar.HOUR_OF_DAY);
                int minute2 = mcurrentTime2.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker2;
                mTimePicker2 = new TimePickerDialog(Classroom.this,AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
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
                                eTime.setText(hour_of_12_hour_format + " : 0" + selectedMinute + " " + status);
                        } else {
                            eTime.setText(hour_of_12_hour_format + " : " + selectedMinute + " " + status);
                        }
                    }
                }, hour2, minute2, false);//Yes 24 hour time
                mTimePicker2.setTitle("Select Time");
                mTimePicker2.show();
                break;
            }


            case R.id.list:{
                rv.setVisibility(View.VISIBLE);

                adapter = new Classroom_booked_Adapter(bookedClassroom, this);
                rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                rv.setItemAnimator(new DefaultItemAnimator());
                rv.hasFixedSize();
                rv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                bookedClassroom.clear();
                final String date1 = date.getText().toString();   //have to reConfigure
                if (isRoomTypeSelected) {
                    databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Classroom").child(User.getCurrent().getDeptName()).child(date1);
                    databaseReference2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot users : dataSnapshot.getChildren()) {
                                Classroom_Object classroom_object = users.getValue(Classroom_Object.class);
                                if (selectRoom.getText().toString().equals(classroom_object.getRoom())) {
                                    bookedClassroom.add(classroom_object);
                                }
                            }

                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else {
                    databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Classroom").child(User.getCurrent().getDeptName()).child(date1);
                    databaseReference2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot users : dataSnapshot.getChildren()) {
                                Classroom_Object classroom_object = users.getValue(Classroom_Object.class);
                                bookedClassroom.add(classroom_object);
                            }

                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                break;
            }
        }
    }

    private void checkForTimeType() {
        result.setVisibility(View.GONE);
        book.setVisibility(View.GONE);
        //result.setText("");

        availableClassroom.clear();
        availableClassroom.add("Available Classrooms : ");

        for(int i =0 ;i<classList.size();i++){
            availableClassroom.add(classList.get(i));
        }

        loadNotAvailableTimeSlots();
    }

    private void checkForRoomType() {
        final ArrayList<String> bookedslots=new ArrayList<>();
        //result.setVisibility(View.GONE);
        book.setVisibility(View.GONE);
        //result.setText("");
        NotAvailableClassroom.clear();

        result.setAdapter(arrayAdapterRoom);

        final String date1 = date.getText().toString();  //have to reConfigure
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Classroom").child(User.getCurrent().getDeptName()).child(date1);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bookedslots.removeAll(bookedslots);
                NotAvailableClassroom.clear();
                for(DataSnapshot users : dataSnapshot.getChildren()){
                    Classroom_Object classroom_object = users.getValue(Classroom_Object.class);
                    if(selectRoom.getText().toString().equals(classroom_object.getRoom()))
                        bookedslots.add(classroom_object.getsTiem()+"  -  "+classroom_object.geteTime());
                }

                if(bookedslots.size() == 0){
                    result.setVisibility(View.VISIBLE);
                    //result.setText("All day is avaiable on \n("+date+")");
                    book.setVisibility(View.VISIBLE);
                    NotAvailableClassroom.add("All Day is Available !!! ");
                }
                else {
                    result.setVisibility(View.VISIBLE);
                    book.setVisibility(View.VISIBLE);
                    list.setVisibility(View.VISIBLE);
                    //result.append("");
                    //result.setText("Below listed time slots are NOT available:\n\n");
                    NotAvailableClassroom.add("Booked Slots :");
                    for (int i =0;i<bookedslots.size();i++){
                        //result.append(bookedslots.get(i)+"\n");
                        NotAvailableClassroom.add(bookedslots.get(i));
                    }
                }

                arrayAdapterRoom.notifyDataSetChanged();
                Helper.getListViewSize(result);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        if(position==0){
            isRoomTypeSelected = true;
            sTime_text.setVisibility(View.GONE);
            sTime.setVisibility(View.GONE);
            eTime_text.setVisibility(View.GONE);
            eTime.setVisibility(View.GONE);
            room_text.setVisibility(View.VISIBLE);
            selectRoom.setVisibility(View.VISIBLE);
            result.setVisibility(View.GONE);
            book.setVisibility(View.GONE);
            list.setVisibility(View.GONE);
            rv.setVisibility(View.GONE);
        }
        else{
            isRoomTypeSelected = false;
            sTime_text.setVisibility(View.VISIBLE);
            sTime.setVisibility(View.VISIBLE);
            eTime_text.setVisibility(View.VISIBLE);
            eTime.setVisibility(View.VISIBLE);
            room_text.setVisibility(View.GONE);
            selectRoom.setVisibility(View.GONE);
            result.setVisibility(View.GONE);
            book.setVisibility(View.GONE);
            list.setVisibility(View.GONE);
            rv.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void loadNotAvailableTimeSlots() {

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

        result.setAdapter(arrayAdapter);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Classroom").child(User.getCurrent().getDeptName()).child(date.getText().toString());
        final Double[] finalStart = {start};
        final Double[] finalEnd = {end};
        finalStart[0] = finalStart[0] + 00.10;
        finalEnd[0] = finalEnd[0] - 00.10;
        final String[] flag = new String[1];
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot users : dataSnapshot.getChildren()){

                    Classroom_Object classroom_object = users.getValue(Classroom_Object.class);

                    /*String fTime = classroom_object.getsTiem();
                    String[] p = fTime.split(" ");
                    String time = p[0]+"."+p[2];
                    Double firstTime = Double.parseDouble(time);
                    if(p[3].equals("PM"))
                    {
                        firstTime = firstTime + 12.00;
                    }

                    String eTime = classroom_object.geteTime();
                    String[] p1 = eTime.split(" ");
                    String time2 = p1[0]+"."+p1[2];
                    Double secondTime = Double.parseDouble(time2);
                    if(p1[3].equals("PM"))
                    {
                        secondTime = secondTime + 12.00;
                    }*/

                    Double firstTime = getDateValue(classroom_object.getsTiem());

                    Double secondTime = getDateValue(classroom_object.geteTime());

                    flag[0] = "false";
                    if(!(finalStart[0] >= secondTime || finalEnd [0]<= firstTime)){
                        availableClassroom.remove(classroom_object.getRoom());
                    }

                    /*if (finalStart[0] >= firstTime && finalStart[0] <= secondTime) {
                        flag[0] = "true01";
                        availableClassroom.remove(classroom_object.getRoom());
                    } else if (finalEnd[0] >= firstTime && finalEnd[0] <= secondTime) {
                        availableClassroom.remove(classroom_object.getRoom());
                        flag[0] = "true02";
                    }*/

                    Log.d("Debug", finalStart[0] + "  " + finalEnd[0] + "    time   =  " + firstTime + "   " + secondTime + "   " + classroom_object.getRoom());
                }

                if(availableClassroom.size() == 0){
                    result.setVisibility(View.VISIBLE);
                    //book.setVisibility(View.VISIBLE);
                    availableClassroom.add("   NONE !!!!");
                    list.setVisibility(View.VISIBLE);
                }
                else {
                    result.setVisibility(View.VISIBLE);
                    // result.setText("Below listed Room is Available:\n");
                    //book.setVisibility(View.VISIBLE);
                    list.setVisibility(View.VISIBLE);
                }

                arrayAdapter.notifyDataSetChanged();
                Helper.getListViewSize(result);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    double getDateValue(String dateS){
        double val = 0;
        String[] p1 = dateS.split(" ");
        String time2 = p1[0]+"."+p1[2];
        val = Double.parseDouble(time2);
        if(p1[3].equals("PM"))
        {
            val = val + 12.00;
        }
        return val;
    }
}
