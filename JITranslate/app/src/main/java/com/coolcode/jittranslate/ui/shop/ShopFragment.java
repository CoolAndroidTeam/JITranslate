package com.coolcode.jittranslate.ui.shop;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.miguelcatalan.materialsearchview.MaterialSearchView;


import com.coolcode.jittranslate.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class ShopFragment extends Fragment {

    private ShopViewModel shopViewModel;
    private MaterialSearchView searchView;
    private Uri baseUri;
    private Uri finalUri;
    private Uri.Builder builder;
    private BookQuery service;
    private final String REQUEST = "https://www.googleapis.com/books/v1/volumes?q=";
    private final String API_KEY = "&key=AIzaSyAdJ4T-Vp1msF88y4UyNdPGmcWL_aXpq_c";
    private String finalQuery;
    private String finalRequest;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private OnItemSelectedListener mListener;


    public interface OnItemSelectedListener {
        void onItemSelected(String book_thumbnail, String book_title, String book_category,
                            String book_rating, String book_author, String book_price,
                            String book_buy, String book_preview, String book_description);
    }

    @Override
    public void onAttach(@NonNull Context activity) {
        super.onAttach(activity);
        mListener = (OnItemSelectedListener) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shopViewModel = new ViewModelProvider(this).get(ShopViewModel.class);
        View root = inflater.inflate(R.layout.fragment_shop, container, false);
        final TextView textView = root.findViewById(R.id.text_shop);
        searchView = root.findViewById(R.id.search_view);
        builder = new Uri.Builder();

        recyclerView = root.findViewById(R.id.book_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        String baseUrl = "https://www.googleapis.com/books/v1/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(BookQuery.class);

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                shopViewModel.setValue("");
                search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        shopViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(true);
        searchView.setMenuItem(item);
        super.onCreateOptionsMenu(menu,inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NavController navController = Navigation.findNavController(ShopFragment.this.requireActivity(), R.id.nav_host_fragment);
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
//    }
//    @Override
//    public void onStop() {
//        super.onStop();
//        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
//    }

    private boolean readNetworkState(Context context) {

        boolean is_connected;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo info = cm.getActiveNetworkInfo();
        is_connected = info != null && info.isConnectedOrConnecting();

        return is_connected;

    }
    private void search(String query) {

        boolean is_connected = readNetworkState(Objects.requireNonNull(this.getContext()));
        if (!is_connected) {
            shopViewModel.setValue("Check your connection or try again");
            return;
        }

        if (query.equals("  ") || query.isEmpty()) {
            shopViewModel.setValue("Please enter your book");
        }

        finalQuery = query.replace(" ", "+");
        finalQuery += API_KEY;
        baseUri = Uri.parse(REQUEST);
        finalUri = Uri.parse(baseUri + finalQuery);
        builder = finalUri.buildUpon();
        finalRequest = builder.toString();

        System.out.println("Final Request  " + finalRequest);
        getBooksQuery();

    }

    private void getBooksQuery() {
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
                        recyclerViewAdapter = new RecyclerViewAdapter(books, ShopFragment.this.getContext());
                        recyclerView.setAdapter(recyclerViewAdapter);
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
    private List<Book> jsonParse(JsonObject root) {

        List<Book> bookData = new ArrayList<>();

        JsonObject item;
        JsonObject volumeInfo;
        JsonObject imageLinks;
        JsonObject saleInfo;
        JsonObject listPrice;

        JsonArray itemArray;
        JsonArray authorsArray;
        JsonArray categoryArray;

        String title;
        String authors;
        String category;
        String smallThumbnail;
        String avgRating;
        String price;
        String description;
        String buyLink;
        String previewLink;

        itemArray = root.getAsJsonArray("items");
        for (int i = 0; i < itemArray.size(); i++) {

            JsonElement element_item = itemArray.get(i);
            item = element_item.getAsJsonObject();

            volumeInfo = item.getAsJsonObject("volumeInfo");
            JsonElement element_title = volumeInfo.get("title");
            title = element_title.getAsString();


            authors = "";

            if (volumeInfo.has("authors") && !volumeInfo.get("authors").isJsonNull()) {
                authorsArray = volumeInfo.getAsJsonArray("authors");

                if (authorsArray.size() > 1) {
                    for (int j = 0; j < authorsArray.size(); j++) {

                        JsonElement element_author = authorsArray.get(j);
                        authors += element_author.getAsString() + "\n";
                    }
                } else {
                    JsonElement element_author = authorsArray.get(0);
                    authors += element_author.getAsString();
                }
            } else {
                authors = "Unknown";
            }

            category = "";

            if (volumeInfo.has("categories") && !volumeInfo.get("categories").isJsonNull()) {
                categoryArray = volumeInfo.getAsJsonArray("categories");

                if (categoryArray.size() > 1) {
                    for (int j = 0; j < categoryArray.size(); j++) {
                        JsonElement element_category = categoryArray.get(j);
                        category += element_category.getAsString() + "|";


                    }
                } else {
                    JsonElement element_category = categoryArray.get(0);
                    category += element_category.getAsString();
                }
            } else {
                category = "No Category";
            }

            if (volumeInfo.has("averageRating") && !volumeInfo.get("averageRating").isJsonNull()) {
                JsonElement element_rating = volumeInfo.get("averageRating");
                avgRating = element_rating.getAsString();

            } else {
                avgRating = "0";
            }

            if (volumeInfo.has("imageLinks") && !volumeInfo.get("imageLinks").isJsonNull()) {
                imageLinks = volumeInfo.getAsJsonObject("imageLinks");
                JsonElement element_smallThumbnail = imageLinks.get("smallThumbnail");
                smallThumbnail = element_smallThumbnail.getAsString();
            } else {
                smallThumbnail = "https://www.sylvansport.com/wp/wp-content/uploads/2018/11/image-placeholder-1200x800.jpg";
            }

            if (volumeInfo.has("description") && !volumeInfo.get("description").isJsonNull()) {
                JsonElement element_description = volumeInfo.get("description");
                description = element_description.getAsString();

            } else {
                description = "No description for this Book!";
            }

            if (volumeInfo.has("previewLink") && !volumeInfo.get("previewLink").isJsonNull()) {
                JsonElement element_preview = volumeInfo.get("previewLink");
                previewLink = element_preview.getAsString();

            } else {
                previewLink = " ";
            }

            saleInfo = item.getAsJsonObject("saleInfo");

            price = " ";
            buyLink = " ";
            if (saleInfo.has("saleability") && !saleInfo.get("saleability").isJsonNull()) {
                JsonElement element_saleability = saleInfo.get("saleability");
                if (element_saleability.getAsString().equals("FOR_SALE")) {
                    if (saleInfo.has("listPrice")) {
                        listPrice = saleInfo.getAsJsonObject("listPrice");
                        JsonElement element_price = listPrice.get("amount");
                        price = Double.toString(element_price.getAsDouble());
                    }
                    if (saleInfo.has("currencyCode")) {
                        JsonElement element_currencyCode = saleInfo.get("currencyCode");
                        price += element_currencyCode.getAsString();
                    }
                    if(saleInfo.has("buyLink")) {
                        JsonElement element_buyLink = saleInfo.get("buyLink");
                        buyLink = element_buyLink.getAsString();
                    }

                } else {

                    price = "Free";
                    buyLink = " ";
                }

            }

            bookData.add(new Book(smallThumbnail, title, category, avgRating, authors, price, description, buyLink, previewLink));

        }
        return bookData;
    }
    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

        List<Book> books;
        Context context;

        public RecyclerViewAdapter(List<Book> books, Context context) {
            this.books = books;
            this.context = context;
        }


        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view;
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(R.layout.book_item, parent, false);
            final MyViewHolder viewHolder = new MyViewHolder(view);

            viewHolder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = viewHolder.getAdapterPosition();
                    System.out.println("CHOSEN  " +  books.get(index).getmTitle());
                    mListener.onItemSelected(books.get(index).getmThumbnail(), books.get(index).getmTitle(),
                            books.get(index).getmCategory(), books.get(index).getmRating(),
                            books.get(index).getmAuthors(), books.get(index).getmPrice(),
                            books.get(index).getmBuyLink(), books.get(index).getmPreviewLink(),
                            books.get(index).getmDescription());
                }
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

            Book book = books.get(position);

            holder.tvTitle.setText(book.getmTitle());
            holder.tvCategories.setText(book.getmCategory());
            try {
                holder.ratingBar.setRating(Float.parseFloat(book.getmRating()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            holder.tvAuthors.setText(book.getmAuthors());

            String imgProtocol = book.getmThumbnail().replace("http", "http");
            Glide.with(context)
                    .load(imgProtocol)
                    .placeholder(R.drawable.placeholder_image)
                    .into(holder.imgThumbnail);

        }

        @Override
        public int getItemCount() {
            return books.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tvTitle, tvCategories, tvAuthors;
            ImageView imgThumbnail;
            RatingBar ratingBar;
            LinearLayout container;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                tvTitle = itemView.findViewById(R.id.book_title);
                tvCategories = itemView.findViewById(R.id.book_categories);
                tvAuthors = itemView.findViewById(R.id.book_authors);
                imgThumbnail = itemView.findViewById(R.id.book_image);
                ratingBar = itemView.findViewById(R.id.book_rating);
                container = itemView.findViewById(R.id.container);


            }
        }
    }

}
