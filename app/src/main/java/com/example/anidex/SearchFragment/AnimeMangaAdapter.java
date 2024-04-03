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
        this.favoritesManager = new FavoritesManager(context); // Ensure FavoritesManager is updated to handle both types
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_search_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Object item = items.get(position);
        if (item instanceof Anime) {
            holder.bindAnime((Anime) item);
        } else if (item instanceof Manga) {
            holder.bindManga((Manga) item);
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
            starIcon = itemView.findViewById(R.id.star_icon);

            starIcon.setOnClickListener(v -> toggleFavorite(getAdapterPosition()));
        }

        private void toggleFavorite(int position) {
            Object item = items.get(position);
            String itemId = item instanceof Anime ? ((Anime) item).getId() : ((Manga) item).getId();
            String type = item instanceof Anime ? "anime" : "manga";

            boolean isFavoriteNow = !favoritesManager.isFavorite(itemId, type);
            if (isFavoriteNow) {
                favoritesManager.addFavorite(item, type);
            } else {
                favoritesManager.removeFavorite(itemId, type);
            }
            // Update icon immediately for better user experience
            starIcon.setImageResource(isFavoriteNow ? R.drawable.ic_baseline_star_24 : R.drawable.ic_baseline_star_border_24);
        }

//
//        private void updateFavoriteIcon() {
//            // This method should check the favorite status of the current item and update the icon accordingly
//            // Example implementation (You might need to adjust based on your actual data structure)
//            Object item = items.get(getAdapterPosition());
//            String itemId = item instanceof Anime ? ((Anime) item).getId() : ((Manga) item).getId();
//            String type = item instanceof Anime ? "anime" : "manga";
//            boolean isFavorite = favoritesManager.isFavorite(itemId, type);
//            starIcon.setImageResource(isFavorite ? R.drawable.ic_baseline_star_24 : R.drawable.ic_baseline_star_border_24);
//        }

        public void bindAnime(Anime anime) {
            textName.setText(anime.getAttributes().getCanonicalTitle());
            textType.setText(anime.getType());
            updateFavoriteIcon(anime.getId(), "anime");
            loadImage(anime.getAttributes().getPosterImage().getMedium());
        }

        public void bindManga(Manga manga) {
            textName.setText(manga.getAttributes().getCanonicalTitle());
            textType.setText(manga.getType());
            updateFavoriteIcon(manga.getId(), "manga");
            loadImage(manga.getAttributes().getPosterImage().getMedium());
        }

        private void updateFavoriteIcon(String itemId, String type) {
            boolean isFavorite = favoritesManager.isFavorite(itemId, type);
            starIcon.setImageResource(isFavorite ? R.drawable.ic_baseline_star_24 : R.drawable.ic_baseline_star_border_24);
        }

        private void loadImage(String url) {
            Picasso.get().load(url).placeholder(R.drawable.noimage).into(imageAnime);
        }
    }
}
