package com.example.asif.du_crs.signUp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.asif.du_crs.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.asif.du_crs.signUp.signUpselect.userThatIsSigningUP;

public class Sign_Up_Code extends AppCompatActivity {

    private ImageButton ok;
    private EditText code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up__code);

        ok = findViewById(R.id.buttonOK);
        code = findViewById(R.id.code);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String mCode = code.getText().toString().trim();
                DatabaseReference databaseUsers= FirebaseDatabase.getInstance().getReference().child("Code");
                databaseUsers.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean flag = false;
                        for(DataSnapshot users : dataSnapshot.getChildren())
                        {
                            Long temp =users.getValue(Long.class);
                            String strLong = Long.toString(temp);

                            if(mCode.equals(strLong)  && users.getKey().equals(userThatIsSigningUP.getDeptName())) {
                                flag =true;
                                Intent signUp = new Intent(Sign_Up_Code.this, Sign_Up.class);
                                startActivity(signUp);
                            }
                        }

                        if(!flag) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Sign_Up_Code.this);
                            alertDialogBuilder.setMessage("Wrong Code");
                            alertDialogBuilder.setPositiveButton("Ok",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            arg0.dismiss();
                                        }
                                    });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
            }
        });
    }
}
