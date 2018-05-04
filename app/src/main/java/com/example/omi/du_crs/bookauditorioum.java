package com.example.omi.du_crs;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.asif.du_crs.R;
import com.example.asif.du_crs.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.omi.du_crs.auditorioumfreeslots.needh;
import static com.example.omi.du_crs.auditorioumfreeslots.needl;
import static com.example.omi.du_crs.auditorioumfreeslots.rdate;
import static com.example.omi.du_crs.auditorioumfreeslots.venue;

public class bookauditorioum extends AppCompatActivity {

    private EditText ed1,ed2,ed3;
    private static final int PICK_IMAGE_REQUEST=234;
    private Uri filePath;
    private StorageReference storageReference= FirebaseStorage.getInstance().getReference();
    ImageView imageView;
    DatabaseReference ds;
    Button b1,b2;
    static String postid;
    DatabaseReference databaseReference;
    ArrayList<AuditorioumDetails> bookings=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookauditorioum);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Auditorioum bookings");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bookings.clear();
                for (DataSnapshot users : dataSnapshot.getChildren()) {

                    AuditorioumDetails temp = new AuditorioumDetails();
                    temp = users.getValue(AuditorioumDetails.class);
                    if(temp.venue.equals(venue) && temp.rdate.equals(rdate))bookings.add(temp);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ed1=findViewById(R.id.stt);
        ed2=findViewById(R.id.edt);
        ed3=findViewById(R.id.desc);
        imageView=findViewById(R.id.imageview);
        b1=findViewById(R.id.button);
        b2=findViewById(R.id.button1);

        final DatabaseReference databaseUsers = FirebaseDatabase.getInstance().getReference().child("Auditorioum bookings");;
        ds = databaseUsers.push();
        postid=ds.getKey()+"";
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ed1.getText().toString().equals("") || ed2.getText().toString().equals("") || ed3.getText().toString().equals(""))
                {
                    Toast.makeText(bookauditorioum.this, "You have to select all field", Toast.LENGTH_LONG).show();
                }
                else
                {
                    final String ss=ed1.getText().toString();
                    final String ee=ed2.getText().toString();
                    final String dd=ed3.getText().toString();
                    boolean flag=AuditorioumDetails.isfree(bookings,needl,needh,FunctionList.getminute(ss),FunctionList.getminute(ee));
                    if(flag)
                    {
                        final AlertDialog.Builder a_buider = new AlertDialog.Builder(bookauditorioum.this);

                        a_buider.setMessage("Do you want to book this slot?");
                        a_buider.setCancelable(false);

                        a_buider.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //Toast.makeText(book_exam_halls.this, "Booking is Done", Toast.LENGTH_LONG).show();
                                ds = databaseUsers.push();
                                postid=ds.getKey()+"";
                                uploadFile();
                                AuditorioumDetails temp=new AuditorioumDetails(0,FunctionList.getminute(ss),FunctionList.getminute(ee),
                                        User.getCurrent().getDeptName().toString(),dd,rdate,ds.getKey()+"",
                                        ds.getKey()+"",venue);
                                ds.setValue(temp);
                                uploadFile();
                                dialogInterface.cancel();
                            }
                        });
                        a_buider.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        AlertDialog alert = a_buider.create();
                        alert.setTitle("Confirmation");
                        alert.show();

                    }
                    else
                    {
                        Toast.makeText(bookauditorioum.this, "Not available", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();

            }
        });

        ed1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(bookauditorioum.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String choosedHour = "";
                        String choosedMinute = "";
                        String choosedTimeZone = "";
                        if(selectedHour > 12){
                            choosedTimeZone = "PM";
                            selectedHour = selectedHour - 12;
                            if(selectedHour < 10){
                                choosedHour = "0"+selectedHour;
                            }else{
                                choosedHour = ""+selectedHour;
                            }
                        }else{
                            choosedTimeZone = "AM";
                            if(selectedHour < 10){
                                choosedHour = "0"+selectedHour;
                            }else{
                                choosedHour = ""+selectedHour;
                            }
                        }

                        if(selectedMinute < 10){
                            choosedMinute = "0"+selectedMinute;
                        }else{
                            choosedMinute= ""+selectedMinute;
                        }

                        ed1.setText(choosedHour + ":" + choosedMinute +" "+choosedTimeZone);
                    }
                }, hour, minute, false);
                mTimePicker.show();
            }
        });


        ed2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(bookauditorioum.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String choosedHour = "";
                        String choosedMinute = "";
                        String choosedTimeZone = "";
                        if(selectedHour > 12){
                            choosedTimeZone = "PM";
                            selectedHour = selectedHour - 12;
                            if(selectedHour < 10){
                                choosedHour = "0"+selectedHour;
                            }else{
                                choosedHour = ""+selectedHour;
                            }
                        }else{
                            choosedTimeZone = "AM";
                            if(selectedHour < 10){
                                choosedHour = "0"+selectedHour;
                            }else{
                                choosedHour = ""+selectedHour;
                            }
                        }

                        if(selectedMinute < 10){
                            choosedMinute = "0"+selectedMinute;
                        }else{
                            choosedMinute= ""+selectedMinute;
                        }

                        ed2.setText(choosedHour + ":" + choosedMinute +" "+choosedTimeZone);
                    }
                }, hour, minute, false);
                mTimePicker.show();
            }
        });
    }
    private void uploadFile() {
        //if there is a file to upload
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            StorageReference riversRef = storageReference.child("POSTimages/"+postid+".jpg");
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


                //uploadFile();

            } catch (IOException e) {
                e.printStackTrace();
            }
            ///jkjlop
        }
    }
}
