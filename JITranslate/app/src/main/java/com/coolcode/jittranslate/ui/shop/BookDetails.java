package com.coolcode.jittranslate.ui.shop;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.coolcode.jittranslate.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.Objects;


public class BookDetails extends Fragment {
    private static final String THUMBNAIL = "thumbnail";
    private static final String TITLE = "title";
    private static final String CATEGORIES = "categories";
    private static final String RATING = "rating";
    private static final String AUTHORS = "authors";
    private static final String PRICE = "price";
    private static final String BUY = "buy";
    private static final String PREWIEW = "preview";
    private static final String DESCRIPTION = "description";


    private String thumbnail = "";
    private String title = "";
    private String categories = "";
    private String rating = "";
    private String authors = "";
    private String price = "";
    private String buy = "";
    private String preview = "";
    private String description = "";

    public BookDetails() {
    }

    public static Fragment newInstance(String book_thumbnail, String book_title, String book_category,
                                       String book_rating, String book_author, String book_price, String book_buy,
                                       String book_preview, String book_description) {
        BookDetails fragment = new BookDetails();
        Bundle args = new Bundle();
        Log.d("CHOSEN FROM BOOKDETAILS", book_title);
        args.putString(THUMBNAIL, book_thumbnail);
        args.putString(TITLE, book_title);
        args.putString(CATEGORIES, book_category);
        args.putString(RATING, book_rating);
        args.putString(AUTHORS, book_author);
        args.putString(PRICE, book_price);
        args.putString(BUY, book_buy);
        args.putString(PREWIEW, book_preview);
        args.putString(DESCRIPTION, book_description);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            thumbnail = getArguments().getString(THUMBNAIL);
            title = getArguments().getString(TITLE);
            categories = getArguments().getString(CATEGORIES);
            rating = getArguments().getString(RATING);
            authors = getArguments().getString(AUTHORS);
            price = getArguments().getString(PRICE);
            buy = getArguments().getString(BUY);
            preview = getArguments().getString(PREWIEW);
            description = getArguments().getString(DESCRIPTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_details, container, false);
        CollapsingToolbarLayout collapsingToolbarLayout = view.findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitleEnabled(true);
        collapsingToolbarLayout.setTitle(title);


        ImageView ivThumbnail = view.findViewById(R.id.detail_book_image);
        TextView tvTitle = view.findViewById(R.id.detail_book_title);
        TextView tvCategory = view.findViewById(R.id.detail_book_categories);
        RatingBar Rating = view.findViewById(R.id.detail_book_rating);
        TextView tvAuthors = view.findViewById(R.id.detail_book_authors);
        TextView tvPrice = view.findViewById(R.id.book_price);
        final TextView tvBuy = view.findViewById(R.id.buy_book);
        TextView tvPreview = view.findViewById(R.id.preview_book);
        TextView tvDescription = view.findViewById(R.id.book_description);

        Glide.with(Objects.requireNonNull(this.getContext())).load(thumbnail).into(ivThumbnail);
        tvTitle.setText(title);
        tvAuthors.setText(authors);
        tvDescription.setText(description);
        tvCategory.setText(categories);
        tvPrice.setText(price);
        Rating.setRating(Float.parseFloat(rating));

        if (price.equals("Free")) {
            tvBuy.setVisibility(View.INVISIBLE);

        } else {
            tvBuy.setVisibility(View.VISIBLE);
        }

        final String finalBuy = buy;
        tvBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(finalBuy));
                startActivity(i);
            }

        });


        final String finalPreview = preview;
        tvPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!finalPreview.isEmpty() || !finalPreview.equals(" ")) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(finalPreview));
                    startActivity(i);
                } else {
                    Toast.makeText(BookDetails.this.getContext(), "PreviewLink Not Available", Toast.LENGTH_LONG).show();
                }

            }
        });

        return view;
    }
}
