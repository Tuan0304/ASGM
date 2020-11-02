package com.example.translate_application.lichsu;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.translate_application.R;
import com.example.translate_application.lichsu.TuVung;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class CustomAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<TuVung> tenList;

    public CustomAdapter(Context context, int layout, ArrayList<TuVung> tenList) {
        this.context = context;
        this.layout = layout;
        this.tenList = tenList;
    }

    @Override
    public int getCount() {
        return tenList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    public class ViewHolder{
        TextView txttuCanDich,TxtBanDich;
        ImageView imgcheck;

    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txttuCanDich =(TextView) view.findViewById(R.id.upText);
            holder.TxtBanDich =(TextView) view.findViewById(R.id.downText);
            holder.imgcheck =(ImageView) view.findViewById(R.id.check);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        TuVung tuVung = tenList.get(i);
        holder.txttuCanDich.setText(tuVung.getTuCanDich());
        holder.TxtBanDich.setText(tuVung.getBanDich());
        Log.d(TAG, "getView: aa");

        return view;
    }
}
