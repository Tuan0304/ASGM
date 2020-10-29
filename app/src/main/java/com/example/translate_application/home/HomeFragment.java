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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
    TextView textView,switchLang;
    SharedPreferences Mywords;
    String KeyWord,strIn,strOut,tentaikhoan;
    SharedPreferences MyAccount;
    EditText editText;
    TextView camera;
    CustomAdapter Adapter;
    public static DatabaseHelper databaseHelper;
    ArrayList<TuVung> arrayList;
    SwipeMenuListView listView;
    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        final String TAG = "MainActivity";
        //database
        databaseHelper = new DatabaseHelper(getActivity(),"Translate3.sqlite",null,1);
        databaseHelper.QueryData("CREATE TABLE IF NOT EXISTS TuVung(Id INTEGER PRIMARY KEY AUTOINCREMENT, TuCanDich VARCHAR(500),BanDich VARCHAR(5000),TaiKhoan VARCHAR(50))");
        databaseHelper.QueryData("CREATE TABLE IF NOT EXISTS SaveWordBook(Id INTEGER PRIMARY KEY AUTOINCREMENT, LuuTuVung VARCHAR(500),LuuBanDich VARCHAR(500),TaiKhoan VARCHAR(50))");
        //end database

        //local key
        MyAccount=getContext().getSharedPreferences("CusACCC",MODE_PRIVATE);
        tentaikhoan=MyAccount.getString("id","");
        //end local key



        //ánh xạ
         strIn="auto";
         strOut="vi";
         switchLang=root.findViewById(R.id.switchLang);
        editText = root.findViewById(R.id.editText);
        textView = root.findViewById(R.id.result);
        ImageView translateButton = root.findViewById(R.id.resultbtn);
        ImageView eraseButton = root.findViewById(R.id.erase);
        final ImageView voicebtn=root.findViewById(R.id.voice);
        camera = root.findViewById(R.id.Camera);
        listView = root.findViewById(R.id.historylist);
        final Spinner spinnerIn = (Spinner) root.findViewById(R.id.spinnerin);
        final Spinner spinnerOut = (Spinner) root.findViewById(R.id.spinnerout);
        //end ánh xạ

        //khai báo arraylist
        arrayList = new ArrayList<>();
        Adapter =  new CustomAdapter(getActivity(),R.layout.listtuvung_dont, arrayList);
        listView.setAdapter(Adapter);
        //end arraylist

      /*  //local key
        MyAccount=getContext().getSharedPreferences("CusACCC",MODE_PRIVATE);
        tentaikhoan=MyAccount.getString("id","");
        //end local key*/

        //hàm onclick dịch
      /* editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),ThemActivity.class));
            }
        });*/
        //end hàm onclick dịch

        //khai báo Spinner

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();categories.add("AUTO DETECT");categories.add("AFRIKAANS");categories.add("ALBANIAN");categories.add("ARABIC");categories.add("ARMENIAN");categories.add("AZERBAIJANI");categories.add("BASQUE");categories.add("BELARUSIAN");categories.add("BENGALI");categories.add("BULGARIAN");categories.add("CATALAN");categories.add("CHINESE");categories.add("CROATIAN");categories.add("CZECH");categories.add("DANISH");categories.add("DUTCH");categories.add("ENGLISH");categories.add("ESTONIAN");categories.add("FILIPINO");categories.add("FINNISH");categories.add("FRENCH");categories.add("GALICIAN");categories.add("GEORGIAN");categories.add("GERMAN");categories.add("GREEK");categories.add("GUJARATI");categories.add("HAITIAN_CREOLE");categories.add("HEBREW");categories.add("HINDI");categories.add("HUNGARIAN");categories.add("ICELANDIC");categories.add("INDONESIAN");categories.add("IRISH");categories.add("ITALIAN");categories.add("JAPANESE");categories.add("KANNADA");categories.add("KOREAN");categories.add("LATIN");categories.add("LATVIAN");categories.add("LITHUANIAN");categories.add("MACEDONIAN");categories.add("MALAY");categories.add("MALTESE");categories.add("NORWEGIAN");categories.add("PERSIAN");categories.add("POLISH");categories.add("PORTUGUESE");categories.add("ROMANIAN");categories.add("RUSSIAN");categories.add("SERBIAN");categories.add("SLOVAK");categories.add("SLOVENIAN");categories.add("SPANISH");categories.add("SWAHILI");categories.add("SWEDISH");categories.add("TAMIL");categories.add("TELUGU");categories.add("THAI");categories.add("TURKISH");categories.add("UKRAINIAN");categories.add("URDU");categories.add("VIETNAMESE");categories.add("WELSH");categories.add("YIDDISH");categories.add("CHINESE_SIMPLIFIED");categories.add("CHINESE_TRADITIONAL");
        List<String> categories2 = new ArrayList<String>();categories2.add("VIETNAMESE");categories2.add("AFRIKAANS");categories2.add("ALBANIAN");categories2.add("ARABIC");categories2.add("ARMENIAN");categories2.add("AZERBAIJANI");categories2.add("BASQUE");categories2.add("BELARUSIAN");categories2.add("BENGALI");categories2.add("BULGARIAN");categories2.add("CATALAN");categories2.add("CHINESE");categories2.add("CROATIAN");categories2.add("CZECH");categories2.add("DANISH");categories2.add("DUTCH");categories2.add("ENGLISH");categories2.add("ESTONIAN");categories2.add("FILIPINO");categories2.add("FINNISH");categories2.add("FRENCH");categories2.add("GALICIAN");categories2.add("GEORGIAN");categories2.add("GERMAN");categories2.add("GREEK");categories2.add("GUJARATI");categories2.add("HAITIAN_CREOLE");categories2.add("HEBREW");categories2.add("HINDI");categories2.add("HUNGARIAN");categories2.add("ICELANDIC");categories2.add("INDONESIAN");categories2.add("IRISH");categories2.add("ITALIAN");categories2.add("JAPANESE");categories2.add("KANNADA");categories2.add("KOREAN");categories2.add("LATIN");categories2.add("LATVIAN");categories2.add("LITHUANIAN");categories2.add("MACEDONIAN");categories2.add("MALAY");categories2.add("MALTESE");categories2.add("NORWEGIAN");categories2.add("PERSIAN");categories2.add("POLISH");categories2.add("PORTUGUESE");categories2.add("ROMANIAN");categories2.add("RUSSIAN");categories2.add("SERBIAN");categories2.add("SLOVAK");categories2.add("SLOVENIAN");categories2.add("SPANISH");categories2.add("SWAHILI");categories2.add("SWEDISH");categories2.add("TAMIL");categories2.add("TELUGU");categories2.add("THAI");categories2.add("TURKISH");categories2.add("UKRAINIAN");categories2.add("URDU");categories2.add("VIETNAMESE");categories2.add("WELSH");categories2.add("YIDDISH");categories2.add("CHINESE_SIMPLIFIED");categories2.add("CHINESE_TRADITIONAL");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text, categories);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text, categories2);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        dataAdapter2.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        // attaching data adapter to spinner
        spinnerIn.setAdapter(dataAdapter);
        spinnerOut.setAdapter(dataAdapter2);

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
                switch (position) {
                    case 0:
                        strOut = "vi";
                        break;
                    case 1:
                        strOut = "af";
                        break;
                    case 2:
                        strOut = "sq";
                        break;
                    case 3:
                        strOut = "ar";
                        break;
                    case 4:
                        strOut = "hy";
                        break;
                    case 5:
                        strOut = "az";
                        break;
                    case 6:
                        strOut = "eu";
                        break;
                    case 7:
                        strOut = "be";
                        break;
                    case 8:
                        strOut = "bn";
                        break;
                    case 9:
                        strOut = "bg";
                        break;
                    case 10:
                        strOut= "ca";
                        break;
                    case 11:
                        strOut= "zh-CN";
                        break;
                    case 12:
                        strOut= "hr";
                        break;
                    case 13:
                        strOut= "cs";
                        break;
                    case 14:
                        strOut= "da";
                        break;
                    case 15:
                        strOut= "nl";
                        break;
                    case 16:
                        strOut= "en";
                        break;
                    case 17:
                        strOut= "et";
                        break;
                    case 18:
                        strOut= "tl";
                        break;
                    case 19:
                        strOut= "fi";
                        break;
                    case 20:
                        strOut= "fr";
                        break;
                    case 21:
                        strOut= "gl";
                        break;
                    case 22:
                        strOut= "ka";
                        break;
                    case 23:
                        strOut= "de";
                        break;
                    case 24:
                        strOut= "el";
                        break;
                    case 25:
                        strOut= "gu";
                        break;
                    case 26:
                        strOut= "ht";
                        break;
                    case 27:
                        strOut= "iw";
                        break;
                    case 28:
                        strOut= "hi";
                        break;
                    case 29:
                        strOut= "hu";
                        break;
                    case 30:
                        strOut= "is";
                        break;
                    case 31:
                        strOut= "id";
                        break;
                    case 32:
                        strOut= "ga";
                        break;
                    case 33:
                        strOut= "it";
                        break;
                    case 34:
                        strOut= "ja";
                        break;
                    case 35:
                        strOut= "kn";
                        break;
                    case 36:
                        strOut= "ko";
                        break;
                    case 37:
                        strOut= "la";
                        break;
                    case 38:
                        strOut= "lv";
                        break;
                    case 39:
                        strOut= "lt";
                        break;
                    case 40:
                        strOut= "mk";
                        break;
                    case 41:
                        strOut= "ms";
                        break;
                    case 42:
                        strOut= "mt";
                        break;
                    case 43:
                        strOut= "no";
                        break;
                    case 44:
                        strOut= "fa";
                        break;
                    case 45:
                        strOut= "pl";
                        break;
                    case 46:
                        strOut= "pt";
                        break;
                    case 47:
                        strOut= "ro";
                        break;
                    case 48:
                        strOut= "ru";
                        break;
                    case 49:
                        strOut= "sr";
                        break;
                    case 50:
                        strOut= "sk";
                        break;
                    case 51:
                        strOut= "sl";
                        break;
                    case 52:
                        strOut= "es";
                        break;
                    case 53:
                        strOut= "sw";
                        break;
                    case 54:
                        strOut= "sv";
                        break;
                    case 55:
                        strOut= "ta";
                        break;
                    case 56:
                        strOut= "te";
                        break;
                    case 57:
                        strOut= "th";
                        break;
                    case 58:
                        strOut= "tr";
                        break;
                    case 59:
                        strOut= "uk";
                        break;
                    case 60:
                        strOut= "ur";
                        break;
                    case 61:
                        strOut= "vi2";
                        break;
                    case 62:
                        strOut= "cy";
                        break;
                    case 64:
                        strOut= "yi";
                        break;
                    case 65:
                        strOut= "zh-CN";
                        break;
                    case 66:
                        strOut= "zh-TW";
                        break;
                }
                }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //end khai báo spinner

        // swipe trên listview
        listView.setSwipeDirection(SwipeMenuListView.DIRECTION_RIGHT);
        listView.setCloseInterpolator(new BounceInterpolator());
        listView.setOpenInterpolator(new AccelerateDecelerateInterpolator());

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
                openItem.setWidth(400);
                // set item title
                //openItem.setTitle("Thêm");
                // set item title fontsize
               // openItem.setTitleSize(18);
                // set item title font color
               // openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

               /* // create "delete" item
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
                menu.addMenuItem(deleteItem);*/
            }
        };
        //end swipe listview

