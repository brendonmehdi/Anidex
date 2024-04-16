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

public class AnimeFragment extends Fragment {

    private RecyclerView popularRecyclerView;
    private RecyclerView newRecyclerView;
    private RecyclerView topRankedRecyclerView;
    private RecyclerView trendingRecyclerview;

    private AnimeAdapter popularAdapter;
    private AnimeAdapter newAdapter;
    private AnimeAdapter topRankedAdapter;
    private AnimeAdapter trendingAdapter;

    private List<Anime> popularAnimeList = new ArrayList<>();
    private List<Anime> newAnimeList = new ArrayList<>();
    private List<Anime> topRankedAnimeList = new ArrayList<>();
    private List<Anime> trendingAnimeList = new ArrayList<>();


    private KitsuService kitsuService;

    public AnimeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_anime, container, false);
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

        popularRecyclerView = view.findViewById(R.id.recyclerViewPopularAnime);
        newRecyclerView = view.findViewById(R.id.recyclerViewNewAnime);
        topRankedRecyclerView = view.findViewById(R.id.recyclerViewTopRankedAnime);
        trendingRecyclerview=view.findViewById(R.id.recyclerviewTrending);



        // Initialize adapters
        popularAdapter = new AnimeAdapter(popularAnimeList, getContext());
        newAdapter = new AnimeAdapter(newAnimeList, getContext());
        topRankedAdapter = new AnimeAdapter(topRankedAnimeList, getContext());
        trendingAdapter = new AnimeAdapter(trendingAnimeList, getContext());


        // Set layout manager for each RecyclerView
        popularRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        newRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        topRankedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        trendingRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


        // Set adapters to RecyclerViews
        popularRecyclerView.setAdapter(popularAdapter);
        newRecyclerView.setAdapter(newAdapter);
        topRankedRecyclerView.setAdapter(topRankedAdapter);
        trendingRecyclerview.setAdapter(trendingAdapter);

        loadPopularAnime();
        loadNewAnime();
        loadTopRankedAnime();
        loadTrendingAnime();
    }

    private void loadPopularAnime() {
        Call<KitsuResponse<Anime>> popularAnimeCall = kitsuService.getPopularAnime("popularityRank", 20);
        popularAnimeCall.enqueue(new Callback<KitsuResponse<Anime>>() {
            @Override
            public void onResponse(Call<KitsuResponse<Anime>> call, Response<KitsuResponse<Anime>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    popularAnimeList.clear(); // Clear the list before adding new data
                    popularAnimeList.addAll(response.body().getData());
                    popularAdapter.notifyDataSetChanged();
                } else {
                    Log.e("Anime Response", "Error: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<KitsuResponse<Anime>> call, Throwable t) {
                Log.d("Retrofit", "Anime search error: " + t.getMessage());
            }
        });
    }

    private void loadNewAnime() {
        Call<KitsuResponse<Anime>> newAnimeCall = kitsuService.getNewAnime("-createdAt", 20);
        newAnimeCall.enqueue(new Callback<KitsuResponse<Anime>>() {
            @Override
            public void onResponse(Call<KitsuResponse<Anime>> call, Response<KitsuResponse<Anime>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    newAnimeList.clear(); // Clear the list before adding new data
                    newAnimeList.addAll(response.body().getData());
                    newAdapter.notifyDataSetChanged();
                } else {
                    Log.d("Retrofit", "Anime search error: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<KitsuResponse<Anime>> call, Throwable t) {
                Log.d("Retrofit", "Anime search error: " + t.getMessage());
            }
        });
    }

    private void loadTopRankedAnime() {
        Call<KitsuResponse<Anime>> topRankedAnimeCall = kitsuService.getTopRankedAnime("ratingRank", 20);
        topRankedAnimeCall.enqueue(new Callback<KitsuResponse<Anime>>() {
            @Override
            public void onResponse(Call<KitsuResponse<Anime>> call, Response<KitsuResponse<Anime>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    topRankedAnimeList.clear(); // Clear the list before adding new data
                    topRankedAnimeList.addAll(response.body().getData());
                    topRankedAdapter.notifyDataSetChanged();
                } else {
                    Log.d("Retrofit", "Anime search error: " + response.toString());

                }
            }

            @Override
            public void onFailure(Call<KitsuResponse<Anime>> call, Throwable t) {
                Log.d("Retrofit", "Anime search error: " + t.getMessage());
            }
        });
    }
private void loadTrendingAnime() {
    Call<KitsuResponse<Anime>> trendingAnimeCall = kitsuService.getTrendingAnime(20);
    trendingAnimeCall.enqueue(new Callback<KitsuResponse<Anime>>() {
        @Override
        public void onResponse(Call<KitsuResponse<Anime>> call, Response<KitsuResponse<Anime>> response) {
            if (response.isSuccessful() && response.body() != null) {
                trendingAnimeList.clear(); // Clear the list before adding new data
                trendingAnimeList.addAll(response.body().getData());
                trendingAdapter.notifyDataSetChanged();
            } else {
                Log.d("Retrofit", "Anime search error: " + response.toString());
            }
        }

        @Override
        public void onFailure(Call<KitsuResponse<Anime>> call, Throwable t) {
            Log.d("Retrofit", "Anime search error: " + t.getMessage());
        }
    });
}
}
