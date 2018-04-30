package com.example.omi.du_crs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.example.asif.du_crs.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ExamHallCalendar extends AppCompatActivity {

    ArrayList<String> choices=new ArrayList<>();
    String choice;
    Spinner spinner;
    NumberPicker numberPicker;
    EventDay event;
    EditText editText;
    int choicee=0;
    com.applandeo.materialcalendarview.CalendarView calendarView;

    static String mdate,mstime,metime,cnt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_hall_calendar);
        choices.add("Schedules");
        choices.add("Free Slots");
        choices.add("My reservations");
        spinner=findViewById(R.id.t1);
        calendarView=findViewById(R.id.t2);
        //calendarView.getCurrentPageDate();
        calendarView.showCurrentMonthPage();
        editText=findViewById(R.id.t3);
        editText.setVisibility(View.GONE);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,choices);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                choicee=i;
                //editText.setText(""+i);
                if(i==1)
                {
                    editText.setVisibility(View.VISIBLE);
                }
                else
                {
                    editText.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar calendar=eventDay.getCalendar();
                SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
                String s=format1.format(calendar.getTime()).toString();
                if(choicee==1)
                {
                    if(editText.getText().toString().equals(""))
                    {
                        Toast.makeText(ExamHallCalendar.this, "Student number was not given", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        cnt = editText.getText().toString();
                        int tp = Integer.parseInt(editText.getText().toString());
                        FunctionList.exam_hall_search = new search_hall_date_range(s, s, "Karjon Hall", tp);
                        Intent intent = new Intent(ExamHallCalendar.this, exam_hall_available_list.class);
                        startActivity(intent);
                    }
                }
                if(choicee==2)
                {
                    FunctionList.exam_hall_search.hall_name="Karjon Hall";
                    mdate=s;
                    Intent intent = new Intent(ExamHallCalendar.this,exam_hall_reservation.class);
                    startActivity(intent);
                }
                if(choicee==0)
                {
                    FunctionList.exam_hall_search.hall_name="Karjon Hall";
                    mdate=s;
                    Intent intent = new Intent(ExamHallCalendar.this,ExamHallSchedule.class);
                    startActivity(intent);
                }
                //editText.setText(s);
            }
        });
    }
}
