package com.example.anidex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anidex.Database.DatabaseHelper;
import com.example.anidex.Models.Anime;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private List<Anime> animeList;
    private Context context;
    private DatabaseHelper db;


    public FavoritesAdapter(List<Anime> animeList, Context context) {
        this.animeList = animeList;
        this.context = context;
        this.db = new DatabaseHelper(context); // Initialize DatabaseHelper here
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favorite, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Anime anime = animeList.get(position);
        holder.titleTextView.setText(anime.getAttributes().getCanonicalTitle());

        holder.removeButton.setOnClickListener(v -> {
            // Use the db instance to delete the anime from the database
            db.deleteFavoriteAnime(anime);

            // Remove the item from the list and notify the adapter
            animeList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, animeList.size());
        });
    }

    @Override
    public int getItemCount() {
        return animeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        Button removeButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.text_view_title);
            removeButton = itemView.findViewById(R.id.remove_button); // Initialize the button
        }
    }
}
