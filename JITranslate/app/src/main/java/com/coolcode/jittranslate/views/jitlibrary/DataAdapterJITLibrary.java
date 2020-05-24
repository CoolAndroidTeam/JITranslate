package com.coolcode.jittranslate.views.jitlibrary;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.coolcode.jittranslate.R;
import com.coolcode.jittranslate.ui.clientslibrary.ClientsLibraryFragment;
import com.coolcode.jittranslate.ui.jitlibrary.JITDialogViewModel;
import com.coolcode.jittranslate.ui.jitlibrary.JITLibraryFragment;
import com.coolcode.jittranslate.utils.Constants;
import com.coolcode.jittranslate.utils.FileReader;
import com.coolcode.jittranslate.utils.FilenameConstructor;
import com.coolcode.jittranslate.viewentities.ClientBook;
import com.coolcode.jittranslate.viewentities.JITBook;

import java.io.File;
import java.util.ArrayList;

public class DataAdapterJITLibrary extends RecyclerView.Adapter<JITBooksViewHolder> {
    private JITLibraryFragment fragment;
    private ArrayList<JITBook> jitBooks;
    private JITDialogViewModel jitDialogViewModel;


    public DataAdapterJITLibrary(Fragment fragment, ArrayList<JITBook> books) {
        this.fragment = (JITLibraryFragment)fragment;
        this.jitBooks = books;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public JITBooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.jit_book_item, parent, false);
        return new JITBooksViewHolder(listItem, fragment);
    }

    @Override
    public void onBindViewHolder(@NonNull JITBooksViewHolder holder, int position) {
        jitDialogViewModel = new ViewModelProvider(fragment.requireActivity()).get(JITDialogViewModel.class);

        ImageView jitBookCoverView = holder.getJitBookCoverView();
        TextView jitBookNameView = holder.getJitBookNameView();
        TextView jitBookAuthorView = holder.getJitBookAuthorView();
        Button downloadButton = holder.getDownloadButton();
        ImageView downloadedIcon = holder.getDownloadedIcon();

        JITBook jitBook = jitBooks.get(position);
        Log.d("jit", jitBook.getAuthor());
        Log.d("jit", jitBook.getName());

        Glide.with(this.fragment.getContext()).load(this.fragment.getBookCoverRef(jitBook.getCoverFileName())).into(jitBookCoverView);
        jitBookAuthorView.setText(jitBook.getAuthor());
        jitBookNameView.setText(jitBook.getName());
        if (jitBook.isDownloaded()) {
            downloadButton.setVisibility(View.GONE);
            downloadedIcon.setVisibility(View.VISIBLE);
        } else {
            downloadButton.setVisibility(View.VISIBLE);
            downloadedIcon.setVisibility(View.GONE);
        }
        downloadButton.setOnClickListener(button-> {
                fragment.openDialog(jitBook);
                jitDialogViewModel.getBook().observe(fragment.getViewLifecycleOwner(), new androidx.lifecycle.Observer<JITBook>() {
                @Override
                public void onChanged(JITBook book) {
                    if (jitBook.isDownloaded()) {
                        downloadButton.setVisibility(View.GONE);
                        downloadedIcon.setVisibility(View.VISIBLE);
                    }
                }
            });

        });
    }

    @Override
    public int getItemCount() {
        return jitBooks.size();
    }
}
