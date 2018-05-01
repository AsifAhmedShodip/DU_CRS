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
 * Created by aniomi on 5/1/18.
 */

public class audi_free_adapter  extends RecyclerView.Adapter<audi_free_adapter.sViewHolder>{


    private List<mypair> list;
    public Context context;
    public audi_free_adapter(List<mypair> list,Context mc) {
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
    public audi_free_adapter.sViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.audifreeslot,parent,false);
        return new audi_free_adapter.sViewHolder(v);
    }
    @Override
    public void onBindViewHolder(audi_free_adapter.sViewHolder holder, int position) {
        final mypair tmp=list.get(position);
        holder.text1.setText(auditorioumfreeslots.rdate+"");
        holder.text2.setText(search_hall_date_range.standardtime(tmp.v1)+" - "+search_hall_date_range.standardtime(tmp.v2)+"");
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,bookauditorioum.class);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }



}
