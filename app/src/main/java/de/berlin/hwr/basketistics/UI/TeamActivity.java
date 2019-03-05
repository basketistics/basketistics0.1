package de.berlin.hwr.basketistics.UI;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import de.berlin.hwr.basketistics.Persistency.Entities.PlayerEntity;
import de.berlin.hwr.basketistics.R;
import de.berlin.hwr.basketistics.ViewModel.TeamViewModel;
public class TeamActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    private BottomNavigationView bottomNavigationView;

    private TeamAdapter teamAdapter;

    private final static int ADD_PLAYER_ACTIVITY_REQUEST_CODE = 3;
    public static final String TAG = "TeamActivity";

    private FloatingActionButton addPlayerButton;
    private TeamViewModel teamViewModel;

    private RecyclerView teamRecyclerView;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i(TAG, "reached onActivityResult().");

        if (requestCode == ADD_PLAYER_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            PlayerEntity playerEntity = (PlayerEntity) data.getExtras().get(AddPlayerActivity.EXTRA_REPLY);
            teamViewModel.insert(playerEntity);
            teamAdapter.setTeam(teamViewModel.getAllPlayers().getValue());

            // Get imageUri
            final String imageUri = (String) data.getExtras().get(AddPlayerActivity.IMAGE_URI);

            // Only save Uri if valid
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("PLAYER" + teamViewModel.getAllPlayers().getValue().size(), imageUri.toString());
            editor.commit();
            Log.e(TAG, imageUri.toString());

        } else {
            // TODO: Exceptionhandling.
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        sharedPreferences = getSharedPreferences(FirstRunActivity.PREFERENCES, MODE_PRIVATE);

        bottomNavigationView = findViewById(R.id.teamBottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.team:
                        Log.i(TAG, "already in TeamActivity.");
                        break;
                    case R.id.matches:
                        Intent matchesIntent = new Intent(TeamActivity.this, MatchesActivity.class);
                        startActivity(matchesIntent);
                        break;
                    case R.id.reports:
                        Log.i(TAG, "ReportsActivity not implemented yet.");
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });

        // Set up RecyclerView
        teamRecyclerView = (RecyclerView) findViewById(R.id.teamRecyclerView);
        teamAdapter = new TeamAdapter(this);
        teamRecyclerView.setAdapter(teamAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        teamRecyclerView.setLayoutManager(linearLayoutManager);

        // get ViewModel
        teamViewModel = ViewModelProviders.of(this).get(TeamViewModel.class);

        // Observe ViewModel
        teamViewModel.getAllPlayers().observe(this, new Observer<List<PlayerEntity>>() {
            @Override
            public void onChanged(@Nullable List<PlayerEntity> playerEntities) {
                Log.e(TAG, "teamViewModel has changed.");
                // Update cached players
                teamAdapter.setTeam(teamViewModel.getAllPlayers().getValue());
            }
        });

        addPlayerButton = findViewById(R.id.addPlayerButton);
        addPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addPlayerIntent = new Intent(TeamActivity.this, AddPlayerActivity.class);
                startActivityForResult(addPlayerIntent, ADD_PLAYER_ACTIVITY_REQUEST_CODE);
            }
        });
    }
}
