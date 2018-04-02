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
 * Created by aniomi on 3/31/18.
 */

public class exam_hall_resrvation_adapter extends RecyclerView.Adapter<exam_hall_resrvation_adapter.sViewHolder> {
    private List<ExamHallSlot> list;
    public exam_hall_resrvation_adapter(List<ExamHallSlot> list) {
        this.list = list;
    }

    public class sViewHolder extends RecyclerView.ViewHolder
    {
        public TextView text1,text2,text3,text4;
        public Button search_b;
        public CardView cv;
        public sViewHolder(View itemView) {
            super(itemView);
            text1=(TextView) itemView.findViewById(R.id.t1);
            text2=(TextView) itemView.findViewById(R.id.t2);
            text4=itemView.findViewById(R.id.t4);
            text4.setVisibility(View.GONE);
            text3=itemView.findViewById(R.id.t3);
            search_b=itemView.findViewById(R.id.mb);
            cv=(CardView) itemView.findViewById(R.id.cv);

        }


    }
    @Override
    public exam_hall_resrvation_adapter.sViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hall_reservation_cardview,parent,false);
        return new exam_hall_resrvation_adapter.sViewHolder(v);
    }
    @Override
    public void onBindViewHolder(exam_hall_resrvation_adapter.sViewHolder holder, int position) {

        final ExamHallSlot temp=list.get(position);

        //holder.setIsRecyclable(false);
        holder.text1.setText("Date : "+temp.rdate);
        holder.text2.setText("Free Slot : "+search_hall_date_range.standardtime(temp.startTime)+" - "+search_hall_date_range.standardtime(temp.endTime));
        holder.text3.setText("Subject : "+temp.getIsCancelled());
        holder.search_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Exam_Hall").child(FunctionList.exam_hall_search.hall_name).child(temp.getReservetionId());
                databaseReference.removeValue();

            }
        });

    }
    @Override
    public int getItemCount() {
        return list.size();
    }


}
