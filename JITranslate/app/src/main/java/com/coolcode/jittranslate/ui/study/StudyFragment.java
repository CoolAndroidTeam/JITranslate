package com.coolcode.jittranslate.ui.study;

import android.content.Context;
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

import com.coolcode.jittranslate.R;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.awt.font.TextAttribute;
import java.util.List;
import java.util.Objects;

public class StudyFragment extends Fragment {

    private StudyViewModel studyViewModel;
    private SwipePlaceHolderView mSwipeView;
    private Context mContext;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        studyViewModel = new ViewModelProvider(requireActivity()).get(StudyViewModel.class);
        View root = inflater.inflate(R.layout.fragment_study, container, false);
        mSwipeView = root.findViewById(R.id.swipeView);
        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.swipe_out_msg_view));

        studyViewModel.getWordsList().observe(getViewLifecycleOwner(), new androidx.lifecycle.Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> booksList) {
                mContext = requireActivity().getApplicationContext();


                for(Word profile : booksList){
                    mSwipeView.addView(new Card(mContext, profile, mSwipeView));
                }
                root.findViewById(R.id.rejectBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mSwipeView.doSwipe(false);
                    }
                });

                root.findViewById(R.id.acceptBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        studyViewModel.deleteWord((String) ((TextView) mSwipeView.findViewById(R.id.word)).getText(), (String) ((TextView) mSwipeView.findViewById(R.id.translate)).getText());
                        mSwipeView.doSwipe(true);
                    }
                });
            }
        });

        return root;
    }
}
