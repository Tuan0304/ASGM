package com.example.translate_application.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import com.example.translate_application.CameraScan;
import com.example.translate_application.Language;
import com.example.translate_application.R;
import com.example.translate_application.ThemActivity;
import com.example.translate_application.TranslateAPI;

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

    ListView list;
    TextView textView,editText;
    SharedPreferences Mywords;
    String KeyWord;

    private HomeViewModel homeViewModel;
    public static final Integer RecordAudioRequestCode=1;
    TextView camera;


    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final String TAG = "MainActivity";




        editText = root.findViewById(R.id.editText);
       textView = root.findViewById(R.id.result);
        ImageView translateButton = root.findViewById(R.id.resultbtn);
        final TextView show=root.findViewById(R.id.speech);
        final ImageView voicebtn=root.findViewById(R.id.voice);
        camera = root.findViewById(R.id.Camera);

        list = root.findViewById(R.id.synlist);

       /* Mywords=getActivity().getApplicationContext().getSharedPreferences("words",MODE_PRIVATE);
        KeyWord=Mywords.getString("kw","");
        Toast.makeText(getActivity(), KeyWord, Toast.LENGTH_SHORT).show();*/






        //camera
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CameraScan.class));
            }
        });
        //end camera


        //speech to text function
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            checkPermission();
        }


        final SpeechRecognizer speechRecognizer= SpeechRecognizer.createSpeechRecognizer(getActivity());

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
                show.setText("");
                show.setHint("Listening...");
            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {
                Toast.makeText(getActivity(), "end", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int i) {
                Toast.makeText(getActivity(), "Lỗi rồi", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResults(Bundle bundle) {
                //them đổi hình ở đây
                voicebtn.setImageResource(R.drawable.ic_voiceoff);
                ArrayList<String> data=bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                show.setText(data.get(0));

                TranslateAPI translateAPI = new TranslateAPI(
                        Language.AUTO_DETECT,
                        Language.VIETNAMESE, data.get(0));

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

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

        voicebtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    speechRecognizer.stopListening();
                }
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    voicebtn.setImageResource(R.drawable.ic_voice);
                    speechRecognizer.startListening(speechRecognizerIntent);
                }
                return false;
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
      /* editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                startActivity(new Intent(getActivity(), ThemActivity.class));
                return true;
            }
        });*/

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
             //handleSynonym(editText.getText().toString());
            // startActivity(new Intent(getActivity(), ThemActivity.class));

             }
        });


//end Chức năng dịch chữ

        return root;
    }
@Override
public void onResume() {

    Bundle bundle = this.getArguments();
    if (bundle == null) {
        Toast.makeText(getActivity(), "nulllll", Toast.LENGTH_SHORT).show();
    }else{
        String myString = bundle.getString("words");
        editText.setText(myString);
        Toast.makeText(getActivity(), myString, Toast.LENGTH_SHORT).show();
    }



    super.onResume();
}

    private void checkPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.RECORD_AUDIO},RecordAudioRequestCode);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RecordAudioRequestCode && grantResults.length > 0 ){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(getActivity(),"Permission Granted", Toast.LENGTH_SHORT).show();
        }
    }


}