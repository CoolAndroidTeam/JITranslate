package com.coolcode.jittranslate.viewentities;

import com.coolcode.jittranslate.utils.TextOrPicture;
import com.kursx.parser.fb2.Binary;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class ClientBook implements Serializable {

    private String name;
    private String author;
    private Integer page = 1;
    private ArrayList<TextOrPicture> dataPages = null;
    private Map<String, Binary> binaries = null;

    public ClientBook (String name, String author) {
        this.author = author;
        this.name = name;
    }

    public ArrayList<TextOrPicture> getDataPages() {
        return dataPages;
    }

    public Map<String, Binary> getBinaries() {
        return binaries;
    }

    public Integer getPage() {
        return page;
    }

    public String getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    public void setDataPages(ArrayList<TextOrPicture> dataPages) {
        this.dataPages = dataPages;
    }

    public void setBinaries(Map<String, Binary> binaries) {
        this.binaries = binaries;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
