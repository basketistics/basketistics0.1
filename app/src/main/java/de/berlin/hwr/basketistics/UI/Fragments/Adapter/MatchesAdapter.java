package de.berlin.hwr.basketistics.UI.Fragments.Adapter;

import android.content.Context;
import android.content.Intent;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import de.berlin.hwr.basketistics.Persistency.Entities.MatchEntity;
import de.berlin.hwr.basketistics.R;
import de.berlin.hwr.basketistics.UI.MainActivity;
import de.berlin.hwr.basketistics.UI.OnMatchReportClickedListener;
import de.berlin.hwr.basketistics.UI.StartGameActivity;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.MatchesViewHolder> {

    private static final String TAG = "MatchesAdapter";
    public static final String MATCH_ID = "de.berlin.hwr.basketistics.UI.Fragments.Adapter.MatchesAdapter.MATCH_ID";

    // Cached copy of Matches
    private List<MatchEntity> matches;

    private LayoutInflater inflater;
    private Context context;

    private String teamName;

    private OnMatchReportClickedListener onMatchReportClickedListener;

    public static class MatchesViewHolder extends RecyclerView.ViewHolder {

        private TextView gameFinished;
        private TextView matchHomeTeam;
        private TextView matchOutTeam;
        private TextView matchDate;
        private TextView matchCity;
        private Button startGameButton;

        public MatchesViewHolder(View itemView) {
            super(itemView);
            gameFinished = (TextView) itemView.findViewById(R.id.newMatchesItemGameFinishedTextView);
            matchHomeTeam = itemView.findViewById(R.id.listMatchHomeTeam);
            matchOutTeam = itemView.findViewById(R.id.newStartOut);
            matchDate = itemView.findViewById(R.id.newStartDate);
            matchCity = itemView.findViewById(R.id.newStartCity);
            startGameButton = itemView.findViewById(R.id.listMatchDoSomethingButton);
        }
    }

    public MatchesAdapter(Context context, String teamName, OnMatchReportClickedListener onMatchReportClickedListener) {
        this.context = context;
        this.teamName = teamName;
        this.onMatchReportClickedListener = onMatchReportClickedListener;
        inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public MatchesAdapter.MatchesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final CardView cardView = (CardView) inflater.inflate(R.layout.new_match_list_item, parent, false);
        MatchesAdapter.MatchesViewHolder matchesViewHolder = new MatchesAdapter.MatchesViewHolder(cardView);
        return matchesViewHolder;
    }

    // Replaces the contents of a view (invoked by layout manager)
    @Override
    public void onBindViewHolder(@NonNull final MatchesViewHolder matchesViewHolder, int i) {

        final int matchId = matches.get(i).getId();

        matchesViewHolder.matchDate.setText(matches.get(i).getDate());
        matchesViewHolder.matchCity.setText(matches.get(i).getCity());

        // check whether game is finished
        if (matches.get(i).getIsFinished()) {
            matchesViewHolder.gameFinished.setVisibility(View.VISIBLE);
            matchesViewHolder.startGameButton.setText("Ergebnis anzeigen");

            // Make Buttons clickable
            matchesViewHolder.startGameButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMatchReportClickedListener.onReportClicked(matchId);
                }
            });
        }
        else {
            matchesViewHolder.gameFinished.setVisibility(View.GONE);
            matchesViewHolder.startGameButton.setText("Spiel auswählen");

            // Make Buttons clickable
            matchesViewHolder.startGameButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent startGameIntent = new Intent(matchesViewHolder.startGameButton.getContext(), StartGameActivity.class);
                    startGameIntent.putExtra(MATCH_ID, matchId);
                    Log.i(TAG, "MatchId: " + matchId);
                    matchesViewHolder.startGameButton.getContext().startActivity(startGameIntent);
                }
            });
        }

        if (matches.get(i).getIsHome()) {
            matchesViewHolder.matchHomeTeam.setText(teamName);
            matchesViewHolder.matchOutTeam.setText(matches.get(i).getOpponent());
        } else {
            matchesViewHolder.matchHomeTeam.setText(matches.get(i).getOpponent());
            matchesViewHolder.matchOutTeam.setText(teamName);
        }

    }

    public void setMatches(List<MatchEntity> matches) {
        this.matches = matches;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    public void setTeamName(String teamName) {this.teamName = teamName;}
}
