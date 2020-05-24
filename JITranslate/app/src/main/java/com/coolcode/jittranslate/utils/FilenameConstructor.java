package com.coolcode.jittranslate.utils;

import com.coolcode.jittranslate.viewentities.JITBook;

public class FilenameConstructor {

    private enum FileType{
        BOOK,
        COVER
    }

    private static final String splitter = "_";
    private static final String nameSplitter = "-";
    private static final String dot = "\\.";
    private static final String bookExt = ".fb2";
    private static final String coverExt = ".png";

    public static String constructPlainFileName(String coverFilename) {
        return coverFilename.split(dot)[0];
    }


    public static String constructBookFileName(String author, String name) {
        return constructFileName(author, name, FileType.BOOK);
    }

    public static String constructBookFileName(String coverFilename) {
        String bookFilename = "";
        String[] bookName = coverFilename.split(dot);
        bookFilename += bookName[0] + bookExt;
        return bookFilename;
    }

    public static String constructCoverFileName(String author, String name) {
        return constructFileName(author, name, FileType.COVER);
    }

    public static String constructCoverFileName(String bookFilename) {
        String coverFilename = "";
        String[] bookName = bookFilename.split(dot);
        coverFilename += bookName[0] + coverExt;
        return coverFilename;
    }

    private static String constructFileName(String author, String name, FileType type) {
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

    public static void createJITBook(String bookCoverFilename, JITBook jitBook) {
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

    public static String getCoverExt() {
        return coverExt.substring(1);
    }

    public static String getBookExt() {
        return bookExt.substring(1);
    }
}
