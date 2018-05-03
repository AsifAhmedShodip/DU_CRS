package com.example.rafi.du_crs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asif.du_crs.R;

public class admin_booking_detail extends AppCompatActivity {
    static Ground_object appliedObject;
    TextView gName, eDate, sTime, eTime, eDetail;
    ImageView ivPic;
    Button bAccept, bCancel;
    private TextView locationTV,eventTV,userTV,dateTV,timeTV;
    private ImageView receiptIV;
    private AppCompatButton cancelB,approveB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_booking_detail);

        locationTV = (TextView) findViewById(R.id.tv_location_name);
        eventTV = (TextView) findViewById(R.id.tv_event_name);
        userTV = (TextView) findViewById(R.id.tv_user_name);
        dateTV = (TextView) findViewById(R.id.tv_date);
        timeTV = (TextView) findViewById(R.id.tv_time_detail);

        receiptIV = (ImageView) findViewById(R.id.iv_receipt);

    }
}
