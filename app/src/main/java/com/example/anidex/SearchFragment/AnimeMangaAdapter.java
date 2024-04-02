package com.example.anidex.SearchFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anidex.Favs.FavoritesManager;
import com.example.anidex.Models.Anime;
import com.example.anidex.Models.Manga;
import com.example.anidex.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AnimeMangaAdapter extends RecyclerView.Adapter<AnimeMangaAdapter.ViewHolder> {

    private List<Object> items; // List of Anime and Manga objects
    private Context context;
    private FavoritesManager favoritesManager;

    public AnimeMangaAdapter(Context context, List<Object> items) {
        this.context = context;
        this.items = items;
        this.favoritesManager = new FavoritesManager(context); // Initialize here
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_search_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Object item = items.get(position);
        if (item instanceof Anime) {
            Anime anime = (Anime) item;
            holder.bindAnime(anime);
        } else if (item instanceof Manga) {
            Manga manga = (Manga) item;
            holder.bindManga(manga);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageAnime;
        TextView textName;
        TextView textType;
        ImageView starIcon; // Star icon for marking as favorite


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageAnime = itemView.findViewById(R.id.imageAnime);
            textName = itemView.findViewById(R.id.textName);
            textType = itemView.findViewById(R.id.textType);
            starIcon = itemView.findViewById(R.id.star_icon); // Initialize the star icon

            // Inside your ViewHolder class
            starIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Object item = items.get(position);
                        if (item instanceof Anime) {
                            Anime anime = (Anime) item;
                            boolean isFavorite = favoritesManager.isFavorite(anime.getId());
                            if (isFavorite) {
                                favoritesManager.removeFavorite(anime.getId());
                                starIcon.setImageResource(R.drawable.ic_baseline_star_border_24); // Update icon
                            } else {
                                favoritesManager.addFavorite(anime);
                                starIcon.setImageResource(R.drawable.ic_baseline_star_24); // Update icon
                            }
                            // Optionally notify changes or update UI
                        }
                        // Handle Manga similarly if needed
//                        if (item instanceof Manga) {
//                            Manga manga = (Manga) item;
//                            boolean isFavorite = favoritesManager.isFavorite(manga.getId());
//                            if (isFavorite) {
//                                favoritesManager.removeFavorite(manga.getId());
//                                starIcon.setImageResource(R.drawable.ic_baseline_star_border_24); // Update icon
//                            } else {
//                                favoritesManager.addFavorite(manga);
//                                starIcon.setImageResource(R.drawable.ic_baseline_star_24); // Update icon
//                            }
//                            // Optionally notify changes or update UI
//                        }
                    }
                }
            });

        }


        // Method to bind data to views including favorite status
        public void bindAnime(Anime anime) {
            textName.setText(anime.getAttributes().getCanonicalTitle());
            textType.setText(anime.getAttributes().getSubType());
            boolean isFavorite = favoritesManager.isFavorite(anime.getId());
            starIcon.setImageResource(isFavorite ? R.drawable.ic_baseline_star_24 : R.drawable.ic_baseline_star_border_24);

            String posterUrl = anime.getAttributes().getPosterImage().getMedium();
            if (posterUrl != null && !posterUrl.isEmpty()) {
                Picasso.get().load(posterUrl).into(imageAnime);
            } else {
                imageAnime.setImageResource(R.drawable.noimage);
            }
        }

        // Similar bind method for Manga...



        public void bindManga(Manga manga) {
            textName.setText(manga.getAttributes().getCanonicalTitle());
            textType.setText(manga.getType());
            boolean isFavorite = favoritesManager.isFavorite(manga.getId());
            starIcon.setImageResource(isFavorite ? R.drawable.ic_baseline_star_24 : R.drawable.ic_baseline_star_border_24);

            // Load image using Picasso
            String posterUrl = manga.getAttributes().getPosterImage().getMedium();
            if (posterUrl != null && !posterUrl.isEmpty()) {
                Picasso.get()
                        .load(posterUrl)
                        .placeholder(R.drawable.noimage) // Placeholder image while loading
                        .into(imageAnime);
            } else {
                // Use placeholder if URL is empty
                imageAnime.setImageResource(R.drawable.noimage);
            }
        }
    }
}
