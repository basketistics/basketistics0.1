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

    private final static String TAG = "StartGameActivity";

    private EditText matchCityEditEText;
    private EditText matchOpponentEditText;
    private Switch matchIsHomeSwitch;
    private Boolean isHome = false;
    private Button startGameButton;

    private MatchesViewModel matchesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        matchesViewModel = ViewModelProviders.of(this).get(MatchesViewModel.class);

        matchCityEditEText = findViewById(R.id.match_cityEditText);
        matchOpponentEditText = findViewById(R.id.match_contrahentEditText);
        matchIsHomeSwitch = findViewById(R.id.match_homeSwitch);
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

                Intent gameIntent = new Intent(StartGameActivity.this, GameActivity.class);
                startActivity(gameIntent);
            }
        });
    }
}
