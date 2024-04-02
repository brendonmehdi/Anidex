package com.example.anidex.Music;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anidex.R;

import java.util.List;
//binds the data from anime themes to views in the RecyclerView.
public class AnimeThemesAdapter extends RecyclerView.Adapter<AnimeThemesAdapter.ViewHolder> {

    private Context context;
    private List<AnimeTheme> themes;

    public AnimeThemesAdapter(Context context, List<AnimeTheme> themes) {
        this.context = context;
        this.themes = themes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_anime_theme, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AnimeTheme theme = themes.get(position);
        holder.tvSongTitle.setText(theme.getTitle());
    }

    @Override
    public int getItemCount() {
        return themes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSongTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            tvSongTitle = itemView.findViewById(R.id.tvSongTitle);
        }
    }
}
