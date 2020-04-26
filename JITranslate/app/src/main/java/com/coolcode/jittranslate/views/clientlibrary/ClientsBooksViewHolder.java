package com.coolcode.jittranslate.views.clientlibrary;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.coolcode.jittranslate.R;
import com.coolcode.jittranslate.ui.bookview.BookViewFragment;
import com.coolcode.jittranslate.ui.clientslibrary.ClientsLibraryFragment;

public class ClientsBooksViewHolder extends RecyclerView.ViewHolder {

    private ImageView clientBookCoverView;
    private TextView clientBookNameView;
    private TextView clientBookAuthorView;

    public ClientsBooksViewHolder(@NonNull View itemView, Fragment fragment) {
        super(itemView);
        clientBookAuthorView = itemView.findViewById(R.id.client_book_author);
        clientBookNameView = itemView.findViewById(R.id.client_book_name);
        clientBookCoverView = itemView.findViewById(R.id.client_book_cover);
        itemView.setOnClickListener(v -> {
            ((ClientsLibraryFragment)fragment).getClientLibraryListenerActivity()
                    .onBookSelected((String) clientBookNameView.getText(), (String) clientBookAuthorView.getText());
        });
    }

    public ImageView getClientBookCoverView() {
        return clientBookCoverView;
    }

    public TextView getClientBookAuthorView() {
        return clientBookAuthorView;
    }

    public TextView getClientBookNameView() {
        return clientBookNameView;
    }
}
