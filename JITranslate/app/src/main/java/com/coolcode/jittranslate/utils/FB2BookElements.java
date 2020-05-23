package com.coolcode.jittranslate.utils;

import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.kursx.parser.fb2.Element;
import com.kursx.parser.fb2.FictionBook;
import com.kursx.parser.fb2.P;
import com.kursx.parser.fb2.Section;

import java.util.ArrayList;
import java.util.HashMap;


public class FB2BookElements {
    private Paint paint = new Paint();
    private Rect rect = new Rect();
    private FictionBook book;
    private ArrayList<TextOrPicture> pages = new ArrayList<>();
    private float textSize = 0;
    private int linesOnPage;
    private HashMap<Integer, Integer> sections = new HashMap<>();
    private int currentLines = 0;
    private String currentString = "";

    @RequiresApi(api = Build.VERSION_CODES.N)
    public FB2BookElements(FictionBook book) {
        this.book = book;
        this.calculateLines();
        this.createPages();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void createPages() {
        final ArrayList<Section> sections = book.getBody().getSections();
        for (Section s : sections) {
            this.iterateSection(s);
        }
        if (!currentString.equals("")) this.pages.add(new TextOrPicture(currentString));
    }

    private void calculateLines() {
        float scaledSizeInPixels = Constants.currentTextSize;
        paint.setTextSize(scaledSizeInPixels);
        String str = "Д!Т –"; //taking random string just for letter height
        paint.getTextBounds(str, 0, str.length(), rect);
        linesOnPage = (int) Math.ceil(Constants.screenHeight/ (float) rect.height()) / 2;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void iterateSection(Section section) {
        ArrayList<Element> sectionPages = section.getElements();
        int lines = 0;
        for (int i=0; i < sectionPages.size(); i++) {
            Element sp = sectionPages.get(i);
            if (sp.getText() != null) {
                lines = (int) Math.ceil(paint.measureText(sp.getText()) / Constants.screenWidth);
                if ((currentLines + lines) < linesOnPage) {
                    currentLines += lines;
                    currentString += "\n" + sp.getText();
                } else {
                    pages.add(new TextOrPicture(currentString));
                    currentString = sp.getText();
                    currentLines = lines;
                }
            } else {
                P p = (P)sp;
                pages.add(new TextOrPicture(p.getImages()));
            }
        }
        sections.put(sections.size(), pages.size()+1);
    }

    public ArrayList<TextOrPicture> getPages() {
        return this.pages;
    }

    public FictionBook getBook() {
        return this.book;
    }

    public float getTextSize() {
        return this.textSize;
    }
}
