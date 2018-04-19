package com.example.omi.du_crs;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.asif.du_crs.R;

import java.util.List;

/**
 * Created by aniomi on 3/30/18.
 */

public class exam_hall_adapter extends RecyclerView.Adapter<exam_hall_adapter.sViewHolder>{

    private List<free_slots> list;
    public Context context;
    public exam_hall_adapter(List<free_slots> list,Context mc) {
        this.list = list;context=mc;
    }

    public class sViewHolder extends RecyclerView.ViewHolder
    {
        public TextView text1,text2;
        public Button button;
        public CardView cv;
        public sViewHolder(View itemView) {
            super(itemView);
            text1=(TextView) itemView.findViewById(R.id.t1);
            text2=(TextView) itemView.findViewById(R.id.t2);
            cv=(CardView) itemView.findViewById(R.id.cv);
            button=(Button) itemView.findViewById(R.id.t3);
        }


    }
    @Override
    public sViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hall_cardview,parent,false);
        return new sViewHolder(v);
    }
    @Override
    public void onBindViewHolder(sViewHolder holder, int position) {

        final free_slots temp=list.get(position);
        //holder.setIsRecyclable(false);
        holder.text1.setText("Date : "+temp.mdate);
        holder.text2.setText("Free Slot : "+temp.start_t+" - "+temp.end_t);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExamHallCalendar.mdate=temp.mdate;
                ExamHallCalendar.mstime=temp.start_t;
                ExamHallCalendar.metime=temp.end_t;
                Intent intent = new Intent(context,book_exam_halls.class);
                context.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return list.size();
    }



}
