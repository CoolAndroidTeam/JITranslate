package com.coolcode.jittranslate.viewentities;


import com.coolcode.jittranslate.utils.FilenameConstructor;

import java.io.Serializable;

public class JITBook implements Serializable {

    private String name;
    private String author;
    private String coverFileName;
    private boolean downloaded;

    public JITBook (String coverFileName, boolean downloaded) {
        this.coverFileName = coverFileName;
        this.downloaded = downloaded;
        FilenameConstructor.createJITBook(coverFileName, this);

    }

    public boolean isDownloaded() {
        return downloaded;
    }

    public String getAuthor() {
        return author;
    }

    public String getCoverFileName() {
        return coverFileName;
    }

    public String getName() {
        return name;
    }

    public void setDownloaded(boolean downloaded) {
        this.downloaded = downloaded;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
