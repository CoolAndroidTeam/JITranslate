package com.coolcode.jittranslate.fragments.bookview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Base64;
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
import com.coolcode.jittranslate.utils.BitmapCreator;
import com.kursx.parser.fb2.Binary;
import com.kursx.parser.fb2.Element;
import com.kursx.parser.fb2.EmptyLine;
import com.kursx.parser.fb2.FictionBook;
import com.kursx.parser.fb2.Image;
import com.kursx.parser.fb2.P;
import com.kursx.parser.fb2.Section;

import java.util.ArrayList;
import java.util.Map;

public class DataAdapterBookView extends RecyclerView.Adapter<BookViewHolder> {
    private BookViewFragment fragment;
    private ArrayList<Element> bookPages;
    private Map<String, Binary> binaries;

    public DataAdapterBookView(Fragment fragment, ArrayList<Element> sections) {
        this.fragment = (BookViewFragment)fragment;
        this.bookPages = sections;
        this.binaries = this.fragment.getFb2Book().getBook().getBinaries();
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
        if (bookPages.get(position).getText() != null) {
            String text = bookPages.get(position).getText();
            pageView.setText(text);
            imageView.setImageDrawable(null);
        } else {
            pageView.setText("");
            P pElem = (P) bookPages.get(position);
            ArrayList<Image> pImage = pElem.getImages();

            BitmapCreator bmCreator = new BitmapCreator(this.binaries.get(pImage.get(pImage.size() - 1).getValue().substring(1)).getBinary());

            DisplayMetrics metrics = new DisplayMetrics();
            this.fragment.getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
            Bitmap scaledBitmap = bmCreator.createResizedBitmap(metrics);
            imageView.setImageBitmap(scaledBitmap);
        }
        TextView pageNumView = holder.getPageNumView();
        pageNumView.setText(String.valueOf(position+1));


    }

    @Override
    public int getItemCount() {
        return bookPages.size();
    }
}
