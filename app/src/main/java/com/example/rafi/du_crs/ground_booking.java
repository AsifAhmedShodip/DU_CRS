package com.example.rafi.du_crs;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.asif.du_crs.Classroom;
import com.example.asif.du_crs.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class ground_booking extends AppCompatActivity implements View.OnClickListener{

    Button bCheck, bBook;
    TextView tvSTime, tvETime, tvDate;
    TextView tvtSTime, tvtETime, tvtGround;
    TextView tvResult;
    Spinner spinnerType, spinnerGround;
    boolean byTimeSelected = false;
    String year;
    String month;
    String day;
    String date;
    String selection;
    String sTime,eTime;
    String groundSelection;
    ArrayList<String> optionList = new ArrayList<>();
    ArrayList<String> groundList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ground_booking);
        getSupportActionBar().setTitle("Ground");

        bCheck = (Button) findViewById(R.id.bt_check);
        bBook = (Button) findViewById(R.id.bt_book);
        tvSTime = (TextView) findViewById(R.id.tv_stime);
        tvETime = (TextView) findViewById(R.id.tv_etime);
        tvDate = (TextView) findViewById(R.id.tv_date);

        tvtSTime = (TextView) findViewById(R.id.tv_title_stime);
        tvtETime = (TextView) findViewById(R.id.tv_title_etime);
        tvtGround = (TextView) findViewById(R.id.tv_title_select_ground);

        spinnerType = (Spinner) findViewById(R.id.spinner_select_type);
        spinnerGround = (Spinner) findViewById(R.id.spinner_select_ground);

        recyclerView = (RecyclerView) findViewById(R.id.ground_recycler);
        recyclerView.setVisibility(View.GONE);

        tvtSTime.setVisibility(View.GONE);
        tvSTime.setVisibility(View.GONE);
        tvtETime.setVisibility(View.GONE);
        tvETime.setVisibility(View.GONE);
        bBook.setVisibility(View.GONE);

        Bundle b = getIntent().getExtras();
        if(b!=null){
            year = b.getString("year");
            month = b.getString("month");
            day = b.getString("day");
        }
        date = day+"-"+month+"-"+year;
        tvDate.setText(day+"-"+month+"-"+year);

        optionList.add("BY GROUND");
        optionList.add("BY TIME");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,optionList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selection = adapterView.getItemAtPosition(i).toString();
                if(selection.equals("BY GROUND")){
                    byTimeSelected = false;
                    tvtSTime.setVisibility(View.GONE);
                    tvSTime.setVisibility(View.GONE);
                    tvtETime.setVisibility(View.GONE);
                    tvETime.setVisibility(View.GONE);
                    bBook.setVisibility(View.GONE);

                    tvtGround.setVisibility(View.VISIBLE);
                    spinnerGround.setVisibility(View.VISIBLE);
                }
                else {
                    byTimeSelected = true;
                    //deptListSpinnerDialog.setVisibility(view.VISIBLE);
                    tvtSTime.setVisibility(View.VISIBLE);
                    tvSTime.setVisibility(View.VISIBLE);
                    tvtETime.setVisibility(View.VISIBLE);
                    tvETime.setVisibility(View.VISIBLE);
                    bBook.setVisibility(View.VISIBLE);

                    tvtGround.setVisibility(View.GONE);
                    spinnerGround.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /*
        groundList.add("Gymnasium");
        groundList.add("Jogonnath");
        */
        groundList.add("Select Ground");
        getGroundList();
        ArrayAdapter<String> gAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,groundList);
        gAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGround.setAdapter(gAdapter);
        spinnerGround.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                groundSelection = adapterView.getItemAtPosition(i).toString();
                spinnerGround.setSelection(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        bCheck.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"bCheck clickked",Toast.LENGTH_SHORT).show();
                if(byTimeSelected){
                    try {
                        Toast.makeText(getApplicationContext(),"bytime",Toast.LENGTH_SHORT).show();
                        getGroundListByTime();
                    } catch (ParseException e) {
                        Toast.makeText(getApplicationContext(),"by ground",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                }
                else {
                        getGroundListByGround();

                }
            }
        });

        tvtETime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mTime = Calendar.getInstance();
                int hour = mTime.get(Calendar.HOUR_OF_DAY);
                int minute = mTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ground_booking.this, AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
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

                        eTime = hour_of_12_hour_format + " : " + selectedMinute + " " + status;
                        tvETime.setText(eTime);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

    }

    void getGroundListByTime() throws ParseException {
        final HashMap<String,Boolean>  gAvailable = new HashMap<String, Boolean>();
        for(int i=1; i<groundList.size();i++){
            gAvailable.put(groundList.get(i),true);
        }
        final SimpleDateFormat dateFormat2 = new SimpleDateFormat("hh : mm aa");
        final Date dSTime = dateFormat2.parse(sTime);
        final Date dETime = dateFormat2.parse(eTime);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Ground booking");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleData : dataSnapshot.getChildren()){
                    Ground_object reservedObject = singleData.getValue(Ground_object.class);
                    try {
                        Date eventSTime = dateFormat2.parse(reservedObject.getStartTime().toString());
                        Date eventETime = dateFormat2.parse(reservedObject.getEndTime().toString());

                        if(dSTime.getTime()>eventETime.getTime() || dETime.getTime()<eventSTime.getTime()){
                            gAvailable.put(reservedObject.getGroundName(),true);
                        }
                        else if(dSTime.getTime()<eventSTime.getTime() && dETime.getTime()<eventETime.getTime()){
                            gAvailable.put(reservedObject.getGroundName(),false);
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ArrayList<Ground_object> availableGList = new ArrayList<>();
        for(String key : gAvailable.keySet()){
            if(gAvailable.get(key)){
                Ground_object newGO = new Ground_object(key,sTime,eTime,date,true);
                availableGList.add(newGO);
            }
        }

        adapter = new GroundAvailableAdapter(availableGList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    void getGroundList(){
        /*
        groundList.add("Gymnasium");
        groundList.add("Jogonnath");
        */
        //firebase ret later
        //c
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("data").child("Ground");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot users : dataSnapshot.getChildren()){
                    groundList.add(users.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    void getGroundListByGround () {
        final String[] sTime = new String[1];
        final String[] eTime = new String[1];
        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference("data").child("Ground");
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    detail temp = d.getValue(detail.class);
                    if(temp.getName().equals(groundSelection)){
                        sTime[0] = temp.startTime;
                        eTime[0] = temp.endTime;
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        SimpleDateFormat  dateFormat = new SimpleDateFormat("hh : mm aa");
        //Date dSt = dateFormat.parse(sTime[0]);

        final ArrayList<Ground_object> unavailableGList = new ArrayList<>();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Ground booking");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleData : dataSnapshot.getChildren()){
                    Ground_object reservedObject = singleData.getValue(Ground_object.class);
                    if(reservedObject.getGroundName().equals(groundSelection)){
                        if(reservedObject.getDate().equals(date)){
                            unavailableGList.add(reservedObject);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ArrayList<Ground_object> availableList = new ArrayList<>();
        Ground_object gTemp = new Ground_object(groundSelection,sTime[0],eTime[0],date,true);
        availableList.add(gTemp);
        for(Ground_object gNow : unavailableGList){
            for (Ground_object gT : availableList){
                if(true){

                }
            }
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_stime:{
                Toast.makeText(getApplicationContext(),"stime clickked",Toast.LENGTH_SHORT).show();
                Calendar mTime = Calendar.getInstance();
                int hour = mTime.get(Calendar.HOUR_OF_DAY);
                int minute = mTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ground_booking.this, AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
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

                        sTime = hour_of_12_hour_format + " : " + selectedMinute + " " + status;
                        tvSTime.setText(sTime);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
                break;
            }

            case R.id.tv_etime:{
                Toast.makeText(getApplicationContext(),"etime clicked",Toast.LENGTH_SHORT).show();
                Calendar mTime = Calendar.getInstance();
                int hour = mTime.get(Calendar.HOUR_OF_DAY);
                int minute = mTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ground_booking.this, AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
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

                        eTime = hour_of_12_hour_format + " : " + selectedMinute + " " + status;
                        tvETime.setText(eTime);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
                break;
            }

        }
    }
}
