package com.example.translate_application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import java.util.List;

public class TuVungAdapter extends BaseAdapter {
    Context context;
    int mylayout;

    public TuVungAdapter(Context context, int mylayout, List<TuVungCT> IDList) {
        this.context = context;
        this.mylayout = mylayout;
        this.IDList=IDList;
    }
    List<TuVungCT> IDList;
    @Override
    public int getCount() {
        return IDList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(mylayout,null);

       CheckedTextView Words=view.findViewById(R.id.item1);

        Words.setText(IDList.get(i).Words);
        return view;
    }
}
