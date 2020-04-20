package com.coolcode.jittranslate.fragments.bookview;

import com.coolcode.jittranslate.utils.Constants;
import com.coolcode.jittranslate.utils.FB2BookElements;
import com.coolcode.jittranslate.utils.FileReader;
import com.kursx.parser.fb2.Element;
import com.kursx.parser.fb2.EmptyLine;
import com.kursx.parser.fb2.FictionBook;

import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
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
    private ArrayList<String> data_new;
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
            data_new = this.fb2Book.getPages();
            //TextView pageTextView = itemView.findViewById(R.id.page_text);
        }
        adapter = new DataAdapterBookView(this, data, data_new);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

//        DisplayMetrics metrics = new DisplayMetrics();
//        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
//
//        int height = metrics.heightPixels;
//        int width = metrics.widthPixels;
//        double pages = this.fb2Book.getTextSize() / width;
//        Log.d("size", String.valueOf(mainView.getWidth()));
//        Log.d("size", String.valueOf(mainView.getMeasuredWidth()));
//        Log.d("size", String.valueOf(height));
//        Log.d("size", String.valueOf(width));
//
//        Rect bounds = new Rect();
//        Paint paint = new Paint();
//        float scaledSizeInPixels = getResources().getDimensionPixelSize(R.dimen.text_standart);
//        paint.setTextSize(scaledSizeInPixels);
//        paint.getTextBounds(str, 0, str.length(), bounds);
//        final int numLines = (int) Math.ceil((float) bounds.width() / width);
//        final int numLines_1 = (int) Math.ceil(height/ (float) bounds.height());
//        Log.d("size", String.valueOf(numLines_1));
//        Log.d("size", String.valueOf(numLines));
//        Log.d("size", String.valueOf(scaledSizeInPixels));

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
