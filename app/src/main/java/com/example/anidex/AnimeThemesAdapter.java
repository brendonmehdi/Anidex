package com.example.anidex;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AnimeThemesAdapter extends RecyclerView.Adapter<AnimeThemesAdapter.ViewHolder> {
    private List<String> themes;

    public AnimeThemesAdapter(List<String> themes) {
        this.themes = themes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.themeTextView.setText(themes.get(position));
    }

    @Override
    public int getItemCount() {
        return themes != null ? themes.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView themeTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            themeTextView = itemView.findViewById(android.R.id.text1);
        }
    }
}

