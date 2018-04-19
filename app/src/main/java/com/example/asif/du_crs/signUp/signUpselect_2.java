package com.example.asif.du_crs.signUp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.asif.du_crs.R;
import com.example.asif.du_crs.User;

import java.util.ArrayList;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

import static com.example.asif.du_crs.signUp.signUpselect.userThatIsSigningUP;

public class signUpselect_2 extends AppCompatActivity {
    private ImageButton ok;
    private TextView department;
    SpinnerDialog spinnerDialog;
    ArrayList<String> deptList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_upselect_2);

        ok = findViewById(R.id.buttonOK);
        department = findViewById(R.id.dept);

        Fill_deptList();
        selectDepartment();
        department.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerDialog.showSpinerDialog();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userThatIsSigningUP.getAccessCode() == 2){
                    Intent signUp=new Intent(signUpselect_2.this,Sign_Up_Code.class);
                    startActivity(signUp);
                }
                else if(userThatIsSigningUP.getAccessCode() == 1){
                    Intent signUp=new Intent(signUpselect_2.this,Sign_Up.class);
                    startActivity(signUp);
                }
            }
        });
    }

    private void selectDepartment(){
        spinnerDialog=new SpinnerDialog(signUpselect_2.this,deptList,"Select Department",R.style.DialogAnimations_SmileWindow,"Close");
        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                department.setText(item);
                userThatIsSigningUP.setDeptName(item);

            }
        });
    }


    private void Fill_deptList() {
        deptList.add("Computer Science and Engineering");
        deptList.add("Pharmacy");
        deptList.add("Physics");
        deptList.add("Chemistry");
    }
}
