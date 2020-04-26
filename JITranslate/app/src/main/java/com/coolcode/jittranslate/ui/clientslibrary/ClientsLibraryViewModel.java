package com.coolcode.jittranslate.ui.clientslibrary;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ClientsLibraryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ClientsLibraryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is library fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}