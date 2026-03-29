package com.example.footballplayersgallery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.footballplayersgallery.R;
import com.example.footballplayersgallery.beans.Player;
import com.example.footballplayersgallery.service.PlayerService;

import de.hdodenhof.circleimageview.CircleImageView;

import java.util.ArrayList;
import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>
        implements Filterable {

    private final Context context;
    private final List<Player> allPlayers;
    private List<Player> displayedPlayers;
    private final PlayerFilter playerFilter;

    public PlayerAdapter(Context context, List<Player> players) {
        this.context = context;
        this.allPlayers = new ArrayList<>(players);
        this.displayedPlayers = new ArrayList<>(players);
        this.playerFilter = new PlayerFilter();
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_player, parent, false);
        PlayerViewHolder holder = new PlayerViewHolder(v);

        holder.itemView.setOnClickListener(view -> {
            int pos = holder.getBindingAdapterPosition();
            if (pos == RecyclerView.NO_ID) return;
            showEditDialog(displayedPlayers.get(pos), pos);
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        Player p = displayedPlayers.get(position);

        holder.playerId.setText(String.valueOf(p.getId()));
        holder.name.setText(p.getFullName());

        String clubText = "🏟  " + p.getClub();
        holder.club.setText(clubText);

        String posShort = p.getPosition().length() >= 3
                ? p.getPosition().substring(0, 3).toUpperCase()
                : p.getPosition().toUpperCase();
        holder.position.setText(posShort);
        holder.rating.setRating(p.getScore());

        Glide.with(context)
                .load(p.getPhotoUrl())
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .circleCrop())
                .into(holder.photo);
    }

    @Override
    public int getItemCount() { return displayedPlayers.size(); }

    @Override
    public Filter getFilter() { return playerFilter; }

    private void showEditDialog(Player player, int position) {
        View popup = LayoutInflater.from(context)
                .inflate(R.layout.dialog_edit_score, null);

        CircleImageView photo     = popup.findViewById(R.id.dialog_photo);
        RatingBar       ratingBar = popup.findViewById(R.id.dialog_ratingbar);
        TextView        name      = popup.findViewById(R.id.dialog_name);
        TextView        club      = popup.findViewById(R.id.dialog_club);

        name.setText(player.getFullName());
        club.setText(player.getClub());
        ratingBar.setRating(player.getScore());

        Glide.with(context)
                .load(player.getPhotoUrl())
                .apply(new RequestOptions()
                        .circleCrop()
                        .error(R.mipmap.ic_launcher))
                .into(photo);

        new AlertDialog.Builder(context)
                .setTitle("Modifier la note")
                .setView(popup)
                .setPositiveButton("OK", (dialog, which) -> {
                    player.setScore(ratingBar.getRating());
                    PlayerService.getInstance().modify(player);
                    notifyItemChanged(position);
                })
                .setNegativeButton("Annuler", null)
                .show();
    }

    public static class PlayerViewHolder extends RecyclerView.ViewHolder {
        CircleImageView photo;
        TextView name, club, position, playerId;
        RatingBar rating;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            photo    = itemView.findViewById(R.id.player_photo);
            name     = itemView.findViewById(R.id.player_name);
            club     = itemView.findViewById(R.id.player_club);
            position = itemView.findViewById(R.id.player_position);
            playerId = itemView.findViewById(R.id.player_id);
            rating   = itemView.findViewById(R.id.player_rating);
        }
    }

    private class PlayerFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence query) {
            List<Player> result = new ArrayList<>();
            if (query == null || query.length() == 0) {
                result.addAll(allPlayers);
            } else {
                String pattern = query.toString().toLowerCase().trim();
                for (Player p : allPlayers) {
                    if (p.getFullName().toLowerCase().contains(pattern)
                            || p.getClub().toLowerCase().contains(pattern)) {
                        result.add(p);
                    }
                }
            }
            FilterResults fr = new FilterResults();
            fr.values = result;
            fr.count  = result.size();
            return fr;
        }

        @Override
        @SuppressWarnings("unchecked")
        protected void publishResults(CharSequence query, FilterResults results) {
            displayedPlayers = (List<Player>) results.values;
            notifyDataSetChanged();
        }
    }
}