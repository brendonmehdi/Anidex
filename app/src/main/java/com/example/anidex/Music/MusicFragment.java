package com.example.anidex.Music;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.example.anidex.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MusicFragment extends Fragment {
    // Add ProgressBar as a member variable
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);

        RecyclerView rvAnimeThemes = view.findViewById(R.id.rvAnimeThemes);
//        List<AnimeTheme> allThemes = new ArrayList<>();
//        AnimeThemesAdapter adapter = new AnimeThemesAdapter(getContext(), allThemes);
//        rvAnimeThemes.setAdapter(adapter);



        SearchView searchView = view.findViewById(R.id.searchView);

        rvAnimeThemes.setLayoutManager(new LinearLayoutManager(getContext()));




        progressBar = view.findViewById(R.id.progressBarAnime);

        JikanApiService service = RetrofitClient.getService();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Show the ProgressBar when search is initiated
                progressBar.setVisibility(View.VISIBLE);

                service.searchAnime(query).enqueue(new Callback<AnimeSearchResponse>() {
                    @Override
                    public void onResponse(Call<AnimeSearchResponse> call, Response<AnimeSearchResponse> response) {
                        // Hide the ProgressBar on receiving a response
                        progressBar.setVisibility(View.GONE);

                        if (response.isSuccessful() && response.body() != null) {
                            int animeId = response.body().getData().get(0).getMalId();
                            fetchAnimeThemes(animeId, service, rvAnimeThemes);
                        }
                    }

                    @Override
                    public void onFailure(Call<AnimeSearchResponse> call, Throwable t) {
                        // Hide the ProgressBar on failure to receive a response
                        progressBar.setVisibility(View.GONE);
                        // Handle failure
                        System.out.println("Error:" + t.toString());
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        return view;
    }




    private void fetchAnimeThemes(int animeId, JikanApiService service, RecyclerView recyclerView) {
        service.getAnimeThemes(animeId).enqueue(new Callback<AnimeThemesResponse>() {
            @Override
            public void onResponse(Call<AnimeThemesResponse> call, Response<AnimeThemesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ThemesData themes = response.body().getData();
                    List<AnimeTheme> allThemes = new ArrayList<>();

                    for (String title : themes.getOpenings()) {
                        allThemes.add(new AnimeTheme(title));
                    }
                    for (String title : themes.getEndings()) {
                        allThemes.add(new AnimeTheme(title));
                    }

                    getActivity().runOnUiThread(() -> {
                        AnimeThemesAdapter adapter = new AnimeThemesAdapter(getContext(), allThemes);
                        recyclerView.setAdapter(adapter);
                    });
                }
            }

            @Override
            public void onFailure(Call<AnimeThemesResponse> call, Throwable t) {
                System.out.println("Error:" + t.toString());
            }
        });
    }




}

