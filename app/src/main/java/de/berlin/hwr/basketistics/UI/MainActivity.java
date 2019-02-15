package de.berlin.hwr.basketistics.UI;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;

import de.berlin.hwr.basketistics.EventObserver;
import de.berlin.hwr.basketistics.Persistency.MockEventDB;
import de.berlin.hwr.basketistics.PlayerButtonsOnClickListener;
import de.berlin.hwr.basketistics.R;
import de.berlin.hwr.basketistics.ViewModel.BasketisticsViewModel;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    // Lets have all the Buttons in an array to save some space
    private Button[][] playerButtons = new Button[5][7];

    private BasketisticsViewModel basketisticsViewModel;

    //  TODO: Only here for testing...
    MockEventDB mockEventDB = new MockEventDB();
    //private final EventObserver eventObserver = new EventObserver(1, 1, mockEventDB);
    private void initMockDB() {

         /* from MockEventTypeDB:
            this.db.add(new Entry(0, "game_begin"));
            this.db.add(new Entry(1, "point_plus_1"));
            this.db.add(new Entry(2, "point_plus_2"));
            this.db.add(new Entry(3, "point_plus_3"));
            this.db.add(new Entry(4, "point_minus_1"));
            this.db.add(new Entry(5, "point_minus_2"));
            this.db.add(new Entry(6, "point_minus_3"));
            this.db.add(new Entry(7, "rebound"));
            this.db.add(new Entry(8, "assist"));
            this.db.add(new Entry(9, "block"));
            this.db.add(new Entry(10, "turnover"));
            this.db.add(new Entry(11, "foul"));
            this.db.add(new Entry(12, "steal"));
            this.db.add(new Entry(13, "game_resume"));
            this.db.add(new Entry(14, "game_pause"));
            this.db.add(new Entry(15, "game_end"));;
        */

        // Special constructor is used for points!
        basketisticsViewModel.getPoints().observe(
                this, new EventObserver(mockEventDB, basketisticsViewModel));
        basketisticsViewModel.getRebound().observe(
                this, new EventObserver(7, mockEventDB, basketisticsViewModel));
        basketisticsViewModel.getAssist().observe(
                this, new EventObserver(8, mockEventDB, basketisticsViewModel));
        basketisticsViewModel.getBlock().observe(
                this, new EventObserver(9, mockEventDB, basketisticsViewModel));
        basketisticsViewModel.getTurnover().observe(
                this, new EventObserver(10, mockEventDB, basketisticsViewModel));
        basketisticsViewModel.getFoul().observe(
                this, new EventObserver(11, mockEventDB, basketisticsViewModel));
        basketisticsViewModel.getSteal().observe(
                this, new EventObserver(12, mockEventDB, basketisticsViewModel));
    }

    public void showPointsPopup(int player, Button button) {

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
        /*/// create OnClickListener
        class PointsOnClickListener implements View.OnClickListener {

            private int points;
            private int player;

            public PointsOnClickListener(int points, int player) {
                this.points = points;
                this.player = player;
            }

            @Override
            // TODO: handle playerID stuff
            public void onClick(View v) {
                // TODO: inc points for playerID
                Log.i(TAG, "inc points - Player: " + player + "; Points: " + points);
                basketisticsViewModel.setPoints(points);
            }
        }
        */

        //// Attach PlayerButtonsOnClickListener to buttons
        plusOneButton.setOnClickListener(
                new PlayerButtonsOnClickListener(player, 0, 1, basketisticsViewModel));
        plusTwoButton.setOnClickListener(
                new PlayerButtonsOnClickListener(player, 0, 2, basketisticsViewModel));
        plusThreeButton.setOnClickListener(
                new PlayerButtonsOnClickListener(player, 0, 3, basketisticsViewModel));
        minusOneButton.setOnClickListener(
                new PlayerButtonsOnClickListener(player, 0, -1, basketisticsViewModel));
        minusTwoButton.setOnClickListener(
                new PlayerButtonsOnClickListener(player, 0, -2, basketisticsViewModel));
        minusThreeButton.setOnClickListener(
                new PlayerButtonsOnClickListener(player, 0, -3, basketisticsViewModel));

    }

    // Attach and enable Points PopUp an points buttons
    private void attachPointsPopUp() {
        // TODO: Refactor to depend on actual array
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

    private void attachViewModel() {
        // Iterate over all players
        for (int i = 0; i < 5; i++) {
            // Iterate over events except 0 (points)
            for (int j = 1; j < 7; j++) {
                playerButtons[i][j].setOnClickListener(
                        new PlayerButtonsOnClickListener(i, j, basketisticsViewModel));
            }
        }
    }

    //// ---------- Lifecycle Callbacks ------------ ////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        basketisticsViewModel = ViewModelProviders.of(this).get(BasketisticsViewModel.class);

        bindPlayerButtons();
        attachPointsPopUp();
        attachViewModel();
        initMockDB();
    }
}
