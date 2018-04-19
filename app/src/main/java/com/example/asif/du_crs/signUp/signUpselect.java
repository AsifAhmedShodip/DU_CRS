package com.example.asif.du_crs.signUp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.asif.du_crs.R;
import com.example.asif.du_crs.User;

import java.util.ArrayList;
import java.util.List;

public class signUpselect extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private ImageButton ok;
    static User userThatIsSigningUP = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_upselect);

        ok = findViewById(R.id.buttonOK);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) signUpselect.this);
        List<String> categories = new ArrayList<String>();
        categories.add("As Department Authority");
        categories.add("As Faculty Member");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent signUp=new Intent(signUpselect.this,signUpselect_2.class);
                    startActivity(signUp);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        if(position==0){
            userThatIsSigningUP.setAccessCode(2);
        }
        else
            userThatIsSigningUP.setAccessCode(1);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
