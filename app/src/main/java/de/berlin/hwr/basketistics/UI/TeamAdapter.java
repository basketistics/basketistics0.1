package de.berlin.hwr.basketistics.UI;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

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

    public static class TeamViewHolder extends RecyclerView.ViewHolder {

        private ImageView playerImageView;
        private TextView playerName;
        private TextView playerNumber;
        private TextView playerDescription;
        private LinearLayout itemLinearLayout;

        public TeamViewHolder(View itemView) {
            super(itemView);
            playerImageView = (ImageView) itemView.findViewById(R.id.playerPicture);
            playerName = (TextView) itemView.findViewById(R.id.playerNameTextView);
            playerNumber = (TextView) itemView.findViewById(R.id.playerNumber);
            playerDescription = (TextView) itemView.findViewById(R.id.playerDescription1);
            itemLinearLayout = (LinearLayout) itemView.findViewById(R.id.teamListItemLinearLayout);
        }
    }

    public interface ClickListener {
        void onItemClicked(PlayerEntity playerEntity);
    }

    public TeamAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public TeamAdapter(Context context, ClickListener clickListener, PopupWindow popupWindow) {
        inflater = LayoutInflater.from(context);
        this.clickListener = clickListener;
        this.popupWindow = popupWindow;
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout playerListItem = (LinearLayout) inflater.inflate(R.layout.team_list_item, parent, false);
        TeamViewHolder teamViewHolder = new TeamViewHolder(playerListItem);
        return teamViewHolder;
    }

    // Replaces the contents of a view (invoked by layout manager)
    @Override
    public void onBindViewHolder(@NonNull final TeamViewHolder teamViewHolder, final int i) {
        Log.i(TAG, "onBindViewHolder was entered.");
        // TODO: teamViewHolder.playerImageView.setImageDrawable();
        teamViewHolder.playerName.setText(team.get(i).getFirstName() + " " + team.get(i).getLastName());
        teamViewHolder.playerNumber.setText("" + team.get(i).getNumber());
        teamViewHolder.playerDescription.setText(team.get(i).getDescription());

        // Set ClickListener
        if (clickListener != null) {
            teamViewHolder.itemLinearLayout.setOnClickListener(new View.OnClickListener() {
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