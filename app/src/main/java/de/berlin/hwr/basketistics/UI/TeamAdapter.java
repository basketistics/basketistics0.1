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
import android.widget.TextView;

import java.util.List;

import de.berlin.hwr.basketistics.Persistency.Entities.PlayerEntity;
import de.berlin.hwr.basketistics.Persistency.MockPlayerDB;
import de.berlin.hwr.basketistics.R;
import de.berlin.hwr.basketistics.ViewModel.TeamViewModel;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {

    private static final String TAG = "TeamAdapter";

    private TeamViewModel teamViewModel;

    // Cached copy of Players
    private List<PlayerEntity> team;
    private LayoutInflater inflater;

    // TODO: Only for testing.
    public MockPlayerDB mockPlayerDB = new MockPlayerDB();

    public static class TeamViewHolder extends RecyclerView.ViewHolder {

        private ImageView playerImageView;
        private TextView playerName;
        private TextView playerNumber;
        private TextView playerDescription;

        public TextView textView;
        public TeamViewHolder(View itemView) {
            super(itemView);
            playerImageView = (ImageView) itemView.findViewById(R.id.playerPicture);
            playerName = (TextView) itemView.findViewById(R.id.playerNameTextView);
            playerNumber = (TextView) itemView.findViewById(R.id.playerNumber);
            playerDescription = (TextView) itemView.findViewById(R.id.playerDescription1);

        }
    }

    public TeamAdapter(Context context) {
        inflater = LayoutInflater.from(context);
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
    public void onBindViewHolder(@NonNull TeamViewHolder teamViewHolder, int i) {
        Log.i(TAG, "onBindViewHolder was entered.");
        // TODO: teamViewHolder.playerImageView.setImageDrawable();
        teamViewHolder.playerName.setText(teamViewModel.getTeam().getValue().get(i).name);
        teamViewHolder.playerNumber.setText("" + teamViewModel.getTeam().getValue().get(i).number);
        teamViewHolder.playerDescription.setText(teamViewModel.getTeam().getValue().get(i).description);
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