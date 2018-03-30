package com.example.omi.du_crs;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asif.du_crs.R;

import java.util.List;

/**
 * Created by aniomi on 3/30/18.
 */

public class exam_hall_adapter extends RecyclerView.Adapter<exam_hall_adapter.sViewHolder>{

    private List<free_slots> list;
    public exam_hall_adapter(List<free_slots> list) {
        this.list = list;
    }

    public class sViewHolder extends RecyclerView.ViewHolder
    {
        public TextView text1,text2;
        public CardView cv;
        public sViewHolder(View itemView) {
            super(itemView);
            text1=(TextView) itemView.findViewById(R.id.t1);
            text2=(TextView) itemView.findViewById(R.id.t2);
            cv=(CardView) itemView.findViewById(R.id.cv);
        }


    }
    @Override
    public sViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hall_cardview,parent,false);
        return new sViewHolder(v);
    }
    @Override
    public void onBindViewHolder(sViewHolder holder, int position) {

        free_slots temp=list.get(position);
        //holder.setIsRecyclable(false);
        holder.text1.setText("Date : "+temp.mdate);
        holder.text2.setText("Free Slot : "+temp.start_t+" - "+temp.end_t);

    }
    @Override
    public int getItemCount() {
        return list.size();
    }



}
