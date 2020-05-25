package com.coolcode.jittranslate.views.bookview;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.coolcode.jittranslate.ui.bookview.BookViewFragment;
import com.coolcode.jittranslate.utils.TextUtils;
import com.coolcode.jittranslate.viewentities.TranslationWord;

public class TextSpan extends ClickableSpan {
    private BookViewFragment fragment;

    TextSpan(BookViewFragment fragment) {
        this.fragment = fragment;
    }

    public void updateDrawState(TextPaint drawSet) {
        //NO DRAW STATE NEEDED
        }

    @Override
    public void onClick(@NonNull View view) {
        TextView textView = (TextView) view;
        String rawWord = textView.getText().subSequence(textView.getSelectionStart(), textView.getSelectionEnd()).toString();
        String trimmedWord = TextUtils.trimSymbols(rawWord);
        Log.d("tapped on:", trimmedWord);
        fragment.createPopUp(new TranslationWord(trimmedWord));
    }
};
