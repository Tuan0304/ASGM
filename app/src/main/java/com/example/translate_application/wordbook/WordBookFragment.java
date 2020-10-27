package com.example.translate_application.wordbook;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
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

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.translate_application.CustomAdapter;
import com.example.translate_application.DatabaseHelper;
import com.example.translate_application.R;
import com.example.translate_application.SaveWordBook;
import com.example.translate_application.TuVung;
import com.example.translate_application.WordBookAdapter;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class WordBookFragment extends Fragment {
SwipeMenuListView listView;
ArrayList<SaveWordBook> arrayList;
WordBookAdapter adapter;
    SharedPreferences MyAccount;
public  static DatabaseHelper databaseHelper;
int index=1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_wordbook, container, false);
        //database
        databaseHelper = new DatabaseHelper(getActivity(),"Translate2.sqlite",null,1);
        databaseHelper.QueryData("CREATE TABLE IF NOT EXISTS TuVung(Id INTEGER PRIMARY KEY AUTOINCREMENT, TuCanDich VARCHAR(150),BanDich VARCHAR(250),TaiKhoan VARCHAR(50))");
        databaseHelper.QueryData("CREATE TABLE IF NOT EXISTS SaveWordBook(Id INTEGER PRIMARY KEY AUTOINCREMENT, LuuTuVung VARCHAR(150),LuuBanDich VARCHAR(250),TaiKhoan VARCHAR(50))");
        //end database
        //ánh xạ
        listView =  root.findViewById(R.id.lvWordBook);
        // end ánh xạ

        //khai báo arraylist
         arrayList = new ArrayList<>();
        adapter =  new WordBookAdapter(getActivity(),R.layout.listtuvung_dont, arrayList);
        listView.setAdapter(adapter);
        //end arraylist

        // swipe listview
        listView.setSwipeDirection(SwipeMenuListView.DIRECTION_RIGHT);

        listView.setCloseInterpolator(new BounceInterpolator());
        listView.setOpenInterpolator(new AccelerateDecelerateInterpolator());

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                    getContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(250);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_dont_close);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        listView.setMenuCreator(creator);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int i, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        index = i;
                        databaseHelper.QueryData("Delete from SaveWordBook where Id = '" + arrayList.get(index).IdTuVung + "'");
                        getListView();
                        break;
                    case 1:
                        // delete
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        listView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {
            @Override
            public void onSwipeStart(int position) {

            }

            @Override
            public void onSwipeEnd(int position) {
                listView.smoothOpenMenu(position);
                Toast.makeText(getActivity(), "delete", Toast.LENGTH_SHORT).show();
            }
        });


        getListView();
        return root;
    }
    //end swipe liset view

    //xổ listview từ vựng đã lưu
    public void getListView(){
        //local key
        MyAccount=getContext().getSharedPreferences("CusACCC",MODE_PRIVATE);
        String tentaikhoan=MyAccount.getString("id","");
        //end local key


        Cursor cursor = databaseHelper.GetData("SELECT * FROM SaveWordBook where Taikhoan='"+tentaikhoan+"' ORDER BY Id DESC");
        arrayList.clear();
        if (cursor != null) {
            while (cursor.moveToNext()){
                arrayList.add(new SaveWordBook(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2)

                ));
            }
        }
        adapter.notifyDataSetChanged();


    }
    //end xổ listview
}
