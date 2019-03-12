package de.berlin.hwr.basketistics.UI;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import de.berlin.hwr.basketistics.ImageSaver;
import de.berlin.hwr.basketistics.Constants;
import de.berlin.hwr.basketistics.Persistency.Entities.PlayerEntity;
import de.berlin.hwr.basketistics.R;
import de.berlin.hwr.basketistics.ViewModel.EventViewModel;
import de.berlin.hwr.basketistics.ViewModel.TeamViewModel;

import static java.lang.String.format;

public class GameActivity extends AppCompatActivity implements TeamAdapter.ClickListener {

    private static final String TAG = "GameActivity";

    private SharedPreferences sharedPreferences;

    // Lets have all the Buttons in an array to save some space
    private Button[][] playerButtons = new Button[5][7];

    // same for TextViews
    private TextView[][] playerTextViews = new TextView[5][7];
    private TextView[] playerDescription = new TextView[5];

    // and player pictures
    private ImageView[] playerImageViews = new ImageView[5];
    PopupWindow playerPopupWindow;
    int clickedPlayerIndex;

    private EventViewModel eventViewModel;
    private TeamViewModel teamViewModel;
    private static int[] currentPlayersIds;
    private static int currentMatchId;

    // Enemy points
    private Button incEnemyPointsButton;
    private Button decEnemyPointsButton;
    private TextView enemyPointsTextView;

    // Timer
    private TextView timerTextView;
    boolean timer_running = false;
    int quarterCount = 1;

