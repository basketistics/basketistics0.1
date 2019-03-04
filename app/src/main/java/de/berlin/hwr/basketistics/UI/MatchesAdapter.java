package de.berlin.hwr.basketistics.UI;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import de.berlin.hwr.basketistics.Persistency.Entities.MatchEntity;
import de.berlin.hwr.basketistics.R;
import de.berlin.hwr.basketistics.ViewModel.MatchesViewModel;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.MatchesViewHolder> {

    private static final String TAG = "MatchesAdapter";

    // Cached copy of Matches
    private List<MatchEntity> matches;

    private LayoutInflater inflater;
    private Context context;

    public static class MatchesViewHolder extends RecyclerView.ViewHolder {

        private MatchesViewModel matchesViewModel;
        private int matchId;

        private TextView matchHomeTeam;
        private TextView matchOutTeam;
        private TextView matchDate;
        private TextView matchCity;
        private Button startGameButton;

        public MatchesViewHolder(View itemView) {
            super(itemView);
            matchHomeTeam = itemView.findViewById(R.id.matchItemHomeTeam);
            matchOutTeam = itemView.findViewById(R.id.matchItemOutTeam);
            matchDate = itemView.findViewById(R.id.matchDateTextView);
            matchCity = itemView.findViewById(R.id.matchCityTextView);
            startGameButton = itemView.findViewById(R.id.selectMatchButton);
        }
    }

    public MatchesAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MatchesAdapter.MatchesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout matchesListItem = (LinearLayout) inflater.inflate(R.layout.match_list_item, parent, false);
        MatchesAdapter.MatchesViewHolder matchesViewHolder = new MatchesAdapter.MatchesViewHolder(matchesListItem);
        return matchesViewHolder;
    }

    // Replaces the contents of a view (invoked by layout manager)
    @Override
    public void onBindViewHolder(@NonNull final MatchesViewHolder matchesViewHolder, int i) {

        final int matchId = matches.get(i).getId();

        if (matches.get(i).getIsHome()) {
            matchesViewHolder.matchHomeTeam.setText("MeinTeam");
            matchesViewHolder.matchOutTeam.setText(matches.get(i).getOpponent());
        } else {
            matchesViewHolder.matchHomeTeam.setText(matches.get(i).getOpponent());
            matchesViewHolder.matchOutTeam.setText("MeinTeam");
        }
        matchesViewHolder.matchDate.setText(matches.get(i).getDate());
        matchesViewHolder.matchCity.setText(matches.get(i).getCity());
        matchesViewHolder.startGameButton.setText("Spiel auswählen");

        // Make Buttons clickable
        matchesViewHolder.startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startGameIntent = new Intent(matchesViewHolder.startGameButton.getContext(), StartGameActivity.class);
                startGameIntent.putExtra("matchID", matchId);
                Log.i(TAG, "MatchId: " + matchId);
                matchesViewHolder.startGameButton.getContext().startActivity(startGameIntent);
            }
        });
    }

    public void setMatches(List<MatchEntity> matches) {
        this.matches = matches;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return matches.size();
    }
}
