package com.coolcode.jittranslate.utils;

import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileReader {

    private AssetManager assets;
    private String filename;
    private File basefile;

    public FileReader(AssetManager assets, File basefile, String filename) {
        this.assets = assets;
        this.basefile = basefile;
        this.filename = filename;
    }

    public File createFile() {
        File file = new File(basefile, filename);
        if (!file.exists()) {
            readFile(file.getAbsolutePath());
        }
        return file;
    }

    private void readFile(String path) {
        BufferedReader reader = null;
        FileOutputStream fos = null;
        try {
            new File(path).createNewFile();
            fos = new FileOutputStream(path);
            reader = new BufferedReader(
                    new InputStreamReader(assets.open(filename)));

            String mLine;
            while ((mLine = reader.readLine()) != null) {
                fos.write(mLine.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (reader != null) {
                    reader.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
