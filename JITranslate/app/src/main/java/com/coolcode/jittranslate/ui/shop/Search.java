package com.coolcode.jittranslate.ui.shop;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.coolcode.jittranslate.ui.shop.BookBuilder.jsonParse;

public class Search {
    private static String API_KEY = "&key=AIzaSyAdJ4T-Vp1msF88y4UyNdPGmcWL_aXpq_c";
    private static String REQUEST = "https://www.googleapis.com/books/v1/volumes?q=";

    static void search(String query, ShopViewModel shopViewModel, boolean is_connected,  RecyclerView recyclerView, Context c) {


        if (!is_connected) {
            shopViewModel.setValue("Check your connection or try again");
            return;
        }

        if (query.equals("  ") || query.isEmpty()) {
            shopViewModel.setValue("Please enter your book");
        }

        String finalQuery = query.replace(" ", "+");
        finalQuery += API_KEY;
        Uri baseUri = Uri.parse(REQUEST);
        Uri finalUri = Uri.parse(baseUri + finalQuery);
        Uri.Builder builder  = finalUri.buildUpon();
        String finalRequest = builder.toString();

        Log.d("Final Request", finalRequest);
        getBooksQuery(finalRequest,shopViewModel, recyclerView, c);

    }

    static void getBooksQuery(String finalRequest, ShopViewModel shopViewModel, RecyclerView recyclerView, Context c) {
        final ShopFragment.RecyclerViewAdapter[] recyclerViewAdapter = new ShopFragment.RecyclerViewAdapter[1];
        String baseUrl = "https://www.googleapis.com/books/v1/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        BookQuery service = retrofit.create(BookQuery.class);

        Call<JsonObject> call = service.getBooks(finalRequest);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (!response.isSuccessful()) {
                    shopViewModel.setValue("code :" + response.code());
                }

                JsonObject root = response.body();

                if (root.get("totalItems").getAsString() != "0") {
                    List<Book> books;
                    books = jsonParse(root);
                    if (books != null && !books.isEmpty()) {
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerViewAdapter[0] = new ShopFragment.RecyclerViewAdapter(books, c);
                        recyclerView.setAdapter(recyclerViewAdapter[0]);
                        //shopViewModel.setValue("OK " + books.get(0));
                    } else {
                        recyclerView.setVisibility(View.INVISIBLE);
                        shopViewModel.setValue("NO MATCHING");
                    }

                } else {
                    recyclerView.setVisibility(View.INVISIBLE);
                    shopViewModel.setValue("NO MATCHING");
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                t.printStackTrace();
                shopViewModel.setValue("onFailure");
            }
        });
    }
}
