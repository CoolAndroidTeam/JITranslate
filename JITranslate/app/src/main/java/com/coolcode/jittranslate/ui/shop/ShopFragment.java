package com.coolcode.jittranslate.ui.shop;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.miguelcatalan.materialsearchview.MaterialSearchView;


import com.coolcode.jittranslate.R;

import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static com.coolcode.jittranslate.ui.shop.Search.search;

public class ShopFragment extends Fragment {

    private ShopViewModel shopViewModel;
    private MaterialSearchView searchView;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private static OnItemSelectedListener mListener;


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

        recyclerView = root.findViewById(R.id.book_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        Context c =  this.getContext();
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                shopViewModel.setValue("");
                boolean is_connected = readNetworkState(c);
                search(query,shopViewModel,is_connected, recyclerView, ShopFragment.this.getContext());
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

    public static class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

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
                    Log.d("CHOSEN", books.get(index).getmTitle());
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
