package com.example.rafi.du_crs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.asif.du_crs.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class ground_booking extends AppCompatActivity {

    Button bCheck, bBook;
    TextView tvSTime, tvETime, tvDate;
    TextView tvtSTime, tvtETime, tvtGround;
    TextView tvResult;
    Spinner spinnerType, spinnerGround;
    boolean byTimeSelected = false;
    String year;
    String month;
    String day;
    String selection;
    String groundSelection;
    ArrayList<String> optionList = new ArrayList<>();
    ArrayList<String> groundList = new ArrayList<>();

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

        tvResult = (TextView) findViewById(R.id.tv_result);

        spinnerType = (Spinner) findViewById(R.id.spinner_select_type);
        spinnerGround = (Spinner) findViewById(R.id.spinner_select_ground);

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
                if(byTimeSelected){
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Ground");

                }
                else {

                }
            }
        });

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
}
