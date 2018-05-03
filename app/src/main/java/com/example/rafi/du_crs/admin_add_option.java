package com.example.rafi.du_crs;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.asif.du_crs.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;


public class admin_add_option extends AppCompatActivity {

    private EditText nameET,locationET , startTimeET, endTimeET, capacityET ;
    private AppCompatButton addButton;
    Spinner optionSpinnerDialog, deptListSpinnerDialog;
    private DatabaseReference databaseReference;

    String fieldS, deptS, nameS, locationS, sTime, eTime;
    int capacityInt;

    ArrayList<String> optionList = new ArrayList<>();
    ArrayList<String> deptList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_option);
        getSupportActionBar().setTitle("Admin Panel : Add New Place");

        nameET = (EditText) findViewById(R.id.et_name);
        locationET = (EditText) findViewById(R.id.et_location);
        startTimeET = (EditText) findViewById(R.id.et_start_time);
        startTimeET.setFocusable(false);
        startTimeET.setClickable(true);
        endTimeET = (EditText) findViewById(R.id.et_end_time);
        endTimeET.setFocusable(false);
        endTimeET.setClickable(true);
        capacityET = (EditText) findViewById(R.id.et_capacity);
        addButton = (AppCompatButton) findViewById(R.id.b_add);
        optionSpinnerDialog = (Spinner) findViewById(R.id.spinner_category);
        deptListSpinnerDialog = (Spinner) findViewById(R.id.spinner_dept_list);

        loadList();
        nullAll();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,optionList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        optionSpinnerDialog.setAdapter(adapter);
        optionSpinnerDialog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fieldS = adapterView.getItemAtPosition(i).toString();
                if(!fieldS.equals("Class") && !fieldS.equals("Lab")){
                    deptListSpinnerDialog.setVisibility(view.GONE);
                }
                else {
                    deptListSpinnerDialog.setVisibility(view.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> deptAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,deptList);
        deptAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deptListSpinnerDialog.setAdapter(deptAdapter);
        deptListSpinnerDialog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                deptS = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        startTimeET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(admin_add_option.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int sHour, int sMinute) {
                        String choosedHour = "";
                        String choosedMinute = "";
                        String choosedTimeZone = "";
                        if(sHour > 12){
                            choosedTimeZone = "PM";
                            sHour = sHour - 12;
                            if(sHour < 10){
                                choosedHour = "0"+sHour;
                            }else{
                                choosedHour = ""+sHour;
                            }
                        }else{
                            choosedTimeZone = "AM";
                            if(sHour < 10){
                                choosedHour = "0"+sHour;
                            }else{
                                choosedHour = ""+sHour;
                            }
                        }

                        if(sMinute < 10){
                            choosedMinute = "0"+sMinute;
                        }else{
                            choosedMinute= ""+sMinute;
                        }

                        startTimeET.setText(choosedHour + ":" + choosedMinute +" "+choosedTimeZone);
                    }
                },hour,minute,false);
                timePickerDialog.show();
            }
        });

        endTimeET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(admin_add_option.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int sHour, int sMinute) {
                        String choosedHour = "";
                        String choosedMinute = "";
                        String choosedTimeZone = "";
                        if(sHour > 12){
                            choosedTimeZone = "PM";
                            sHour = sHour - 12;
                            if(sHour < 10){
                                choosedHour = "0"+sHour;
                            }else{
                                choosedHour = ""+sHour;
                            }
                        }else{
                            choosedTimeZone = "AM";
                            if(sHour < 10){
                                choosedHour = "0"+sHour;
                            }else{
                                choosedHour = ""+sHour;
                            }
                        }

                        if(sMinute < 10){
                            choosedMinute = "0"+sMinute;
                        }else{
                            choosedMinute= ""+sMinute;
                        }

                        endTimeET.setText(choosedHour + ":" + choosedMinute +" "+choosedTimeZone);
                    }
                },hour,minute,false);
                timePickerDialog.show();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkNull()){
                    detail newData = new detail(nameS, locationS, sTime, eTime, capacityInt);
                    if(fieldS.equals("Class") || fieldS.equals("Lab")){
                        databaseReference = FirebaseDatabase.getInstance().getReference("data").child(fieldS).child(deptS).child(nameS);
                        databaseReference.setValue(newData);
                    }
                    else {
                        databaseReference = FirebaseDatabase.getInstance().getReference("data").child(fieldS).child(nameS);
                        databaseReference.setValue(newData);
                    }

                }

                nullAll();
                Toast.makeText(getApplicationContext(), "PLACE ADDED !", Toast.LENGTH_SHORT).show();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
    }

    void loadList(){
        optionList.add("Select type");
        optionList.add("Exam Hall");
        optionList.add("Class");
        optionList.add("Lab");
        optionList.add("Auditorium");
        optionList.add("Ground");

        String[] depts = new String[]{"Select dept","Computer Science and Engineering","EEE","GE","PHYSICS","Chemistry"};
        deptList = new ArrayList<String>(Arrays.asList(depts)) ;
    }

    void nullAll(){
        fieldS = "";
        nameET.setText("");
        locationET.setText("");
        startTimeET.setText("");
        endTimeET.setText("");
        capacityET.setText("");
    }

    boolean checkNull(){
        boolean result = false;
        nameS = nameET.getText().toString();
        locationS = locationET.getText().toString();
        sTime = startTimeET.getText().toString();
        eTime = endTimeET.getText().toString();
        capacityInt = Integer.parseInt(capacityET.getText().toString());

        if(nameS.equals("") || locationS.equals("") || sTime.equals("") || eTime.equals("") || capacityInt==0){
            result = true;
        }

        return result;
    }
}
