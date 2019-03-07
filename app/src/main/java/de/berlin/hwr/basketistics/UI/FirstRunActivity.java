package de.berlin.hwr.basketistics.UI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import de.berlin.hwr.basketistics.R;

public class FirstRunActivity extends AppCompatActivity {

    public static final String PREFERENCES = "de.berlin.hwr.basketistics.PREFERENCES";

    private EditText teamName;
    private EditText teamHome;
    private EditText teamWhatEver;

    private Button startButton;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_run);

        teamName = findViewById(R.id.firstTeamName);
        teamHome = findViewById(R.id.firstTeamHome);
        teamWhatEver = findViewById(R.id.firstTeamWhatever);
        startButton = findViewById(R.id.firstStartButton);

        sharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(teamName.getText().length() < 5 && teamHome.getText().length() < 5)) {

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("team_name", teamName.getText().toString());
                    editor.putString("team_home", teamHome.getText().toString());
                    //editor.putString("team_whatever", teamWhatEver.getText().toString());
                    editor.putBoolean("first_run", false);
                    editor.commit();

                    Intent matchesIntent = new Intent(FirstRunActivity.this, MatchesActivity.class);
                    startActivity(matchesIntent);
                }
            }
        });
    }
}
