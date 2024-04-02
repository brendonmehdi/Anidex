package com.example.anidex.Favs;

import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anidex.Database.DatabaseHelper;
import com.example.anidex.Models.Anime;
import com.example.anidex.R;

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
        holder.commentTextView.setText(anime.getUserComment()); // Set the comment text

        // Remove button click listener
        holder.removeButton.setOnClickListener(v -> {
            // Use the db instance to delete the anime from the database
            db.deleteFavoriteAnime(anime);
            // Remove the item from the list and notify the adapter
            animeList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, animeList.size());
        });

        // Comment button click listener
        holder.commentButton.setOnClickListener(v -> {
            showCommentDialog(anime, position, holder.commentTextView); // Pass the comment TextView to the dialog method
        });

        holder.editCommentButton.setOnClickListener(v -> {
            showEditCommentDialog(anime, position, holder.commentTextView);
        });
    }

    @Override
    public int getItemCount() {
        return animeList.size();
    }

    private void showEditCommentDialog(Anime anime, int position, TextView commentTextView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Comment");

        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(anime.getUserComment());
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String newComment = input.getText().toString();
            anime.setUserComment(newComment);
            db.updateFavoriteAnime(anime); // Make sure your DB helper supports updating the comment
            commentTextView.setText(newComment);
            notifyItemChanged(position);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void showCommentDialog(Anime anime, int position, TextView commentTextView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Leave a comment");

        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(anime.getUserComment()); // Pre-populate with the current comment
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String comment = input.getText().toString();
            anime.setUserComment(comment);
            db.updateFavoriteAnime(anime);
            commentTextView.setText(comment); // Update the comment TextView directly
            notifyItemChanged(position);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView commentTextView; // TextView for displaying comments
        Button removeButton;
        Button editCommentButton;
        Button commentButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.text_view_title);
            commentTextView = itemView.findViewById(R.id.comment); // Initialize the comment TextView
            editCommentButton = itemView.findViewById(R.id.edit_button);
            removeButton = itemView.findViewById(R.id.remove_button);
            commentButton = itemView.findViewById(R.id.commentButton);
        }
    }
}
