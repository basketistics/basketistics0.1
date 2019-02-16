package de.berlin.hwr.basketistics.UI;

import android.arch.lifecycle.ViewModelProviders;
import android.content.ClipData;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import de.berlin.hwr.basketistics.Persistency.MockPlayerStatsDB;
import de.berlin.hwr.basketistics.Persistency.MockDBObserver;
import de.berlin.hwr.basketistics.Persistency.MockEventDB;
import de.berlin.hwr.basketistics.R;
import de.berlin.hwr.basketistics.ViewModel.BasketisticsViewModel;

import static java.lang.String.format;

public class GameActivity extends AppCompatActivity {

    private static final String TAG = "GameActivity";

    // Lets have all the Buttons in an array to save some space
    private Button[][] playerButtons = new Button[5][7];

    // same for TextViews
    private TextView[][] playerTextViews = new TextView[5][7];

    private BasketisticsViewModel basketisticsViewModel;

    // ----- TODO: Only here for testing... ----- //
    private MockEventDB mockEventDB = new MockEventDB();
    private MockPlayerStatsDB mockPlayerStatsDB = new MockPlayerStatsDB();
    private TextView timerTextView;
    private TimerOnClickListener pauseListener;
    boolean timer_running = false;
    // public CountDownTimer timer;
    int quaterCount = 1;
    // ------ Test 'til here... ----- //

