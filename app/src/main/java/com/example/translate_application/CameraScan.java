package com.example.translate_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lucem.anb.characterscanner.Scanner;
import com.lucem.anb.characterscanner.ScannerListener;
import com.lucem.anb.characterscanner.ScannerView;



public class CameraScan extends AppCompatActivity {
    private ScaleGestureDetector scaleGestureDetector;
    private GestureDetector gestureDetector;
    int check=0;
    String request_syn,detect,strIn,strOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_scan);
       final TextView text=findViewById(R.id.text);

        Intent ii=getIntent();
        Bundle b=ii.getExtras();
        if(b!=null)
        {
            strIn=(String) b.get("langin");
            strOut=(String) b.get("langout");
        }
        Toast.makeText(this, strIn, Toast.LENGTH_SHORT).show();

        final ScannerView scanner = findViewById(R.id.scanner);
        Button take=findViewById(R.id.takebtn);
        scanner.setOnDetectedListener(this, new ScannerListener() {
            @Override
            public void onDetected(String detections) {
                if(check==1){
                    detect=detections;
                    check=0;
                }
            }
            @Override
            public void onStateChanged(String state, int i) {
                Log.d("state", state);
            }
        });
        take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               check=1;
                TranslateAPI translateAPI = new TranslateAPI(
                        strIn,
                        strOut, detect);

                translateAPI.setTranslateListener(new TranslateAPI.TranslateListener() {
                    @Override
                    public void onSuccess(String translatedText) {
                        text.setText(translatedText);
                    }

                    @Override
                    public void onFailure(String ErrorText) {

                    }
                });
            }
        });


    }
}