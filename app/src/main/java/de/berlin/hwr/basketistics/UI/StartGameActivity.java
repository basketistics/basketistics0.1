package de.berlin.hwr.basketistics.UI;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.media.AudioTrack;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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
import de.berlin.hwr.basketistics.R;
import de.berlin.hwr.basketistics.ViewModel.MatchesViewModel;
import de.berlin.hwr.basketistics.ViewModel.TeamViewModel;

public class StartGameActivity extends AppCompatActivity {

    public final static String TAG = "StartGameActivity";
    public final static String STARTERS = "de.berlin.hwr.basketistics.UI.StartGameActivity.STARTERS";
    public final static String MATCH = "de.berlin.hwr.basketistics.UI.StartGameActivity.MATCH";

    private MatchesViewModel matchesViewModel;
    private TeamViewModel teamViewModel;

    // Match
    private TextView opponentTextView;
    private TextView teamTextView;
    private TextView cityTextView;
    private TextView dateTextView;
    LinearLayout matchLinearLayout;

    // Starters
    private TextView starter1;
    private TextView starter2;
    private TextView starter3;
    private TextView starter4;
    private TextView starter5;

    // Players popup
    PopupWindow playerPopupWindow;

    private Button startGameButton;

    private void showPlayerPopUp() {
    }

    private void attachPopups() {

        //TODO: Refactor into proper OnClicklistener

        // Make player-listings clickable
        starter1.setOnClickListener(new View.OnClickListener() {

            RecyclerView playerRecyclerView;
            TeamAdapter teamAdapter;

            @Override
            public void onClick(View v) {
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
                teamAdapter = new TeamAdapter(StartGameActivity.this);
                teamAdapter.setTeam(teamViewModel.getAllPlayers().getValue());
                playerRecyclerView.setAdapter(teamAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(StartGameActivity.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                playerRecyclerView.setLayoutManager(linearLayoutManager);
            }
        });

        starter2.setOnClickListener(new View.OnClickListener() {

            RecyclerView playerRecyclerView;
            TeamAdapter teamAdapter;

            @Override
            public void onClick(View v) {
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
                teamAdapter = new TeamAdapter(StartGameActivity.this);
                teamAdapter.setTeam(teamViewModel.getAllPlayers().getValue());
                playerRecyclerView.setAdapter(teamAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(StartGameActivity.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                playerRecyclerView.setLayoutManager(linearLayoutManager);
            }
        });

        starter3.setOnClickListener(new View.OnClickListener() {

            RecyclerView playerRecyclerView;
            TeamAdapter teamAdapter;

            @Override
            public void onClick(View v) {
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
                teamAdapter = new TeamAdapter(StartGameActivity.this);
                teamAdapter.setTeam(teamViewModel.getAllPlayers().getValue());
                playerRecyclerView.setAdapter(teamAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(StartGameActivity.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                playerRecyclerView.setLayoutManager(linearLayoutManager);
            }
        });

        starter4.setOnClickListener(new View.OnClickListener() {

            RecyclerView playerRecyclerView;
            TeamAdapter teamAdapter;

            @Override
            public void onClick(View v) {
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
                teamAdapter = new TeamAdapter(StartGameActivity.this);
                teamAdapter.setTeam(teamViewModel.getAllPlayers().getValue());
                playerRecyclerView.setAdapter(teamAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(StartGameActivity.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                playerRecyclerView.setLayoutManager(linearLayoutManager);
            }
        });

        starter5.setOnClickListener(new View.OnClickListener() {

            RecyclerView playerRecyclerView;
            TeamAdapter teamAdapter;

            @Override
            public void onClick(View v) {
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
                teamAdapter = new TeamAdapter(StartGameActivity.this);
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

        // Get ViewModels
        matchesViewModel = ViewModelProviders.of(this).get(MatchesViewModel.class);
        teamViewModel = ViewModelProviders.of(this).get(TeamViewModel.class);

        // Find Views
        opponentTextView = findViewById(R.id.start_matchItemOutTeam);
        teamTextView = findViewById(R.id.start_matchItemHomeTeam);
        cityTextView = findViewById(R.id.start_matchCityTextView);
        dateTextView = findViewById(R.id.start_matchDateTextView);

        starter1 = findViewById(R.id.starter1);
        starter2 = findViewById(R.id.starter2);
        starter3 = findViewById(R.id.starter3);
        starter4 = findViewById(R.id.starter4);
        starter5 = findViewById(R.id.starter5);

        try {
            // Get current match from MatchesViewModel
            int matchId = (int) getIntent().getExtras().get(MatchesAdapter.MATCH_ID);
            MatchEntity match = matchesViewModel.getMatchById(matchId);

            // Set texts for match
            opponentTextView.setText(match.getOpponent());
            teamTextView.setText("MeinTeam");
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
    }
}
