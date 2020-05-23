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


        View mainView = inflater.inflate(R.layout.fragment_client_library, container, false);

        RecyclerView recyclerView = mainView.findViewById(R.id.client_books_list);

        DataAdapterClientsLibrary adapter;
        if (data == null) {
            createData(BookDBModel.getAllBooks());
        }
        adapter = new DataAdapterClientsLibrary(this, data);
        recyclerView.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);

        if (data.size() == 0) {
            TextView textView = mainView.findViewById(R.id.text_library);
            // for live data
            clientsLibraryViewModel.getText().observe(getViewLifecycleOwner(), s -> textView.setText(s));
        }

        return mainView;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void createData(ArrayList<BookDBModel> dbbooks) {

        data = new ArrayList<>();
        for (int i=0; i<dbbooks.size(); i++) {
            BookDBModel bookDBModel = dbbooks.get(i);
            this.data.add(new ClientBook(bookDBModel.getName(), bookDBModel.getAuthor()));
        }
    }
}
