package com.example.anidex.SearchFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anidex.Models.Anime;
import com.example.anidex.Models.Manga;
import com.example.anidex.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchFragment extends Fragment {
    private ProgressBar progressBar;

    private RecyclerView recyclerView;
    private AnimeMangaAdapter adapter;
    private List<Object> searchResults;
    private KitsuService kitsuService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        progressBar = view.findViewById(R.id.progressBarAnime);

        recyclerView = view.findViewById(R.id.searchRecyclerView);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchResults = new ArrayList<>();
        adapter = new AnimeMangaAdapter(getContext(), searchResults);
        recyclerView.setAdapter(adapter);
        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(layoutManager);

        String baseUrl="https://kitsu.io/api/edge/";

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        kitsuService = retrofit.create(KitsuService.class);

        SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!TextUtils.isEmpty(query.trim())) {
                    searchResults.clear();
                    searchAnime(query);
                    searchManga(query);

                    //makes the progress the bar visible
                    progressBar.setVisibility(View.VISIBLE);

                    //resets the recycler animation
                    adapter.resetAnimations();

                    // Hide the keyboard on search
                    View view = getActivity().getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }

                    // Clear focus from SearchView
                    searchView.clearFocus();

                } else {
                    Toast.makeText(getContext(), "Please enter a search query", Toast.LENGTH_SHORT).show();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return view;
    }

    private void searchAnime(String query) {
        //gets number from settings
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        int searchLimit = Integer.parseInt(sharedPreferences.getString("search_limit", "10"));


        Call<KitsuResponse<Anime>> call = kitsuService.searchAnime(query, searchLimit);
        call.enqueue(new Callback<KitsuResponse<Anime>>() {
            @Override
            public void onResponse(@NonNull Call<KitsuResponse<Anime>> call, @NonNull Response<KitsuResponse<Anime>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    KitsuResponse<Anime> kitsuResponse = response.body();
                    List<Anime> animeList = kitsuResponse.getData();
                    if (animeList != null && !animeList.isEmpty()) {
                        searchResults.addAll(animeList);
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    } else {
                        Log.e("Anime Response", "Empty anime list");
                    }
                } else {
                    Log.e("Anime Response", "Error: " + response.toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<KitsuResponse<Anime>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Failed to retrieve Anime results", Toast.LENGTH_SHORT).show();
                Log.d("Retrofit", "Anime search error: " + t.getMessage());
            }
        });
    }

    private void searchManga(String query) {
        //gets number from settings
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        int searchLimit = Integer.parseInt(sharedPreferences.getString("search_limit", "10"));


        Call<KitsuResponse<Manga>> call = kitsuService.searchManga(query, searchLimit);
        call.enqueue(new Callback<KitsuResponse<Manga>>() {
            @Override
            public void onResponse(@NonNull Call<KitsuResponse<Manga>> call, @NonNull Response<KitsuResponse<Manga>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    KitsuResponse<Manga> kitsuResponse = response.body();
                    List<Manga> mangaList = kitsuResponse.getData();
                    if (mangaList != null && !mangaList.isEmpty()) {
                        searchResults.addAll(mangaList);
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.e("Manga Response", "Empty manga list");
                    }
                } else {
                    Log.e("Manga Response", "Error: " + response.toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<KitsuResponse<Manga>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Failed to retrieve Manga results", Toast.LENGTH_SHORT).show();
                Log.d("Retrofit", "Manga search error: " + t.getMessage());
            }
        });
    }
}
