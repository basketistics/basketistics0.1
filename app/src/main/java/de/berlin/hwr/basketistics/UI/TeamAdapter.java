package de.berlin.hwr.basketistics.UI;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

import de.berlin.hwr.basketistics.Persistency.Entities.PlayerEntity;
import de.berlin.hwr.basketistics.R;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {

    private static final String TAG = "TeamAdapter";

    // Cached copy of Players
    private List<PlayerEntity> team;
    private LayoutInflater inflater;
    private ClickListener clickListener;
    private PopupWindow popupWindow;
    private Context context;

    public static class TeamViewHolder extends RecyclerView.ViewHolder {

        private ImageView playerImageView;
        private TextView playerName;
        private TextView playerNumber;
        private TextView playerDescription;
        private CardView cardView;

        public TeamViewHolder(View itemView) {
            super(itemView);
            playerImageView = (ImageView) itemView.findViewById(R.id.listPLayerImageView);
            playerName = (TextView) itemView.findViewById(R.id.listPLayerName);
            playerNumber = (TextView) itemView.findViewById(R.id.listPlayerNumberTextView);
            playerDescription = (TextView) itemView.findViewById(R.id.listPLayerDescription);
            cardView = (CardView) itemView.findViewById(R.id.newPlayerListItem);
        }
    }

    public interface ClickListener {
        void onItemClicked(PlayerEntity playerEntity);
    }

    public TeamAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public TeamAdapter(Context context, ClickListener clickListener, PopupWindow popupWindow) {
        inflater = LayoutInflater.from(context);
        this.clickListener = clickListener;
        this.popupWindow = popupWindow;
        this.context = context;
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) inflater.inflate(R.layout.new_player_list_item, parent, false);
        TeamViewHolder teamViewHolder = new TeamViewHolder(cardView);
        return teamViewHolder;
    }

    // Replaces the contents of a view (invoked by layout manager)
    @Override
    public void onBindViewHolder(@NonNull final TeamViewHolder teamViewHolder, final int i) {

        Log.i(TAG, "onBindViewHolder was entered.");

        teamViewHolder.playerName.setText(team.get(i).getFirstName() + " " + team.get(i).getLastName());
        teamViewHolder.playerNumber.setText("" + team.get(i).getNumber());
        teamViewHolder.playerDescription.setText(team.get(i).getDescription());

        // Set Image
        String fileName = team.get(i).getImageFilename();
        Log.e(TAG, fileName);
        if (fileName != "") {

            File directory = context.getDir("images", Context.MODE_PRIVATE);
            File image = new File(directory, team.get(i).getImageFilename());
            Uri imageUri = Uri.fromFile(image);

            Glide.with(context)
                    .load(imageUri)
                    .centerCrop()
                    .placeholder(R.drawable.marcel_davis)
                    .into(teamViewHolder.playerImageView);

        }

        // Set ClickListener
        if (clickListener != null) {
            teamViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlayerEntity player = team.get(i);
                    clickListener.onItemClicked(player);
                    popupWindow.dismiss();
                }
            });
        }
    }

    public void setTeam(List<PlayerEntity> team) {
        this.team = team;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return team.size();
    }
}