package com.coolcode.jittranslate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.coolcode.jittranslate.fragments.bookview.BookViewFragment;
import com.coolcode.jittranslate.utils.Constants;

public class MainActivity extends AppCompatActivity implements EventListenerActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setDisplayMetrics();

        if (savedInstanceState == null) {
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.fragment_container, new BookViewFragment(), "tag");
            transaction.commit();
        }
        Log.d("activity", "onCreate");
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        if (fragment instanceof BookViewFragment) {
            ((BookViewFragment) fragment).setEventListenerActivity(this);
        }
    }

    @Override
    public void onWordSelected(String word) {

    }

    private void setDisplayMetrics() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Constants.screenHeight = metrics.heightPixels;
        Constants.screenWidth = metrics.widthPixels;
        Constants.currentTextSize = getResources().getDimensionPixelSize(R.dimen.text_standart);
    }
}