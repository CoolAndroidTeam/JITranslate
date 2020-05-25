package com.coolcode.jittranslate.ui.study;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.coolcode.jittranslate.database.JITBooksShop;
import com.coolcode.jittranslate.dbentities.BookDBModel;
import com.coolcode.jittranslate.dbentities.WordDBModel;
import com.coolcode.jittranslate.ui.bookview.BookViewModel;
import com.coolcode.jittranslate.viewentities.JITBook;

import java.util.ArrayList;
import java.util.List;

public class StudyViewModel extends AndroidViewModel {

    private final MediatorLiveData<List<Word>> wordsList = new MediatorLiveData<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    public StudyViewModel(@NonNull Application application) {
        super(application);
        this.createWordsList();
    }

    public LiveData<List<Word>> getWordsList() {
        return wordsList;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void createWordsList() {
        MutableLiveData<ArrayList<WordDBModel>> words = WordDBModel.getAllWords();
        wordsList.addSource(words, new Observer<ArrayList<WordDBModel>>() {
            @Override
            public void onChanged(ArrayList<WordDBModel> coverNames) {
                ArrayList<Word> data = new ArrayList<>();
                for (int i = 0; i < coverNames.size(); i++) {
                    WordDBModel wDB = coverNames.get(i);
                    data.add(new Word(wDB.getWord(), wDB.getTranslate(), wDB.getLanguage()));
                }
                wordsList.setValue(data);
            }
        });
    }

}