package com.coolcode.jittranslate;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.coolcode.jittranslate.database.JITDataBase;
import com.coolcode.jittranslate.ui.bookview.BookViewFragment;
import com.coolcode.jittranslate.ui.bookview.BookViewListener;
import com.coolcode.jittranslate.ui.clientslibrary.ClientBooksListener;
import com.coolcode.jittranslate.ui.shop.BookDetails;
import com.coolcode.jittranslate.ui.shop.ShopFragment;
import com.coolcode.jittranslate.utils.Constants;
import com.coolcode.jittranslate.utils.FileReader;
import com.coolcode.jittranslate.viewentities.ClientBook;
import com.google.android.material.navigation.NavigationView;

import java.io.File;


public class MainActivity extends AppCompatActivity implements BookViewListener, ClientBooksListener, ShopFragment.OnItemSelectedListener {

    private FragmentManager fragmentManager;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bottom_menu);
        NavigationView navView = findViewById(R.id.nav_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_library, R.id.navigation_shop, R.id.navigation_study,R.id.navigation_forum)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        new JITDataBase(getBaseContext());
        setDisplayMetrics();

        addBooksToExtStorage();

        Log.d("activity", "onCreate");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        MenuItem search_item = menu.findItem(R.id.action_search);
        search_item.setVisible(false);
        return true;
    }

    @SuppressLint("WrongConstant")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_open) {
            DrawerLayout navDrawer = findViewById(R.id.drawer_layout);
            if(!navDrawer.isDrawerOpen(GravityCompat.END)) navDrawer.openDrawer(GravityCompat.END);
            else navDrawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    @Override
    public void onItemSelected(String book_thumbnail, String book_title, String book_category,
                               String book_rating, String book_author, String book_price,
                               String book_buy, String book_preview, String book_description) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_container);
        Log.d("FRAGMENT FROM ACTIVITY", String.valueOf(currentFragment));
        Log.d("CHOSEN FROM ACTIVITY", String.valueOf(book_title));

        fragmentManager.beginTransaction().replace(R.id.shop_container, BookDetails.newInstance(book_thumbnail,
                book_title, book_category, book_rating, book_author, book_price, book_buy, book_preview,
                book_description))
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        if (fragment instanceof BookViewFragment) {
            ((BookViewFragment) fragment).setEventListenerActivity(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("activity", "onDestroy");
    }

    @Override
    public void onWordSelected(String word) {

    }

    private void setDisplayMetrics() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Constants.screenHeight = metrics.heightPixels;
        Constants.screenWidth = metrics.widthPixels;
        Constants.currentTextSize = getResources().getDimensionPixelSize(R.dimen.font_text_standart);
    }

    public NavController getNavController() {
        return navController;
    }

    @Override
    public void onBookSelected(String name, String author) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", new ClientBook(name, author));
        navController.navigate(R.id.navigation_bookview, bundle);
    }

    private void addBooksToExtStorage() {
        File f = new File(getExternalFilesDir(null), Constants.clientsBooksDir);
        if (!f.exists()) {
            f.mkdirs();
        }
        f = new File(getExternalFilesDir(null), Constants.clientsBooksCoversDir);
        if (!f.exists()) {
            f.mkdirs();
        }
        FileReader fileReader = new FileReader(getAssets(), getExternalFilesDir(null));

        fileReader.copyFilesFromAssetsToExternalStorage(Constants.assetsBooksDir, Constants.clientsBooksDir);
        fileReader.copyFilesFromAssetsToExternalStorage(Constants.assetsBooksCoversDir, Constants.clientsBooksCoversDir);
    }
}
