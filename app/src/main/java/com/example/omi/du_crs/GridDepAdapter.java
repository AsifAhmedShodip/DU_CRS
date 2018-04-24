package com.example.omi.du_crs;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asif.du_crs.Classroom;
import com.example.asif.du_crs.MainActivity;
import com.example.asif.du_crs.R;
import com.example.rafi.du_crs.ground_booking;
import com.example.asif.du_crs.sign_in;
import com.example.rafi.du_crs.admin_add_option;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.zip.Inflater;

/**
 * Created by aniomi on 4/19/18.
 */

public class GridDepAdapter extends ArrayAdapter {
    ArrayList<String> choices=new ArrayList<>();
    Context context;
    boolean groundSelected = false;

    public GridDepAdapter(Context context, int textViewResourceId, ArrayList<String> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        choices = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.optionss, null);
        TextView t1=v.findViewById(R.id.t1);
        TextView t2=v.findViewById(R.id.t2);
        ImageView imageView=v.findViewById(R.id.image);
        GridView gridView = v.findViewById(R.id.simpleGridView);
        String s=""+choices.get(position).charAt(0);
        t1.setText(s);
        s=""+choices.get(position);
        t2.setText(s);
        t1.setVisibility(View.GONE);
        if(position==1)
        {
            imageView.setImageResource(R.drawable.microscope_512);
        }
        if(position==2)
        {
            imageView.setImageResource(R.drawable.travel_25_512);
        }
        if(position==3)
        {
            imageView.setImageResource(R.drawable.collegelife010512);
        }
        if(position==4)
        {
            imageView.setImageResource(R.drawable.a23748_200);
        }
        if(position==5)
        {
            imageView.setImageResource(R.drawable.cinema_512);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position == 0){
                    groundSelected = false;
                    getDate();
                    //gotoClassroom();
                }
                if(position==1){
                    groundSelected = false;
                    getDate();
                }
                if(position==3)
                {
                    gotoExamHall();
                }
                if(position==4){
                    groundSelected = true;
                    getDate();
                    /*
                    gotoGround();
                    */
                }
                if(position==2)
                {
                    ExamHallCalendar.mdate="ALL";
                    gotoallreserved();
                }
            }
        });
        return v;
    }

    private void getDate() {
        DatePickerDialog datePickerDialog;
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        /*
                        Intent intent = new Intent(context, Classroom.class);
                        intent.putExtra("year",Integer.toString(year));
                        intent.putExtra("month",Integer.toString(monthOfYear+1));
                        intent.putExtra("day",Integer.toString(dayOfMonth));
                        */
                        if(groundSelected){
                            groundSelected = false;
                            Intent groundIntent = new Intent(context, ground_booking.class);
                            groundIntent.putExtra("year",Integer.toString(year));
                            groundIntent.putExtra("month",Integer.toString(monthOfYear+1));
                            groundIntent.putExtra("day",Integer.toString(dayOfMonth));
                            context.startActivity(groundIntent);
                        }
                        else {
                            groundSelected = true;
                            Intent intent = new Intent(context, Classroom.class);
                            intent.putExtra("year",Integer.toString(year));
                            intent.putExtra("month",Integer.toString(monthOfYear+1));
                            intent.putExtra("day",Integer.toString(dayOfMonth));
                            context.startActivity(intent);
                        }
                    }

                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void gotoClassroom() {
        Intent intent = new Intent(context, Classroom.class);
        context.startActivity(intent);
    }
    private void gotoExamHall()
    {
        Intent intent = new Intent(context, ExamHallCalendar.class);
        context.startActivity(intent);
    }
    private void gotoGround()
    {
        Intent intent = new Intent(context,ground_booking.class );
        context.startActivity(intent);
    }
    private void gotoallreserved()
    {
        Intent intent = new Intent(context,exam_hall_reservation.class );
        context.startActivity(intent);
    }
}
