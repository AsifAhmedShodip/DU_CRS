package com.example.asif.du_crs;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class signUpselect extends AppCompatActivity {
    private ImageButton ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_upselect);

        ok = findViewById(R.id.buttonOK);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUp=new Intent(signUpselect.this,Sign_Up.class);
                startActivity(signUp);
            }
        });
    }
}
