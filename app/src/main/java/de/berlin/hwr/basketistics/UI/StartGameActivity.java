package de.berlin.hwr.basketistics.UI;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.media.AudioTrack;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import de.berlin.hwr.basketistics.Persistency.Entities.MatchEntity;
import de.berlin.hwr.basketistics.R;
import de.berlin.hwr.basketistics.ViewModel.MatchesViewModel;

public class StartGameActivity extends AppCompatActivity {

    public final static String TAG = "StartGameActivity";
    public final static String STARTERS ="de.berlin.hwr.basketistics.UI.StartGameActivity.STARTERS";
    public final static String MATCH ="de.berlin.hwr.basketistics.UI.StartGameActivity.MATCH";

    // Match
    private TextView opponentTextView;
    private TextView teamTextView;
    private TextView cityTextView;
    private TextView dateTextView;
    private TextView descriptionTextView;
    LinearLayout matchLinearLayout;

    // Starters
    private TextView starter1;
    private TextView starter2;
    private TextView starter3;
    private TextView starter4;
    private TextView starter5;

    private Button startGameButton;

    private MatchesViewModel matchesViewModel;

    private void showPlayerPopUp() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        opponentTextView = findViewById(R.id.startOutTeam);
        teamTextView = findViewById(R.id.startHomeTeam);
        cityTextView = findViewById(R.id.startCityTextView);
        dateTextView = findViewById(R.id.startDateTextView);
        descriptionTextView = findViewById(R.id.startDescriptionTextView);
        matchLinearLayout = findViewById(R.id.matchListItemLinearLayout);
        matchLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPlayerPopUp();
            }
        });

        starter1 = findViewById(R.id.starter1);
        starter2 = findViewById(R.id.starter2);
        starter3 = findViewById(R.id.starter3);
        starter4 = findViewById(R.id.starter4);
        starter5 = findViewById(R.id.starter5);


    }
}
