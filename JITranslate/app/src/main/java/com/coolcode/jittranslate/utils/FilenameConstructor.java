package com.coolcode.jittranslate.utils;

import com.coolcode.jittranslate.viewentities.JITBook;

public class FilenameConstructor {

    private enum FileType{
        BOOK,
        COVER
    }

    public static final String splitter = "_";
    public static final String nameSplitter = "-";
    public static final String dot = ".";
    public static final String bookExt = ".fb2";
    public static final String coverExt = ".png";


    public FilenameConstructor() {
    }

    public String constructBookFileName(String author, String name) {
        return this.constructFileName(author, name, FileType.BOOK);
    }

    public String constructCoverFileName(String author, String name) {
        return this.constructFileName(author, name, FileType.COVER);
    }

    public String constructFileName(String author, String name, FileType type) {
       String filename = "";
       String[] authorSplit = author.split(" ");
       int lastSymbol =  authorSplit.length - 1;
       for (int i=0; i < lastSymbol; i++) {
           filename += authorSplit[i] + splitter;
       }
       filename += authorSplit[lastSymbol] + nameSplitter;
       String[] nameSplit = name.split(" ");
       lastSymbol =  nameSplit.length - 1;
       for (int i=0; i < lastSymbol; i++) {
           filename += nameSplit[i] + splitter;
       }
       filename += nameSplit[lastSymbol];
       switch (type){
           case BOOK:{
                filename += bookExt;
                break;
           }
           case COVER:{
               filename += coverExt;
           }
       }
       return filename;
    }

    public void createJITBook(String bookCoverFilename, JITBook jitBook) {
        String[] bookName = bookCoverFilename.split(dot);
        String[] nameAndAuthor = bookName[0].split(nameSplitter);
        String author = nameAndAuthor[0];
        String name = nameAndAuthor[1];
        String[] authorSplit = author.split(splitter);
        String[] nameSplit = name.split(splitter);
        String authorBook = "";
        int lastSymbol =  authorSplit.length - 1;
        for (int i=0; i < lastSymbol; i++) {
            authorBook += authorSplit[i] + " ";
        }
        authorBook += authorSplit[lastSymbol];
        String nameBook = "";
        lastSymbol =  nameSplit.length - 1;
        for (int i=0; i < lastSymbol; i++) {
            nameBook += nameSplit[i] + " ";
        }
        nameBook += nameSplit[lastSymbol];
        jitBook.setAuthor(authorBook);
        jitBook.setName(nameBook);
    }
}
