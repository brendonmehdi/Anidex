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

import com.example.anidex.Models.Manga;
import com.squareup.picasso.Picasso;

public class MangaDetailFragment extends Fragment {

    private static final String ARG_MANGA = "manga";

    private Manga manga;

    public MangaDetailFragment() {
        // Required empty public constructor
    }

    public static MangaDetailFragment newInstance(Manga manga) {
        MangaDetailFragment fragment = new MangaDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_MANGA, manga);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            manga = getArguments().getParcelable(ARG_MANGA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_manga_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView imageViewManga = view.findViewById(R.id.imageViewManga);
        TextView textViewTitle = view.findViewById(R.id.textViewTitle);
        TextView textViewSubtype = view.findViewById(R.id.textViewSubtype);
        TextView textViewSynopsis = view.findViewById(R.id.textViewSynopsis);
        TextView textViewAverageRating = view.findViewById(R.id.textViewAverageRating);
        TextView textViewStartDate = view.findViewById(R.id.textViewStartDate);
        TextView textViewEndDate = view.findViewById(R.id.textViewEndDate);
        TextView textViewChapterCount = view.findViewById(R.id.textViewChapterCount);
        TextView textViewVolumeCount = view.findViewById(R.id.textViewVolumeCount);

        if (manga != null) {
            Picasso.get().load(manga.getAttributes().getPosterImage().getMedium()).into(imageViewManga);
            textViewTitle.setText(manga.getAttributes().getCanonicalTitle());
            textViewSubtype.setText(manga.getAttributes().getSubType());
            textViewSynopsis.setText(manga.getAttributes().getSynopsis());
            textViewAverageRating.setText("Average Rating: " + manga.getAttributes().getAverageRating());
            textViewStartDate.setText("Start Date: " + manga.getAttributes().getStartDate());
            textViewEndDate.setText("End Date: " + manga.getAttributes().getEndDate());
            textViewChapterCount.setText("Chapter Count: " + manga.getAttributes().getChapterCount());
            textViewVolumeCount.setText("Volume Count: " + manga.getAttributes().getVolumeCount());
        }
    }
}

