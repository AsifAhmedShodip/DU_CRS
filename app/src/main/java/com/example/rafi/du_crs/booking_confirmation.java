package com.example.rafi.du_crs;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
        //loadImage();

        bAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appliedObject.setStatus(1);
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Auditorium bookings").child(appliedObject.getPicloc());
                databaseReference.setValue(appliedObject);
            }
        });

        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appliedObject.setStatus(2);
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Auditorium bookings").child(appliedObject.getPicloc());
                databaseReference.setValue(appliedObject);
            }
        });

        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    void loadText(){
        gName.setText(appliedObject.getVenue());
        eDate.setText(appliedObject.getRdate());
        sTime.setText(appliedObject.getStartt());
        eTime.setText(appliedObject.getEndt());
        eDetail.setText(appliedObject.getSubject());
    }

    void loadImage(){
        setImageFromStorage(getApplicationContext(),"POSTimages/"+appliedObject.getPicloc()+".jpg",ivPic);
    }

    static void setImageFromStorage(Context context, String uri, ImageView imageView)
    {
        StorageReference storageRef;// = FirebaseStorage.getInstance().getReference();
        StorageReference forestRef;

        storageRef = FirebaseStorage.getInstance().getReference();

        forestRef = storageRef.child(uri);
        Glide.with(context)
                .load(forestRef)
                .into(imageView);
    }
}
