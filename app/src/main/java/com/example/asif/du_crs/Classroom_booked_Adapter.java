package com.example.asif.du_crs;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asif on 01-May-18.
 */

public class Classroom_booked_Adapter extends RecyclerView.Adapter<Classroom_booked_Adapter.MyViewHolder> {

    List<Classroom_Object> list = new ArrayList<>();
    Context mContext;
    DatabaseReference databaseReference;

    public Classroom_booked_Adapter(List<Classroom_Object> list , Context mContext){
        this.list = list;
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.classroom_booked_list_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.details.setText(list.get(position).detail);
        holder.room.setText("Room No : "+list.get(position).room);
        holder.time.setText("Time Slot : "+list.get(position).sTiem+" - "+list.get(position).eTime);

        holder.contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", list.get(position).bookedBy, null));
                //emailIntent.putExtra(Intent.EXTRA_SUBJECT, "This is my subject text");
                mContext.startActivity(Intent.createChooser(emailIntent, null));
            }
        });

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot users : dataSnapshot.getChildren()){
                    User temp = new User();
                    temp = users.getValue(User.class);
                    if(temp.getEmail().equals(list.get(position).bookedBy)){
                        holder.bookedBy.setText("Booked By : "+temp.getName());
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView details,room,time,bookedBy;
        Button contact;
        public MyViewHolder(View itemView) {
            super(itemView);

            details = itemView.findViewById(R.id.detail);
            room = itemView.findViewById(R.id.room);
            time = itemView.findViewById(R.id.time);
            bookedBy = itemView.findViewById(R.id.booked_by);
            contact = itemView.findViewById(R.id.contact);

        }
    }
}
