package de.berlin.hwr.basketistics.UI;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import de.berlin.hwr.basketistics.Persistency.Entities.MatchEntity;
import de.berlin.hwr.basketistics.Persistency.Entities.PlayerEntity;
import de.berlin.hwr.basketistics.R;
import de.berlin.hwr.basketistics.UI.Fragments.Adapter.MatchesAdapter;
import de.berlin.hwr.basketistics.UI.Fragments.Adapter.TeamAdapter;
import de.berlin.hwr.basketistics.ViewModel.MatchesViewModel;
import de.berlin.hwr.basketistics.ViewModel.TeamViewModel;

public class StartGameActivity extends AppCompatActivity implements TeamAdapter.ClickListener {

    public final static String TAG = "StartGameActivity";
    public final static String STARTERS = "de.berlin.hwr.basketistics.UI.StartGameActivity.STARTERS";
    public final static String MATCH = "de.berlin.hwr.basketistics.UI.StartGameActivity.MATCH";

    private SharedPreferences sharedPreferences;

    private MatchesViewModel matchesViewModel;
    private TeamViewModel teamViewModel;

    // Match
    private TextView opponentTextView;
    private TextView teamTextView;
    private TextView cityTextView;
    private TextView dateTextView;
    LinearLayout matchLinearLayout;

    int matchId;

    // Starters
    private TextView[] starterTextViews = new TextView[5];

    // Players popup
    PopupWindow playerPopupWindow;
    int clickedPlayerIndex;

    private Button startGameButton;

    private int[] starters = new int[5];

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putIntArray("starters", starters);
    }

    @Override
    public void onItemClicked(PlayerEntity playerEntity) {
        starters[clickedPlayerIndex] = playerEntity.getId();
        starterTextViews[clickedPlayerIndex].setText(
                playerEntity.getFirstName() + " "
                        + playerEntity.getLastName() + " "
                        + playerEntity.getNumber());
    }

    private void attachPopups() {

        //TODO: Refactor into proper OnClicklistener

        for (int i = 0; i < 5; i++) {

            // Make player-listings clickable
            final int finalI = i;
            starterTextViews[i].setOnClickListener(new View.OnClickListener() {

                RecyclerView playerRecyclerView;
                TeamAdapter teamAdapter;

                @Override
                public void onClick(View v) {

                    if (teamViewModel.getAllPlayers().getValue().size() < 5) {
                        Toast.makeText(
                                StartGameActivity.this,
                                "Bitte legen Sie mindestens 5 Spieler an.",
                                Toast.LENGTH_LONG
                        ).show();

                    } else {

                        clickedPlayerIndex = finalI;

                        // Inflate the popup_points.xml View
                        LayoutInflater layoutInflater = StartGameActivity.this.getLayoutInflater();
                        View playerListView = layoutInflater.inflate(R.layout.player_list_popup, null);

                        // Create the popup Window
                        playerPopupWindow = new PopupWindow(StartGameActivity.this);
                        playerPopupWindow.setContentView(playerListView);
                        playerPopupWindow.setFocusable(true);
                        // TODO: Calculate from displaysize and pixeldensity!
                        playerPopupWindow.setClippingEnabled(false);
                        playerPopupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
                        playerPopupWindow.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
                        playerPopupWindow.showAsDropDown(v);

                        // Set up RecyclerView
                        playerRecyclerView =
                                (RecyclerView) playerPopupWindow.getContentView().findViewById(R.id.playerListRecyclerView);
                        teamAdapter = new TeamAdapter(StartGameActivity.this, StartGameActivity.this, playerPopupWindow);
                        teamAdapter.setTeam(teamViewModel.getAllPlayers().getValue());
                        playerRecyclerView.setAdapter(teamAdapter);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(StartGameActivity.this);
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        playerRecyclerView.setLayoutManager(linearLayoutManager);
                    }
                }
            });
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null && savedInstanceState.get("starters") != null) {
            starters = (int[]) savedInstanceState.get("starters");
        }

        setContentView(R.layout.new_start_game_activity);

        sharedPreferences = getSharedPreferences(FirstRunActivity.PREFERENCES, MODE_PRIVATE);

        // Get ViewModels
        matchesViewModel = ViewModelProviders.of(this).get(MatchesViewModel.class);
        teamViewModel = ViewModelProviders.of(this).get(TeamViewModel.class);

        // Find Views
        opponentTextView = findViewById(R.id.newStartOut);
        teamTextView = findViewById(R.id.newStartHome);
        cityTextView = findViewById(R.id.newStartCity);
        dateTextView = findViewById(R.id.newStartDate);

        starterTextViews[0] = findViewById(R.id.newStartGamePLayer1);
        starterTextViews[1] = findViewById(R.id.newStartGamePlayer2);
        starterTextViews[2] = findViewById(R.id.newStartGamePlayer3);
        starterTextViews[3] = findViewById(R.id.newStartGamePlayer4);
        starterTextViews[4] = findViewById(R.id.newStartGamePlayer5);

        startGameButton = findViewById(R.id.newStartGameButton);

        try {
            // Get current match from MatchesViewModel
            matchId = (int) getIntent().getExtras().get(MatchesAdapter.MATCH_ID);
            Log.e(TAG, "" + matchId);
            MatchEntity match = matchesViewModel.getMatchById(matchId);

            // Set texts for match
            opponentTextView.setText(match.getOpponent());
            teamTextView.setText(sharedPreferences.getString("team_name", "<PREFERENCES CORRUPTED>"));
            cityTextView.setText(match.getCity());
            dateTextView.setText(match.getDate());

        } catch (ExecutionException e) {
            e.printStackTrace();
            Intent matchesIntent = new Intent(this, MatchesActivity.class);
            startActivity(matchesIntent);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Intent matchesIntent = new Intent(this, MatchesActivity.class);
            startActivity(matchesIntent);
        }

        attachPopups();

        // Attach startGame Button
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Integer> startersUniq = new ArrayList<>();
                for (int starterId : starters) {
                    if (!startersUniq.contains(starterId)) {
                        startersUniq.add(starterId);
                    }
                }
                if (starters != null) {
                    if (startersUniq.size() == 5) {

                        Intent gameIntent = new Intent(StartGameActivity.this, GameActivity.class);
                        gameIntent.putExtra("origin", TAG);
                        gameIntent.putExtra(STARTERS, starters);
                        gameIntent.putExtra(MATCH, matchId);
                        Log.e(TAG, "" + matchId);
                        finishAffinity();
                        startActivity(gameIntent);
                    } else {
                        String message = "Bitte waehlen Sie jeden Spieler nur einmal aus.";
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        // Check, whether players have already been choosen
        for (int i = 0; i < 5; i++) {
            if (starters[i] != 0) {
                starterTextViews[i].setText(
                        teamViewModel.getAllPlayers().getValue().get(i).getFirstName() + " " + teamViewModel.getAllPlayers().getValue().get(i).getLastName());
            }
        }
    }
}
