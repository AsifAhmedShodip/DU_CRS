package com.example.asif.du_crs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asif.du_crs.signUp.signUpselect;
import com.example.rafi.du_crs.admin_add_option;
import com.example.omi.du_crs.SearchActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class sign_in extends AppCompatActivity {

    private Button mSignIn;
    private TextView mSignUp,text;
    private EditText mEmail,mPass;
    static User userThatIsSignedIn = new User();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
       // getActionBar().hide();
        mSignUp = findViewById(R.id.signUpbutton);
        mSignIn = findViewById(R.id.signinbutton);
        mEmail = findViewById(R.id.userID);
        mPass = findViewById(R.id.pass);
        text = findViewById(R.id.textView);

        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/BerkshireSwash-Regular.ttf");
        text.setTypeface(type);
        
        //Check_Login_Status();

        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();

            }
        });

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent select=new Intent(sign_in.this,signUpselect.class);
                startActivity(select);
            }
        });

    }

    public void login()
    {
        final String email = mEmail.getText().toString().trim();
        String password = mPass.getText().toString().trim();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            final ProgressDialog progressDialog = ProgressDialog.show(sign_in.this,"","Logging",true);
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(sign_in.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        DatabaseReference databaseUsers= FirebaseDatabase.getInstance().getReference().child("Users");
                        databaseUsers.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for(DataSnapshot users : dataSnapshot.getChildren())
                                {
                                    User temp=users.getValue(User.class);

                                    if(temp.getEmail().equals(email)) {
                                        User.current=temp;
                                        userThatIsSignedIn = temp;
                                        if(temp.getAccessCode() == 10) {
                                            Intent intent = new Intent(sign_in.this, admin_add_option.class);
                                            startActivity(intent);
                                        }
                                        else if(temp.getAccessCode() == 1 || userThatIsSignedIn.getAccessCode() == 2){
                                            Intent intent = new Intent(sign_in.this, Reservations.class);
                                            startActivity(intent);
                                        }
                                        break;
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        if(User.current.getAccessCode() == 10) {
                            Intent intent = new Intent(sign_in.this, admin_add_option.class);
                            startActivity(intent);
                        }
                        else if(User.current.getAccessCode() == 1 || userThatIsSignedIn.getAccessCode() == 2){
                            Intent intent = new Intent(sign_in.this, Reservations.class);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(sign_in.this, "Login failed", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }
            });
        }
    }

    private void Check_Login_Status() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            DatabaseReference databaseUsers= FirebaseDatabase.getInstance().getReference().child("Users");
            databaseUsers.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for(DataSnapshot users : dataSnapshot.getChildren())
                    {
                        User temp=users.getValue(User.class);

                        if(temp.getEmail().equals(user.getEmail()))
                        {
                            User.current=temp;
                            userThatIsSignedIn = temp;
                            Toast.makeText(sign_in.this, temp.email, Toast.LENGTH_LONG).show();
                            if(temp.getAccessCode() == 10) {
                                Intent intent = new Intent(sign_in.this, admin_add_option.class);
                                startActivity(intent);
                            }
                            else if(temp.getAccessCode() == 1 || userThatIsSignedIn.getAccessCode() == 2){
                                Intent intent = new Intent(sign_in.this, Reservations.class);
                                startActivity(intent);
                            }
                            break;
                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
