package com.example.rafi.du_crs;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.asif.du_crs.R;
import com.example.omi.du_crs.AuditorioumDetails;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class booking_confirmation extends AppCompatActivity {
    static AuditorioumDetails appliedObject;
    TextView gName, eDate, sTime, eTime, eDetail;
    ImageView ivPic;
    Button bAccept, bCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("created","af");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirmation);

        gName = (TextView) findViewById(R.id.tv_gname);
        eDate = (TextView) findViewById(R.id.tv_date);
        sTime = (TextView) findViewById(R.id.tv_bp_stime);
        eTime = (TextView) findViewById(R.id.tv_bp_etime);
        eDetail = (TextView) findViewById(R.id.tv_bp_detail);

        ivPic = (ImageView) findViewById(R.id.iv_pic);
        bAccept = (Button) findViewById(R.id.bt_accept);
        bCancel = (Button) findViewById(R.id.bt_cancel);

        loadText();
        loadImage();

        bAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appliedObject.setStatus(1);
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Auditorioum bookings").child(appliedObject.getPicloc());
                databaseReference.setValue(appliedObject);
                Toast.makeText(getApplicationContext(),"Accepted !",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appliedObject.setStatus(2);
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Auditorioum bookings").child(appliedObject.getPicloc());
                databaseReference.setValue(appliedObject);
                Toast.makeText(getApplicationContext(),"Cancelled !",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    void loadText(){
        gName.setText(appliedObject.getVenue());
        eDate.setText(appliedObject.getRdate());
        sTime.setText(standardtime(appliedObject.getStartt()));
        eTime.setText(standardtime(appliedObject.getEndt()));
        eDetail.setText(appliedObject.getSubject());
    }

    void loadImage(){
        setImageFromStorage(getApplicationContext(),"https://firebasestorage.googleapis.com/v0/b/ducrs-cb938.appspot.com/o/POSTimages%2F"+appliedObject.getPicloc()+".jpg?alt=media&token=e2cba7e7-ac04-4c4a-b917-fc078e301674",ivPic);
    }

    static void setImageFromStorage(Context context, String uri, ImageView imageView)
    {
        /*
        StorageReference storageRef;// = FirebaseStorage.getInstance().getReference();
        StorageReference forestRef;

        storageRef = FirebaseStorage.getInstance().getReference();

        forestRef = storageRef.child(uri);
        */
        Glide.with(context)
                .load(uri)
                .into(imageView);
    }

    static String standardtime(int ms){
        String s="";
        String isPm="AM";
        if(ms>12*60) isPm="PM";
        ms=(ms%(12*60));
        int sh=ms/60;
        int sm=ms%60;
        if(sh==0) sh=12;

        s=sh+":"+sm+" "+isPm;
        s="";
        if(sh<10) s+="0"+sh+":";
        else s+=sh+":";
        if(sm<10) s+="0"+sm;
        else s+=sm;
        s+=" "+isPm;
        return s;
    }
}
