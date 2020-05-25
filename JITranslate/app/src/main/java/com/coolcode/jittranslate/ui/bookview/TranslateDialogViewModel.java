package com.coolcode.jittranslate.ui.bookview;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;

import com.coolcode.jittranslate.viewentities.TranslationWord;

public class TranslateDialogViewModel extends AndroidViewModel {

    private final MediatorLiveData<TranslationWord> translationWord = new MediatorLiveData<>();

    public TranslateDialogViewModel(@NonNull Application application) {
        super(application);
    }

    public MediatorLiveData<TranslationWord> getTranslationWord() {
        return translationWord;
    }

    public void setTranslationWord(TranslationWord word) {
        translationWord.setValue(word);
    }

}