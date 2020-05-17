package com.coolcode.jittranslate.ui.forum;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ForumViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public ForumViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("We suggest you visit www.booktalk.org to discuss your favorite books!");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
