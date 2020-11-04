package com.example.translate_application.tuvung;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.translate_application.R;
import com.example.translate_application.tuvung.SaveWordBook;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class WordBookAdapter extends BaseAdapter {
    private Context context;
    private int layout;


    public WordBookAdapter(Context context, int layout, ArrayList<SaveWordBook> List) {
        this.context = context;
        this.layout = layout;
        this.List = List;
    }
    List<SaveWordBook> List;

    @Override
    public int getCount() {
        return List.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public class Holder{
        TextView txtLuutuCanDich,TxtLuuBanDich;
        ImageView imguncheck;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
     Holder holder;
        if (view == null){
            holder = new Holder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtLuutuCanDich =(TextView) view.findViewById(R.id.upText);
            holder.TxtLuuBanDich =(TextView) view.findViewById(R.id.downText);
            holder.imguncheck =(ImageView) view.findViewById(R.id.check);
            view.setTag(holder);
        }else {
            holder = (Holder) view.getTag();
        }
        SaveWordBook saveWordBook = List.get(i);
        holder.txtLuutuCanDich.setText(saveWordBook.getLuuTuVung());
        holder.TxtLuuBanDich.setText(saveWordBook.getLuuBanDich());
        Log.d(TAG, "getView: aa");
        holder.imguncheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        return view;
    }


}
