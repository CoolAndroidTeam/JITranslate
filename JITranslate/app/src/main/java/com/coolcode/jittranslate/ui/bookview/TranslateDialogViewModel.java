package com.coolcode.jittranslate.ui.bookview;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.coolcode.jittranslate.database.JITBooksShop;
import com.coolcode.jittranslate.dbentities.BookDBModel;
import com.coolcode.jittranslate.viewentities.JITBook;
import com.google.firebase.storage.StorageReference;

public class TranslateDialogViewModel extends AndroidViewModel {

    private final MutableLiveData<JITBook> chosenBook = new MutableLiveData<>();
    private BookDBModel bookDBModel;

    public TranslateDialogViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<JITBook> getBook() {
        return chosenBook;
    }

    public void setChosenBook(JITBook jitBook) {
        chosenBook.setValue(jitBook);
        createBookDB(jitBook);
    }

    public StorageReference getBookReference(String bookCover) {
        return JITBooksShop.getBookCoverRef(bookCover);
    }

    public void saveBook(String bookCover) {
        JITBooksShop.saveBook(bookCover);
        try {
            this.bookDBModel.addBook();
        } catch (Exception e) {
            Log.d("saving book", e.getMessage());
        }
    }

    private void createBookDB(JITBook jitBook) {
        this.bookDBModel = new BookDBModel(jitBook.getName(), jitBook.getAuthor());
    }
}