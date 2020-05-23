package com.coolcode.jittranslate.views.clientlibrary;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.coolcode.jittranslate.R;
import com.coolcode.jittranslate.ui.clientslibrary.ClientsLibraryFragment;
import com.coolcode.jittranslate.utils.Constants;
import com.coolcode.jittranslate.utils.FileReader;
import com.coolcode.jittranslate.utils.FilenameConstructor;
import com.coolcode.jittranslate.viewentities.ClientBook;

import java.io.File;
import java.util.ArrayList;

public class DataAdapterClientsLibrary extends RecyclerView.Adapter<ClientsBooksViewHolder> {
    private ClientsLibraryFragment fragment;
    private ArrayList<ClientBook> clientBooks;

    public DataAdapterClientsLibrary(Fragment fragment, ArrayList<ClientBook> books) {
        this.fragment = (ClientsLibraryFragment)fragment;
        this.clientBooks = books;
    }

    @NonNull
    @Override
    public ClientsBooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_book_item, parent, false);
        return new ClientsBooksViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientsBooksViewHolder holder, int position) {
        ImageView clientBookCoverView = holder.getClientBookCoverView();
        TextView clientBookNameView = holder.getClientBookNameView();
        TextView clientBookAuthorView = holder.getClientBookAuthorView();

        ClientBook clientBook = clientBooks.get(position);
        clientBookAuthorView.setText(clientBook.getAuthor());
        clientBookNameView.setText(clientBook.getName());

        String imageFilename = new FilenameConstructor().constructCoverFileName(clientBook.getAuthor(), clientBook.getName());
        File coverFile = new FileReader(this.fragment.getActivity().getAssets(), this.fragment.getActivity().getExternalFilesDir(null))
                .createFile(Constants.clientsBooksCoversDir,imageFilename);
        if (coverFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(String.valueOf(coverFile));
            clientBookCoverView.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return clientBooks.size();
    }
}
