package com.example.translate_application.voice;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VoiceViewModel extends ViewModel {
    private MutableLiveData<String> mText;
    public VoiceViewModel(){
        mText = new MutableLiveData<>();
        mText.setValue("This is Voice Fragment");
    }
    public LiveData<String> getText() {
        return mText;
    }
}
