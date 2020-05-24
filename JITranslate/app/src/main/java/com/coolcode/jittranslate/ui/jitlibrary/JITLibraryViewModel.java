package com.coolcode.jittranslate.ui.jitlibrary;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.coolcode.jittranslate.database.JITBooksShop;
import com.coolcode.jittranslate.database.UserBooksStorage;
import com.coolcode.jittranslate.viewentities.JITBook;

import java.util.ArrayList;

public class JITLibraryViewModel extends AndroidViewModel {

    private final MutableLiveData<ArrayList<JITBook>> jitBookList = new MutableLiveData<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    public JITLibraryViewModel(@NonNull Application application) {
        super(application);
        this.createBooksList();
    }

    public LiveData<ArrayList<JITBook>> getJITBookList() {
        return jitBookList;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void createBooksList() {
        ArrayList<String> storageBookCovers = new JITBooksShop().getBookCoverNames();
        ArrayList<JITBook> data = new ArrayList<>();
        for (int i = 0; i < storageBookCovers.size(); i++) {
            String filename = storageBookCovers.get(i);
            data.add(new JITBook(filename, checkBookDownloaded(filename)));
        }
        jitBookList.setValue(data);
    }

    private boolean checkBookDownloaded(String filename) {
        return UserBooksStorage.checkBookCoverFileExists(filename);
    }

}