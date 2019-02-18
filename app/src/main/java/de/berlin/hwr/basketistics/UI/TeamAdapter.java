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

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {

    private static final String TAG = "TeamAdapter";

    private String[] teamDataset;

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

    public TeamAdapter(String[] teamDataset) {
        this.teamDataset = teamDataset;
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
        teamViewHolder.playerName.setText(mockPlayerDB.db.get(i).playerName);
        teamViewHolder.playerNumber.setText("" + mockPlayerDB.db.get(i).playerNumber);
        teamViewHolder.playerDescription.setText(mockPlayerDB.db.get(i).playerDescription);
    }

    @Override
    public int getItemCount() {
        return teamDataset.length;
    }
}