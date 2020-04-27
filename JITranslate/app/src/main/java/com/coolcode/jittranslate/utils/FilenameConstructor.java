package com.coolcode.jittranslate.utils;

import java.io.File;

public class FilenameConstructor {

    private enum FileType{
        BOOK,
        COVER
    }

    public static final String splitter = "_";
    public static final String nameSplitter = "-";
    public static final String bookExt = ".fb2";


    public FilenameConstructor() {
    }

    public String constructBookFileName(String author, String name) {
        return this.constructFileName(author, name, FileType.BOOK);
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
       }
       return filename;
    }
}
