package com.coolcode.jittranslate.utils;

import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.kursx.parser.fb2.Element;
import com.kursx.parser.fb2.FictionBook;
import com.kursx.parser.fb2.Section;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FB2BookElements {
    private Paint paint = new Paint();
    private Rect rect = new Rect();
    private FictionBook book;
    private ArrayList<Element> allPages = new ArrayList<>();
    private ArrayList<String> pages = new ArrayList<>();
    private float textSize = 0;
    private int linesOnPage;
    private HashMap<Integer, Integer> sections = new HashMap<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    public FB2BookElements(FictionBook book) {
        this.book = book;
        this.calculateLines();
        this.createPages();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void createPages() {
        final ArrayList<Section> sections = book.getBody().getSections();
        sections.forEach(this::iterateSection);
    }

    private void calculateLines() {
        float scaledSizeInPixels = Constants.currentTextSize;
        paint.setTextSize(scaledSizeInPixels);
        String str = "Д!Т –";
        paint.getTextBounds(str, 0, str.length(), rect); //taking "A" just for letter height
        linesOnPage = (int) Math.ceil(Constants.screenHeight/ (float) rect.height()) / 2;
        Log.d("s", String.valueOf(Constants.screenHeight));
        Log.d("s", String.valueOf((float) rect.height()));
        Log.d("s", String.valueOf(linesOnPage));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void iterateSection(Section section) {
        ArrayList<Element> sectionPages = section.getElements();
        int currLines = 0;
        int lines = 0;
        String string = "";
        for (int i=0; i < sectionPages.size(); i++) {
            Element sp = sectionPages.get(i);
            if (sp.getText() != null) {
                 lines = (int) Math.ceil(paint.measureText(sp.getText()) / Constants.screenWidth);

                if ((currLines + lines) < linesOnPage) {
                    currLines += lines;
                    string += "\n" + sp.getText();
                } else {
                    pages.add(string);
                    Log.d("page", string);
                    string = sp.getText();
                    currLines = lines;
                }
            }
        }
        if (!string.equals("")) {
            pages.add(string);
        }
        sections.put(sections.size(), pages.size()+1);

        allPages.addAll(sectionPages);

    }

    public ArrayList<Element> getAllPages() {
        return this.allPages;
    }

    public ArrayList<String> getPages() {
        return this.pages;
    }

    public FictionBook getBook() {
        return this.book;
    }

    public float getTextSize() {
        return this.textSize;
    }
}
