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
    String date;
    String selection;
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
                    tvtSTime.setVisibility(View.GONE);
                    tvSTime.setVisibility(View.GONE);
                    tvtETime.setVisibility(View.GONE);
                    tvETime.setVisibility(View.GONE);
                    bBook.setVisibility(View.GONE);

                    spinnerGround.setVisibility(View.VISIBLE);
                }
                else {
                    //deptListSpinnerDialog.setVisibility(view.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }
}