// thao tac trên list view
        listView.setMenuCreator(creator);
        listView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {
            @Override
            public void onSwipeStart(int position) {
                final String tuvung = arrayList.get(position).getTuCanDich();
                final String bandich = arrayList.get(position).getBanDich();
                databaseHelper.QueryData("INSERT Into SaveWordBook Values (NULL, '"+ tuvung + "','"+ bandich+"','"+ tentaikhoan +"') ");
                databaseHelper.QueryData("Delete from TuVung where Id = '" + arrayList.get(position).Id + "'");
            }

            @Override
            public void onSwipeEnd(int position) {
                getListView();
            }
        });
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

        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.length()>0) {
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
                        });
                        if(textView.length()>0){
                            databaseHelper.Uploaddata("insert into TuVung values(null,'" + editText.getText().toString() +  "','" + textView.getText().toString() +  "','"+ tentaikhoan +"')");
                        }


                getListView();
                }else {
                    Toast.makeText(getActivity(), "Không được để trống", Toast.LENGTH_SHORT).show();
                }
            }
        });

        eraseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              editText.setText("");
              textView.setText("");
                Mywords=getContext().getSharedPreferences("words",MODE_PRIVATE);
                SharedPreferences.Editor myeditor = Mywords.edit();
                myeditor.remove("kw");
                myeditor.commit();
            }
        });



//Tuan-Chuc năng dich chữ:
    /*  editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String putString=strIn;
                String putStringout=strOut;
                Intent i=new Intent(getActivity(),ThemActivity.class);
                i.putExtra("langin",putString);
                i.putExtra("langout",putStringout);
                startActivity(i);

            }
        });*/

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                /*if(editText.getText().toString().equals("")){
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
                    }); }*/

                }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });


//end Chức năng dịch chữ
        getListView();
        return root;
    }
    //end oncreateview
    @Override
    public void onResume() {
        Mywords=getContext().getSharedPreferences("words",MODE_PRIVATE);
        KeyWord=Mywords.getString("kw","");
        editText.setText(KeyWord);
        textView.setText("");
        getListView();
        super.onResume();
    }


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
