package com.coolcode.jittranslate.dbentities;

import com.google.firebase.storage.StorageReference;

public class FileStorageModel {
    private String filename;
    private boolean downloaded;

    public FileStorageModel(String filename) {

    }

    public boolean isDownloaded() {
        return downloaded;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setDownloaded(boolean downloaded) {
        this.downloaded = downloaded;
    }
}
