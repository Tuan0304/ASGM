package com.example.translate_application.voice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.translate_application.R;
import com.example.translate_application.wordbook.WordBookViewModel;

public class VoiceFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_voice, container, false);
        Toast.makeText(getActivity(), "addd", Toast.LENGTH_SHORT).show();


        return root;
    }
}
