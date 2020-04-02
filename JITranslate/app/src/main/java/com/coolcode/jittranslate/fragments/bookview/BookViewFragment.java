package com.coolcode.jittranslate.fragments.bookview;

import com.coolcode.jittranslate.utils.Constants;
import com.coolcode.jittranslate.utils.FileReader;
import com.kursx.parser.fb2.Element;
import com.kursx.parser.fb2.FictionBook;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coolcode.jittranslate.EventListenerActivity;
import com.coolcode.jittranslate.R;
import com.kursx.parser.fb2.Section;

import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

public class BookViewFragment extends Fragment {
    private EventListenerActivity activity;
    private ArrayList<Element> data;

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
        DataAdapterBookView adapter;
        if (data == null) {
            FileReader fileReader = new FileReader(this.getActivity().getAssets(), getActivity().getCacheDir(),Constants.testFb2File);
            data = readBook(fileReader.createFile());
        }
        adapter = new DataAdapterBookView(this, data);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        return mainView;
    }

    @RequiresApi(api =
            Build.VERSION_CODES.N)
    private ArrayList<Element> readBook(File file) {
        ArrayList<Element> pages = new ArrayList<>();
        try {
            FictionBook fictionBook = new FictionBook(file);
            final ArrayList<Section> sections = fictionBook.getBody().getSections();
            sections.forEach(section -> {
                ArrayList<Element> sectionPages = section.getElements();
                ArrayList<Element> newSectionPages = new ArrayList<>();
                sectionPages.forEach(sectionPage -> {
                    if (sectionPage.getText() != null) {
                        newSectionPages.add(sectionPage);
                    }
                });
                    pages.addAll(newSectionPages);
            });
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return pages;
    }

}
