package com.coolcode.jittranslate.ui.study;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Word {

    @SerializedName("word")
    @Expose
    private String word;

    @SerializedName("translate")
    @Expose
    private String translate;

    @SerializedName("language")
    @Expose
    private String language;

    public String getWord() {
        return word;
    }

    public void setWord(String name) {
        this.word = name;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
