package com.coolcode.jittranslate.viewentities;

public class TranslationWord {
    private String englishWord;
    private String translatedWord;

    public TranslationWord(String englishWord) {
        this.englishWord = englishWord;
    }

    public TranslationWord(String englishWord, String translatedWord) {
        this.englishWord = englishWord;
        this.translatedWord = translatedWord;
    }

    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }

    public void setTranslatedWord(String translatedWord) {
        this.translatedWord = translatedWord;
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public String getTranslatedWord() {
        return translatedWord;
    }
}
