package com.example.translate_application.home;

import android.os.Bundle;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.example.translate_application.Language;
import com.example.translate_application.R;
import com.example.translate_application.TranslateAPI;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        final String TAG = "MainActivity";
        SpeechRecognizer speechRecognizer;

        final EditText editText = root.findViewById(R.id.editText);
        final TextView textView = root.findViewById(R.id.result);
        TextView translateButton = root.findViewById(R.id.button);
//Tuan-Chuc năng dich chữ:
        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });
//end Chức năng dịch chữ


        return root;
    }
}