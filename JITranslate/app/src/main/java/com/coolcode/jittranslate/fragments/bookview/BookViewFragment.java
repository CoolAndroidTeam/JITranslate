package com.coolcode.jittranslate.fragments.bookview;

import com.coolcode.jittranslate.utils.Constants;
import com.coolcode.jittranslate.utils.FB2BookElements;
import com.coolcode.jittranslate.utils.FileReader;
import com.kursx.parser.fb2.Element;
import com.kursx.parser.fb2.EmptyLine;
import com.kursx.parser.fb2.FictionBook;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import com.kursx.parser.fb2.Image;
import com.kursx.parser.fb2.P;
import com.kursx.parser.fb2.Section;

import org.w3c.dom.Node;
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
        DataAdapterBookView adapter;
        if (data == null) {
            FileReader fileReader = new FileReader(this.getActivity().getAssets(), getActivity().getCacheDir(),Constants.testFb2File);
            readBook(fileReader.createFile());
            data = this.fb2Book.getAllPages();
            //TextView pageTextView = itemView.findViewById(R.id.page_text);
        }
        adapter = new DataAdapterBookView(this, data);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int width = recyclerView.getMeasuredWidth();
        int height = recyclerView.getMeasuredHeight();
        Log.d("size", String.valueOf(this.fb2Book.getTextSize()));
        Log.d("size", String.valueOf(width));
        Log.d("size", String.valueOf(height));
        Log.d("size", String.valueOf(this.fb2Book.getTextSize()/width));
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

    public FB2BookElements getFb2Book() {
        return this.fb2Book;
    }

}
