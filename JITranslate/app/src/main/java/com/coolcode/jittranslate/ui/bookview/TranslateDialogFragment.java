package com.coolcode.jittranslate.ui.bookview;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.coolcode.jittranslate.R;
import com.coolcode.jittranslate.viewentities.TranslationWord;

public class TranslateDialogFragment extends DialogFragment {

    private TranslateDialogViewModel translateDialogViewModel;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        translateDialogViewModel = new ViewModelProvider(requireActivity()).get(TranslateDialogViewModel.class);

        View mainView = inflater.inflate(R.layout.translate_dialog, container, false);
        TextView translateView = mainView.findViewById(R.id.dialog_translation);
        Button learn = mainView.findViewById(R.id.dialog_learn_btn);
        Button close = mainView.findViewById(R.id.dialog_close_button);
        close.setOnClickListener(
                button -> dismiss());

        translateDialogViewModel.getTranslationWord().observe(getViewLifecycleOwner(), new androidx.lifecycle.Observer<TranslationWord>() {
            @Override
            public void onChanged(TranslationWord translationWord) {

            }
        });

        return mainView;
    }

}