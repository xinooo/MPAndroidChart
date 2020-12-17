package com.example.MPAndroidChart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.List;

public class MySpinnerAdapter extends BaseAdapter implements SpinnerAdapter {
    private Context mcontext;
    private List<String> List;
    private int select;

    public MySpinnerAdapter(Context context, List<String> list) {
        this.mcontext = context;
        this.List = list;
    }
    @Override
    public int getCount() {
        return List.size();
    }

    @Override
    public Object getItem(int i) {
        return List.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void setSelect(int i) {
        this.select = i;
        notifyDataSetChanged();
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(mcontext).inflate(R.layout.style_spinner_25, null);
        TextView tv = (TextView) view.findViewById(R.id.textview);
        tv.setText(List.get(i));
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mcontext).inflate(R.layout.style_spinner_25, null);
        TextView tv = (TextView) convertView.findViewById(R.id.textview);
        tv.setText(List.get(position));
        if (select == position){
            tv.setBackgroundColor(Color.parseColor("#55ffff"));
        }
        return convertView;
    }
}
