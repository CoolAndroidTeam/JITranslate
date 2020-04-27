package com.coolcode.jittranslate.utils;

import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class FileReader {

    private AssetManager assets;
    private File externalStorageDir;

    public FileReader(AssetManager assets, File externalStorageDir) {
        this.assets = assets;
        this.externalStorageDir = externalStorageDir;
    }

    public void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }

    public void copyFilesFromAssetsToExternalStorage(String filesDirInAssets, String filesDirInExternalStorage) {
        String[] files = null;
        try {
            files = assets.list(filesDirInAssets);
        } catch (IOException e) {
            Log.e("filereader", "Failed to get asset file list.", e);
        }
        String externalDir = externalStorageDir + File.separator + filesDirInExternalStorage;
        String assetsDir = filesDirInAssets + File.separator;
        if (files != null) for (String filename : files) {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = assets.open(assetsDir + filename);
                File outFile = new File(externalDir, filename);
                out = new FileOutputStream(outFile);
                copyFile(in, out);
            } catch(IOException e) {
                Log.e("filereader", "Failed to copy asset file: " + filename, e);
            }
            finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                    if (out != null) {
                        out.close();
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public File createFile(String filesDirInExternalStorage, String filename) {
        String externalDir = externalStorageDir + File.separator + filesDirInExternalStorage;
        File file = new File(externalDir, filename);
        if (!file.exists()) {
            Log.d("filereader", "Failed to find file: " + filename);
        }
        return file;
    }

//    private void readFile(String path) {
//        BufferedReader reader = null;
//        FileOutputStream fos = null;
//        try {
//            new File(path).createNewFile();
//            fos = new FileOutputStream(path);
//            reader = new BufferedReader(
//                    new InputStreamReader(assets.open(filename)));
//
//            String mLine;
//            while ((mLine = reader.readLine()) != null) {
//                fos.write(mLine.getBytes());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            try {
//                if (fos != null) {
//                    fos.close();
//                }
//                if (reader != null) {
//                    reader.close();
//                }
//            }
//            catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
