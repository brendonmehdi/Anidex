package com.example.anidex.Favs;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anidex.Models.Anime;
import com.example.anidex.Models.Manga;
import com.example.anidex.R;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private List<Object> animeList;
    private Context context;
    private FavoritesManager favoritesManager;

    public FavoritesAdapter(List<Object> animeList, Context context) {
        this.animeList = animeList;
        this.context = context;
        this.favoritesManager = new FavoritesManager(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favorite, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Object item = animeList.get(position);

        if (item instanceof Anime) {
            Anime anime = (Anime) item;
            holder.bind(anime.getAttributes().getCanonicalTitle(), anime.getUserComment());

//            Log.d("AnimeBind", "Binding anime: " + anime.getUserComment());
        }
        if (item instanceof Manga) {
            Manga manga = (Manga) item;
            holder.bind(manga.getAttributes().getCanonicalTitle(), manga.getUserComment());

//            Log.d("MangaBind", "Binding manga: " + manga.getUserComment());
        }

        holder.removeButton.setOnClickListener(v -> {
            if (item instanceof Anime) {
                favoritesManager.removeFavorite(((Anime) item).getId(), "anime");
            } else if (item instanceof Manga) {
                favoritesManager.removeFavorite(((Manga) item).getId(), "manga");
            }
            animeList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, animeList.size());
        });

        holder.editCommentButton.setOnClickListener(v -> showEditCommentDialog(item, position, holder.commentTextView));
        holder.commentButton.setOnClickListener(v -> showCommentDialog(item, position, holder.commentTextView));
    }

    @Override
    public int getItemCount() {
        return animeList.size();
    }

    private void showEditCommentDialog(Object item, int position, TextView commentTextView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Comment");

        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        input.setMaxLines(3);
        input.setText(commentTextView.getText().toString());
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String newComment = input.getText().toString();
            if (item instanceof Anime) {
                Anime anime = (Anime) item;
                anime.setUserComment(newComment);
                favoritesManager.updateFavoriteAnime(anime);
            } else if (item instanceof Manga) {
                Manga manga = (Manga) item;
                manga.setUserComment(newComment);
                favoritesManager.updateFavoriteManga(manga);
            }
            commentTextView.setText(newComment);
            notifyItemChanged(position);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void showCommentDialog(Object item, int position, TextView commentTextView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Leave a Comment");

        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        input.setMaxLines(3);
        input.setText(commentTextView.getText().toString());
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String newComment = input.getText().toString();
            if (item instanceof Anime) {
                Anime anime = (Anime) item;
                anime.setUserComment(newComment);
                favoritesManager.updateFavoriteAnime(anime);
            } else if (item instanceof Manga) {
                Manga manga = (Manga) item;
                manga.setUserComment(newComment);
                favoritesManager.updateFavoriteManga(manga);
            }
            commentTextView.setText(newComment);
            notifyItemChanged(position);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titleTextView, commentTextView;
        Button removeButton, editCommentButton, commentButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.text_view_title);
            commentTextView = itemView.findViewById(R.id.comment);
            removeButton = itemView.findViewById(R.id.remove_button);
            editCommentButton = itemView.findViewById(R.id.edit_button);
            commentButton = itemView.findViewById(R.id.commentButton);
            itemView.setOnClickListener(this); // Set the click listener for the entire view
        }

        public void bind(String title, String comment) {
            titleTextView.setText(title);
            commentTextView.setText(comment);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Object item = animeList.get(position);
                Bundle bundle = new Bundle();

                if (item instanceof Anime) {
                    Anime anime = (Anime) item;
                    bundle.putParcelable("anime", anime);
                    // Ensure this ID matches the one in your navigation graph
                    Navigation.findNavController(view).navigate(R.id.action_navigation_fav_to_animeDetailFragment, bundle);

                } else if (item instanceof Manga) {
                    Manga manga = (Manga) item;
                    bundle.putParcelable("manga", manga);
                    // Ensure this ID matches the one in your navigation graph
                    Navigation.findNavController(view).navigate(R.id.action_navigation_fav_to_mangaDetailFragment, bundle);
                }
            }
        }

    }
}
