package com.coolcode.jittranslate.ui.bookview;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.coolcode.jittranslate.dbentities.BookDBModel;
import com.coolcode.jittranslate.utils.Constants;
import com.coolcode.jittranslate.utils.FB2BookElements;
import com.coolcode.jittranslate.utils.FileReader;
import com.coolcode.jittranslate.utils.FilenameConstructor;
import com.coolcode.jittranslate.viewentities.ClientBook;
import com.kursx.parser.fb2.FictionBook;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

public class BookViewModel extends AndroidViewModel {
    private FB2BookElements fb2Book;
    private ClientBook clientBook = new ClientBook(Constants.testBookName, Constants.testBookAuthor);
    private BookDBModel bookDBModel ;

    private final MutableLiveData<ClientBook> selectedClientBook = new MutableLiveData<>();

    public BookViewModel(@NonNull Application application) {
        super(application);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void selectClientBook(ClientBook clientBook) {
        this.clientBook = clientBook;
        this.createBookDB();
        this.clientBook.setPage(this.getBookPage());

        if (clientBook.getDataPages() == null) {
            this.setClientBookPages();
        }
        selectedClientBook.setValue(clientBook);
    }

    public LiveData<ClientBook> getSelectedClientBook() {
        return selectedClientBook;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setClientBookPages() {
        String bookFilename = new FilenameConstructor().constructBookFileName(this.clientBook.getAuthor(), this.clientBook.getName());
        Log.d(Constants.clientBookKey, bookFilename);
        FileReader fileReader = new FileReader(getApplication().getAssets(), getApplication().getExternalFilesDir(null));
        readBook(fileReader.createFile(Constants.clientsBooksDir, bookFilename));
        this.clientBook.setDataPages(this.fb2Book.getPages());
        this.clientBook.setBinaries(this.fb2Book.getBook().getBinaries());
    }

    private void createBookDB() {
        this.bookDBModel = new BookDBModel(this.clientBook.getName(), this.clientBook.getAuthor());

    }

    public void saveBookPage(int page) {
        try {
            this.bookDBModel.savePage(page);
        } catch (Exception e) {
            Log.d("saving page", e.getMessage());
        }

    }

    private int getBookPage() {
        try {
            return this.bookDBModel.getPage();
        }
        catch (Exception e) {
            Log.d("getting page", e.getMessage());
        }
        return 1;
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
}
