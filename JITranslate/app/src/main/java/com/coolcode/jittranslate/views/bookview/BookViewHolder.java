package com.coolcode.jittranslate.views.bookview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.coolcode.jittranslate.R;

public class BookViewHolder extends RecyclerView.ViewHolder {

    private TextView pageTextView;
    private ImageView imageView;
    private TextView pageNumView;

    public BookViewHolder(@NonNull View itemView, Fragment fragment) {
        super(itemView);
        pageTextView = itemView.findViewById(R.id.page_text);
        imageView = itemView.findViewById(R.id.page_pic);
        pageNumView = itemView.findViewById(R.id.page_number);
    }

    public TextView getPageTextView() {
        return pageTextView;
    }
    public TextView getPageNumView() {
        return pageNumView;
    }
    public ImageView getImageView() {
        return imageView;
    }


}
