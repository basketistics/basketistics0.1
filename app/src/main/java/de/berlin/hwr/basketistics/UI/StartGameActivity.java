package de.berlin.hwr.basketistics.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import de.berlin.hwr.basketistics.R;

public class StartGameActivity extends AppCompatActivity {

    EditText matchCityEditEText;
    EditText matchOpponentEditText;
    Switch matchIsHomeSwitch;
    Button startGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        matchCityEditEText = findViewById(R.id.match_cityEditText);
        matchOpponentEditText = findViewById(R.id.match_contrahentEditText);
        matchIsHomeSwitch = findViewById(R.id.match_homeSwitch);
        startGameButton = findViewById(R.id.match_startGameButton);

        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameIntent = new Intent(StartGameActivity.this, GameActivity.class);
                startActivity(gameIntent);
            }
        });
    }
}
