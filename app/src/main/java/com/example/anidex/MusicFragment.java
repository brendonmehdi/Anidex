package com.example.anidex;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.anidex.Music.AnimeSearchResponse;
import com.example.anidex.Music.AnimeTheme;
import com.example.anidex.Music.AnimeThemesAdapter;
import com.example.anidex.Music.AnimeThemesResponse;
import com.example.anidex.Music.JikanApiService;
import com.example.anidex.Music.RetrofitClient;
import com.example.anidex.Music.ThemesData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MusicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MusicFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MusicFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MusicFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MusicFragment newInstance(String param1, String param2) {
        MusicFragment fragment = new MusicFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        SearchView searchView = view.findViewById(R.id.searchView);
        RecyclerView rvAnimeThemes = view.findViewById(R.id.rvAnimeThemes);
        rvAnimeThemes.setLayoutManager(new LinearLayoutManager(getContext()));

        JikanApiService service = RetrofitClient.getService();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Use the search endpoint to find an anime by title
                service.searchAnime(query).enqueue(new Callback<AnimeSearchResponse>() {
                    @Override
                    public void onResponse(Call<AnimeSearchResponse> call, Response<AnimeSearchResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            int animeId = response.body().getData().get(0).getMalId();
                            fetchAnimeThemes(animeId, service, rvAnimeThemes);
                        }
                    }

                    @Override
                    public void onFailure(Call<AnimeSearchResponse> call, Throwable t) {
                        // Handle failure
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
                // Handle failure
            }
        });
    }




}

