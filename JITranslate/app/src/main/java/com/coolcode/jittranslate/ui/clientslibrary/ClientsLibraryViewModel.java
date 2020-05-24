package com.coolcode.jittranslate.ui.clientslibrary;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.coolcode.jittranslate.dbentities.BookDBModel;
import com.coolcode.jittranslate.viewentities.ClientBook;
import com.coolcode.jittranslate.viewentities.JITBook;

import java.util.ArrayList;

public class ClientsLibraryViewModel extends AndroidViewModel {

    private final MutableLiveData<ArrayList<ClientBook>> clientBookList = new MutableLiveData<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ClientsLibraryViewModel(@NonNull Application application) {
        super(application);
        this.createBooksList();
    }

    public LiveData<ArrayList<ClientBook>> getClientBookList() {
        return clientBookList;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void createBooksList() {
        ArrayList<BookDBModel> dbbooks = BookDBModel.getAllBooks();
        ArrayList<ClientBook> data = new ArrayList<>();
        for (int i = 0; i < dbbooks.size(); i++) {
            BookDBModel bookDBModel = dbbooks.get(i);
            data.add(new ClientBook(bookDBModel.getName(), bookDBModel.getAuthor()));
        }
        clientBookList.setValue(data);
    }

    public void updateBookList(JITBook jitBook) {
        ArrayList<ClientBook> currentList = clientBookList.getValue();
        currentList.add(new ClientBook(jitBook.getName(), jitBook.getAuthor()));
        clientBookList.postValue(currentList);
    }


}