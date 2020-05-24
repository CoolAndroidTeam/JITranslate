package com.coolcode.jittranslate.ui.jitlibrary;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.coolcode.jittranslate.R;
import com.coolcode.jittranslate.database.JITBooksShop;
import com.coolcode.jittranslate.database.UserBooksStorage;
import com.coolcode.jittranslate.viewentities.JITBook;
import com.coolcode.jittranslate.views.jitlibrary.DataAdapterJITLibrary;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class JITLibraryViewModel extends AndroidViewModel {

    private final MediatorLiveData<ArrayList<JITBook>> jitBookList = new MediatorLiveData<>();

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
        MutableLiveData<ArrayList<String>> bookCoverNames = JITBooksShop.getBookCoverNames();
        jitBookList.addSource(bookCoverNames, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> coverNames) {
                ArrayList<JITBook> data = new ArrayList<>();
                for (int i = 0; i < coverNames.size(); i++) {
                    String filename = coverNames.get(i);
                    data.add(new JITBook(filename, checkBookDownloaded(filename)));
                }
                jitBookList.setValue(data);
            }
        });
    }

    public StorageReference getBookReference(String bookCover) {
        return JITBooksShop.getBookCoverRef(bookCover);
    }

    private boolean checkBookDownloaded(String filename) {
        return UserBooksStorage.checkBookCoverFileExists(filename);
    }

}