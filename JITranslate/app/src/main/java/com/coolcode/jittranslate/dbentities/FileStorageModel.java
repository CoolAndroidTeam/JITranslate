package com.coolcode.jittranslate.dbentities;

import com.google.firebase.storage.StorageReference;

public class FileStorageModel {
    private StorageReference book;
    private StorageReference cover;

    public FileStorageModel(StorageReference cover) {
        this.cover = cover;
    }

    public StorageReference getBook() {
        return book;
    }

    public StorageReference getCover() {
        return cover;
    }

    public void setBook(StorageReference book) {
        this.book = book;
    }

    public void setCover(StorageReference cover) {
        this.cover = cover;
    }
}
