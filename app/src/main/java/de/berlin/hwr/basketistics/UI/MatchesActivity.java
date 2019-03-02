package de.berlin.hwr.basketistics.UI;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.List;

import de.berlin.hwr.basketistics.Persistency.Entities.MatchEntity;
import de.berlin.hwr.basketistics.R;
import de.berlin.hwr.basketistics.ViewModel.MatchesViewModel;

public class MatchesActivity extends AppCompatActivity {


    private BottomNavigationView bottomNavigationView;

    private MatchesAdapter matchesAdapter;

    private final static int ADD_MATCH_ACTIVITY_REQUEST_CODE = 4;
    public static final String TAG = "MatchesActivity";

    private Button addMatchButton;
    private MatchesViewModel matchesViewModel;

    private RecyclerView matchesRecyclerView;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i(TAG, "reached onActivityResult().");

        if (requestCode == ADD_MATCH_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            MatchEntity matchEntity = (MatchEntity) data.getExtras().get(AddMatchActivity.EXTRA_REPLY);
            matchesViewModel.insert(matchEntity);
            matchesAdapter.setMatches(matchesViewModel.getAllMatches().getValue());

            // Test
            for (MatchEntity match : matchesViewModel.getAllMatches().getValue()) {
                Log.e(TAG, "MatchID: " + match.getId());
            }
        } else {
            // TODO: Exceptionhandling.
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        bottomNavigationView = findViewById(R.id.matchesBottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.team:
                        Intent teamIntent = new Intent(MatchesActivity.this, TeamActivity.class);
                        startActivity(teamIntent);
                        break;
                    case R.id.matches:
                        Log.i(TAG, "already in MatchesActivity");
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
        matchesRecyclerView = (RecyclerView) findViewById(R.id.matchesRecyclerView);
        matchesAdapter = new MatchesAdapter(this);
        matchesRecyclerView.setAdapter(matchesAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        matchesRecyclerView.setLayoutManager(linearLayoutManager);

        // get ViewModel
        matchesViewModel = ViewModelProviders.of(this).get(MatchesViewModel.class);

        // Observe ViewModel
        matchesViewModel.getAllMatches().observe(this, new Observer<List<MatchEntity>>() {
            @Override
            public void onChanged(@Nullable List<MatchEntity> playerEntities) {
                Log.e(TAG, "matchesViewModel has changed.");
                // Update cached players
                matchesAdapter.setMatches(matchesViewModel.getAllMatches().getValue());
            }
        });

        addMatchButton = (Button) findViewById(R.id.addMatchButton);
        addMatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addPlayerIntent = new Intent(MatchesActivity.this, AddMatchActivity.class);
                startActivityForResult(addPlayerIntent, ADD_MATCH_ACTIVITY_REQUEST_CODE);
            }
        });

        // For testing
        Intent gameIntent = new Intent(this, GameActivity.class);
        gameIntent.putExtra("origin", StartGameActivity.TAG);
        int[] starters = {1, 2, 3, 4, 5};
        gameIntent.putExtra(StartGameActivity.STARTERS, starters);
        startActivity(gameIntent);
    }
}
