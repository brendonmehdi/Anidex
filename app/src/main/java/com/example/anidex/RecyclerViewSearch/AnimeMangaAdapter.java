package com.example.anidex.RecyclerViewSearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.anidex.Models.Anime;
import com.example.anidex.Models.Manga;
import com.example.anidex.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AnimeMangaAdapter extends RecyclerView.Adapter<AnimeMangaAdapter.ResultViewHolder> {

    private List<Object> resultList;
    private Context context;

    public AnimeMangaAdapter(Context context) {
        this.resultList = new ArrayList<>();
        this.context = context;
    }

    public void addResults(List<?> results) {
        resultList.clear();
        resultList.addAll(results);
        notifyDataSetChanged();
    }


    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_search_item, parent, false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ResultViewHolder holder, int position) {
        Object item = resultList.get(position);
        if (item instanceof Anime) {
            holder.bindAnime((Anime) item);
        } else if (item instanceof Manga) {
            holder.bindManga((Manga) item);
        }
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    static class ResultViewHolder extends RecyclerView.ViewHolder {
        ImageView imageAnime;
        TextView textName, textType;

        ResultViewHolder(View itemView) {
            super(itemView);
            imageAnime = itemView.findViewById(R.id.imageAnime);
            textName = itemView.findViewById(R.id.textName);
            textType = itemView.findViewById(R.id.textType);
        }

        void bindAnime(Anime anime) {
            textName.setText(anime.getCanonicalTitle());
            textType.setText(anime.getType());

            // Load image using Picasso
            Picasso.get()
                    .load(anime.getPosterImage())
                    .placeholder(R.drawable.dragonballtest)
                    .error(R.drawable.dragonballtest)
                    .into(imageAnime);
        }

        void bindManga(Manga manga) {
            textName.setText(manga.getCanonicalTitle());
            textType.setText(manga.getType());

            // Load image using Picasso
            Picasso.get()
                    .load(manga.getPosterImage())
                    .placeholder(R.drawable.dragonballtest)
                    .error(R.drawable.dragonballtest)
                    .into(imageAnime);
        }
    }
}
