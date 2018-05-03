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
import com.example.omi.du_crs.FunctionList;

import java.util.ArrayList;
import java.util.List;

public class PendingBookingsAdapter extends RecyclerView.Adapter<PendingBookingsAdapter.MyViewHolder> {
    List<AuditorioumDetails> gList = new ArrayList<>();
    Context context;

    public PendingBookingsAdapter(List<AuditorioumDetails> gList, Context context) {
        this.gList = gList;
        this.context = context;
    }

    @NonNull
    @Override
    public PendingBookingsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pending_bookings, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingBookingsAdapter.MyViewHolder holder, int position) {
        final AuditorioumDetails groundObject = gList.get(position);
        String timeDeatils = standardtime(groundObject.getStartt()) + " - " + standardtime(groundObject.getEndt());
        holder.aName.setText(groundObject.getVenue());
        holder.tvTime.setText(timeDeatils);
        holder.tvDate.setText(groundObject.getRdate());

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
                booking_confirmation.appliedObject = groundObject;
                Intent intent = new Intent(context,booking_confirmation.class);
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