    void initEnemyPoints() {
        incEnemyPointsButton = findViewById(R.id.ePoints_inc);
        decEnemyPointsButton = findViewById(R.id.ePoints_dec);
        enemyPointsTextView = findViewById(R.id.score);

        incEnemyPointsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventViewModel.incEnemyPoints();
            }
        });

        decEnemyPointsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventViewModel.decEnemyPoints();
            }
        });

        eventViewModel.getEnemyPoints().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                enemyPointsTextView.setText(eventViewModel.getPoints().getValue() + " : " + eventViewModel.getEnemyPoints().getValue());
            }
        });

        eventViewModel.getPoints().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                enemyPointsTextView.setText(eventViewModel.getPoints().getValue() + " : " + eventViewModel.getEnemyPoints().getValue());
            }
        });

    }

    private void finishCurrentQuater(){
        switch (quarterCount) {
            case 1:
                quater_running=0;
                eventViewModel.endFirstQuarter();
                timerTextView.setText("End of 1st");
                quarterCount++;
                break;
            case 2:
                quater_running=0;
                eventViewModel.endSecondQuarter();
                timerTextView.setText("End of 2nd");
                quarterCount++;
                break;
            case 3:
                quater_running=0;
                eventViewModel.endThirdQuarter();
                timerTextView.setText("End of 3rd");
                quarterCount++;
                break;
            case 4:
                quater_running=0;
                eventViewModel.endFourthQuarter();
                timerTextView.setText("End of 4th");
                quarterCount = 1;
                break;

        }
    }



    private void insertQuaterstart(){
        switch (quarterCount){
            case 1: eventViewModel.startFirstQuarter();
                break;
            case 2:
                eventViewModel.startSecondQuarter();
                break;
            case 3:
                eventViewModel.startThirdQuarter();
                break;
            case 4:
                eventViewModel.startFourthQuarter();
                break;
        }
    }

    CountDownTimerWithPause timer = new CountDownTimerWithPause(
            600000,
            1000,
            false) {
        @Override
        public void onTick(long millisUntilFinished) {
            String timeLeft = format("%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
            timerTextView.setText(timeLeft);
        }

        @Override
        public void onFinish() {
            finishCurrentQuater();
            timer_running = false;
        }
    };
        private int quater_running=0;
    private void timerHandler()
    {

        timerTextView = findViewById(R.id.currTime);


        Button timerStart = findViewById(R.id.timer_start);
        timerStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (timer_running)
                {
                    Log.i(TAG, "timer already running");
                }

                else if ((timer.timeLeft() < 600000)&& quater_running==1){
                    timer.resume();
                    timer_running = true;
                    Log.i(TAG, "timer resumed");
                    eventViewModel.startGame();

                }
                if(timerTextView.getText().toString().startsWith("E") | quater_running == 0) {
                    timer.cancel();
                    timer.create();
                    Log.i(TAG, quarterCount + " quater started");
                    timer.resume();
                    insertQuaterstart();
                    quater_running = 1;
timer_running = true;
                }
            }
        });

        Button timerPause = findViewById(R.id.timer_end);
        timerPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.pause();
                timer_running = false;
                Log.i(TAG, "timer paused");
                eventViewModel.pauseGame();
            }
        });
    }

    public void showPointsPopup(int playerIndex, Button button) {

        PopupWindow pointsPopupWindow;

        Button plusOneButton;
        Button plusTwoButton;
        Button plusThreeButton;
        Button onPointAttemptButton;
        Button twoPointsAttemptButton;
        Button threePointsAttemptButton;

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
        onPointAttemptButton = (Button) pointsPopupView.findViewById(R.id.dec1PointButton);
        twoPointsAttemptButton = (Button) pointsPopupView.findViewById(R.id.dec2PointButton);
        threePointsAttemptButton = (Button) pointsPopupView.findViewById(R.id.dec3PointButton);

        // set OnClickListeners on Buttons to connect them to ViewModel
        //// Attach PlayerButtonsOnClickListener to buttons
        plusOneButton.setOnClickListener(
                new PlayerButtonsOnClickListener(playerIndex, Constants.ONE_POINT, eventViewModel, pointsPopupWindow));
        plusTwoButton.setOnClickListener(
                new PlayerButtonsOnClickListener(playerIndex, Constants.TWO_POINTS, eventViewModel, pointsPopupWindow));
        plusThreeButton.setOnClickListener(
                new PlayerButtonsOnClickListener(playerIndex, Constants.THREE_POINTS, eventViewModel, pointsPopupWindow));
        onPointAttemptButton.setOnClickListener(
                new PlayerButtonsOnClickListener(playerIndex, Constants.ONE_POINT_ATTEMPT, eventViewModel, pointsPopupWindow));
        twoPointsAttemptButton.setOnClickListener(
                new PlayerButtonsOnClickListener(playerIndex, Constants.TWO_POINTS_ATTEMPT, eventViewModel, pointsPopupWindow));
        threePointsAttemptButton.setOnClickListener(
                new PlayerButtonsOnClickListener(playerIndex, Constants.THREE_POINTS_ATTEMPT, eventViewModel, pointsPopupWindow));
    }

    // Attach and enable Points PopUp an points buttons
    private void attachPointsPopUp() {
        for (int i = 0; i < 5; i++) {
            playerButtons[i][0].setOnClickListener(new PopupOnClickListener(i));
        }
    }

    private void attachPlayerDescriptionToViewModel() {
        for (int i = 0; i < 5; i++) {
            final int finalI = i;
            eventViewModel.getPlayerEvents(i).getPlayer() .observe(this, new Observer<PlayerEntity>() {
                @Override
                public void onChanged(@Nullable PlayerEntity playerEntity) {
                    Log.e(TAG, "onChanged() was called.");
                    playerDescription[finalI].setText(
                            playerEntity.getFirstName() + " "
                                    + playerEntity.getLastName() + "\n"
                                    + playerEntity.getNumber());
                }
            });
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

    private void bindPlayerImageViews() {
        playerImageViews[0] = findViewById(R.id.player_1_imageView);
        playerImageViews[1] = findViewById(R.id.player_1_imageView2);
        playerImageViews[2] = findViewById(R.id.player_1_imageView3);
        playerImageViews[3] = findViewById(R.id.player_1_imageView4);
        playerImageViews[4] = findViewById(R.id.player_1_imageView5);
    }

    private void attachImageViewPopUps() {

        for (int i = 0; i < 5; i++) {

            final int finalI = i;

            playerImageViews[i].setOnClickListener(new View.OnClickListener() {

                RecyclerView playerRecyclerView;
                TeamAdapter teamAdapter;

                @Override
                public void onClick(View v) {

                    clickedPlayerIndex = finalI;

                    // Inflate the popup_points.xml View
                    LayoutInflater layoutInflater = GameActivity.this.getLayoutInflater();
                    View playerListView = layoutInflater.inflate(R.layout.player_list_popup, null);

                    // Create the popup Window
                    playerPopupWindow = new PopupWindow(GameActivity.this);
                    playerPopupWindow.setContentView(playerListView);
                    playerPopupWindow.setFocusable(true);
                    // TODO: Calculate from displaysize and pixeldensity!
                    playerPopupWindow.setWidth(1300);
                    playerPopupWindow.showAsDropDown(v);

                    // Set up RecyclerView
                    playerRecyclerView =
                            (RecyclerView) playerPopupWindow.getContentView().findViewById(R.id.playerListRecyclerView);
                    teamAdapter = new TeamAdapter(GameActivity.this, GameActivity.this, playerPopupWindow);
                    teamAdapter.setTeam(teamViewModel.getAllPlayers().getValue());
                    playerRecyclerView.setAdapter(teamAdapter);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(GameActivity.this);
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    playerRecyclerView.setLayoutManager(linearLayoutManager);
                }
            });
        }
    }

    @Override
    public void onItemClicked(PlayerEntity playerEntity) {

        EventViewModel.PlayerEvents[] playerEventsArray = eventViewModel.getCurrentPlayerEvents();
        Boolean isUniq = true;
        for (EventViewModel.PlayerEvents playerEvents : playerEventsArray) {
            if (playerEvents.getPlayer().getValue().getId() != playerEntity.getId()) {
            } else {
                isUniq = false;
            }
        }
        if (isUniq) {
            eventViewModel.insertPlayer(playerEntity.getId(), clickedPlayerIndex);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "Sie koennen einen Spieler nicht mehrfach einsetzen.",
                    Toast.LENGTH_LONG).show();
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
            PlayerEntity playerEntity = eventViewModel.getPlayerByIndex(i).getValue();
            playerDescription[i].setText(
                playerEntity.getFirstName() + " "
                + playerEntity.getLastName() + "\n"
                + playerEntity.getNumber());
        }
    }

    private void attachButtonsToViewModel() {

        Map<Integer, Integer> buttonsEventMap = new HashMap<Integer, Integer>();
        buttonsEventMap.put(0, Constants.POINTS);
        buttonsEventMap.put(1, Constants.REBOUND);
        buttonsEventMap.put(2, Constants.ASSIST);
        buttonsEventMap.put(3, Constants.STEAL);
        buttonsEventMap.put(4, Constants.BLOCK);
        buttonsEventMap.put(5, Constants.TURNOVER);
        buttonsEventMap.put(6, Constants.FOUL);

        // Iterate over all players
        for (int i = 0; i < 5; i++) {
            // Iterate over events except 0 (points)
            for (int j = 1; j < 7; j++) {
                playerButtons[i][j].setOnClickListener(
                        new PlayerButtonsOnClickListener(i, buttonsEventMap.get(j), eventViewModel));
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
                    this, new PlayerViewObserver(i, 0, eventViewModel, playerTextViews));
            eventViewModel.getPlayerEvents(i).getRebound().observe(
                    this, new PlayerViewObserver(i, 1, eventViewModel, playerTextViews));
            eventViewModel.getPlayerEvents(i).getAssist().observe(
                    this, new PlayerViewObserver(i, 2, eventViewModel, playerTextViews));
            eventViewModel.getPlayerEvents(i).getSteal().observe(
                    this, new PlayerViewObserver(i, 3, eventViewModel, playerTextViews));
            eventViewModel.getPlayerEvents(i).getBlock().observe(
                    this, new PlayerViewObserver(i, 4, eventViewModel, playerTextViews));
            eventViewModel.getPlayerEvents(i).getTurnover().observe(
                    this, new PlayerViewObserver(i, 5, eventViewModel, playerTextViews));
            eventViewModel.getPlayerEvents(i).getFoul().observe(
                    this, new PlayerViewObserver(i, 6, eventViewModel, playerTextViews));


        }
    }

    //// ---------- Lifecycle Callbacks ------------ ////

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putIntArray("currentPlayerIds", currentPlayersIds);
        bundle.putInt("currentMatchId", currentMatchId);
        Log.e(TAG, "onSaveInstanceState() was called.");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // getIntent() should always return the most recent
        setIntent(getIntent());
        Log.e(TAG, "onNewIntent() was entered!");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        /*sharedPreferences = getSharedPreferences(FirstRunActivity.PREFERENCES, MODE_PRIVATE);

        // Get ViewModels
        eventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        teamViewModel = ViewModelProviders.of(this).get(TeamViewModel.class);

        // Check, which Activity we are coming from
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (intent.getStringExtra("origin").equals(StartGameActivity.TAG)) {

                currentPlayersIds = (int[]) extras.get(StartGameActivity.STARTERS);
                currentMatchId = (int) extras.get(StartGameActivity.MATCH);
                Log.e(TAG, "" + currentMatchId);

                // TODO: Setting matchId from ViewModel this way is only for testing
                eventViewModel.init(currentPlayersIds, currentMatchId);

            } else if (intent.getExtras().get("origin").equals(TeamActivity.TAG)) {
                // update matchId in ViewModel
                // update currentPlayerIds in ViewModel
                int i = 0;
                for (int id : currentPlayersIds) {
                    eventViewModel.insertPlayer(id, i);
                    i++;
                }
            }
        }

        // Observe matchId
        eventViewModel.getCurrentMatchId().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                currentMatchId = integer;
            }
        });

        // Set starters
        eventViewModel.setStarters(currentPlayersIds);

        bindPlayerButtons();
        bindPlayerTextViews();
        bindPlayerImageViews();
        bindPlayerDescriptionTextViews();
        attachPointsPopUp();
        attachImageViewPopUps();
        attachButtonsToViewModel();
        attachTextViewsToViewModel();
        attachPlayerDescriptionToViewModel();
        attachPlayerImageViewToViewModel();
        initEnemyPoints();
        // initImages();
        timerHandler();

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Moechten Sie Basketistics wirklich beenden?")
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GameActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("Nein", null)
                .show();


    }

    private void attachPlayerImageViewToViewModel() {

        for (int i = 0; i < 5; i++) {

            final int finalI = i;
            eventViewModel.getPlayerEvents(i).getPlayer().observe(this, new Observer<PlayerEntity>() {
                @Override
                public void onChanged(@Nullable PlayerEntity playerEntity) {

                    int playerId = eventViewModel.getPlayerEvents(finalI).getPlayer().getValue().getId();
                    String fileName = sharedPreferences.getString("PLAYER" + playerId, "");
                    Log.e(TAG, "PLAYER" + playerId);

                    ImageSaver imageSaver = new ImageSaver(GameActivity.this.getApplicationContext());
                    Bitmap bitmap = imageSaver.setExternal(false)
                            .setFileName(fileName)
                            .setDirectoryName("images")
                            .load();
                    playerImageViews[finalI].setImageBitmap(bitmap);
                }
            });
        }
        */
    }
}
