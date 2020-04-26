package com.coolcode.jittranslate.ui.clientslibrary;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coolcode.jittranslate.R;
import com.coolcode.jittranslate.dbentities.BookDBModel;
import com.coolcode.jittranslate.viewentities.ClientBook;
import com.coolcode.jittranslate.views.clientlibrary.DataAdapterClientsLibrary;

import java.util.ArrayList;

public class ClientsLibraryFragment extends Fragment {

    private ClientsLibraryViewModel clientsLibraryViewModel;
    private ArrayList<ClientBook> data;


    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        clientsLibraryViewModel = new ViewModelProvider(this).get(ClientsLibraryViewModel.class);

        // for live data
        View mainView = inflater.inflate(R.layout.fragment_client_library, container, false);
//        final TextView textView = mainView.findViewById(R.id.text_library);
//        clientsLibraryViewModel.getText().observe(getViewLifecycleOwner(), s -> textView.setText(s));

        RecyclerView recyclerView = mainView.findViewById(R.id.client_books_list);

        DataAdapterClientsLibrary adapter;
        if (data == null) {
            Log.d("data", "here");
            createData(BookDBModel.getAllBooks());
        }
        adapter = new DataAdapterClientsLibrary(this, data);
        recyclerView.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);

        return mainView;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void createData(ArrayList<BookDBModel> dbbooks) {

        data = new ArrayList<>();
        dbbooks.forEach(bookDBModel -> this.data.add(new ClientBook(bookDBModel.getName(), bookDBModel.getAuthor())));
    }

    public ClientBooksListener getClientLibraryListenerActivity() {
        return (ClientBooksListener)getActivity();
    }
}
