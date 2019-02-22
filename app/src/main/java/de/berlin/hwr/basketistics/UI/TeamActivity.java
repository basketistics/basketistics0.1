package de.berlin.hwr.basketistics.UI;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import de.berlin.hwr.basketistics.Persistency.Entities.Player;
import de.berlin.hwr.basketistics.R;
import de.berlin.hwr.basketistics.ViewModel.TeamViewModel;

public class TeamActivity extends AppCompatActivity {

    private RecyclerView teamRecyclerView;
    private RecyclerView.LayoutManager teamLayoutManager;
    private RecyclerView.Adapter teamAdapter;
    private String[] teamDataset = {"Spieler 1", "Spieler 2", "Spieler 3", "Spieler 4", "Spieler 5", "Spieler 6"};
    private TeamViewModel teamViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        // Get TeamViewModel
        teamViewModel = ViewModelProviders.of(this).get(TeamViewModel.class);

        // Check whether coming from AddPlayerActivity and add player to ViewModel
        Player player = (Player) getIntent().getExtras().get("player");
        if (player != null) {
            player.id = teamViewModel.getTeam().getValue().size() + 1;
            teamViewModel.addPlayer(player);
        }

        // Attach RecyclerView
        teamRecyclerView = (RecyclerView) findViewById(R.id.teamRecyclerView);

        ///// Use only if size of layout does not change!
        teamRecyclerView.setHasFixedSize(true);

        //// use a linear LayoutManager
        teamLayoutManager = new LinearLayoutManager(this);
        teamRecyclerView.setLayoutManager(teamLayoutManager);

        //// specify adapter
        teamAdapter = new TeamAdapter(teamViewModel);
        teamRecyclerView.setAdapter(teamAdapter);

    }
}
