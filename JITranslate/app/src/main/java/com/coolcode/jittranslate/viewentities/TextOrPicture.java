package com.coolcode.jittranslate.viewentities;

import com.kursx.parser.fb2.Image;

import java.util.ArrayList;

public class TextOrPicture {
    ArrayList<Image> images = new ArrayList<>();
    String text = null;

    public TextOrPicture(String string) {
        this.text = string;
    }

    public TextOrPicture(ArrayList<Image> images) {
        this.images = images;
    }

    public boolean isText() {
        return (text != null);
    }

    public String getText() {
        return this.text;
    }

    public String getImageKey() {
        return images.get(images.size() - 1).getValue().substring(1);
    }
}
