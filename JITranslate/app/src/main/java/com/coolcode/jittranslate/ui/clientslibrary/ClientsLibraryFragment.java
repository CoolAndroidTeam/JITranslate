package com.coolcode.jittranslate.ui.clientslibrary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.coolcode.jittranslate.R;

public class ClientsLibraryFragment extends Fragment {

    private ClientsLibraryViewModel clientsLibraryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        clientsLibraryViewModel = new ViewModelProvider(this).get(ClientsLibraryViewModel.class);

        // for live data
        View root = inflater.inflate(R.layout.fragment_library, container, false);
        final TextView textView = root.findViewById(R.id.text_library);
        clientsLibraryViewModel.getText().observe(getViewLifecycleOwner(), s -> textView.setText(s));
        return root;


    }
}
