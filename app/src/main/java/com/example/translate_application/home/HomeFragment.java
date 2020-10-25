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
        final String TAG = "MainActivity";
        //database
        databaseHelper = new DatabaseHelper(getActivity(),"Translate2.sqlite",null,1);
        databaseHelper.QueryData("CREATE TABLE IF NOT EXISTS TuVung(Id INTEGER PRIMARY KEY AUTOINCREMENT, TuCanDich VARCHAR(150),BanDich VARCHAR(250),TaiKhoan VARCHAR(50))");
        databaseHelper.QueryData("CREATE TABLE IF NOT EXISTS SaveWordBook(Id INTEGER PRIMARY KEY AUTOINCREMENT, LuuTuVung VARCHAR(150),LuuBanDich VARCHAR(250),TaiKhoan VARCHAR(50))");
        //end database
        editText = root.findViewById(R.id.editText);
        //ánh xạ
        editText = root.findViewById(R.id.editText);
        textView = root.findViewById(R.id.result);
        ImageView translateButton = root.findViewById(R.id.resultbtn);
        final ImageView voicebtn=root.findViewById(R.id.voice);
        camera = root.findViewById(R.id.Camera);
        final Spinner spinnerIn = (Spinner) root.findViewById(R.id.spinnerin);
        final Spinner spinnerOut = (Spinner) root.findViewById(R.id.spinnerout);
        //end ánh xạ

        //khai báo arraylist
        arrayList = new ArrayList<>();
        listView = root.findViewById(R.id.historylist);
        Adapter =  new CustomAdapter(getActivity(),R.layout.listtuvung_dont, arrayList);
        listView.setAdapter(Adapter);
        //end arraylist

        //local key
        MyAccount=getContext().getSharedPreferences("CusACCC",MODE_PRIVATE);
        tentaikhoan=MyAccount.getString("id","");
        //end local key

        //hàm onclick dịch
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),ThemActivity.class));
            }
        });
        //end hàm onclick dịch

        //khai báo Spinner

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("AUTO DETECT");
        categories.add("AFRIKAANS");
        categories.add("ALBANIAN");
        categories.add("ARABIC");
        categories.add("ARMENIAN");
        categories.add("AZERBAIJANI");
        categories.add("BASQUE");
        categories.add("BELARUSIAN");
        categories.add("BENGALI");
        categories.add("BULGARIAN");
        categories.add("CATALAN");
        categories.add("CHINESE");
        categories.add("CROATIAN");
        categories.add("CZECH");
        categories.add("DANISH");
        categories.add("DUTCH");
        categories.add("ENGLISH");
        categories.add("ESTONIAN");
        categories.add("FILIPINO");
        categories.add("FINNISH");
        categories.add("FRENCH");
        categories.add("GALICIAN");
        categories.add("GEORGIAN");
        categories.add("GERMAN");
        categories.add("GREEK");
        categories.add("GUJARATI");
        categories.add("HAITIAN_CREOLE");
        categories.add("HEBREW");
        categories.add("HINDI");
        categories.add("HUNGARIAN");
        categories.add("ICELANDIC");
        categories.add("INDONESIAN");
        categories.add("IRISH");
        categories.add("ITALIAN");
        categories.add("JAPANESE");
        categories.add("KANNADA");
        categories.add("KOREAN");
        categories.add("LATIN");
        categories.add("LATVIAN");
        categories.add("LITHUANIAN");
        categories.add("MACEDONIAN");
        categories.add("MALAY");
        categories.add("MALTESE");
        categories.add("NORWEGIAN");
        categories.add("PERSIAN");
        categories.add("POLISH");
        categories.add("PORTUGUESE");
        categories.add("ROMANIAN");
        categories.add("RUSSIAN");
        categories.add("SERBIAN");
        categories.add("SLOVAK");
        categories.add("SLOVENIAN");
        categories.add("SPANISH");
        categories.add("SWAHILI");
        categories.add("SWEDISH");
        categories.add("TAMIL");
        categories.add("TELUGU");
        categories.add("THAI");
        categories.add("TURKISH");
        categories.add("UKRAINIAN");
        categories.add("URDU");
        categories.add("VIETNAMESE");
        categories.add("WELSH");
        categories.add("YIDDISH");
        categories.add("CHINESE_SIMPLIFIED");
        categories.add("CHINESE_TRADITIONAL");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerIn.setAdapter(dataAdapter);
        spinnerOut.setAdapter(dataAdapter);

        // Spinner click listener
        spinnerIn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                String item = parent.getItemAtPosition(position).toString();
                switch (position) {
                    case 0:
                        strIn="auto";
                        break;
                    case 1:
                        strIn="af";
                        break;
                    case 2:
                        strIn="sq";
                        break;
                    case 3:
                        strIn="ar";
                        break;
                    case 4:
                        strIn="hy";
                        break;
                    case 5:
                        strIn="az";
                        break;
                    case 6:
                        strIn="eu";
                        break;
                    case 7:
                        strIn="be";
                        break;
                    case 8:
                        strIn="bn";
                        break;
                    case 9:
                        strIn="bg";
                        break;
                    case 10:
                        strIn="ca";
                        break;
                    case 11:
                        strIn="zh-CN";
                        break;
                    case 12:
                        strIn="hr";
                        break;
                    case 13:
                        strIn="cs";
                        break;
                    case 14:
                        strIn="da";
                        break;
                    case 15:
                        strIn="nl";
                        break;
                    case 16:
                        strIn="en";
                        break;
                    case 17:
                        strIn="et";
                        break;
                    case 18:
                        strIn="tl";
                        break;
                    case 19:
                        strIn="fi";
                        break;
                    case 20:
                        strIn="fr";
                        break;
                    case 21:
                        strIn="gl";
                        break;
                    case 22:
                        strIn="ka";
                        break;
                    case 23:
                        strIn="de";
                        break;
                    case 24:
                        strIn="el";
                        break;
                    case 25:
                        strIn="gu";
                        break;
                    case 26:
                        strIn="ht";
                        break;
                    case 27:
                        strIn="iw";
                        break;
                    case 28:
                        strIn="hi";
                        break;
                    case 29:
                        strIn="hu";
                        break;
                    case 30:
                        strIn="is";
                        break;
                    case 31:
                        strIn="id";
                        break;
                    case 32:
                        strIn="ga";
                        break;
                    case 33:
                        strIn="it";
                        break;
                    case 34:
                        strIn="ja";
                        break;
                    case 35:
                        strIn="kn";
                        break;
                    case 36:
                        strIn="ko";
                        break;
                    case 37:
                        strIn="la";
                        break;
                    case 38:
                        strIn="lv";
                        break;
                    case 39:
                        strIn="lt";
                        break;
                    case 40:
                        strIn="mk";
                        break;
                    case 41:
                        strIn="ms";
                        break;
                    case 42:
                        strIn="mt";
                        break;
                    case 43:
                        strIn="no";
                        break;
                    case 44:
                        strIn="fa";
                        break;
                    case 45:
                        strIn="pl";
                        break;
                    case 46:
                        strIn="pt";
                        break;
                    case 47:
                        strIn="ro";
                        break;
                    case 48:
                        strIn="ru";
                        break;
                    case 49:
                        strIn="sr";
                        break;
                    case 50:
                        strIn="sk";
                        break;
                    case 51:
                        strIn="sl";
                        break;
                    case 52:
                        strIn="es";
                        break;
                    case 53:
                        strIn="sw";
                        break;
                    case 54:
                        strIn="sv";
                        break;
                    case 55:
                        strIn="ta";
                        break;
                    case 56:
                        strIn="te";
                        break;
                    case 57:
                        strIn="th";
                        break;
                    case 58:
                        strIn="tr";
                        break;
                    case 59:
                        strIn="uk";
                        break;
                    case 60:
                        strIn="ur";
                        break;
                    case 61:
                        strIn="vi";
                        break;
                    case 62:
                        strIn="cy";
                        break;
                    case 64:
                        strIn="yi";
                        break;
                    case 65:
                        strIn="zh-CN";
                        break;
                    case 66:
                        strIn="zh-TW";
                        break;



                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerOut.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                String item = parent.getItemAtPosition(position).toString();
                strOut=item;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //end khai báo spinner

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

        getListView();
        return root;

    }
    //end oncreateview


    //hàm xổ listview Lich Sử
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
    }
    //end hàm xổ listview Lịch Sử

}
