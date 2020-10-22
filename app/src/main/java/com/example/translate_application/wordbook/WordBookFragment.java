package com.example.translate_application.wordbook;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.example.translate_application.CustomAdapter;
import com.example.translate_application.DatabaseHelper;
import com.example.translate_application.R;
import com.example.translate_application.SaveWordBook;
import com.example.translate_application.TuVung;
import com.example.translate_application.WordBookAdapter;

import java.util.ArrayList;


public class WordBookFragment extends Fragment {
ListView listView;
ArrayList<SaveWordBook> arrayList;
WordBookAdapter adapter;
public  static DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_wordbook, container, false);
        databaseHelper = new DatabaseHelper(getActivity(),"Translate.sqlite",null,1);
        databaseHelper.QueryData("CREATE TABLE IF NOT EXISTS TuVung(Id INTEGER PRIMARY KEY AUTOINCREMENT, TuCanDich VARCHAR(150),BanDich VARCHAR(250))");
        databaseHelper.QueryData("CREATE TABLE IF NOT EXISTS SaveWordBook(Id INTEGER PRIMARY KEY AUTOINCREMENT, LuuTuVung VARCHAR(150),LuuBanDich VARCHAR(250))");

        listView =  root.findViewById(R.id.lvWordBook);
         arrayList = new ArrayList<>();
        adapter =  new WordBookAdapter(getActivity(),R.layout.listtuvung_dont, arrayList);
        listView.setAdapter(adapter);


        getListView();
        return root;
    }
    public void getListView(){
        Cursor cursor = databaseHelper.GetData("SELECT * FROM SaveWordBook ");
        if (cursor != null) {


            while (cursor.moveToNext()){
                Toast.makeText(getActivity(), "sfgg", Toast.LENGTH_SHORT).show();

                arrayList.add(new SaveWordBook(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2)

                ));
            }
        }



        adapter.notifyDataSetChanged();
        Log.d("don","ad");

    }
}
