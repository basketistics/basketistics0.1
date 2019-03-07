package de.berlin.hwr.basketistics.UI;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioTrack;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import de.berlin.hwr.basketistics.Persistency.Entities.MatchEntity;
import de.berlin.hwr.basketistics.Persistency.Entities.PlayerEntity;
import de.berlin.hwr.basketistics.R;
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
    public void onItemClicked(PlayerEntity playerEntity) {
        starters[clickedPlayerIndex] = playerEntity.getId();
        starterTextViews[clickedPlayerIndex].setText(
                playerEntity.getFirstName() + " "
                        + playerEntity.getLastName() + " "
                        + playerEntity.getNumber());
    }

    private void attachPopups() {

        //TODO: Refactor into proper OnClicklistener

        // Make player-listings clickable
        starterTextViews[0].setOnClickListener(new View.OnClickListener() {

            RecyclerView playerRecyclerView;
            TeamAdapter teamAdapter;

            @Override
            public void onClick(View v) {

                clickedPlayerIndex = 0;

                // Inflate the popup_points.xml View
                LayoutInflater layoutInflater = StartGameActivity.this.getLayoutInflater();
                View playerListView = layoutInflater.inflate(R.layout.player_list_popup, null);

                // Create the popup Window
                playerPopupWindow = new PopupWindow(StartGameActivity.this);
                playerPopupWindow.setContentView(playerListView);
                playerPopupWindow.setFocusable(true);
                // TODO: Calculate from displaysize and pixeldensity!
                playerPopupWindow.setWidth(1300);
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
        });

        starterTextViews[1].setOnClickListener(new View.OnClickListener() {

            RecyclerView playerRecyclerView;
            TeamAdapter teamAdapter;

            @Override
            public void onClick(View v) {

                clickedPlayerIndex = 1;

                // Inflate the popup_points.xml View
                LayoutInflater layoutInflater = StartGameActivity.this.getLayoutInflater();
                View playerListView = layoutInflater.inflate(R.layout.player_list_popup, null);

                // Create the popup Window
                playerPopupWindow = new PopupWindow(StartGameActivity.this);
                playerPopupWindow.setContentView(playerListView);
                playerPopupWindow.setFocusable(true);
                // TODO: Calculate from displaysize and pixeldensity!
                playerPopupWindow.setWidth(1300);
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
        });

        starterTextViews[2].setOnClickListener(new View.OnClickListener() {

            RecyclerView playerRecyclerView;
            TeamAdapter teamAdapter;

            @Override
            public void onClick(View v) {

                clickedPlayerIndex = 2;

                // Inflate the popup_points.xml View
                LayoutInflater layoutInflater = StartGameActivity.this.getLayoutInflater();
                View playerListView = layoutInflater.inflate(R.layout.player_list_popup, null);

                // Create the popup Window
                playerPopupWindow = new PopupWindow(StartGameActivity.this);
                playerPopupWindow.setContentView(playerListView);
                playerPopupWindow.setFocusable(true);
                // TODO: Calculate from displaysize and pixeldensity!
                playerPopupWindow.setWidth(1300);
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
        });

        starterTextViews[3].setOnClickListener(new View.OnClickListener() {

            RecyclerView playerRecyclerView;
            TeamAdapter teamAdapter;

            @Override
            public void onClick(View v) {

                clickedPlayerIndex = 3;

                // Inflate the popup_points.xml View
                LayoutInflater layoutInflater = StartGameActivity.this.getLayoutInflater();
                View playerListView = layoutInflater.inflate(R.layout.player_list_popup, null);

                // Create the popup Window
                playerPopupWindow = new PopupWindow(StartGameActivity.this);
                playerPopupWindow.setContentView(playerListView);
                playerPopupWindow.setFocusable(true);
                // TODO: Calculate from displaysize and pixeldensity!
                playerPopupWindow.setWidth(1300);
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
        });

        starterTextViews[4].setOnClickListener(new View.OnClickListener() {

            RecyclerView playerRecyclerView;
            TeamAdapter teamAdapter;

            @Override
            public void onClick(View v) {

                clickedPlayerIndex = 4;

                // Inflate the popup_points.xml View
                LayoutInflater layoutInflater = StartGameActivity.this.getLayoutInflater();
                View playerListView = layoutInflater.inflate(R.layout.player_list_popup, null);

                // Create the popup Window
                playerPopupWindow = new PopupWindow(StartGameActivity.this);
                playerPopupWindow.setContentView(playerListView);
                playerPopupWindow.setFocusable(true);
                // TODO: Calculate from displaysize and pixeldensity!
                playerPopupWindow.setWidth(1300);
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
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        sharedPreferences = getSharedPreferences(FirstRunActivity.PREFERENCES, MODE_PRIVATE);

        // Get ViewModels
        matchesViewModel = ViewModelProviders.of(this).get(MatchesViewModel.class);
        teamViewModel = ViewModelProviders.of(this).get(TeamViewModel.class);

        // Find Views
        opponentTextView = findViewById(R.id.start_matchItemOutTeam);
        teamTextView = findViewById(R.id.start_matchItemHomeTeam);
        cityTextView = findViewById(R.id.start_matchCityTextView);
        dateTextView = findViewById(R.id.start_matchDateTextView);

        starterTextViews[0] = findViewById(R.id.starter1);
        starterTextViews[1] = findViewById(R.id.starter2);
        starterTextViews[2] = findViewById(R.id.starter3);
        starterTextViews[3] = findViewById(R.id.starter4);
        starterTextViews[4] = findViewById(R.id.starter5);

        startGameButton = findViewById(R.id.match_startGameButton);

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
                if (starters != null) {
                    Intent gameIntent = new Intent(StartGameActivity.this, GameActivity.class);
                    gameIntent.putExtra("origin", TAG);
                    gameIntent.putExtra(STARTERS, starters);
                    gameIntent.putExtra(MATCH, matchId);
                    Log.e(TAG, "" + matchId);
                    startActivity(gameIntent);
                }
            }
        });
    }
}
