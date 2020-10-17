package com.example.translate_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ThemActivity extends AppCompatActivity {
    ImageView btnclose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_text);
        btnclose = findViewById(R.id.ext_home);
        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThemActivity.this.onBackPressed();
            }
        });
    }
}