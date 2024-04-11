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

        favoriteAnimes = db.getAllFavorites("anime");
        adapter = new FavoritesAdapter(favoriteAnimes, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        List<Object> animeFavorites = db.getAllFavorites("anime");
        List<Object> mangaFavorites = db.getAllFavorites("manga");
        favoriteAnimes.clear();
        favoriteAnimes.addAll(animeFavorites);
        favoriteAnimes.addAll(mangaFavorites);
        adapter.notifyDataSetChanged();
    }



}
