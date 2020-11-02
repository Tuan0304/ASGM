package com.example.translate_application.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.translate_application.DatabaseHelper;
import com.example.translate_application.R;

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

public class ThemActivity extends AppCompatActivity {

    ImageView btnclose,result;
    TextView nhapVB,Bandich;
    String api_url = "https://api.datamuse.com/words?";
    String syn = "rel_syn=";
    String max_results = "&max=30";
    String request_syn,tentaikhoan,strIn,strOut;
    ListView list;
    DatabaseHelper databaseHelper;
    SharedPreferences Mywords,MyAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_text);

        //hàm lấy intent lang từ home
        Intent ii=getIntent();
        Bundle b=ii.getExtras();
        if(b!=null)
        {
            strIn=(String) b.get("langin");
            strOut=(String) b.get("langout");

        }
        //end hàm lấy intent lang

        //anh xa
        btnclose = findViewById(R.id.ext_home);
        result = findViewById(R.id.resultbtn);

        nhapVB=findViewById(R.id.NhapVB);
        Bandich=findViewById(R.id.BanDich);
        //end anh xa

        Mywords=getApplicationContext().getSharedPreferences("words",MODE_PRIVATE);

        //local key
        MyAccount=getApplicationContext().getSharedPreferences("CusACCC",MODE_PRIVATE);
        tentaikhoan=MyAccount.getString("id","");
        //end local key

        //database
        databaseHelper = new DatabaseHelper(this,"Translate3.sqlite",null,1);
        databaseHelper.QueryData("CREATE TABLE IF NOT EXISTS TuVung(Id INTEGER PRIMARY KEY AUTOINCREMENT, TuCanDich VARCHAR(500),BanDich VARCHAR(500),TaiKhoan VARCHAR(50))");
        //end database

        nhapVB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(nhapVB.getText().toString().equals("")){
                    Toast.makeText(ThemActivity.this, "null", Toast.LENGTH_SHORT).show();
                }else{
                        handleSynonym(nhapVB.getText().toString());
                       /* TranslateAPI translateAPI = new TranslateAPI(
                                strIn,
                                strOut, nhapVB.getText().toString());

                        translateAPI.setTranslateListener(new TranslateAPI.TranslateListener() {
                            @Override
                            public void onSuccess(String translatedText) {
                                Bandich.setText(translatedText);
                            }

                            @Override
                            public void onFailure(String ErrorText) {

                            }
                        });*/
                } }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation zoom = AnimationUtils.loadAnimation(ThemActivity.this, R.anim.zoomin);
                view.startAnimation(zoom);
                nhapVB.setText("");

            }
        });
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              SharedPreferences.Editor myeditor = Mywords.edit();
              if (nhapVB.length()>0) {
                  Animation zoom = AnimationUtils.loadAnimation(ThemActivity.this, R.anim.zoomin);
                  v.startAnimation(zoom);
                  myeditor.putString("kw", nhapVB.getText().toString());
                  myeditor.commit();

                  //databaseHelper.Uploaddata("insert into TuVung values(null,'" + nhapVB.getText().toString() + "','" + Bandich.getText().toString() + "','" + tentaikhoan + "')");

                  finish();
              }else{
                  Toast.makeText(ThemActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
              }
            }

        });
    }

    private void handleSynonym(String str) {
        request_syn = api_url+syn+str+max_results;
        new DatamuseQuery(request_syn, "synonym").execute();
    }
    private void fill_List(ArrayList<String> results, String list_name) {

        if (list_name.equals("synonym"))
            list = findViewById(R.id.synlist);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, results);
        list.setAdapter(arrayAdapter);
    }
    @SuppressLint("StaticFieldLeak")
    private class DatamuseQuery extends AsyncTask<Void,Void,Void> {

        private String api_url;
        private ArrayList<String> words;
        private String json_rez;
        String list_name;

        DatamuseQuery(String url, String list_name) {
            this.list_name = list_name;
            this.api_url = url;
            this.words = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {

                URL url = new URL(api_url);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                //Getting JSON from API
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder resultBuilder = new StringBuilder();

                while ((line = reader.readLine()) != null)
                    resultBuilder.append(line);

                reader.close();

                json_rez = resultBuilder.toString();

                //JSON parsing
                try {
                    JSONArray jArray = new JSONArray(json_rez);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject jObject = jArray.getJSONObject(i);
                        words.add(jObject.getString("word"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void voids) {
            //calling a Activity method to fill the tables with results
            fill_List(words, list_name);
        }
    }
}