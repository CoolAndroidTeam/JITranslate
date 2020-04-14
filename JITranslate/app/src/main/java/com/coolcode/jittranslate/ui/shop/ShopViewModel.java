package com.coolcode.jittranslate.ui.shop;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShopViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ShopViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Here you can find books");
    }


    public LiveData<String> getText() {
        return mText;
    }
    public void setValue(String str){
        mText.setValue(str);
    }
}