package com.example.asif.du_crs.signUp;

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

import com.example.asif.du_crs.MainActivity;
import com.example.asif.du_crs.R;
import com.example.asif.du_crs.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

import static com.example.asif.du_crs.signUp.signUpselect.userThatIsSigningUP;

public class Sign_Up extends AppCompatActivity {

    private EditText mEmail, mPass , mRepass , mName;
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

        mEmail = (EditText) findViewById(R.id.email);
        mPass = (EditText) findViewById(R.id.password);
        mSignUp= (Button) findViewById(R.id.btn_signup);
        mRepass = findViewById(R.id.retype);
        mLogin = findViewById(R.id.link_login);
        mAuth = FirebaseAuth.getInstance();
        mName = findViewById(R.id.name);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignUp();
            }
        });
    }

    private void startSignUp() {
        final String email = mEmail.getText().toString().trim();
        final String pass = mPass.getText().toString().trim();
        final String name = mName.getText().toString().trim();
        final String rePass = mRepass.getText().toString().trim(); //need to implement


        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(rePass))
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
                        userThatIsSigningUP.setEmail(email);
                        userThatIsSigningUP.setName(name);
                        userThatIsSigningUP.setPass(pass);
                        userThatIsSigningUP.setUid(uid);
                        current.setValue(userThatIsSigningUP);
                        Intent intent = new Intent(Sign_Up.this,MainActivity.class);
                        startActivity(intent) ;
                    }
                }
            });
        }
    }
}
