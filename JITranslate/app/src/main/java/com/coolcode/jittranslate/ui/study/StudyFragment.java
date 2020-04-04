package com.coolcode.jittranslate.ui.study;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.coolcode.jittranslate.R;

public class StudyFragment extends Fragment {

    private StudyViewModel studyViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        studyViewModel =
                ViewModelProviders.of(this).get(StudyViewModel.class);
        View root = inflater.inflate(R.layout.fragment_study, container, false);
        final TextView textView = root.findViewById(R.id.text_study);
        studyViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
