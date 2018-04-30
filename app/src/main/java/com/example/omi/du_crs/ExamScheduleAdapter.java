package com.example.omi.du_crs;

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

import java.util.List;

/**
 * Created by aniomi on 4/19/18.
 */

public class ExamScheduleAdapter  extends RecyclerView.Adapter<ExamScheduleAdapter.sViewHolder>{
    private List<ExamHallSlot> list;
    public ExamScheduleAdapter(List<ExamHallSlot> list) {
        this.list = list;
    }

    public class sViewHolder extends RecyclerView.ViewHolder
    {
        public TextView text1,text2,text3,text4;
        public sViewHolder(View itemView) {
            super(itemView);
            text1=itemView.findViewById(R.id.t1);
            text2=itemView.findViewById(R.id.t2);
            text3=itemView.findViewById(R.id.t3);
            text4=itemView.findViewById(R.id.t5);
        }

    }
    @Override
    public ExamScheduleAdapter.sViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_card_view,parent,false);
        return new ExamScheduleAdapter.sViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ExamScheduleAdapter.sViewHolder holder, int position) {

        final ExamHallSlot temp=list.get(position);
        holder.text1.setText("Date : "+temp.getRdate());
        holder.text2.setText("Time Slot : "+search_hall_date_range.standardtime(temp.getStartTime())
                +"-"+search_hall_date_range.standardtime(temp.getEndTime()));
        holder.text3.setText("Subject : "+temp.getIsCancelled());
        holder.text4.setText("Reserver Id : "+temp.getReserverId());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

}
