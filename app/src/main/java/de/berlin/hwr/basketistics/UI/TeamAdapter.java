package de.berlin.hwr.basketistics.UI;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import de.berlin.hwr.basketistics.R;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {

    private String[] teamDataset;

    public static class TeamViewHolder extends RecyclerView.ViewHolder {
        // Example with Strings
        // TODO: Change.
        public TextView textView;
        public TeamViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }

    public TeamAdapter(String[] teamDataset) {
        this.teamDataset = teamDataset;
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.team_list_item, parent, false);

        return null;
    }

    // Replaces the contents of a view (invoked by layout manager)
    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder teamViewHolder, int i) {
        teamViewHolder.textView.setText(teamDataset[i]);
    }

    @Override
    public int getItemCount() {
        return teamDataset.length;
    }
}
