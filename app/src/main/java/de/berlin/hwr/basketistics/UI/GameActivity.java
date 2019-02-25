package de.berlin.hwr.basketistics.UI;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import de.berlin.hwr.basketistics.Persistency.Entities.PlayerEntity;
import de.berlin.hwr.basketistics.R;
import de.berlin.hwr.basketistics.ViewModel.EventViewModel;

public class GameActivity extends AppCompatActivity {

    // For testing
    Button toTeamActivityButton;

    private static final String TAG = "GameActivity";

    // Lets have all the Buttons in an array to save some space
    private Button[][] playerButtons = new Button[5][7];

    // same for TextViews
    private TextView[][] playerTextViews = new TextView[5][7];
    private TextView[] playerDescription = new TextView[5];

    private EventViewModel eventViewModel;
    private int[] starters;


    public void showPointsPopup(int playerIndex, Button button) {

        PopupWindow pointsPopupWindow;

        Button plusOneButton;
        Button plusTwoButton;
        Button plusThreeButton;
        Button minusOneButton;
        Button minusTwoButton;
        Button minusThreeButton;

        // Inflate the popup_points.xml View
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View pointsPopupView = layoutInflater.inflate(R.layout.popup_points, null);

        // Create the popup Window
        pointsPopupWindow = new PopupWindow(this);
        pointsPopupWindow.setContentView(pointsPopupView);
        pointsPopupWindow.setFocusable(true);
        pointsPopupWindow.showAsDropDown(button);

        // Attach PopUp Buttons
        plusOneButton = (Button) pointsPopupView.findViewById(R.id.inc1PointButton);
        plusTwoButton = (Button) pointsPopupView.findViewById(R.id.inc2PointButton);
        plusThreeButton = (Button) pointsPopupView.findViewById(R.id.inc3PointButton);
        minusOneButton = (Button) pointsPopupView.findViewById(R.id.dec1PointButton);
        minusTwoButton = (Button) pointsPopupView.findViewById(R.id.dec2PointButton);
        minusThreeButton = (Button) pointsPopupView.findViewById(R.id.dec3PointButton);

        // set OnClickListeners on Buttons to connect them to ViewModel
        //// Attach PlayerButtonsOnClickListener to buttons
        plusOneButton.setOnClickListener(
                new PlayerButtonsOnClickListener(playerIndex, 0, 1, eventViewModel));
        plusTwoButton.setOnClickListener(
                new PlayerButtonsOnClickListener(playerIndex, 0, 2, eventViewModel));
        plusThreeButton.setOnClickListener(
                new PlayerButtonsOnClickListener(playerIndex, 0, 3, eventViewModel));
        minusOneButton.setOnClickListener(
                new PlayerButtonsOnClickListener(playerIndex, 0, -1, eventViewModel));
        minusTwoButton.setOnClickListener(
                new PlayerButtonsOnClickListener(playerIndex, 0, -2, eventViewModel));
        minusThreeButton.setOnClickListener(
                new PlayerButtonsOnClickListener(playerIndex, 0, -3, eventViewModel));
    }

    // Attach and enable Points PopUp an points buttons
    private void attachPointsPopUp() {
        for (int i = 0; i < 5; i++) {
            playerButtons[i][0].setOnClickListener(new PopupOnClickListener(i));
        }
    }

    class PopupOnClickListener implements View.OnClickListener {

        private int player;

        public PopupOnClickListener(int player) {
            this.player = player;
        }

        @Override
        public void onClick(View v) {
            showPointsPopup(player, (Button) v);
        }
    }

