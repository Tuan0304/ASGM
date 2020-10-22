package com.example.translate_application.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Checkable;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import com.example.translate_application.CameraScan;
import com.example.translate_application.DatabaseHelper;
import com.example.translate_application.Language;
import com.example.translate_application.R;
import com.example.translate_application.ThemActivity;
import com.example.translate_application.TranslateAPI;
import com.example.translate_application.TuVungAdapter;
import com.example.translate_application.TuVungCT;
import com.example.translate_application.VoiceActivity;

import org.intellij.lang.annotations.PrintFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {
    int vitri=-1;
    boolean check =false;
    ListView list;
    TextView textView,editText;
    ImageView checkstar;
    SharedPreferences Mywords;
    String KeyWord;
    DatabaseHelper databaseHelper;
    SharedPreferences MyAccount;
    ArrayList<TuVungCT> arrayroom;
    TuVungAdapter adapter;

    private HomeViewModel homeViewModel;

    TextView camera;


    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        final String TAG = "MainActivity";

        arrayroom = new ArrayList<>();
        adapter = new TuVungAdapter(getActivity(),R.layout.lslv, arrayroom);
        list = root.findViewById(R.id.hystorylist);

        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        list.setAdapter(adapter);
      //  checkstar= root.findViewById(R.id.checkstar);
        //khai bao database
        databaseHelper=new DatabaseHelper(getActivity(),"ListLS.sqllite",null,1);
        databaseHelper.Uploaddata("CREATE TABLE IF NOT EXISTS LichSu(ID INTEGER PRIMARY KEY AUTOINCREMENT,Words NVARCHAR(200) )");
        databaseHelper.Uploaddata("CREATE TABLE IF NOT EXISTS TuVung(ID INTEGER PRIMARY KEY AUTOINCREMENT,WordsTV NVARCHAR(200) )");
        //end database





        editText = root.findViewById(R.id.editText);
       textView = root.findViewById(R.id.result);
        ImageView translateButton = root.findViewById(R.id.resultbtn);
        final TextView show=root.findViewById(R.id.speech);
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

        //hàm chọn từ vựng
       /* list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long id) {
                vitri=i;

                checkstar.setActivated();

                return false;
            }
        });*/
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
               vitri=i;
               /* for (i = 0; i < list.getChildCount(); i++) {
                    if (vitri == i) {
                        list.getChildAt(i).setBackgroundColor(Color.LTGRAY);

                    } else {
                        list.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                    }
                }*/


                Cursor dataselect2 = databaseHelper.GetData("select * from LichSu where ID=" + arrayroom.get(vitri).Id + "");

                while (dataselect2.moveToNext()) {
                    final String TuVung = dataselect2.getString(1);

                        Cursor dataselect3 = databaseHelper.GetData("select * from TuVung");
                        while (dataselect3.moveToNext()){
                            final String TuVungTV = dataselect3.getString(1);

                            if (TuVung.equals(TuVungTV)) {
                                Toast.makeText(getActivity(), "Từ này đã có trong Từ Vựng đã học", Toast.LENGTH_SHORT).show();
                            }else{
                                databaseHelper.Uploaddata("insert into TuVung values(null,'" + TuVung + "')");
                                Toast.makeText(getActivity(), TuVung, Toast.LENGTH_SHORT).show();
                            }
                        }




                }





            }
        });




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

        return root;
    }
@Override
public void onResume() {
    Mywords=getActivity().getApplicationContext().getSharedPreferences("words",MODE_PRIVATE);
    KeyWord=Mywords.getString("kw","");
    editText.setText(KeyWord);

    arrayroom.clear();
    Cursor cursor = databaseHelper.GetData("select * from LichSu");
    while (cursor.moveToNext()) {
        arrayroom.add(new TuVungCT(
                cursor.getInt(0),
                cursor.getString(1)
        ));
    }
    adapter.notifyDataSetChanged();


    super.onResume();
}


    @Override
    public void onDestroy() {

        super.onDestroy();
    }





}