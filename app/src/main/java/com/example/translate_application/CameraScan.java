package com.example.translate_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import com.lucem.anb.characterscanner.Scanner;
import com.lucem.anb.characterscanner.ScannerListener;
import com.lucem.anb.characterscanner.ScannerView;



public class CameraScan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_scan);
       final TextView text=findViewById(R.id.text);
        SurfaceView surfaceView = findViewById(R.id.surface);
        Scanner scanner = new Scanner(this, surfaceView, new ScannerListener() {
            @Override
            public void onDetected(String detections) {
                TranslateAPI translateAPI = new TranslateAPI(
                        Language.AUTO_DETECT,
                        Language.VIETNAMESE, detections);

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

            @Override
            public void onStateChanged(String state, int i) {
                Log.d("state", state);
            }
        });
    }
}