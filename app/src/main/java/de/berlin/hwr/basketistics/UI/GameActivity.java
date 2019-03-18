package de.berlin.hwr.basketistics.UI;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import de.berlin.hwr.basketistics.BroadcastService;
import de.berlin.hwr.basketistics.Constants;
import de.berlin.hwr.basketistics.Persistency.Entities.PlayerEntity;
import de.berlin.hwr.basketistics.R;
import de.berlin.hwr.basketistics.UI.Fragments.Adapter.TeamAdapter;
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
    private int quarterRunning = 0;
    private TextView timerTextView;
    boolean timerRunning = false;
    int quarterCount = 1;
    private BroadcastReceiver br;
    private long millisLeft;
    private long quarterMillis = 5000;
    Button timerStart;
    Button timerPause;
    Button endGame;

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(br, new IntentFilter(BroadcastService.COUNTDOWN_BR));
        Log.i(TAG, "Registered broacast receiver");
    }

    void initEnemyPoints() {
        incEnemyPointsButton = findViewById(R.id.newGameIncEnemyPoints);
        decEnemyPointsButton = findViewById(R.id.newGameDecEnemyPoints);
        enemyPointsTextView = findViewById(R.id.newGamePointsTextView);

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

    private void finishCurrentQuarter(){
        switch (quarterCount) {
            case 1:
                timerRunning = false;
                quarterRunning =0;
                eventViewModel.endFirstQuarter();
                timerTextView.setText("End of 1st");
                quarterCount++;
                break;
            case 2:
                timerRunning = false;
                quarterRunning =0;
                eventViewModel.endSecondQuarter();
                timerTextView.setText("End of 2nd");
                quarterCount++;
                break;
            case 3:
                timerRunning = false;
                quarterRunning =0;
                eventViewModel.endThirdQuarter();
                timerTextView.setText("End of 3rd");
                quarterCount++;
                break;
            case 4:
                timerRunning = false;
                quarterRunning =0;
                eventViewModel.endFourthQuarter();
                timerTextView.setText("End of 4th");
                quarterCount = 1;

                // Stop listening to timer service (Does that actually kill it??)
                unregisterReceiver(br);

                timerPause.setVisibility(View.GONE);
                timerStart.setVisibility(View.GONE);

                endGame.setVisibility(View.VISIBLE);
                endGame.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // End event
                        eventViewModel.gameOver();

                        Intent mainIntent = new Intent(GameActivity.this, MainActivity.class);
                        mainIntent.putExtra("lastGame", currentMatchId);
                        startActivity(mainIntent);
                        finishAffinity();
                    }
                });
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


    private void timerHandler() {

        timerTextView = findViewById(R.id.newGameTimerTextView);

        timerStart = findViewById(R.id.newGameTimeStart);
        timerStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: ");

                if (timerRunning) { Log.i(TAG, "timer already running"); }
                else if ((millisLeft < quarterMillis) && quarterRunning == 1){
                    startService(new Intent(GameActivity.this, BroadcastService.class)
                            .putExtra("millisLeft", millisLeft));
                    Log.i(TAG, "Started service");
                    timerRunning = true;
                    Log.i(TAG, "timer resumed");
                    eventViewModel.startGame();
                }
                if(timerTextView.getText().toString().startsWith("E") | quarterRunning == 0) {
                    Log.i(TAG, quarterCount + " quater started");
                    millisLeft = quarterMillis;
                    startService(new Intent(GameActivity.this, BroadcastService.class)
                            .putExtra("millisLeft", millisLeft));
                    Log.i(TAG, "Started service");
                    insertQuaterstart();
                    quarterRunning = 1;
                    timerRunning = true;
                }
            }
        });

        timerPause = findViewById(R.id.newGameTimeStop);
        timerPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(GameActivity.this, BroadcastService.class));
                timerRunning = false;
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
        playerImageViews[0] = findViewById(R.id.newGamePlayer1ImageView);
        playerImageViews[1] = findViewById(R.id.newGamePlayer2ImageView);
        playerImageViews[2] = findViewById(R.id.newGamePlayer3ImageView);
        playerImageViews[3] = findViewById(R.id.newGamePlayer4ImageView);
        playerImageViews[4] = findViewById(R.id.newGamePlayer4ImageView);
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
                    playerPopupWindow.setClippingEnabled(false);
                    playerPopupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
                    playerPopupWindow.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
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
            int lastPlayerId = eventViewModel.insertPlayer(playerEntity.getId(), clickedPlayerIndex);
            eventViewModel.playerIn(playerEntity.getId());
            eventViewModel.playerOut(lastPlayerId);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "Sie koennen einen Spieler nicht mehrfach einsetzen.",
                    Toast.LENGTH_LONG).show();
        }
    }

    // find button views and bind them to Button objects
    private void bindPlayerButtons() {

        playerButtons[0][0] = findViewById(R.id.newGamePlayerShotButton1);
        playerButtons[1][0] = findViewById(R.id.newGamePlayerShotButton2);
        playerButtons[2][0] = findViewById(R.id.newGamePlayerShotButton3);
        playerButtons[3][0] = findViewById(R.id.newGamePlayerShotButton4);
        playerButtons[4][0] = findViewById(R.id.newGamePlayerShotButton5);

        playerButtons[0][1] = findViewById(R.id.newGameReboundButton1);
        playerButtons[1][1] = findViewById(R.id.newGameReboundButton2);
        playerButtons[2][1] = findViewById(R.id.newGameReboundButton3);
        playerButtons[3][1] = findViewById(R.id.newGameReboundButton4);
        playerButtons[4][1] = findViewById(R.id.newGameReboundButton5);

        playerButtons[0][2] = findViewById(R.id.newGameAssistButton1);
        playerButtons[1][2] = findViewById(R.id.newGameAssistButton2);
        playerButtons[2][2] = findViewById(R.id.newGameAssistButton3);
        playerButtons[3][2] = findViewById(R.id.newGameAssistButton4);
        playerButtons[4][2] = findViewById(R.id.newGameAssistButton5);

        playerButtons[0][3] = findViewById(R.id.newGameStealButton1);
        playerButtons[1][3] = findViewById(R.id.newGameStealButton2);
        playerButtons[2][3] = findViewById(R.id.newGameStealButton3);
        playerButtons[3][3] = findViewById(R.id.newGameStealButton4);
        playerButtons[4][3] = findViewById(R.id.newGameStealButton5);

        playerButtons[0][4] = findViewById(R.id.newGameBlockButton1);
        playerButtons[1][4] = findViewById(R.id.newGameBlockButton2);
        playerButtons[2][4] = findViewById(R.id.newGameBlockButton3);
        playerButtons[3][4] = findViewById(R.id.newGameBlockButton4);
        playerButtons[4][4] = findViewById(R.id.newGameBlockButton5);

        playerButtons[1][5] = findViewById(R.id.newGameTurnoverButton1);
        playerButtons[0][5] = findViewById(R.id.newGameTurnoverButton2);
        playerButtons[2][5] = findViewById(R.id.newGameTurnoverButton3);
        playerButtons[3][5] = findViewById(R.id.newGameTurnoverButton4);
        playerButtons[4][5] = findViewById(R.id.newGameTurnoverButton5);

        playerButtons[0][6] = findViewById(R.id.newGameFoulButton1);
        playerButtons[1][6] = findViewById(R.id.newGameFoulButton2);
        playerButtons[2][6] = findViewById(R.id.newGameFoulButton3);
        playerButtons[3][6] = findViewById(R.id.newGameFoulButton4);
        playerButtons[4][6] = findViewById(R.id.newGameFoulButton5);
    }

    private void bindPlayerTextViews() {

        playerTextViews[0][0] = findViewById(R.id.newGamePlayerShot1);
        playerTextViews[1][0] = findViewById(R.id.newGamePlayerShot2);
        playerTextViews[2][0] = findViewById(R.id.newGamePlayerShot3);
        playerTextViews[3][0] = findViewById(R.id.newGamePlayerShot4);
        playerTextViews[4][0] = findViewById(R.id.newGamePlayerShot5);

        playerTextViews[0][1] = findViewById(R.id.newGameReboundTextView1);
        playerTextViews[1][1] = findViewById(R.id.newGameReboundTextView2);
        playerTextViews[2][1] = findViewById(R.id.newGameReboundTextView3);
        playerTextViews[3][1] = findViewById(R.id.newGameReboundTextView4);
        playerTextViews[4][1] = findViewById(R.id.newGameReboundTextView5);

        playerTextViews[0][2] = findViewById(R.id.newGameAssistTextView1);
        playerTextViews[1][2] = findViewById(R.id.newGameAssistTextView2);
        playerTextViews[2][2] = findViewById(R.id.newGameAssistTextView3);
        playerTextViews[3][2] = findViewById(R.id.newGameAssistTextView4);
        playerTextViews[4][2] = findViewById(R.id.newGameAssistTextView5);

        playerTextViews[0][3] = findViewById(R.id.newGameStealTextView1);
        playerTextViews[1][3] = findViewById(R.id.newGameStealTextView2);
        playerTextViews[2][3] = findViewById(R.id.newGameStealTextView3);
        playerTextViews[3][3] = findViewById(R.id.newGameStealTextView4);
        playerTextViews[4][3] = findViewById(R.id.newGameStealTextView5);

        playerTextViews[0][4] = findViewById(R.id.newGameBlockTextView1);
        playerTextViews[1][4] = findViewById(R.id.newGameBlockTextView2);
        playerTextViews[2][4] = findViewById(R.id.newGameBlockTextView3);
        playerTextViews[3][4] = findViewById(R.id.newGameBlockTextView4);
        playerTextViews[4][4] = findViewById(R.id.newGameBlockTextView5);

        playerTextViews[0][5] = findViewById(R.id.newGameTurnoverTextView1);
        playerTextViews[1][5] = findViewById(R.id.newGameTurnoverTextView2);
        playerTextViews[2][5] = findViewById(R.id.newGameTurnoverTextView3);
        playerTextViews[3][5] = findViewById(R.id.newGameTurnoverTextView4);
        playerTextViews[4][5] = findViewById(R.id.newGameTurnoverTextView5);

        playerTextViews[0][6] = findViewById(R.id.newGameFoulTextView1);
        playerTextViews[1][6] = findViewById(R.id.newGameFoulTextView2);
        playerTextViews[2][6] = findViewById(R.id.newGameFoulTextView3);
        playerTextViews[3][6] = findViewById(R.id.newGameFoulTextView4);
        playerTextViews[4][6] = findViewById(R.id.newGameFoulTextView5);

    }

    private void bindPlayerDescriptionTextViews() {

        playerDescription[0] = findViewById(R.id.newGamePlayerNameTextView1);
        playerDescription[1] = findViewById(R.id.newGamePlayerNameTextView2);
        playerDescription[2] = findViewById(R.id.newGamePlayerNameTextView3);
        playerDescription[3] = findViewById(R.id.newGamePlayerNameTextView4);
        playerDescription[4] = findViewById(R.id.newGamePlayerNameTextView5);

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
        setContentView(R.layout.new_activity_game);

        sharedPreferences = getSharedPreferences(FirstRunActivity.PREFERENCES, MODE_PRIVATE);

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
        timerHandler();

        endGame = findViewById(R.id.endGameButton);

        timerTextView = findViewById(R.id.newGameTimerTextView);

        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                millisLeft  = (long) intent.getExtras().get("countdown");
                Log.e(TAG, "onReceive: millisLeft = " + millisLeft);

                if (millisLeft == 0) {
                    finishCurrentQuarter();
                    millisLeft = quarterMillis;
                }
                else {
                    String timeLeft = format("%02d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(millisLeft) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisLeft)),
                            TimeUnit.MILLISECONDS.toSeconds(millisLeft) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisLeft)));
                    timerTextView.setText(timeLeft);
                }
            }
        };
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

                    String fileName = playerEntity.getImageFilename();

                    File directory = GameActivity.this.getDir("images", Context.MODE_PRIVATE);
                    File image = new File(directory, playerEntity.getImageFilename());
                    Uri imageUri = Uri.fromFile(image);

                    Glide.with(GameActivity.this)
                            .load(imageUri)
                            .centerCrop()
                            .placeholder(R.drawable.avatar_icon)
                            .into(playerImageViews[finalI]);

                }
            });
        }
    }
}
