package com.example.rafi.du_crs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asif.du_crs.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class ground_book_page extends AppCompatActivity {
    static Ground_object booking;
    private static final int PICK_IMAGE_REQUEST=234;
    private StorageReference storageReference= FirebaseStorage.getInstance().getReference();
    String postID;
    private Uri filePath;
    TextView tvDate, tvStime, tvEtime, tvGName, tvDetail;
    ImageView imageView;
    Button chooseButton, bookButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ground_book_page);

        tvDate = (TextView) findViewById(R.id.tv_date);
        tvGName = (TextView) findViewById(R.id.tv_gname);
        tvStime= (TextView) findViewById(R.id.tv_bp_stime);
        tvEtime = (TextView) findViewById(R.id.tv_bp_etime);
        tvDetail = (TextView) findViewById(R.id.tv_bp_detail);
        imageView = (ImageView) findViewById(R.id.iv_pic);
        chooseButton = (Button) findViewById(R.id.bt_choose);
        /*
        bookButton = (Button) findViewById(R.id.bt_book);
        */

        loadText();
        final DatabaseReference databaseUsers = FirebaseDatabase.getInstance().getReference().child("Ground booking");;
        final DatabaseReference ds2 = databaseUsers.push();
        postID = ds2.getKey()+"";
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNull()){
                    Toast.makeText(getApplicationContext(),"Fill every field first !", Toast.LENGTH_SHORT).show();
                }
                else {
                    booking.setBookedBy("User");
                    booking.setEventName(tvDetail.getText().toString());
                    booking.setBookingID(postID);
                    ds2.setValue(booking);
                    uploadFile();
                }

            }
        });

        chooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });

    }

    boolean isNull(){    //if null : true // if not null : false
        if(tvDetail.getText().equals("") || tvGName.getText().equals("") || tvStime.getText().equals("") ||
                tvEtime.getText().equals("") || tvDate.getText().equals("") || filePath.toString().equals("")){
            return true;
        }
        else
            return false;
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);

                Toast.makeText(getApplicationContext(),"Reach",Toast.LENGTH_LONG);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void loadText(){
        tvDate.setText(booking.date);
        tvStime.setText(booking.startTime);
        tvEtime.setText(booking.endTime);
        tvGName.setText(booking.groundName);
    }

    private void uploadFile() {
        //if there is a file to upload
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            StorageReference riversRef = storageReference.child("POSTimages/"+postID+".jpg");
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying a success toast
                            Toast.makeText(getApplicationContext(),"Reach",Toast.LENGTH_SHORT);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        }
        //if there is not any file
        else {
            //you can display an error toast
        }
    }
}
