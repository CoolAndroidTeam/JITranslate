package com.coolcode.jittranslate.ui.jitlibrary;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.Navigator;
import androidx.navigation.NavigatorProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.coolcode.jittranslate.R;
import com.coolcode.jittranslate.ui.clientslibrary.ClientsLibraryViewModel;
import com.coolcode.jittranslate.viewentities.JITBook;
import com.coolcode.jittranslate.views.jitlibrary.DataAdapterJITLibrary;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class JITDialogFragment extends DialogFragment {

    private JITDialogViewModel dialogViewModel;
    private ClientsLibraryViewModel clientsLibraryViewModel;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        dialogViewModel = new ViewModelProvider(requireActivity()).get(JITDialogViewModel.class);
        clientsLibraryViewModel = new ViewModelProvider(requireActivity()).get(ClientsLibraryViewModel.class);

        View mainView = inflater.inflate(R.layout.jit_library_dialog, container, false);
        TextView nameView = mainView.findViewById(R.id.dialog_book_name);
        TextView authorView = mainView.findViewById(R.id.dialog_book_author);
        ImageView coverView = mainView.findViewById(R.id.dialog_book_cover);
        Button download = mainView.findViewById(R.id.dialog_download_btn);
        Button cancel = mainView.findViewById(R.id.dialog_cancel_btn);
        cancel.setOnClickListener(
                button -> dismiss());

        dialogViewModel.getBook().observe(getViewLifecycleOwner(), new androidx.lifecycle.Observer<JITBook>() {
            @Override
            public void onChanged(JITBook jitBook) {
                nameView.setText(jitBook.getName());
                authorView.setText(jitBook.getAuthor());
                Glide.with(getContext()).load(getBookCoverRef(jitBook.getCoverFileName())).into(coverView);
                download.setOnClickListener(button-> {
                    dialogViewModel.saveBook(jitBook.getCoverFileName());
                    jitBook.setDownloaded(true);
                    dialogViewModel.setChosenBook(jitBook);
                    clientsLibraryViewModel.updateBookList(jitBook);
                    dismiss();
                });
            }
        });

        return mainView;
    }

    public StorageReference getBookCoverRef(String bookCover) {
        return dialogViewModel.getBookReference(bookCover);
    }

}
