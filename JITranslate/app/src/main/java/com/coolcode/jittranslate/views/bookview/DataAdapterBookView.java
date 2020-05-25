package com.coolcode.jittranslate.views.bookview;

import android.graphics.Bitmap;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.coolcode.jittranslate.R;
import com.coolcode.jittranslate.ui.bookview.BookViewFragment;
import com.coolcode.jittranslate.utils.BitmapCreator;
import com.coolcode.jittranslate.utils.TextUtils;
import com.coolcode.jittranslate.viewentities.TextOrPicture;

import java.util.ArrayList;

public class DataAdapterBookView extends RecyclerView.Adapter<BookViewHolder> {
    private BookViewFragment fragment;
    private ArrayList<TextOrPicture> bookPages;

    public DataAdapterBookView(Fragment fragment, ArrayList<TextOrPicture> sections) {
        this.fragment = (BookViewFragment)fragment;
        this.bookPages = sections;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.page, parent, false);
        return new BookViewHolder(listItem, this.fragment);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        ImageView imageView = holder.getImageView();
        TextView pageView = holder.getPageTextView();
        TextOrPicture top = bookPages.get(position);
        if (top.isText()) {
            String text = top.getText();
            createClickableWords(pageView, text);
//            pageView.setText(text);
            imageView.setImageDrawable(null);
        } else {
            pageView.setText("");
            BitmapCreator bmCreator = new BitmapCreator(fragment.getBinary(top.getImageKey()));

            DisplayMetrics metrics = new DisplayMetrics();
            this.fragment.getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
            Bitmap scaledBitmap = bmCreator.createResizedBitmap(metrics);
            imageView.setImageBitmap(scaledBitmap);
        }
        TextView pageNumView = holder.getPageNumView();
        pageNumView.setText(String.valueOf(position+1));
    }

    private void createClickableWords(TextView textView, String text) {
        String definition = text.trim();
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(definition, TextView.BufferType.SPANNABLE);

        Spannable spans = (Spannable) textView.getText();
        ArrayList<Integer> indices = TextUtils.getWhitespaceIndexes(definition);
        createSpans(spans, indices);
    }

    private void createSpans(Spannable spans, ArrayList<Integer> indices) {
        int start = 0;
        int end = 0;
        for (int i=0; i<=indices.size(); i++) {

            ClickableSpan clickSpan = new TextSpan(fragment);
            if (i<indices.size()) {
                end = indices.get(i);
            } else {
                end = spans.length();
            };
            spans.setSpan(clickSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            start = end + 1;
        }
    }

    @Override
    public int getItemCount() {
        return bookPages.size();
    }
}
