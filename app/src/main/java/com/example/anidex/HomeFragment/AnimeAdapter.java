package com.example.anidex.HomeFragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anidex.AnimeDetailFragment;
import com.example.anidex.Models.Anime;
import com.example.anidex.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AnimeAdapter extends RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder> {

    private List<Anime> animeList;
    private Context context;

    public AnimeAdapter(List<Anime> animeList, Context context) {
        this.animeList = animeList;
        this.context = context;
    }

    @Override
    public AnimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent, false);
        return new AnimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AnimeViewHolder holder, int position) {
        Anime anime = animeList.get(position);
        holder.bind(anime);

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_from_right);
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return animeList.size();
    }

    public class AnimeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private ImageView imageView;
        private TextView titleTextView;
        private TextView subtypeTextView;

        public AnimeViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.homeImage);
            titleTextView = itemView.findViewById(R.id.homeName);
            subtypeTextView = itemView.findViewById(R.id.homeType);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void bind(Anime anime) {
            titleTextView.setText(anime.getAttributes().getCanonicalTitle());
            subtypeTextView.setText(anime.getAttributes().getSubType());
            Picasso.get().load(anime.getAttributes().getPosterImage().getLarge()).into(imageView);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Anime clickedAnime = animeList.get(position);

                NavController navController = Navigation.findNavController(view);

                Bundle bundle = new Bundle();
                bundle.putParcelable("anime", clickedAnime);
                navController.navigate(R.id.action_navigation_home_to_animeDetailFragment, bundle);
            }
        }


        @Override
        public boolean onLongClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Anime clickedAnime = animeList.get(position);
                String searchQuery = clickedAnime.getAttributes().getCanonicalTitle();

                if (!searchQuery.isEmpty()) {
                    String searchUrl = "https://www.crunchyroll.com/search?q=" + searchQuery;
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(searchUrl));
                    context.startActivity(intent);
                    return true;
                }
            }
            return false;
        }
    }
}