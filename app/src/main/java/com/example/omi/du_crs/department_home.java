package com.example.omi.du_crs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.example.asif.du_crs.R;

import java.util.ArrayList;

public class department_home extends AppCompatActivity {

    GridView simpleList;
    ArrayList<String> choices=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_home);
        choices.add("Class Room");
        choices.add("Lab Room");
        choices.add("Reservations");
        choices.add("Exam Hall");
        choices.add("Ground");
        choices.add("Auditorium");
        choices.add("Upload Routine");
        simpleList = findViewById(R.id.simpleGridView);
        GridDepAdapter adapter=new GridDepAdapter(this,R.layout.optionss,choices);
        simpleList.setAdapter(adapter);
    }
}
