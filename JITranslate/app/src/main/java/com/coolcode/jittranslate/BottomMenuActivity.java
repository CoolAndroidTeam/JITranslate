package com.coolcode.jittranslate;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.coolcode.jittranslate.ui.shop.BookDetails;
import com.coolcode.jittranslate.ui.shop.ShopFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class BottomMenuActivity extends AppCompatActivity implements ShopFragment.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_menu);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_library, R.id.navigation_shop, R.id.navigation_study,R.id.navigation_forum)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


    }


    @Override
    public void onItemSelected(String book_thumbnail, String book_title, String book_category,
                               String book_rating, String book_author, String book_price,
                               String book_buy, String book_preview, String book_description) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_container);
        System.out.println("FRAGMENT FROM ACTIVITY  " +  currentFragment);
//        if (currentFragment instanceof ShopFragment) {
        System.out.println("CHOSEN FROM ACTIVITY  " + book_title);
        fragmentManager.beginTransaction().replace(R.id.shop_container, BookDetails.newInstance(book_thumbnail,
                book_title, book_category, book_rating, book_author, book_price, book_buy, book_preview,
                book_description))
                .addToBackStack(null)
                .commit();
//        }


    }
}
