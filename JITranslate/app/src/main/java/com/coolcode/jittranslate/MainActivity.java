package com.coolcode.jittranslate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.coolcode.jittranslate.fragments.bookview.BookViewFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.fragment_container, new BookViewFragment(), "tag");
            transaction.commit();
        }
        Log.d("activity", "onCreate");
    }

//    @Override
//    public void onAttachFragment(@NonNull Fragment fragment) {
//        if (fragment instanceof BookViewFragment) {
//            ((BookViewFragment) fragment).setEventListenerActivity(this);
//        }
//    }
//
//    @Override
//    public void onWordSelected(String word) {
//
//    }
}
