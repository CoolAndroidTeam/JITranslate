package com.coolcode.jittranslate.views.clientlibrary;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.coolcode.jittranslate.R;
import com.coolcode.jittranslate.ui.bookview.BookViewFragment;
import com.coolcode.jittranslate.ui.clientslibrary.ClientsLibraryFragment;
import com.coolcode.jittranslate.viewentities.ClientBook;

public class ClientsBooksViewHolder extends RecyclerView.ViewHolder {

    private ImageView clientBookCoverView;
    private TextView clientBookNameView;
    private TextView clientBookAuthorView;

    public ClientsBooksViewHolder(@NonNull View itemView) {
        super(itemView);
        clientBookAuthorView = itemView.findViewById(R.id.client_book_author);
        clientBookNameView = itemView.findViewById(R.id.client_book_name);
        clientBookCoverView = itemView.findViewById(R.id.client_book_cover);
        itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", new ClientBook((String) clientBookNameView.getText(), (String) clientBookAuthorView.getText()));
            Navigation.findNavController(itemView).navigate(R.id.navigation_bookview, bundle);
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
