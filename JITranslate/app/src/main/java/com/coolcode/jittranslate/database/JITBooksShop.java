package com.coolcode.jittranslate.database;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.coolcode.jittranslate.dbentities.FileStorageModel;
import com.coolcode.jittranslate.utils.FilenameConstructor;
import com.coolcode.jittranslate.viewentities.JITBook;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class JITBooksShop {
    private static final String coversDirectory = "covers";
    private static final String booksDirectory = "books";
    private static HashMap<String, StorageReference> bookCovers = new HashMap<>();
    private static HashMap<String, StorageReference> books = new HashMap<>();
    private static final MutableLiveData< ArrayList<String>> bookCoverList = new MutableLiveData<>();

    private static FirebaseStorage storage = null;

    public JITBooksShop() {
        storage = FirebaseStorage.getInstance();
    }

    public static MutableLiveData<ArrayList<String>> getBookCoverNames() {
        getAllRefs();
        return bookCoverList;
    }

    public static StorageReference getBookCoverRef(String cover) {
        return bookCovers.get(cover);
    }

    private static void getAllRefs() {
        StorageReference coversRef = storage.getReference().child(coversDirectory);
        StorageReference booksRef = storage.getReference().child(booksDirectory);
        coversRef.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        ArrayList<String> bookCoverNames = new ArrayList<>();
                        for (StorageReference item : listResult.getItems()) {
                            bookCovers.put(item.getName(), item);
                            bookCoverNames.add(item.getName());
                        }
                        bookCoverList.setValue(bookCoverNames);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("firebase_cover_query", e.getMessage());
                    }
                });
        booksRef.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        for (StorageReference item : listResult.getItems()) {
                            books.put(item.getName(), item);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("firebase_book_query", e.getMessage());
                    }
                });

    }

    public static void saveBook(String coverName) {
       StorageReference cover = bookCovers.get(coverName);
       String bookName = FilenameConstructor.constructBookFileName(coverName);
       StorageReference book = books.get(bookName);
       try {
           String externalDir = UserBooksStorage.getClientsBooksCoversFullDir();
           File outFile = new File(externalDir, coverName);
           cover.getFile(outFile);
       } catch (Exception e) {
           Log.d("save_cover", e.getMessage());
       }
        try {
            String externalDir = UserBooksStorage.getClientsBooksFullDir();
            File outFile = new File(externalDir, bookName);
            book.getFile(outFile);
        } catch (Exception e) {
            Log.d("save_book", e.getMessage());
        }
    }

}
