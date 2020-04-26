package com.coolcode.jittranslate;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
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

import com.coolcode.jittranslate.ui.shop.BookDetails;
import com.coolcode.jittranslate.ui.shop.ShopFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class BottomMenuActivity extends AppCompatActivity implements ShopFragment.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bottom_menu);
        NavigationView navView = findViewById(R.id.nav_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_library, R.id.navigation_shop, R.id.navigation_study,R.id.navigation_forum)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


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
