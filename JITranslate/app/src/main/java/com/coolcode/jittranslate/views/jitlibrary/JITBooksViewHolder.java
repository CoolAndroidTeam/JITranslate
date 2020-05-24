package com.coolcode.jittranslate.views.jitlibrary;

import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.coolcode.jittranslate.R;
import com.coolcode.jittranslate.ui.clientslibrary.ClientsLibraryFragment;
import com.coolcode.jittranslate.viewentities.ClientBook;

public class JITBooksViewHolder extends RecyclerView.ViewHolder {

    private ImageView jitBookCoverView;
    private TextView jitBookNameView;
    private TextView jitBookAuthorView;
    private Button downloadButton;
    private ImageView downloadedIcon;

    public JITBooksViewHolder(@NonNull View itemView, Fragment fragment) {
        super(itemView);
        jitBookAuthorView = itemView.findViewById(R.id.jit_book_author);
        jitBookNameView = itemView.findViewById(R.id.jit_book_name);
        jitBookCoverView = itemView.findViewById(R.id.jit_book_cover);
        downloadButton = itemView.findViewById(R.id.jit_book_download_btn);
        downloadedIcon = itemView.findViewById(R.id.jit_book_done_ic);
    }

    public Button getDownloadButton() {
        return downloadButton;
    }

    public ImageView getJitBookCoverView() {
        return jitBookCoverView;
    }

    public ImageView getDownloadedIcon() {
        return downloadedIcon;
    }

    public TextView getJitBookAuthorView() {
        return jitBookAuthorView;
    }

    public TextView getJitBookNameView() {
        return jitBookNameView;
    }
}