    // find button views and bind them to Button objects
    private void bindPlayerButtons() {

        playerButtons[0][0] = findViewById(R.id.points_1);
        playerButtons[1][0] = findViewById(R.id.points_2);
        playerButtons[2][0] = findViewById(R.id.points_3);
        playerButtons[3][0] = findViewById(R.id.points_4);
        playerButtons[4][0] = findViewById(R.id.points_5);

        playerButtons[0][1] = findViewById(R.id.rebound_1);
        playerButtons[1][1] = findViewById(R.id.rebound_2);
        playerButtons[2][1] = findViewById(R.id.rebound_3);
        playerButtons[3][1] = findViewById(R.id.rebound_4);
        playerButtons[4][1] = findViewById(R.id.rebound_5);

        playerButtons[0][2] = findViewById(R.id.assist_1);
        playerButtons[1][2] = findViewById(R.id.assist_2);
        playerButtons[2][2] = findViewById(R.id.assist_3);
        playerButtons[3][2] = findViewById(R.id.assist_4);
        playerButtons[4][2] = findViewById(R.id.assist_5);

        playerButtons[0][3] = findViewById(R.id.steal_1);
        playerButtons[1][3] = findViewById(R.id.steal_2);
        playerButtons[2][3] = findViewById(R.id.steal_3);
        playerButtons[3][3] = findViewById(R.id.steal_4);
        playerButtons[4][3] = findViewById(R.id.steal_5);

        playerButtons[0][4] = findViewById(R.id.block_1);
        playerButtons[1][4] = findViewById(R.id.block_2);
        playerButtons[2][4] = findViewById(R.id.block_3);
        playerButtons[3][4] = findViewById(R.id.block_4);
        playerButtons[4][4] = findViewById(R.id.block_5);

        playerButtons[0][5] = findViewById(R.id.turnOver_1);
        playerButtons[1][5] = findViewById(R.id.turnOver_2);
        playerButtons[2][5] = findViewById(R.id.turnOver_3);
        playerButtons[3][5] = findViewById(R.id.turnOver_4);
        playerButtons[4][5] = findViewById(R.id.turnOver_5);

        playerButtons[0][6] = findViewById(R.id.foul_1);
        playerButtons[1][6] = findViewById(R.id.foul_2);
        playerButtons[2][6] = findViewById(R.id.foul_3);
        playerButtons[3][6] = findViewById(R.id.foul_4);
        playerButtons[4][6] = findViewById(R.id.foul_5);
    }

    private void bindPlayerTextViews() {

        playerTextViews[0][0] = findViewById(R.id.shot_1_textView);
        playerTextViews[1][0] = findViewById(R.id.shot_2_textView);
        playerTextViews[2][0] = findViewById(R.id.shot_3_textView);
        playerTextViews[3][0] = findViewById(R.id.shot_4_textView);
        playerTextViews[4][0] = findViewById(R.id.shot_5_textView);

        playerTextViews[0][1] = findViewById(R.id.rebound_1_textView);
        playerTextViews[1][1] = findViewById(R.id.rebound_2_textView);
        playerTextViews[2][1] = findViewById(R.id.rebound_3_textView);
        playerTextViews[3][1] = findViewById(R.id.rebound_4_textView);
        playerTextViews[4][1] = findViewById(R.id.rebound_5_textView);

        playerTextViews[0][2] = findViewById(R.id.assist_1_TextView);
        playerTextViews[1][2] = findViewById(R.id.assist_2_TextView);
        playerTextViews[2][2] = findViewById(R.id.assist_3_TextView);
        playerTextViews[3][2] = findViewById(R.id.assist_4_TextView);
        playerTextViews[4][2] = findViewById(R.id.assist_5_TextView);

        playerTextViews[0][3] = findViewById(R.id.steal_1_TextView);
        playerTextViews[1][3] = findViewById(R.id.steal_2_TextView);
        playerTextViews[2][3] = findViewById(R.id.steal_3_TextView);
        playerTextViews[3][3] = findViewById(R.id.steal_4_TextView);
        playerTextViews[4][3] = findViewById(R.id.steal_5_TextView);

        playerTextViews[0][4] = findViewById(R.id.block_1_TextView);
        playerTextViews[1][4] = findViewById(R.id.block_2_TextView);
        playerTextViews[2][4] = findViewById(R.id.block_3_TextView);
        playerTextViews[3][4] = findViewById(R.id.block_4_TextView);
        playerTextViews[4][4] = findViewById(R.id.block_5_TextView);

        playerTextViews[0][5] = findViewById(R.id.turnover_1_TextView);
        playerTextViews[1][5] = findViewById(R.id.turnover_2_TextView);
        playerTextViews[2][5] = findViewById(R.id.turnover_3_TextView);
        playerTextViews[3][5] = findViewById(R.id.turnover_4_TextView);
        playerTextViews[4][5] = findViewById(R.id.turnover_5_TextView);

        playerTextViews[0][6] = findViewById(R.id.foul_1_TextView);
        playerTextViews[1][6] = findViewById(R.id.foul_2_TextView);
        playerTextViews[2][6] = findViewById(R.id.foul_3_TextView);
        playerTextViews[3][6] = findViewById(R.id.foul_4_TextView);
        playerTextViews[4][6] = findViewById(R.id.foul_5_TextView);

    }

