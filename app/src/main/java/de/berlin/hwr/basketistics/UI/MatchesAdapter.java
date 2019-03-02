package de.berlin.hwr.basketistics.UI;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import de.berlin.hwr.basketistics.Persistency.Entities.MatchEntity;
import de.berlin.hwr.basketistics.R;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.MatchesViewHolder> {

    private static final String TAG = "MatchesAdapter";

    // Cached copy of Matches
    private List<MatchEntity> matches;
    private LayoutInflater inflater;

    public static class MatchesViewHolder extends RecyclerView.ViewHolder {

        private TextView matchHomeTeam;
        private TextView matchOutTeam;
        private TextView matchDate;
        private TextView matchCity;
        private TextView matchDescription;

        public MatchesViewHolder(View itemView) {
            super(itemView);
            matchHomeTeam = itemView.findViewById(R.id.matchItemHomeTeam);
            matchOutTeam = itemView.findViewById(R.id.matchItemOutTeam);
            matchDate = itemView.findViewById(R.id.matchDateTextView);
            matchCity = itemView.findViewById(R.id.matchCityTextView);
            matchDescription = itemView.findViewById(R.id.matchDescriptionTextView);
        }
    }

    public MatchesAdapter(Context context) {
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
    public void onBindViewHolder(@NonNull MatchesViewHolder matchesViewHolder, int i) {
        if (matches.get(i).getHome()) {
            matchesViewHolder.matchHomeTeam.setText("MeinTeam");
            matchesViewHolder.matchOutTeam.setText(matches.get(i).getOpponent());
        } else {
            matchesViewHolder.matchHomeTeam.setText(matches.get(i).getOpponent());
            matchesViewHolder.matchOutTeam.setText("MeinTeam");
        }
        matchesViewHolder.matchDate.setText(matches.get(i).getDate().toString());
        matchesViewHolder.matchCity.setText(matches.get(i).getCity());
        matchesViewHolder.matchDescription.setText(matches.get(i).getDescription());
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
