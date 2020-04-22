package com.coolcode.jittranslate.fragments.bookview;

import com.coolcode.jittranslate.utils.Constants;
import com.coolcode.jittranslate.utils.FB2BookElements;
import com.coolcode.jittranslate.utils.FileReader;
import com.coolcode.jittranslate.utils.TextOrPicture;
import com.kursx.parser.fb2.FictionBook;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coolcode.jittranslate.EventListenerActivity;
import com.coolcode.jittranslate.R;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import me.zhanghai.android.fastscroll.FastScrollerBuilder;

public class BookViewFragment extends Fragment {
    private EventListenerActivity activity;
    private ArrayList<TextOrPicture> data;
    private FB2BookElements fb2Book;

    public void setEventListenerActivity(EventListenerActivity activity) {
        this.activity = activity;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.bookview, container, false);
        RecyclerView recyclerView = mainView.findViewById(R.id.pages_list);
        setFastScroll(mainView, recyclerView);
        DataAdapterBookView adapter;
        if (data == null) {
            FileReader fileReader = new FileReader(this.getActivity().getAssets(), getActivity().getCacheDir(),Constants.testFb2File);
            readBook(fileReader.createFile());
            data = this.fb2Book.getPages();
        }
        adapter = new DataAdapterBookView(this, data);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        return mainView;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void readBook(File file) {
        try {
            FictionBook fictionBook = new FictionBook(file);
            fb2Book = new FB2BookElements(fictionBook);

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    private void setFastScroll(View mainView, RecyclerView recyclerView) {
        FastScrollerBuilder fastScrollerBuilder = new FastScrollerBuilder(recyclerView);
        Context context = mainView.getContext();
        fastScrollerBuilder.setTrackDrawable(AppCompatResources.getDrawable(context, R.drawable.fs_thumb_background));
        fastScrollerBuilder.setThumbDrawable(AppCompatResources.getDrawable(context, R.drawable.fs_thumb));
        fastScrollerBuilder.setPadding(10, 0, 0, 0);
        fastScrollerBuilder.build();
    }

    public FB2BookElements getFb2Book() {
        return this.fb2Book;
    }

}
