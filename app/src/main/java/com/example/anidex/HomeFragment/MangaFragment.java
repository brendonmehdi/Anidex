package com.example.anidex.HomeFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anidex.Models.Anime;
import com.example.anidex.Models.Manga;
import com.example.anidex.R;
import com.example.anidex.SearchFragment.KitsuResponse;
import com.example.anidex.SearchFragment.KitsuService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MangaFragment extends Fragment {

    private RecyclerView popularRecyclerView;
    private RecyclerView newRecyclerView;
    private RecyclerView topRankedRecyclerView;
    private RecyclerView trendingRecyclerView;



    private MangaAdapter popularAdapter;
    private MangaAdapter newAdapter;
    private MangaAdapter topRankedAdapter;
    private MangaAdapter trendingAdapter;

    private List<Manga> popularMangaList = new ArrayList<>();
    private List<Manga> newMangaList = new ArrayList<>();
    private List<Manga> topRankedMangaList = new ArrayList<>();
    private List<Manga> trendingMangaList = new ArrayList<>();

    private KitsuService kitsuService;

    public MangaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manga, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://kitsu.io/api/edge/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of the KitsuService
        kitsuService = retrofit.create(KitsuService.class);

        popularRecyclerView = view.findViewById(R.id.recyclerViewPopularManga);
        newRecyclerView = view.findViewById(R.id.recyclerViewNewManga);
        topRankedRecyclerView = view.findViewById(R.id.recyclerViewTopRankedManga);
        trendingRecyclerView = view.findViewById(R.id.recyclerviewTrendingManga);

        // Initialize adapters
        popularAdapter = new MangaAdapter(popularMangaList, getContext());
        newAdapter = new MangaAdapter(newMangaList, getContext());
        topRankedAdapter = new MangaAdapter(topRankedMangaList, getContext());
        trendingAdapter = new MangaAdapter(trendingMangaList, getContext());


        // Set layout manager for each RecyclerView
        popularRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        newRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        topRankedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        trendingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


        // Set adapters to RecyclerViews
        popularRecyclerView.setAdapter(popularAdapter);
        newRecyclerView.setAdapter(newAdapter);
        topRankedRecyclerView.setAdapter(topRankedAdapter);
        trendingRecyclerView.setAdapter(trendingAdapter);


        loadPopularManga();
        loadNewManga();
        loadTopRankedManga();
        loadTrendingManga();
    }

    private void loadPopularManga() {
        Call<KitsuResponse<Manga>> popularMangaCall = kitsuService.getPopularManga("popularityRank", 10);
        popularMangaCall.enqueue(new Callback<KitsuResponse<Manga>>() {
            @Override
            public void onResponse(Call<KitsuResponse<Manga>> call, Response<KitsuResponse<Manga>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    popularMangaList.clear(); // Clear the list before adding new data
                    popularMangaList.addAll(response.body().getData());
                    popularAdapter.notifyDataSetChanged();
                } else {
                    Log.e("Manga Response", "Error: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<KitsuResponse<Manga>> call, Throwable t) {
                Log.d("Retrofit", "Manga search error: " + t.getMessage());
            }
        });
    }

    private void loadNewManga() {
        Call<KitsuResponse<Manga>> newMangaCall = kitsuService.getNewManga("-createdAt", 10);
        newMangaCall.enqueue(new Callback<KitsuResponse<Manga>>() {
            @Override
            public void onResponse(Call<KitsuResponse<Manga>> call, Response<KitsuResponse<Manga>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    newMangaList.clear(); // Clear the list before adding new data
                    newMangaList.addAll(response.body().getData());
                    newAdapter.notifyDataSetChanged();
                } else {
                    Log.d("Retrofit", "Manga search error: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<KitsuResponse<Manga>> call, Throwable t) {
                Log.d("Retrofit", "Manga search error: " + t.getMessage());
            }
        });
    }

    private void loadTopRankedManga() {
        Call<KitsuResponse<Manga>> topRankedMangaCall = kitsuService.getTopRankedManga("ratingRank", 10);
        topRankedMangaCall.enqueue(new Callback<KitsuResponse<Manga>>() {
            @Override
            public void onResponse(Call<KitsuResponse<Manga>> call, Response<KitsuResponse<Manga>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    topRankedMangaList.clear(); // Clear the list before adding new data
                    topRankedMangaList.addAll(response.body().getData());
                    topRankedAdapter.notifyDataSetChanged();
                } else {
                    Log.d("Retrofit", "Manga search error: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<KitsuResponse<Manga>> call, Throwable t) {
                Log.d("Retrofit", "Manga search error: " + t.getMessage());
            }
        });
    }

    private void loadTrendingManga() {
        Call<KitsuResponse<Manga>> trendingMangaCall = kitsuService.getTrendingManga(10);
        trendingMangaCall.enqueue(new Callback<KitsuResponse<Manga>>() {
            @Override
            public void onResponse(Call<KitsuResponse<Manga>> call, Response<KitsuResponse<Manga>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    trendingMangaList.clear(); // Clear the list before adding new data
                    trendingMangaList.addAll(response.body().getData());
                    trendingAdapter.notifyDataSetChanged();
                } else {
                    Log.d("Retrofit", "Anime search error: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<KitsuResponse<Manga>> call, Throwable t) {
                Log.d("Retrofit", "Anime search error: " + t.getMessage());
            }
        });
    }
}

