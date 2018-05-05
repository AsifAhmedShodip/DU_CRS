package com.example.asif.du_crs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent sign_in=new Intent(MainActivity.this,sign_in.class);
        startActivity(sign_in);
        finish();
    }

}
