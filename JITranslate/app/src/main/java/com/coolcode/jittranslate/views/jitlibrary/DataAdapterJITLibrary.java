package com.coolcode.jittranslate.views.jitlibrary;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.coolcode.jittranslate.R;
import com.coolcode.jittranslate.ui.clientslibrary.ClientsLibraryFragment;
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

    public DataAdapterJITLibrary(Fragment fragment, ArrayList<JITBook> books) {
        this.fragment = (JITLibraryFragment)fragment;
        this.jitBooks = books;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public JITBooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_book_item, parent, false);
        return new JITBooksViewHolder(listItem, fragment);
    }

    @Override
    public void onBindViewHolder(@NonNull JITBooksViewHolder holder, int position) {
        ImageView jitBookCoverView = holder.getJitBookCoverView();
        TextView jitBookNameView = holder.getJitBookNameView();
        TextView jitBookAuthorView = holder.getJitBookAuthorView();
        Button downloadButton = holder.getDownloadButton();
        ImageView downloadedIcon = holder.getDownloadedIcon();

        JITBook jitBook = jitBooks.get(position);
        jitBookAuthorView.setText(jitBook.getAuthor());
        jitBookNameView.setText(jitBook.getName());
        if (jitBook.isDownloaded()) {
            downloadButton.setVisibility(View.GONE);
            downloadedIcon.setVisibility(View.VISIBLE);
        } else {
            downloadButton.setVisibility(View.VISIBLE);
            downloadedIcon.setVisibility(View.GONE);
        }

//        String imageFilename = new FilenameConstructor().constructCoverFileName(jitBook.getAuthor(), clientBook.getName());
//        File coverFile = new FileReader(this.fragment.getActivity().getAssets(), this.fragment.getActivity().getExternalFilesDir(null))
//                .createFile(Constants.clientsBooksCoversDir,imageFilename);
//        if (coverFile.exists()) {
//            Bitmap bitmap = BitmapFactory.decodeFile(String.valueOf(coverFile));
//            clientBookCoverView.setImageBitmap(bitmap);
//        }
    }

    @Override
    public int getItemCount() {
        return jitBooks.size();
    }
}