    private void bindPlayerDescriptionTextViews() {

        playerDescription[0] = findViewById(R.id.game_playerDescription1);
        playerDescription[1] = findViewById(R.id.game_playerDescription2);
        playerDescription[2] = findViewById(R.id.game_playerDescription3);
        playerDescription[3] = findViewById(R.id.game_playerDescription4);
        playerDescription[4] = findViewById(R.id.game_playerDescription5);

        for (int i = 0; i < 5; i++) {
            PlayerEntity playerEntity = eventViewModel.getPlayerByIndex(i);
            playerDescription[i].setText(
                playerEntity.getFirstName() + " "
                + playerEntity.getLastName() + "\n"
                + playerEntity.getNumber());
        }
    }

    private void attachButtonsToViewModel() {
        // Iterate over all players
        for (int i = 0; i < 5; i++) {
            // Iterate over events except 0 (points)
            for (int j = 1; j < 7; j++) {
                playerButtons[i][j].setOnClickListener(
                        new PlayerButtonsOnClickListener(i, j, eventViewModel));
            }
        }
    }

    private void attachTextViewsToViewModel() {
        // Iterate over all players
        for (int i = 0; i < 5; i++) {
            // Iterate over events
            for (int j = 0; j < 7; j++) {
                playerTextViews[i][j].setText("" + 0);
            }
            // attach actual observer
            eventViewModel.getPlayerEvents(i).getPoints().observe(
                    this, new PlayerTextViewObserver(i, 0, eventViewModel, playerTextViews));
            eventViewModel.getPlayerEvents(i).getRebound().observe(
                    this, new PlayerTextViewObserver(i, 1, eventViewModel, playerTextViews));
            eventViewModel.getPlayerEvents(i).getAssist().observe(
                    this, new PlayerTextViewObserver(i, 2, eventViewModel, playerTextViews));
            eventViewModel.getPlayerEvents(i).getSteal().observe(
                    this, new PlayerTextViewObserver(i, 3, eventViewModel, playerTextViews));
            eventViewModel.getPlayerEvents(i).getBlock().observe(
                    this, new PlayerTextViewObserver(i, 4, eventViewModel, playerTextViews));
            eventViewModel.getPlayerEvents(i).getTurnover().observe(
                    this, new PlayerTextViewObserver(i, 5, eventViewModel, playerTextViews));
            eventViewModel.getPlayerEvents(i).getFoul().observe(
                    this, new PlayerTextViewObserver(i, 6, eventViewModel, playerTextViews));


        }
    }

    //// ---------- Lifecycle Callbacks ------------ ////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        eventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);

        // If coming from StartGameActivity set starters
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            int[] starters = (int[]) extras.get(StartGameActivity.STARTERS);
            if (starters != null) {
                this.starters = starters;
                eventViewModel.proposeStarters(starters);
            }
        }


        bindPlayerButtons();
        bindPlayerTextViews();
        bindPlayerDescriptionTextViews();
        attachPointsPopUp();
        attachButtonsToViewModel();
        attachTextViewsToViewModel();


        // For testing
        final Intent teamActivityIntent = new Intent(this, TeamActivity.class);
        Intent addPlayerintent = new Intent(this, AddPlayerActivity.class);
        //startActivity(teamActivityIntent);

        toTeamActivityButton = findViewById(R.id.toTeamActivityButton);
        toTeamActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(teamActivityIntent);
            }
        });
    }
}