    CountDownTimerWithPause timer = new CountDownTimerWithPause(600000, 1000, false) {
        @Override
        public void onTick(long millisUntilFinished) {
            String timeLeft = format("%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
            timerTextView.setText(timeLeft);
        }

        @Override
        public void onFinish() {

            switch (quaterCount) {
                case 1:
                    timerTextView.setText("End of 1st");
                    quaterCount++;
                    break;
                case 2:
                    timerTextView.setText("End of 2nd");
                    quaterCount++;
                    break;
                case 3:
                    timerTextView.setText("End of 3rd");
                    quaterCount++;
                    break;
                case 4:
                    timerTextView.setText("End of 4th");
                    quaterCount = 1;
                    break;

            }
        }

    };


    private void timerHandler()
    {

        timerTextView = findViewById(R.id.currTime);

        timer.create();

        Button timerStart = findViewById(R.id.timer_start);
        timerStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timerTextView.getText().toString().startsWith("E"))
                    timer.create();
                if (timer_running)
                {
                    timer.resume();
                }
                 else if (!timer_running){
                    Log.i(TAG, "start timer.");
                    timer.resume();
                    timer_running = true;
                }
            }
        });

        Button timerPause = findViewById(R.id.timer_end);
        timerPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.pause();
            }
        });


    }

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
                this, new MockDBObserver(mockEventDB, basketisticsViewModel));
        basketisticsViewModel.getRebound().observe(
                this, new MockDBObserver(7, mockEventDB, basketisticsViewModel));
        basketisticsViewModel.getAssist().observe(
                this, new MockDBObserver(8, mockEventDB, basketisticsViewModel));
        basketisticsViewModel.getBlock().observe(
                this, new MockDBObserver(9, mockEventDB, basketisticsViewModel));
        basketisticsViewModel.getTurnover().observe(
                this, new MockDBObserver(10, mockEventDB, basketisticsViewModel));
        basketisticsViewModel.getFoul().observe(
                this, new MockDBObserver(11, mockEventDB, basketisticsViewModel));
        basketisticsViewModel.getSteal().observe(
                this, new MockDBObserver(12, mockEventDB, basketisticsViewModel));

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
        //// Attach PlayerButtonsOnClickListener to buttons
        plusOneButton.setOnClickListener(
                new PlayerButtonsOnClickListener(player, 0, 1, basketisticsViewModel, pointsPopupWindow));
        plusTwoButton.setOnClickListener(
                new PlayerButtonsOnClickListener(player, 0, 2, basketisticsViewModel, pointsPopupWindow));
        plusThreeButton.setOnClickListener(
                new PlayerButtonsOnClickListener(player, 0, 3, basketisticsViewModel, pointsPopupWindow));
        minusOneButton.setOnClickListener(
                new PlayerButtonsOnClickListener(player, 0, -1, basketisticsViewModel, pointsPopupWindow));
        minusTwoButton.setOnClickListener(
                new PlayerButtonsOnClickListener(player, 0, -2, basketisticsViewModel, pointsPopupWindow));
        minusThreeButton.setOnClickListener(
                new PlayerButtonsOnClickListener(player, 0, -3, basketisticsViewModel, pointsPopupWindow));
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

        /*

        Das verursacht einen Fehler.

        TextView scoreTextView = findViewById(R.id.score);
        scoreTextView.setText();

         */

    }

    private void attachButtonsToViewModel() {
        // Iterate over all players
        for (int i = 0; i < 5; i++) {
            // Iterate over events except 0 (points)
            for (int j = 1; j < 7; j++) {
                playerButtons[i][j].setOnClickListener(
                        new PlayerButtonsOnClickListener(i, j, basketisticsViewModel));
            }
        }
    }

    private void attachTextViewsToViewModel() {
        // Iterate over all players
        // starts at 1
        for (int i = 1; i < 6; i++) {
            // Iterate over events
            for (int j = 0; j < 7; j++) {
                // - 1 here due to i starting at 1
                playerTextViews[i - 1][j].setText("" + 0);
            }
            // attach actual observer
            basketisticsViewModel.getPoints().observe(
                    this, new PlayerTextViewObserver(i, 0, mockPlayerStatsDB, basketisticsViewModel, playerTextViews));
            basketisticsViewModel.getRebound().observe(
                    this, new PlayerTextViewObserver(i, 1, mockPlayerStatsDB, basketisticsViewModel, playerTextViews));
            basketisticsViewModel.getAssist().observe(
                    this, new PlayerTextViewObserver(i, 2, mockPlayerStatsDB, basketisticsViewModel, playerTextViews));
            basketisticsViewModel.getSteal().observe(
                    this, new PlayerTextViewObserver(i, 3, mockPlayerStatsDB, basketisticsViewModel, playerTextViews));
            basketisticsViewModel.getBlock().observe(
                    this, new PlayerTextViewObserver(i, 4, mockPlayerStatsDB, basketisticsViewModel, playerTextViews));
            basketisticsViewModel.getTurnover().observe(
                    this, new PlayerTextViewObserver(i, 5, mockPlayerStatsDB, basketisticsViewModel, playerTextViews));
            basketisticsViewModel.getFoul().observe(
                    this, new PlayerTextViewObserver(i, 6, mockPlayerStatsDB, basketisticsViewModel, playerTextViews));


        }
    }

    public void inflatePopup(ImageView bild)
    {
        LayoutInflater layoutInflater = this.getLayoutInflater();
        PopupWindow playerPopupWindow = new PopupWindow(this);
        View playerPopupView = layoutInflater.inflate(R.layout.popup_wechsel, null);
        playerPopupWindow.setContentView(playerPopupView);
        playerPopupWindow.setFocusable(true);
        playerPopupWindow.showAsDropDown(bild);
    }

    public void spielerwechsel()
    {
        final ImageView spieler1 = findViewById(R.id.player_1_imageView);
        spieler1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                inflatePopup(spieler1);

                return false;
            }
        });

        final ImageView spieler2 = findViewById(R.id.player_2_imageView);
        spieler2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                inflatePopup(spieler2);

                return false;
            }
        });

        final ImageView spieler3 = findViewById(R.id.player_3_imageView);
        spieler3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                inflatePopup(spieler3);

                return false;
            }
        });

        final ImageView spieler4 = findViewById(R.id.player_4_imageView);
        spieler4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                inflatePopup(spieler4);

                return false;
            }
        });

        final ImageView spieler5 = findViewById(R.id.player_5_imageView);
        spieler5.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                inflatePopup(spieler5);

                return false;
            }
        });
    }


    //// ---------- Lifecycle Callbacks ------------ ////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        basketisticsViewModel = ViewModelProviders.of(this).get(BasketisticsViewModel.class);

        spielerwechsel();
        timerHandler();
        bindPlayerButtons();
        bindPlayerTextViews();
        attachPointsPopUp();
        attachButtonsToViewModel();
        attachTextViewsToViewModel();
        initMockDB();
    }
}
