package com.example.asif.du_crs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.omi.du_crs.ExamHallSlot;
import com.example.omi.du_crs.FunctionList;
import com.example.omi.du_crs.MyAllExamAdapter;
import com.example.omi.du_crs.node;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asif on 04-May-18.
 */

public class ClassroomAdapter extends RecyclerView.Adapter<ClassroomAdapter.sViewHolder> {
    private List<Classroom_Object> list;
    private Context context;
    private List<String> mp = new ArrayList<>();

    public ClassroomAdapter(Context con, List<Classroom_Object> list) {
        this.list = list;
        context = con;
    }

    @Override
    public ClassroomAdapter.sViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.examcard, parent, false);
        return new ClassroomAdapter.sViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ClassroomAdapter.sViewHolder holder, int position) {

        final Classroom_Object temp = list.get(position);

        //node tt=node.stringtoclass(temp.getRdate());
        holder.text1.setText("" + temp.getDate().split("-")[0]);
        String month = temp.getDate().split("-")[1];
        if (month.equals("1")) {
            holder.text2.setText("January");
        } else if (month.equals("2")) {
            holder.text2.setText("February");
        } else if (month.equals("3")) {
            holder.text2.setText("March");
        } else if (month.equals("3")) {
            holder.text2.setText("April");
        } else if (month.equals("5")) {
            holder.text2.setText("May");
        } else if (month.equals("6")) {
            holder.text2.setText("June");
        }

        holder.text3.setText(temp.getsTiem() + " - " + temp.geteTime());
        holder.text4.setText(temp.getRoom());
        //holder.setIsRecyclable(false);
        /*holder.search_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Exam_Hall").child(FunctionList.exam_hall_search.hall_name).child(temp.getReservetionId());
                //databaseReference.removeValue();

            }
        });*/
        holder.cancel_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder a_buider = new AlertDialog.Builder(context);

                a_buider.setMessage("Do you want to cancel this reservation?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Exam_Hall").child(FunctionList.exam_hall_search.hall_name).child(temp.getReservetionId());
                                // databaseReference.removeValue();

                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Classroom")
                                        .child(User.getCurrent().getDeptName()).child(temp.getDate());
                                databaseReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        //classroom_objects.clear();
                                        for (DataSnapshot users : dataSnapshot.getChildren()) {
                                            Classroom_Object temp01 = new Classroom_Object();
                                            temp01 = users.getValue(Classroom_Object.class);
                                            Log.d("Remove", temp.getDetail());
                                            if (temp.getBookedBy().equals(temp01.getBookedBy()) &&
                                                    temp.getDate().equals(temp01.getDate()) &&
                                                    temp.getsTiem().equals(temp01.getsTiem()) &&
                                                    temp.geteTime().equals(temp01.geteTime()) &&
                                                    temp.getRoom().equals(temp01.getRoom()) &&
                                                    temp.getDetail().equals(temp01.getDetail())) {
                                                Log.d("Remove", "Deleting");
                                                DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Classroom")
                                                        .child(User.getCurrent().getDeptName()).child(temp.getDate()).child(users.getKey());
                                                databaseReference2.removeValue();
                                            }
                                        }
                                        //Log.d("Class 10", String.valueOf(classroom_objects.size()));

                                        //adapter.notifyDataSetChanged();

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });
                                dialogInterface.cancel();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                //DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Exam_Hall").child(FunctionList.exam_hall_search.hall_name).child(temp.getReservetionId());
                //databaseReference.removeValue();
                AlertDialog alert = a_buider.create();
                alert.setTitle("Confirmation");
                alert.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class sViewHolder extends RecyclerView.ViewHolder {
        public TextView text1, text2, text3, text4;
        public Button cancel_b;
        public CardView cv;

        public sViewHolder(View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.datee);
            text2 = itemView.findViewById(R.id.monthe);
            text3 = itemView.findViewById(R.id.slote);
            text4 = itemView.findViewById(R.id.sube);
            cancel_b = itemView.findViewById(R.id.cancelb);
            cv = itemView.findViewById(R.id.cv);

        }


    }

}

