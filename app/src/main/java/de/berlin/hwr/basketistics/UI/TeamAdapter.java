package de.berlin.hwr.basketistics.UI;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.berlin.hwr.basketistics.Persistency.MockPlayerDB;
import de.berlin.hwr.basketistics.R;
import de.berlin.hwr.basketistics.ViewModel.TeamViewModel;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {

    private static final String TAG = "TeamAdapter";

    private TeamViewModel teamViewModel;

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

    public TeamAdapter(TeamViewModel teamViewModel) {
        this.teamViewModel = teamViewModel;
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout playerListItem = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.team_list_item, parent, false);
        TeamViewHolder teamViewHolder = new TeamViewHolder(playerListItem);
        return teamViewHolder;
    }

    // Replaces the contents of a view (invoked by layout manager)
    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder teamViewHolder, int i) {
        Log.e(TAG, "onBindViewHolder was entered.");
        // TODO: teamViewHolder.playerImageView.setImageDrawable();
        teamViewHolder.playerName.setText(teamViewModel.getTeam().getValue().get(i).name);
        teamViewHolder.playerNumber.setText("" + teamViewModel.getTeam().getValue().get(i).number);
        teamViewHolder.playerDescription.setText(teamViewModel.getTeam().getValue().get(i).description);
    }

    @Override
    public int getItemCount() {
        return teamViewModel.getTeam().getValue().size();
    }
}