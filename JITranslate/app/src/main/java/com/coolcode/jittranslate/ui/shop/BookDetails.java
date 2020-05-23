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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        Book book = (Book) bundle.getSerializable("data");
        if (getArguments() != null) {
            thumbnail = book.getmThumbnail();
            title = book.getmTitle();
            categories = book.getmCategory();
            rating = book.getmRating();
            authors = book.getmAuthors();
            price = book.getmPrice();
            buy = book.getmBuyLink();
            preview = book.getmPreviewLink();
            description = book.getmDescription();
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

        Glide.with(this.requireContext()).load(thumbnail).into(ivThumbnail);
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
