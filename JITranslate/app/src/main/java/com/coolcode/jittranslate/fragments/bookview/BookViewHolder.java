package com.coolcode.jittranslate.fragments.bookview;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.coolcode.jittranslate.R;

public class BookViewHolder extends RecyclerView.ViewHolder {

    private TextView pageTextView;

    public BookViewHolder(@NonNull View itemView, Fragment fragment) {
        super(itemView);
        pageTextView = itemView.findViewById(R.id.page_text);
    }

    public TextView getPageView() {
        return pageTextView;
    }

}
