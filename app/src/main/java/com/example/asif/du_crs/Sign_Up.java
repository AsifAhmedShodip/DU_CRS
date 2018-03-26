package com.example.asif.du_crs;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class Sign_Up extends AppCompatActivity {

    private EditText deptName , mCode , mEmail, mPass , mRepass;
    private Button mSignUp;
    private TextView mLogin;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    SpinnerDialog spinnerDialog;

    ArrayList<String> deptList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);


        deptName = (EditText) findViewById(R.id.deptName);
        mEmail = (EditText) findViewById(R.id.email);
        mPass = (EditText) findViewById(R.id.password);
        mSignUp= (Button) findViewById(R.id.btn_signup);
        mRepass = findViewById(R.id.retype);
        mCode=findViewById(R.id.code);
        mLogin = findViewById(R.id.link_login);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        Fill_deptList();
        selectDepartment();
        deptName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerDialog.showSpinerDialog();
            }
        });


        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignUp();
            }
        });
    }

    private void selectDepartment(){
        spinnerDialog=new SpinnerDialog(Sign_Up.this,deptList,"Select Department",R.style.DialogAnimations_SmileWindow,"Close");
        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                deptName.setText(item);
            }
        });
    }


    private void Fill_deptList() {
        deptList.add("Computer Science and Engineering");
        deptList.add("Pharmacy");
        deptList.add("Physics");
        deptList.add("Chemistry");
    }

    private void startSignUp() {
        final String dept = deptName.getText().toString().trim();
        final String email = mEmail.getText().toString().trim();
        final String pass = mPass.getText().toString().trim();
        final String rePass = mRepass.getText().toString().trim(); //need to implement
        final String code = mCode.getText().toString().trim();


        if(!TextUtils.isEmpty(dept) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(rePass)
                && !TextUtils.isEmpty(code))
        {
           /* mprog.setMessage("Signing UP...");
            mprog.show();*/
            mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(getApplicationContext(),"DONE",Toast.LENGTH_LONG).show();
                        String uid = mAuth.getCurrentUser().getUid();
                        DatabaseReference current  = mDatabase.child(uid);
                        /*Students temp = new Students(name,pass,email,"CSE","2nd","Dhaka","O+",userID);
                        current.child("Name").setValue(name);
                        current.child("Department").setValue("CSE");
                        current.child("Year").setValue("2nd");
                        current.child("Blood Group").setValue("O+");*/

                        User_Department temp= new User_Department(dept,email,pass,uid);
                        current.setValue(temp);
                        Intent intent = new Intent(Sign_Up.this,MainActivity.class);
                        startActivity(intent) ;
                    }
                }
            });
        }
    }
}
