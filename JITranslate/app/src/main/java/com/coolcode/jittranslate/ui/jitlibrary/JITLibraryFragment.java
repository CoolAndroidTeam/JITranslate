package com.coolcode.jittranslate.ui.jitlibrary;

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
import com.coolcode.jittranslate.viewentities.JITBook;
import com.coolcode.jittranslate.views.clientlibrary.DataAdapterClientsLibrary;
import com.coolcode.jittranslate.views.jitlibrary.DataAdapterJITLibrary;

import java.util.ArrayList;

public class JITLibraryFragment extends Fragment {

    private JITLibraryViewModel jitLibraryViewModel;
    private ArrayList<JITBook> booksListData;


    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        jitLibraryViewModel = new ViewModelProvider(requireActivity()).get(JITLibraryViewModel.class);

        View mainView = inflater.inflate(R.layout.fragment_client_library, container, false);

        RecyclerView recyclerView = mainView.findViewById(R.id.client_books_list);

        jitLibraryViewModel.getJITBookList().observe(getViewLifecycleOwner(), new androidx.lifecycle.Observer<ArrayList<JITBook>>() {
            @Override
            public void onChanged(ArrayList<JITBook> booksList) {
                booksListData = booksList;
                DataAdapterJITLibrary adapter = new DataAdapterJITLibrary(JITLibraryFragment.this, booksList);

                recyclerView.setAdapter(adapter);
                int spanCount = getResources().getInteger(R.integer.span_count);
                GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), spanCount);
                recyclerView.setLayoutManager(layoutManager);
            }
        });
        return mainView;
    }
}
