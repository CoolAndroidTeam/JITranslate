package com.coolcode.jittranslate.viewentities;

import java.io.Serializable;

public class ClientBook implements Serializable {

    private String name;
    private String author;
    private Integer page = 1;

    public ClientBook (String name, String author) {
        this.author = author;
        this.name = name;
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
