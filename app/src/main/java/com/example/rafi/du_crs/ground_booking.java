package com.example.rafi.du_crs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.asif.du_crs.R;

import org.w3c.dom.Text;

import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class ground_booking extends AppCompatActivity {

    Button bCheck;
    TextView tvSTime, tvETime, tvDate;
    TextView tvtSTime, tvtETime, tvtGround;
    Spinner spinnerType, spinnerGround;
    boolean byTimeSelected = false;
    String year;
    String month;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ground_booking);
        getSupportActionBar().setTitle("Ground");

        bCheck = (Button) findViewById(R.id.bt_check);
        tvSTime = (TextView) findViewById(R.id.tv_stime);
        tvETime = (TextView) findViewById(R.id.tv_etime);
        tvDate = (TextView) findViewById(R.id.tv_date);

        tvtSTime = (TextView) findViewById(R.id.tv_title_stime);
        tvtETime = (TextView) findViewById(R.id.tv_title_etime);
        tvtGround = (TextView) findViewById(R.id.tv_title_select_ground);

        spinnerType = (Spinner) findViewById(R.id.spinner_select_type);
        spinnerGround = (Spinner) findViewById(R.id.spinner_select_ground);

        tvtSTime.setVisibility(View.GONE);
        tvSTime.setVisibility(View.GONE);
        tvtETime.setVisibility(View.GONE);
        tvETime.setVisibility(View.GONE);




    }
}
