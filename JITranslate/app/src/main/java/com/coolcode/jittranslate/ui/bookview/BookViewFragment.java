package com.coolcode.jittranslate.ui.bookview;

import com.coolcode.jittranslate.MainActivity;
import com.coolcode.jittranslate.dbentities.BookDBModel;
import com.coolcode.jittranslate.utils.Constants;
import com.coolcode.jittranslate.utils.FB2BookElements;
import com.coolcode.jittranslate.utils.FileReader;
import com.coolcode.jittranslate.utils.FilenameConstructor;
import com.coolcode.jittranslate.utils.TextOrPicture;
import com.coolcode.jittranslate.viewentities.ClientBook;
import com.coolcode.jittranslate.views.bookview.DataAdapterBookView;
import com.kursx.parser.fb2.FictionBook;

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

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coolcode.jittranslate.R;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import me.zhanghai.android.fastscroll.FastScrollerBuilder;

public class BookViewFragment extends Fragment {
    private BookViewListener activity;
    private ArrayList<TextOrPicture> data;
    private FB2BookElements fb2Book;
    private ClientBook book = new ClientBook(Constants.testBookName, Constants.testBookAuthor);
    private BookDBModel bookDBModel ;

    public void setEventListenerActivity(BookViewListener activity) {
        this.activity = activity;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        this.book = (ClientBook) bundle.getSerializable("data");
        String bookFilename = new FilenameConstructor().constructBookFileName(this.book.getAuthor(), this.book.getName());
        Log.d("book", bookFilename);
        this.createBookDB();
        View mainView = inflater.inflate(R.layout.bookview, container, false);
        RecyclerView recyclerView = mainView.findViewById(R.id.pages_list);
        setFastScroll(mainView, recyclerView);
        addScrollListener(recyclerView);
        DataAdapterBookView adapter;
        if (data == null) {
            FileReader fileReader = new FileReader(this.getActivity().getAssets(), this.getActivity().getExternalFilesDir(null));
            readBook(fileReader.createFile(Constants.clientsBooksDir, bookFilename));
            data = this.fb2Book.getPages();
        }
        adapter = new DataAdapterBookView(this, data);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        ActionBar bar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        bar.setTitle(book.getName());

        try {
            recyclerView.scrollToPosition(this.bookDBModel.getPage()-1);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return mainView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem item = menu.findItem(R.id.menu_open);
        item.setVisible(false);
        super.onCreateOptionsMenu(menu,inflater);
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
                    bookDBModel.savePage(pos+1);
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

    public FB2BookElements getFb2Book() {
        return this.fb2Book;
    }

    private void createBookDB() {
        this.bookDBModel = new BookDBModel(this.book.getName(), this.book.getAuthor());
    }

    public BookViewListener getBookViewistenerActivity() {
        return (BookViewListener)getActivity();
    }
}
