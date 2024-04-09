package com.example.anidex;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.anidex.Models.Anime;
import com.example.anidex.R;
import com.squareup.picasso.Picasso;

public class AnimeDetailFragment extends Fragment {

    private static final String ARG_ANIME = "anime";

    private Anime anime;

    public AnimeDetailFragment() {
        // Required empty public constructor
    }

    public static AnimeDetailFragment newInstance(Anime anime) {
        AnimeDetailFragment fragment = new AnimeDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_ANIME, anime);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            anime = getArguments().getParcelable(ARG_ANIME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_anime_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView imageViewAnime = view.findViewById(R.id.imageViewAnime);
        TextView textViewTitle = view.findViewById(R.id.textViewTitle);
        TextView textViewSubtype = view.findViewById(R.id.textViewSubtype);

        if (anime != null) {
            Picasso.get().load(anime.getAttributes().getPosterImage().getMedium()).into(imageViewAnime);
            textViewTitle.setText(anime.getAttributes().getCanonicalTitle());
            textViewSubtype.setText(anime.getAttributes().getSubType());
        }
    }
}
