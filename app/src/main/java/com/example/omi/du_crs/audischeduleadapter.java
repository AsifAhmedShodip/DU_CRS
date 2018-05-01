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
 * Created by aniomi on 5/2/18.
 */

public class audischeduleadapter extends RecyclerView.Adapter<audischeduleadapter.sViewHolder>{
    private List<AuditorioumDetails> list;
    public Context context;
    public audischeduleadapter(List<AuditorioumDetails> list,Context mc) {
        this.list = list;context=mc;
    }

    public class sViewHolder extends RecyclerView.ViewHolder
    {
        public TextView text1,text2,text3;
        public Button button;
        public CardView cv;
        public sViewHolder(View itemView) {
            super(itemView);
            text1=(TextView) itemView.findViewById(R.id.t1);
            text2=(TextView) itemView.findViewById(R.id.t2);
            cv=(CardView) itemView.findViewById(R.id.cv);
            text3= itemView.findViewById(R.id.t3);
        }


    }
    @Override
    public audischeduleadapter.sViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedulecard,parent,false);
        return new audischeduleadapter.sViewHolder(v);
    }
    @Override
    public void onBindViewHolder(audischeduleadapter.sViewHolder holder, int position) {
        final AuditorioumDetails tmp=list.get(position);
        holder.text1.setText(audischedule.rrdate+"");
        holder.text2.setText(search_hall_date_range.standardtime(tmp.getStartt())+" - "+search_hall_date_range.standardtime(tmp.getEndt())+"");
        holder.text3.setText(tmp.getRequesterid());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

}
