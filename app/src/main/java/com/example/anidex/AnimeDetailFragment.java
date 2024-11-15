package com.example.anidex;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
        TextView textViewSynopsis = view.findViewById(R.id.textViewSynopsis);
        TextView textViewAverageRating = view.findViewById(R.id.textViewAverageRating);
        TextView textViewStartDate = view.findViewById(R.id.textViewStartDate);
        TextView textViewEndDate = view.findViewById(R.id.textViewEndDate);
        TextView textViewEpisodeCount = view.findViewById(R.id.textViewEpisodeCount);
        TextView textViewEpisodeLength = view.findViewById(R.id.textViewEpisodeLength);
        ImageButton imageButton=view.findViewById(R.id.buttonOpenYouTube);
        ImageButton twitterButton=view.findViewById(R.id.buttonOpenTwitter);
        ImageButton crunchyButton=view.findViewById(R.id.buttonCrunchyroll);

        //intents for youtube and twitter

        imageButton.setOnClickListener(view1 -> {
            Intent youtubeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + anime.getAttributes().getYoutubeVideoId()));
            youtubeIntent.putExtra("force_fullscreen", false);
            startActivity(youtubeIntent);

        });

        twitterButton.setOnClickListener(view1 -> {

            String tweetText = "Hey everyone! "+anime.getAttributes().getCanonicalTitle()+" is an awesome anime, check it out and add it to your watchlist in the AniDex App!";

            String tweetUrl = "https://twitter.com/intent/tweet?text=" + tweetText;

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(tweetUrl));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

        crunchyButton.setOnClickListener(view1 -> {
            String searchUrl = "https://www.crunchyroll.com/search?q=" + anime.getAttributes().getCanonicalTitle();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(searchUrl));
            startActivity(intent);
        });

        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey("anime")) {
            Anime anime = arguments.getParcelable("anime");
            if (anime != null && anime.getAttributes() != null && anime.getAttributes().getPosterImage() != null && anime.getAttributes().getPosterImage().getLarge() != null) {
                Picasso.get().load(anime.getAttributes().getPosterImage().getLarge()).into(imageViewAnime);
                textViewTitle.setText(anime.getAttributes().getCanonicalTitle());
                textViewSubtype.setText(anime.getAttributes().getSubType());
                textViewSynopsis.setText(anime.getAttributes().getSynopsis());
                textViewAverageRating.setText("Average Rating:" + anime.getAttributes().getAverageRating());
                textViewStartDate.setText("Start Date:" + anime.getAttributes().getStartDate());
                textViewEndDate.setText("End Date:" + anime.getAttributes().getEndDate());
                textViewEpisodeCount.setText("Episode Count:" + String.valueOf(anime.getAttributes().getEpisodeCount()));
                textViewEpisodeLength.setText("Episode Length:" + String.valueOf(anime.getAttributes().getEpisodeLength()));

            }
        }
    }

}
