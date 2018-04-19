package com.example.omi.du_crs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asif.du_crs.MainActivity;
import com.example.asif.du_crs.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by aniomi on 4/19/18.
 */

public class GridDepAdapter extends ArrayAdapter {
    ArrayList<String> choices=new ArrayList<>();

    public GridDepAdapter(Context context, int textViewResourceId, ArrayList<String> objects) {
        super(context, textViewResourceId, objects);
        choices = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.optionss, null);
        TextView t1=v.findViewById(R.id.t1);
        TextView t2=v.findViewById(R.id.t2);
        String s=""+choices.get(position).charAt(0);
        t1.setText(s);
        s=""+choices.get(position);
        t2.setText(s);
        return v;
    }
}
