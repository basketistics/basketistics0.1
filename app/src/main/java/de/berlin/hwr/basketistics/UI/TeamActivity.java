package de.berlin.hwr.basketistics.UI;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import de.berlin.hwr.basketistics.Persistency.Entities.PlayerEntity;
import de.berlin.hwr.basketistics.R;
import de.berlin.hwr.basketistics.ViewModel.TeamDBViewModel;
public class TeamActivity extends AppCompatActivity {

    private TeamAdapter teamAdapter;

    private final static int ADD_PLAYER_ACTIVITY_REQUEST_CODE = 3;
    private static final String TAG = "AddPlayerActivity";

    private Button addPlayerButton;
    private TeamDBViewModel teamDBViewModel;

    private RecyclerView teamRecyclerView;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i(TAG, "reached onActivityResult().");

        if (requestCode == ADD_PLAYER_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            PlayerEntity playerEntity = (PlayerEntity) data.getExtras().get(AddPlayerActivity.EXTRA_REPLY);
            teamDBViewModel.insert(playerEntity);
            teamAdapter.setTeam(teamDBViewModel.getAllPlayers().getValue());

            // Test
            for (PlayerEntity player : teamDBViewModel.getAllPlayers().getValue()) {
                Log.e(TAG, "PlayerID: " + player.getId());
            }
        } else {
            // TODO: Exceptionhandling.
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        // Set up RecyclerView
        teamRecyclerView = (RecyclerView) findViewById(R.id.teamRecyclerView);
        teamAdapter = new TeamAdapter(this);
        teamRecyclerView.setAdapter(teamAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        teamRecyclerView.setLayoutManager(linearLayoutManager);

        // get ViewModel
        teamDBViewModel = ViewModelProviders.of(this).get(TeamDBViewModel.class);

        // Observe ViewModel
        teamDBViewModel.getAllPlayers().observe(this, new Observer<List<PlayerEntity>>() {
            @Override
            public void onChanged(@Nullable List<PlayerEntity> playerEntities) {
                Log.e(TAG, "teamViewModel has changed.");
                // Update cached players
                teamAdapter.setTeam(teamDBViewModel.getAllPlayers().getValue());
            }
        });

        addPlayerButton = (Button) findViewById(R.id.addPlayerButton);
        addPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addPlayerIntent = new Intent(TeamActivity.this, AddPlayerActivity.class);
                startActivityForResult(addPlayerIntent, ADD_PLAYER_ACTIVITY_REQUEST_CODE);
            }
        });
    }
}
