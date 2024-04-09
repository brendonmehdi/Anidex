package com.example.anidex.Music;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

        // Load the slide from right animation
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_from_right);
        // Apply the animation to the holder's itemView
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return themes != null ? themes.size() : 0;
    }

    public void updateData(List<AnimeTheme> newThemes) {
        this.themes.clear();
        this.themes.addAll(newThemes);
        notifyDataSetChanged(); // Notify any registered observers that the data set has changed.
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSongTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            tvSongTitle = itemView.findViewById(R.id.tvSongTitle);
        }
    }


}
