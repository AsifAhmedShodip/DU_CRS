package com.example.omi.du_crs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.asif.du_crs.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aniomi on 4/28/18.
 */

public class MyAllExamAdapter extends RecyclerView.Adapter<MyAllExamAdapter.sViewHolder>{
    private List<ExamHallSlot> list;
    private Context context;
    public MyAllExamAdapter(Context con,List<ExamHallSlot> list) {
        this.list = list;context=con;
    }
    private List<String> mp=new ArrayList<>();
    public class sViewHolder extends RecyclerView.ViewHolder
    {
        public TextView text1,text2,text3,text4;
        public Button cancel_b;
        public CardView cv;
        public sViewHolder(View itemView) {
            super(itemView);
            text1=itemView.findViewById(R.id.datee);
            text2=itemView.findViewById(R.id.monthe);
            text3=itemView.findViewById(R.id.slote);
            text4=itemView.findViewById(R.id.sube);
            cancel_b=itemView.findViewById(R.id.cancelb);
            cv=(CardView) itemView.findViewById(R.id.cv);

        }


    }
    @Override
    public MyAllExamAdapter.sViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.examcard,parent,false);
        return new MyAllExamAdapter.sViewHolder(v);
    }
    @Override
    public void onBindViewHolder(MyAllExamAdapter.sViewHolder holder, int position) {

        final ExamHallSlot temp=list.get(position);

        node tt=node.stringtoclass(temp.getRdate());
        holder.text1.setText(""+tt.d);
        holder.text2.setText(""+node.mp.get(tt.m-1));
        holder.text3.setText(search_hall_date_range.standardtime(temp.startTime)+"-"+search_hall_date_range.standardtime(temp.endTime));
        holder.text4.setText(temp.isCancelled+"");
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
                AlertDialog.Builder a_buider=new AlertDialog.Builder(context);

                a_buider.setMessage("Do you want to cancel this reservation?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Exam_Hall").child(FunctionList.exam_hall_search.hall_name).child(temp.getReservetionId());
                                databaseReference.removeValue();
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
                AlertDialog alert=a_buider.create();
                alert.setTitle("Confirmation");
                alert.show();
            }
        });

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

}
