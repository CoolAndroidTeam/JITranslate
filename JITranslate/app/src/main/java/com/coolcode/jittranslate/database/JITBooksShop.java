package com.coolcode.jittranslate.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.coolcode.jittranslate.dbentities.FileStorageModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;

public class JITBooksShop {
    private static final String coversDirectory = "covers";
    private static final String booksDirectory = "books";
    private HashMap<String, StorageReference> bookCovers = new HashMap<>();

    private FirebaseStorage storage = FirebaseStorage.getInstance();

    public ArrayList<String> getBookCoverNames() {
        StorageReference coversRef = storage.getReference().child(coversDirectory);
        ArrayList<String> bookCoverNames = new ArrayList<>();
        coversRef.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        for (StorageReference item : listResult.getItems()) {
                            bookCovers.put(item.getName(), item);
                            bookCoverNames.add(item.getName());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("firebase_query", e.getMessage());
                    }
                });

        return bookCoverNames;
    }


}
