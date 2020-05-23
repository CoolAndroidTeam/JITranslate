package com.coolcode.jittranslate.ui.bookview;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coolcode.jittranslate.R;
import com.coolcode.jittranslate.utils.TextOrPicture;
import com.coolcode.jittranslate.viewentities.ClientBook;
import com.coolcode.jittranslate.views.bookview.DataAdapterBookView;
import com.kursx.parser.fb2.Binary;

import java.util.ArrayList;

import me.zhanghai.android.fastscroll.FastScrollerBuilder;

public class BookViewFragment extends Fragment {
    private ClientBook clientBook;

    private BookViewModel bookViewModel;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View mainView = inflater.inflate(R.layout.bookview, container, false);
        RecyclerView recyclerView = mainView.findViewById(R.id.pages_list);
        setFastScroll(mainView, recyclerView);
        addScrollListener(recyclerView);

        bookViewModel =  new ViewModelProvider(requireActivity()).get(BookViewModel.class);
        bookViewModel.getSelectedClientBook().observe(getViewLifecycleOwner(), new androidx.lifecycle.Observer<ClientBook>() {
            @Override
            public void onChanged(ClientBook bookFragments) {
                clientBook = bookFragments;
                DataAdapterBookView adapter = new DataAdapterBookView(getTargetFragment(), bookFragments.getDataPages());

                recyclerView.setAdapter(adapter);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                ActionBar bar = ((AppCompatActivity) getActivity()).getSupportActionBar();
                bar.setTitle(bookFragments.getName());
                recyclerView.scrollToPosition(bookFragments.getPage()-1);
            }
        });

        return mainView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem item = menu.findItem(R.id.menu_open);
        item.setVisible(false);
        super.onCreateOptionsMenu(menu,inflater);
    }

    public String getBinary(String imageKey) {
        return this.clientBook.getBinaries().get(imageKey).getBinary();
    }


    private void addScrollListener(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                ActionBar bar = ((AppCompatActivity) getActivity()).getSupportActionBar();
                if (newState == 0) {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
                    int pos = linearLayoutManager.findFirstVisibleItemPosition();
                    Log.d("pos", String.valueOf(pos));
                    bookViewModel.saveBookPage(pos+1);
                }
            }
        });
    }

    private void setFastScroll(View mainView, RecyclerView recyclerView) {
        FastScrollerBuilder fastScrollerBuilder = new FastScrollerBuilder(recyclerView);
        Context context = mainView.getContext();
        fastScrollerBuilder.setTrackDrawable(AppCompatResources.getDrawable(context, R.drawable.fs_thumb_background));
        fastScrollerBuilder.setThumbDrawable(AppCompatResources.getDrawable(context, R.drawable.fs_thumb));
        fastScrollerBuilder.setPadding(10, 0, 0, 0);
        fastScrollerBuilder.build();
    }


}
