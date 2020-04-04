package com.coolcode.jittranslate.utils;

import android.graphics.Paint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.kursx.parser.fb2.Element;
import com.kursx.parser.fb2.FictionBook;
import com.kursx.parser.fb2.Section;

import java.util.ArrayList;

public class FB2BookElements {
    private FictionBook book;
    private ArrayList<Element> allPages = new ArrayList<>();
    private float textSize = 0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public FB2BookElements(FictionBook book) {
        this.book = book;
        this.createPages();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void createPages() {
        Paint p = new Paint();
        final ArrayList<Section> sections = book.getBody().getSections();
        sections.forEach(section -> {
            ArrayList<Element> sectionPages = section.getElements();
            sectionPages.forEach(sp -> {
                if (sp.getText() != null) {
                    textSize += p.measureText(sp.getText());
                }
            });
            allPages.addAll(sectionPages);
        });
    }

    public ArrayList<Element> getAllPages() {
        return this.allPages;
    }
    public FictionBook getBook() {
        return this.book;
    }

    public float getTextSize() {
        return this.textSize;
    }
}
