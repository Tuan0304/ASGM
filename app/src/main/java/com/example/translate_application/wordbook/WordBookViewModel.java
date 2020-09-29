package com.example.translate_application.wordbook;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WordBookViewModel extends ViewModel {
    private MutableLiveData<String> mText;
    public WordBookViewModel(){
        mText = new MutableLiveData<>();
        mText.setValue("This is WordBook fragment");
    }

    public LiveData<String> getText(){
        return mText;
    }
}
