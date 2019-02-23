package de.berlin.hwr.basketistics.UI;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import de.berlin.hwr.basketistics.Persistency.Entities.Player;
import de.berlin.hwr.basketistics.Persistency.Entities.PlayerEntity;
import de.berlin.hwr.basketistics.R;
import de.berlin.hwr.basketistics.ViewModel.TeamDBViewModel;
import de.berlin.hwr.basketistics.ViewModel.TeamViewModel;

public class TeamActivity extends AppCompatActivity {

    private String[] teamDataset = {"Spieler 1", "Spieler 2", "Spieler 3", "Spieler 4", "Spieler 5", "Spieler 6"};
    private TeamViewModel teamViewModel;

    private TeamDBViewModel teamDBViewModel;

    private RecyclerView teamRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        // TODO: Remove
        teamViewModel = ViewModelProviders.of(this).get(TeamViewModel.class);

        // Check whether coming from AddPlayerActivity and add player to ViewModel
        Player player = (Player) getIntent().getExtras().get("player");
        if (player != null) {
            player.id = teamViewModel.getTeam().getValue().size() + 1;
            teamViewModel.addPlayer(player);
        }

        // Set up RecyclerView
        teamRecyclerView = (RecyclerView) findViewById(R.id.teamRecyclerView);
        final TeamAdapter teamAdapter = new TeamAdapter(this);
        teamRecyclerView.setAdapter(teamAdapter);
        teamRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // get ViewModel
        teamDBViewModel = ViewModelProviders.of(this).get(TeamDBViewModel.class);

        // Observe ViewModel
        teamDBViewModel.getAllPlayers().observe(this, new Observer<List<PlayerEntity>>() {
            @Override
            public void onChanged(@Nullable List<PlayerEntity> playerEntities) {
                // Update cached players
                teamAdapter.setTeam(playerEntities);
            }
        });

    }
}
