package com.coolcode.jittranslate.fragments.bookview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.coolcode.jittranslate.R;
import com.kursx.parser.fb2.Element;
import com.kursx.parser.fb2.Section;

import java.util.ArrayList;

public class DataAdapterBookView extends RecyclerView.Adapter<BookViewHolder> {
    private BookViewFragment fragment;
    private ArrayList<Element> bookPages;

    public DataAdapterBookView(Fragment fragment, ArrayList<Element> sections) {
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
        TextView pageView = holder.getPageView();
        pageView.setText(bookPages.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return bookPages.size();
    }
}
