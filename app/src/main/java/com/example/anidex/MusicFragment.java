package com.example.anidex;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MusicFragment() {
        // Required empty public constructor
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        RecyclerView rvAnimeThemes = view.findViewById(R.id.rvAnimeThemes);
        rvAnimeThemes.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize Retrofit JikanApiService
        JikanApiService service = RetrofitClient.getService();

        // Assume animeId is obtained somehow (for example, as an argument)
        int animeId = 20; // Placeholder anime ID

        // Make the API call to get anime themes
        service.getAnimeThemes(animeId).enqueue(new Callback<AnimeThemesResponse>() {
            @Override
            public void onResponse(Call<AnimeThemesResponse> call, Response<AnimeThemesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ThemesData themes = response.body().getData();
                    List<String> allThemes = new ArrayList<>();
                    allThemes.addAll(themes.getOpenings());
                    allThemes.addAll(themes.getEndings());

                //updates the thread
                    getActivity().runOnUiThread(() -> {
                        AnimeThemesAdapter adapter = new AnimeThemesAdapter(allThemes);
                        rvAnimeThemes.setAdapter(adapter);
                    });
                }
            }

            @Override
            public void onFailure(Call<AnimeThemesResponse> call, Throwable t) {

            }
        });

        return view;
    }

}