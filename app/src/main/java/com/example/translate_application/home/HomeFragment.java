package com.example.translate_application.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.BounceInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.translate_application.CameraScan;
import com.example.translate_application.CustomAdapter;
import com.example.translate_application.DatabaseHelper;
import com.example.translate_application.Language;
import com.example.translate_application.R;
import com.example.translate_application.ThemActivity;
import com.example.translate_application.TranslateAPI;
import com.example.translate_application.TuVung;
import com.example.translate_application.VoiceActivity;


import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {
    boolean check =false;
    TextView textView,editText;
    SharedPreferences Mywords;
    String KeyWord,strIn,strOut,tentaikhoan;
    SharedPreferences MyAccount;
    private HomeViewModel homeViewModel;

    TextView camera;
    CustomAdapter Adapter;
    public static DatabaseHelper databaseHelper;
    ArrayList<TuVung> arrayList;
    SwipeMenuListView listView;
    int index = 1;



    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        databaseHelper = new DatabaseHelper(getActivity(),"Translate2.sqlite",null,1);
        databaseHelper.QueryData("CREATE TABLE IF NOT EXISTS TuVung(Id INTEGER PRIMARY KEY AUTOINCREMENT, TuCanDich VARCHAR(150),BanDich VARCHAR(250),TaiKhoan VARCHAR(50))");
        databaseHelper.QueryData("CREATE TABLE IF NOT EXISTS SaveWordBook(Id INTEGER PRIMARY KEY AUTOINCREMENT, LuuTuVung VARCHAR(150),LuuBanDich VARCHAR(250),TaiKhoan VARCHAR(50))");
        arrayList = new ArrayList<>();
        listView = root.findViewById(R.id.historylist);

        final String TAG = "MainActivity";
        editText = root.findViewById(R.id.editText);

        Adapter =  new CustomAdapter(getActivity(),R.layout.listtuvung_dont, arrayList);
        listView.setAdapter(Adapter);

        //local key
        MyAccount=getContext().getSharedPreferences("CusACCC",MODE_PRIVATE);
        tentaikhoan=MyAccount.getString("id","");
        //end local key

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),ThemActivity.class));
            }
        });


        editText = root.findViewById(R.id.editText);
       textView = root.findViewById(R.id.result);
        ImageView translateButton = root.findViewById(R.id.resultbtn);

        final ImageView voicebtn=root.findViewById(R.id.voice);
        camera = root.findViewById(R.id.Camera);

        final Spinner spinner = (Spinner) root.findViewById(R.id.spinner);


        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Item 1");
        categories.add("Item 2");
        categories.add("Item 3");
        categories.add("Item 4");
        categories.add("Item 5");
        categories.add("Item 6");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        // Spinner click listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                String item = parent.getItemAtPosition(position).toString();

                // Showing selected spinner item
                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





// swipe trên listview
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(200);
                // set item title
                openItem.setTitle("Thêm");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(200);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_dont_close);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        //end swipe listview

// thao tac trên list view
        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int i, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // open
                        index = i;

                        final String tuvung = arrayList.get(index).getTuCanDich();
                        final String bandich = arrayList.get(index).getBanDich();
                        databaseHelper.QueryData("INSERT Into SaveWordBook Values (NULL, '"+ tuvung + "','"+ bandich+"','"+ tentaikhoan +"') ");
                        break;
                    case 1:
                        index = i;
                        Log.d("tag",""+index);
                        databaseHelper.QueryData("Delete from TuVung where Id = '" + arrayList.get(index).Id + "'");
                       getListView();


                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
        listView.setSwipeDirection(SwipeMenuListView.DIRECTION_RIGHT);

        // Close Interpolator
        listView.setCloseInterpolator(new BounceInterpolator());
// Open Interpolator
        //listView.setOpenInterpolator(...);
        //end thao tac trên listview

        //camera
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CameraScan.class));
            }
        });
        //end camera


        //speech to text function
        voicebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), VoiceActivity.class));
            }
        });

        //end-speech to text function


//Tuan-Chuc năng dich chữ:
      editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ThemActivity.class));
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editText.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "null", Toast.LENGTH_SHORT).show();
                }else{


                TranslateAPI translateAPI = new TranslateAPI(
                        strIn,
                        strOut, editText.getText().toString());

                translateAPI.setTranslateListener(new TranslateAPI.TranslateListener() {
                    @Override
                    public void onSuccess(String translatedText) {
                        textView.setText(translatedText);

                    }

                    @Override
                    public void onFailure(String ErrorText) {
                        Log.d(TAG, "onFailure: "+ErrorText);
                    }
                }); } }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        translateButton.setOnClickListener(new View.OnClickListener() {
         @Override
            public void onClick(View v) {

             }
        });
//end Chức năng dịch chữ

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

      }

        }

        );

      listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
              /* index = i;
                final int a = arrayList.get(index).getId();
                final String tucandich = arrayList.get(index).getTuCanDich();
                final String bandich = arrayList.get(index).getBanDich();

                PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.delete:
                                DialogXoa(a);
                                break;

                        }
                        return false;
                    }
                });

                popupMenu.show();*/
                return false;
            }
        });


        getListView();
        return root;
    }//end oncreateview


    //listview tu vung
    public void getListView(){



        Cursor cursor = databaseHelper.GetData("SELECT * FROM TuVung where Taikhoan='"+tentaikhoan+"' ORDER BY Id DESC ");
        arrayList.clear();
        if (cursor != null) {
            while (cursor.moveToNext()){
                arrayList.add(new TuVung(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2)

                ));
            }
        }

        Adapter.notifyDataSetChanged();
    Log.d("don","ad");

    }
 /*   public void DialogXoa(final int id){
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(getActivity());
        dialogXoa.setMessage("Bạn muốn xóa video này? ");
        dialogXoa.setPositiveButton("Đồng Ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                databaseHelper.QueryData("DELETE FROM TuVung WHERE Id = '"+ id +"'");
                arrayList.clear();
                getListView();


            }
        });
        dialogXoa.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        getListView();
        dialogXoa.show();
    }*/
   /* public void DialogThem(){
//        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(getActivity());

    }*/
//        @Override
//        public void onResume() {
//
//            Mywords = getActivity().getApplicationContext().getSharedPreferences("words", MODE_PRIVATE);
//            KeyWord = Mywords.getString("kw", "");
//            editText.setText(KeyWord);
//
//            getListView();
//
//
//            super.onResume();
//        }


}
