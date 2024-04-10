package com.example.anidex.SearchFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anidex.Favs.FavoritesManager;
import com.example.anidex.Models.Anime;
import com.example.anidex.Models.Manga;
import com.example.anidex.R;
import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AnimeMangaAdapter extends RecyclerView.Adapter<AnimeMangaAdapter.ViewHolder> {
    //made it so the animations isnt clunky and only runs when once when the item is visible not each time when it loads all items
    private Set<Integer> animatedItems = new HashSet<>();
    private List<Object> items;
    private Context context;
    private FavoritesManager favoritesManager;

    //used to reset the saved animations for the next search
    public void resetAnimations() {
        animatedItems.clear();
    }

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

        if (!animatedItems.contains(position)) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_from_right);
            holder.itemView.startAnimation(animation);
            animatedItems.add(position); // Remembers that this position has been animated
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
            itemView.setOnClickListener(this);
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


        public void bindAnime(Anime anime) {
            textName.setText(anime.getAttributes().getCanonicalTitle());
            textType.setText(anime.getType());
            updateFavoriteIcon(anime.getId(), "anime");
            loadImage(anime.getAttributes().getPosterImage().getLarge());

        }

        public void bindManga(Manga manga) {
            textName.setText(manga.getAttributes().getCanonicalTitle());

            textType.setText(manga.getAttributes().getSubType());

            textType.setText(manga.getType());
            updateFavoriteIcon(manga.getId(), "manga");
            loadImage(manga.getAttributes().getPosterImage().getLarge());
        }


        private void updateFavoriteIcon(String itemId, String type) {
            boolean isFavorite = favoritesManager.isFavorite(itemId, type);
            starIcon.setImageResource(isFavorite ? R.drawable.ic_baseline_star_24 : R.drawable.ic_baseline_star_border_24);
        }

        private void loadImage(String url) {
            Picasso.get().load(url).placeholder(R.drawable.noimage).into(imageAnime);
        }

        //Navigate to either AnimeDetail or MangaDetail based on what type of item was clicked in search
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Object clickedItem = items.get(position);

                NavController navController = Navigation.findNavController(view);

                if (clickedItem instanceof Anime) {
                    Anime clickedAnime = (Anime) clickedItem;
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("anime", clickedAnime);
                    navController.navigate(R.id.action_searchFragment_to_animeDetailFragment, bundle);
                } else if (clickedItem instanceof Manga) {
                    Manga clickedManga = (Manga) clickedItem;
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("manga", clickedManga);
                    navController.navigate(R.id.action_searchFragment_to_mangaDetailFragment, bundle);
                }
            }
        }

    }
}
