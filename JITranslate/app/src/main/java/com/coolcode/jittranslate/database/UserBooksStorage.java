package com.coolcode.jittranslate.database;


import android.util.Log;

import java.io.File;

public class UserBooksStorage {
    private static File externalStorageDir;
    private static final String clientsBooksCoversDir = "client_books_covers";
    private static final String clientsBooksDir = "client_books";

    public UserBooksStorage(File extStorageDir) {
        externalStorageDir = extStorageDir;
    }

    public static boolean checkBookFileExists(String filename) {
        return checkFileExists(clientsBooksDir, filename);
    }

    public static boolean checkBookCoverFileExists(String filename) {
        return checkFileExists(clientsBooksCoversDir, filename);
    }

    private static boolean checkFileExists(String filesDirInExternalStorage, String filename) {
        String externalDir = externalStorageDir + File.separator + filesDirInExternalStorage;
        File file = new File(externalDir, filename);
        return file.exists();
    }

    public static File createFile(String filesDirInExternalStorage, String filename) {
        String externalDir = externalStorageDir + File.separator + filesDirInExternalStorage;
        File file = new File(externalDir, filename);
        if (!file.exists()) {
            Log.d("filereader", "Failed to find file: " + filename);
        }
        return file;
    }


    public static String getClientsBooksCoversFullDir() {
        return externalStorageDir + File.separator + clientsBooksCoversDir;
    }

    public static String getClientsBooksFullDir() {
        return externalStorageDir + File.separator + clientsBooksDir;
    }
}
