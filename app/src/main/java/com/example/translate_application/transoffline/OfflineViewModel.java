package com.example.translate_application.transoffline;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OfflineViewModel extends ViewModel {
    private MutableLiveData<String> mText;
    public OfflineViewModel(){
        mText = new MutableLiveData<>();
        mText.setValue("This is Voice Fragment");
    }
    public LiveData<String> getText() {
        return mText;
    }
}
