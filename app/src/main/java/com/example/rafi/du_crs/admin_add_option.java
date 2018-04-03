package com.example.rafi.du_crs;

import android.app.TimePickerDialog;
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

import com.example.asif.du_crs.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;


public class admin_add_option extends AppCompatActivity {

    private EditText nameET, startTimeET, endTimeET, capacityET ;
    private AppCompatButton addButton;
    Spinner optionSpinnerDialog;
    private DatabaseReference databaseReference;

    String fieldS, nameS, sTime, eTime;
    int capacityInt;

    ArrayList<String> optionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_option);

        nameET = (EditText) findViewById(R.id.et_name);
        startTimeET = (EditText) findViewById(R.id.et_start_time);
        endTimeET = (EditText) findViewById(R.id.et_end_time);
        capacityET = (EditText) findViewById(R.id.et_capacity);
        addButton = (AppCompatButton) findViewById(R.id.b_add);
        optionSpinnerDialog = (Spinner) findViewById(R.id.spinner_category);

        loadList();
        nullAll();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,optionList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        optionSpinnerDialog.setAdapter(adapter);
        optionSpinnerDialog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fieldS = adapterView.getItemAtPosition(i).toString();
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
                    detail newData = new detail(nameS, sTime, eTime, capacityInt);
                    databaseReference = FirebaseDatabase.getInstance().getReference("data").child(fieldS).child(nameS);
                    databaseReference.setValue(newData);

                }

                nullAll();
            }
        });
    }

    void loadList(){
        optionList.add("Exam Hall");
        optionList.add("Class");
        optionList.add("Lab");
        optionList.add("Auditorium");
        optionList.add("Ground");
    }

    void nullAll(){
        fieldS = "";
        nameET.setText("");
        startTimeET.setText("");
        endTimeET.setText("");
        capacityET.setText("");
    }

    boolean checkNull(){
        boolean result = false;
        nameS = nameET.getText().toString();
        sTime = startTimeET.getText().toString();
        eTime = endTimeET.getText().toString();
        capacityInt = Integer.parseInt(capacityET.getText().toString());

        if(nameS.equals("") || sTime.equals("") || eTime.equals("") || capacityInt==0){
            result = true;
        }

        return result;
    }
}
