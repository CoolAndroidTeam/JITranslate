package com.coolcode.jittranslate.ui.clientslibrary;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coolcode.jittranslate.R;
import com.coolcode.jittranslate.ui.bookview.BookViewModel;
import com.coolcode.jittranslate.viewentities.ClientBook;
import com.coolcode.jittranslate.views.clientlibrary.DataAdapterClientsLibrary;

import java.util.ArrayList;

public class ClientsLibraryFragment extends Fragment {

    private ClientsLibraryViewModel clientsLibraryViewModel;
    private BookViewModel bookViewModel;
    private ArrayList<ClientBook> booksListData;


    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        clientsLibraryViewModel = new ViewModelProvider(requireActivity()).get(ClientsLibraryViewModel.class);
        bookViewModel =  new ViewModelProvider(requireActivity()).get(BookViewModel.class);

        View mainView = inflater.inflate(R.layout.fragment_client_library, container, false);

        RecyclerView recyclerView = mainView.findViewById(R.id.client_books_list);

        clientsLibraryViewModel.getClientBookList().observe(getViewLifecycleOwner(), new androidx.lifecycle.Observer<ArrayList<ClientBook>>() {
            @Override
            public void onChanged(ArrayList<ClientBook> booksList) {
                booksListData = booksList;
                DataAdapterClientsLibrary adapter = new DataAdapterClientsLibrary(ClientsLibraryFragment.this, booksList);

                recyclerView.setAdapter(adapter);
                int spanCount = getResources().getInteger(R.integer.span_count);
                GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), spanCount);
                recyclerView.setLayoutManager(layoutManager);
            }
        });
        return mainView;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setSelectedBook (ClientBook book) {
        bookViewModel.selectClientBook(book);
    }

}
