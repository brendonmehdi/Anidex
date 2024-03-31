package com.example.anidex.SearchFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anidex.Models.Anime;
import com.example.anidex.Models.Manga;
import com.example.anidex.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AnimeMangaAdapter extends RecyclerView.Adapter<AnimeMangaAdapter.ViewHolder> {

    private List<Object> items; // List of Anime and Manga objects
    private Context context;

    public AnimeMangaAdapter(Context context, List<Object> items) {
        this.context = context;
        this.items = items;
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageAnime = itemView.findViewById(R.id.imageAnime);
            textName = itemView.findViewById(R.id.textName);
            textType = itemView.findViewById(R.id.textType);
        }

        public void bindAnime(Anime anime) {
            textName.setText(anime.getAttributes().getCanonicalTitle());
            textType.setText(anime.getAttributes().getSubType());

            // Load image using Picasso
            String posterUrl = anime.getAttributes().getPosterImage().getMedium();
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

        public void bindManga(Manga manga) {
            textName.setText(manga.getAttributes().getCanonicalTitle());
            textType.setText(manga.getType());

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
