package com.example.asif.du_crs;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
    Button check,book,list;
    TextView selectRoom,result,date,room_text,sTime_text,sTime,eTime_text,eTime;
    ArrayList<String> classList=new ArrayList<>();
    SpinnerDialog spinnerDialog;
    String year;
    String month;
    String day;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom);
        
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

        result.setVisibility(View.GONE);
        book.setVisibility(View.GONE);
        list.setVisibility(View.GONE);
        sTime_text.setVisibility(View.GONE);
        sTime.setVisibility(View.GONE);
        eTime_text.setVisibility(View.GONE);
        eTime.setVisibility(View.GONE);

        Spinner spinner = findViewById(R.id.select_type);
        spinner.setOnItemSelectedListener(Classroom.this);
        List<String> categories = new ArrayList<String>();
        categories.add("BY ROOM");
        categories.add("BY TIME");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setSelection(0);

        Bundle b = getIntent().getExtras();
        if(b!=null){
            year = b.getString("year");
            month = b.getString("month");
            day = b.getString("day");
        }

        date.setText(day+"-"+month+"-"+year);

        Fill_classList(); // Remember to fill the list from admin
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
    }

    private void selectClassroom() {
        spinnerDialog=new SpinnerDialog(this,classList,"Select Classroom",R.style.DialogAnimations_SmileWindow,"Close");
        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                selectRoom.setText(item);
                //userThatIsSigningUP.setDeptName(item);

            }
        });
    }

    private void Fill_classList() {
        classList.add("320");
        classList.add("324");
        classList.add("312");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.check:{
                final ArrayList<String> bookedslots=new ArrayList<>();
                bookedslots.removeAll(bookedslots);
                result.setVisibility(View.INVISIBLE);
                book.setVisibility(View.INVISIBLE);
                result.setText("");

                final String date = day+"-"+month+"-"+year;  //have to reConfigure
                databaseReference= FirebaseDatabase.getInstance().getReference().child("Classroom").child(User.getCurrent().getDeptName()).child(date);
                databaseReference.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {
                       for(DataSnapshot users : dataSnapshot.getChildren()){
                            Classroom_Object classroom_object = users.getValue(Classroom_Object.class);
                            if(selectRoom.getText().toString().equals(classroom_object.getRoom()))
                                bookedslots.add(classroom_object.getsTiem()+"  -  "+classroom_object.geteTime());
                       }

                       if(bookedslots.size() == 0){
                           result.setVisibility(View.VISIBLE);
                           result.setText("All day is avaiable on \n("+date+")");
                           book.setVisibility(View.VISIBLE);
                       }
                       else {
                           result.setVisibility(View.VISIBLE);
                           book.setVisibility(View.VISIBLE);
                           list.setVisibility(View.VISIBLE);
                           //result.append("");
                           result.setText("Below listed time slots are NOT available:\n\n");
                           for (int i =0;i<bookedslots.size();i++){
                               result.append(bookedslots.get(i)+"\n");
                           }
                       }

                   }

                   @Override
                   public void onCancelled(DatabaseError databaseError) {

                   }
                });
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
                                date.setText(Integer.toString(dayOfMonth)+"/"+Integer.toString(monthOfYear+1)+"/"+Integer.toString(yr));
                                year = Integer.toString(yr);
                                month  = Integer.toString(monthOfYear);
                                day = Integer.toString(dayOfMonth);
                            }

                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            }

            case R.id.book:{
                Intent intent = new Intent(this, Classroom_book.class);
                intent.putExtra("date",date.getText().toString());
                intent.putExtra("room",selectRoom.getText().toString());
                startActivity(intent);
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        if(position==0){
            sTime_text.setVisibility(View.GONE);
            sTime.setVisibility(View.GONE);
            eTime_text.setVisibility(View.GONE);
            eTime.setVisibility(View.GONE);
            room_text.setVisibility(View.VISIBLE);
            selectRoom.setVisibility(View.VISIBLE);
            result.setVisibility(View.GONE);
            book.setVisibility(View.GONE);
        }
        else{
            sTime_text.setVisibility(View.VISIBLE);
            sTime.setVisibility(View.VISIBLE);
            eTime_text.setVisibility(View.VISIBLE);
            eTime.setVisibility(View.VISIBLE);
            room_text.setVisibility(View.GONE);
            selectRoom.setVisibility(View.GONE);
            result.setVisibility(View.GONE);
            book.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
