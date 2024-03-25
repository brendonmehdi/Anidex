package com.example.anidex;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anidex.Models.Anime;
import com.example.anidex.Models.Manga;
import com.example.anidex.RecyclerViewSearch.AnimeMangaAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchFragment extends Fragment {

    private KitsuService kitsuService;
    private AnimeMangaAdapter adapter;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrofit setup
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://kitsu.io/api/edge/") // Base URL
                .addConverterFactory(GsonConverterFactory.create()) // JSON converter
                .build();

        // Create KitsuService
        kitsuService = retrofit.create(KitsuService.class);

        adapter = new AnimeMangaAdapter(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.searchRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // You can implement live search here if needed
                return false;
            }
        });

        return view;
    }

    private void performSearch(String query) {
        Call<List<Anime>> animeCall = kitsuService.searchAnime(query, 10);
        Call<List<Manga>> mangaCall = kitsuService.searchManga(query, 10);

        animeCall.enqueue(new Callback<List<Anime>>() {
            @Override
            public void onResponse(Call<List<Anime>> call, Response<List<Anime>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Anime> animeResults = response.body();
                    adapter.addResults(animeResults);
                }
            }

            @Override
            public void onFailure(Call<List<Anime>> call, Throwable t) {
                // Handle failure
            }
        });

        mangaCall.enqueue(new Callback<List<Manga>>() {
            @Override
            public void onResponse(Call<List<Manga>> call, Response<List<Manga>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Manga> mangaResults = response.body();
                    adapter.addResults(mangaResults);
                }
            }

            @Override
            public void onFailure(Call<List<Manga>> call, Throwable t) {
                // Handle failure
            }
        });
    }

}
