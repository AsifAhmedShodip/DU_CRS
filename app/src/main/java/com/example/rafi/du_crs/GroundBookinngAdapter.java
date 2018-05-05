package com.example.rafi.du_crs;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.asif.du_crs.R;
import com.example.omi.du_crs.AuditorioumDetails;

import java.util.ArrayList;
import java.util.List;

public class GroundBookinngAdapter extends RecyclerView.Adapter<GroundBookinngAdapter.MyViewHolder> {
    List<Ground_object> gList = new ArrayList<>();
    Context context;

    public GroundBookinngAdapter(List<Ground_object> gList, Context context) {
        this.gList = gList;
        this.context = context;
    }

    @NonNull
    @Override
    public GroundBookinngAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pending_bookings, parent, false);
        return new GroundBookinngAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroundBookinngAdapter.MyViewHolder holder, int position) {
        final Ground_object groundObject = gList.get(position);
        String timeDeatils = groundObject.startTime + " - " + groundObject.endTime;
        holder.aName.setText(groundObject.getGroundName());
        holder.tvTime.setText(timeDeatils);
        holder.tvDate.setText(groundObject.eventName);

        /*
        final View.OnClickListener vL = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent to confirmation page
                booking_confirmation.appliedObject = groundObject;
                Intent intent = new Intent(context,booking_confirmation.class);
                context.startActivity(intent);
            }
        };
        */
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("onclick","before");
                admin_booking_detail.appliedObject = groundObject;
                Intent intent = new Intent(context,admin_booking_detail.class);
                context.startActivity(intent);
                Log.e("onclick","after");
            }
        });
    }

    @Override
    public int getItemCount() {
        return gList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView aName;
        TextView tvTime;
        TextView tvDate;
        Button button;

        public MyViewHolder(View itemView) {
            super(itemView);
            aName = itemView.findViewById(R.id.tv_pb_name);
            tvTime = itemView.findViewById(R.id.tv_pb_time);
            tvDate = itemView.findViewById(R.id.tv_pb_date);
            button = itemView.findViewById(R.id.b_click);
        }
    }

    static String standardtime(int ms){
        String s="";
        String isPm="AM";
        if(ms>12*60) isPm="PM";
        ms=(ms%(12*60));
        int sh=ms/60;
        int sm=ms%60;
        if(sh==0) sh=12;

        s=sh+":"+sm+" "+isPm;
        s="";
        if(sh<10) s+="0"+sh+":";
        else s+=sh+":";
        if(sm<10) s+="0"+sm;
        else s+=sm;
        s+=" "+isPm;
        return s;
    }
}