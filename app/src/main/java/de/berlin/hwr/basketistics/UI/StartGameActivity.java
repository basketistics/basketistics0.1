package de.berlin.hwr.basketistics.UI;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import de.berlin.hwr.basketistics.Persistency.Entities.MatchEntity;
import de.berlin.hwr.basketistics.R;
import de.berlin.hwr.basketistics.ViewModel.MatchesViewModel;

public class StartGameActivity extends AppCompatActivity {

    public final static String TAG = "StartGameActivity";
    public final static String STARTERS ="de.berlin.hwr.basketistics.UI.StartGameActivity.STARTERS";
    public final static String MATCH ="de.berlin.hwr.basketistics.UI.StartGameActivity.MATCH";

    private EditText matchCityEditEText;
    private EditText matchOpponentEditText;
    private Switch matchIsHomeSwitch;
    private Boolean isHome = false;
    private EditText player1EditText;
    private EditText player2EditText;
    private EditText player3EditText;
    private EditText player4EditText;
    private EditText player5EditText;
    private Button startGameButton;

    private MatchesViewModel matchesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        // Test
        Intent teamIntent = new Intent(this, TeamActivity.class);
        // startActivity(teamIntent);

        matchesViewModel = ViewModelProviders.of(this).get(MatchesViewModel.class);

        matchCityEditEText = findViewById(R.id.match_cityEditText);
        matchOpponentEditText = findViewById(R.id.match_contrahentEditText);
        matchIsHomeSwitch = findViewById(R.id.match_homeSwitch);
        player1EditText = findViewById(R.id.player1);
        player2EditText = findViewById(R.id.player2);
        player3EditText = findViewById(R.id.player3);
        player4EditText = findViewById(R.id.player4);
        player5EditText = findViewById(R.id.player5);
        startGameButton = findViewById(R.id.match_startGameButton);

        matchIsHomeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isHome = isChecked;
            }
        });

        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MatchEntity match = new MatchEntity(
                        matchCityEditEText.getText().toString(),
                        matchOpponentEditText.getText().toString(),
                        isHome
                );

                Log.i(TAG, match.getCity() + " " + match.getOpponent() + " " + match.getHome());

                matchesViewModel.insert(match);

                int[] starters = new int[5];
                starters[0] = Integer.parseInt(player1EditText.getText().toString());
                starters[1] = Integer.parseInt(player2EditText.getText().toString());
                starters[2] = Integer.parseInt(player3EditText.getText().toString());
                starters[3] = Integer.parseInt(player4EditText.getText().toString());
                starters[4] = Integer.parseInt(player5EditText.getText().toString());

                int matchId = matchesViewModel.getAllMatches().getValue().get(
                        matchesViewModel.getAllMatches().getValue().size() - 1).getId();

                // Changed for testing to getApplicationContext
                Intent gameIntent = new Intent(StartGameActivity.this, GameActivity.class);
                gameIntent.putExtra(STARTERS, starters);

                // matchId is always null here
                // gameIntent.putExtra(MATCH, matchId);
                gameIntent.putExtra("origin", TAG);
                startActivity(gameIntent);

            }
        });

    }
}
