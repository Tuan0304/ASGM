package com.example.translate_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sembozdemir.speechorbview.library.SpeechOrbView;

import java.util.ArrayList;

public class VoiceActivity extends AppCompatActivity {
    public static final Integer RecordAudioRequestCode=1;
    SharedPreferences Mywords;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);

        final TextView voiceRs=findViewById(R.id.resultvoice);
        final SpeechOrbView voicemic=findViewById(R.id.voiceorb);

        Mywords=getApplicationContext().getSharedPreferences("words",MODE_PRIVATE);

        //database
        databaseHelper = new DatabaseHelper(this,"Translate2.sqlite",null,1);
        databaseHelper.QueryData("CREATE TABLE IF NOT EXISTS TuVung(Id INTEGER PRIMARY KEY AUTOINCREMENT, TuCanDich VARCHAR(150),BanDich VARCHAR(250),TaiKhoan VARCHAR(50))");
        //end database


        if(ContextCompat.checkSelfPermission(VoiceActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            checkPermission();
        }


        final SpeechRecognizer speechRecognizer= SpeechRecognizer.createSpeechRecognizer(VoiceActivity.this);

        final Intent speechRecognizerIntent= new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Language.JAPANESE);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Language.VIETNAMESE);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Language.CHINESE);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Language.GERMAN);


        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {
               voicemic.play();
            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {
                voicemic.stop();
            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                //them đổi hình ở đây

                ArrayList<String> data=bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                voiceRs.setText(data.get(0));

                SharedPreferences.Editor myeditor = Mywords.edit();
                myeditor.putString("kw",data.get(0));
                myeditor.commit();

                finish();
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

        voicemic.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){

                    speechRecognizer.stopListening();
                }
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    speechRecognizer.startListening(speechRecognizerIntent);
                }
                return false;
            }
        });
    }
    private void checkPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            ActivityCompat.requestPermissions(VoiceActivity.this,new String[]{Manifest.permission.RECORD_AUDIO},RecordAudioRequestCode);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RecordAudioRequestCode && grantResults.length > 0 ){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(VoiceActivity.this,"Permission Granted", Toast.LENGTH_SHORT).show();
        }
    }
}