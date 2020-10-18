package com.example.translate_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ThemActivity extends AppCompatActivity {
    ImageView btnclose;
    TextView nhapVB,Bandich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_text);
        btnclose = findViewById(R.id.ext_home);
        nhapVB=findViewById(R.id.home_NhapVB);
        Bandich=findViewById(R.id.home_BanDich);


        nhapVB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                        TranslateAPI translateAPI = new TranslateAPI(
                                Language.AUTO_DETECT,
                                Language.VIETNAMESE, nhapVB.getText().toString());

                        translateAPI.setTranslateListener(new TranslateAPI.TranslateListener() {
                            @Override
                            public void onSuccess(String translatedText) {
                                Bandich.setText(translatedText);
                            }

                            @Override
                            public void onFailure(String ErrorText) {

                            }
                        });



            }

            @Override
            public void afterTextChanged(Editable s) {
                Toast.makeText(ThemActivity.this, "haleluza", Toast.LENGTH_SHORT).show();
            }
        });


        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    ThemActivity.this.onBackPressed();






            }
        });
    }
}