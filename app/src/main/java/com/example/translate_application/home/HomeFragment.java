package com.example.translate_application.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


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

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {
    int vitri=-1;
    boolean check =false;
    TextView textView,editText;
    SharedPreferences Mywords;
    String KeyWord;
    SharedPreferences MyAccount;
    private HomeViewModel homeViewModel;

    TextView camera;
    CustomAdapter Adapter;
    public static DatabaseHelper databaseHelper;
    ArrayList<TuVung> arrayList;
    ListView listView;
    int index = 1;



    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        databaseHelper = new DatabaseHelper(getActivity(),"Translate.sqlite",null,1);
        databaseHelper.QueryData("CREATE TABLE IF NOT EXISTS TuVung(Id INTEGER PRIMARY KEY AUTOINCREMENT, TuCanDich VARCHAR(150),BanDich VARCHAR(250))");
        databaseHelper.QueryData("CREATE TABLE IF NOT EXISTS SaveWordBook(Id INTEGER PRIMARY KEY AUTOINCREMENT, LuuTuVung VARCHAR(150),LuuBanDich VARCHAR(250))");
        arrayList = new ArrayList<>();
        listView = root.findViewById(R.id.historylist);

        final String TAG = "MainActivity";
        editText = root.findViewById(R.id.editText);
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
                        Language.AUTO_DETECT,
                        Language.VIETNAMESE, editText.getText().toString());

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



                }

                }


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
                    index = i;

                final String tuvung = arrayList.get(index).getTuCanDich();
                final String bandich = arrayList.get(index).getBanDich();
                    databaseHelper.QueryData("INSERT Into SaveWordBook Values (NULL, '"+ tuvung + "','"+ bandich+"') ");




      }

        }

        );

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                index = i;
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

                popupMenu.show();
                return false;
            }
        });
        getListView();
        return root;
    }
    //listview tu vung
    public void getListView(){
        Cursor cursor = databaseHelper.GetData("SELECT * FROM TuVung ORDER BY Id DESC ");
        if (cursor != null) {
            while (cursor.moveToNext()){
                arrayList.add(new TuVung(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2)

                ));
            }
        }
        Adapter =  new CustomAdapter(getActivity(),R.layout.listtuvung_dont, arrayList);
        listView.setAdapter(Adapter);
        Adapter.notifyDataSetChanged();
    Log.d("don","ad");

    }
    public void DialogXoa(final int id){
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
    }
    public void DialogThem(){
//        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(getActivity());

    }
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
