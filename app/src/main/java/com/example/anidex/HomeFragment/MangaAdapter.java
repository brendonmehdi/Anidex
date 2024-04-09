package com.example.anidex.HomeFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anidex.Models.Manga;
import com.example.anidex.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MangaAdapter extends RecyclerView.Adapter<MangaAdapter.MangaViewHolder> {

    private List<Manga> mangaList;
    private Context context;

    public MangaAdapter(List<Manga> mangaList, Context context) {
        this.mangaList = mangaList;
        this.context = context;
    }

    @NonNull
    @Override
    public MangaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent, false);
        return new MangaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MangaViewHolder holder, int position) {
        Manga manga = mangaList.get(position);
        holder.bind(manga);

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_from_right);
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return mangaList.size();
    }

    public static class MangaViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView titleTextView;
        private TextView subtypeTextView;

        public MangaViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.homeImage);
            titleTextView = itemView.findViewById(R.id.homeName);
            subtypeTextView = itemView.findViewById(R.id.homeType);
        }

        public void bind(Manga manga) {
            // Set data to views
            titleTextView.setText(manga.getAttributes().getCanonicalTitle());
            subtypeTextView.setText(manga.getAttributes().getSubType());

            // Load image using Picasso
            Picasso.get().load(manga.getAttributes().getPosterImage().getMedium()).into(imageView);
        }
    }
}
