package com.example.anidex.Favs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anidex.Database.DatabaseHelper;
import com.example.anidex.Models.Anime;
import com.example.anidex.R;

import java.util.List;


public class FavFragment extends Fragment {
    private RecyclerView recyclerView;
    private FavoritesAdapter adapter;
    private List<Object> favoriteAnimes;
    private DatabaseHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav, container, false);
        recyclerView = view.findViewById(R.id.favoritesRecyclerView);

        db = new DatabaseHelper(getContext());
        // Adjust this line to use the generalized method, if you have such in FavoritesManager
        favoriteAnimes = db.getAllFavorites("anime"); // Assuming this method now exists and returns List<Anime>
        adapter = new FavoritesAdapter(favoriteAnimes, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        refreshFavorites();
    }

    private void refreshFavorites() {
        favoriteAnimes = db.getAllFavorites("anime"); // Adjust as necessary for your implementation
        adapter = new FavoritesAdapter(favoriteAnimes, getContext());
        recyclerView.setAdapter(adapter);
    }


}
