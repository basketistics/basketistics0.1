package de.berlin.hwr.basketistics.UI.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import de.berlin.hwr.basketistics.Persistency.Entities.PlayerEntity;
import de.berlin.hwr.basketistics.R;
import de.berlin.hwr.basketistics.UI.Fragments.Adapter.TeamAdapter;
import de.berlin.hwr.basketistics.UI.GameActivity;
import de.berlin.hwr.basketistics.UI.MainActivity;
import de.berlin.hwr.basketistics.UI.OnPlayerClickedListener;
import de.berlin.hwr.basketistics.UI.StartGameActivity;
import de.berlin.hwr.basketistics.ViewModel.PlayerReportViewModel;
import de.berlin.hwr.basketistics.ViewModel.TeamViewModel;


public class SinglePlayerReportFragment extends Fragment implements OnPlayerClickedListener {

    private static final String TAG = "PlayerReportFragment";

    PlayerReportViewModel gameViewModel;
    private TeamViewModel teamViewModel;

    private CardView cardView;
    private TextView playerNameTextView;
    private TextView playerNumberTextView;
    private TextView playerDescriptionTextView;
    private ImageView playerImageView;
    TextView gamesPlayed;
    TextView pointsMade;
    TextView freeThrows;
    TextView fieldGoals;
    TextView fieldGoals3;
    TextView rebounds;
    TextView assists;
    TextView blocks;
    TextView steals;
    TextView turnover;
    TextView fouls;


    private TeamAdapter teamAdapter;
    private PopupWindow playerPopupWindow;

    private String[] parseTextViewItems(String[] list) {

        String[] parsedList = new String[3];
        for (int i = 0; i < 3; i++) {
            if (list[i].length() >= 12)
                parsedList[i] = (list[i].substring(0, 12) + "%");
            else
                parsedList[i] = list[i] + "%";

        }

        return parsedList;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.stat_single_player, container, false);

        Log.i(TAG, "onCreateView: in Fragment");

        teamViewModel = ViewModelProviders.of(this).get(TeamViewModel.class);

        return rootview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        cardView = view.findViewById(R.id.playerReportCard);
        playerNameTextView = view.findViewById(R.id.playerReportName);
        playerNumberTextView = view.findViewById(R.id.playerReportNumber);
        playerDescriptionTextView = view.findViewById(R.id.playerReportDescription);
        playerImageView = view.findViewById(R.id.playerReportImage);


        gamesPlayed = view.findViewById(R.id.visu_games_played_valS);
        pointsMade = view.findViewById(R.id.visu_points_valS);
        freeThrows = view.findViewById(R.id.visu_fts_valS);
        fieldGoals = view.findViewById(R.id.visu_fgs_valS);
        fieldGoals3 = view.findViewById(R.id.visu_fgs3_valS);
        rebounds = view.findViewById(R.id.visu_rebounds_valS);
        assists = view.findViewById(R.id.visu_assists_valS);
        blocks = view.findViewById(R.id.visu_blocks_valS);
        steals = view.findViewById(R.id.visu_steals_valSi);
        turnover = view.findViewById(R.id.visu_tov_valSi);
        fouls = view.findViewById(R.id.visu_fouls_valSi);


        playerNameTextView.setText("Bitte Spieler waehlen.");

        teamAdapter = new TeamAdapter(getActivity(), this);


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Inflate the popup_points.xml View
                LayoutInflater layoutInflater = getLayoutInflater();
                View playerListView = layoutInflater.inflate(R.layout.player_list_popup, null);

                // Create the popup Window
                playerPopupWindow = new PopupWindow(getActivity());
                playerPopupWindow.setContentView(playerListView);
                playerPopupWindow.setFocusable(true);
                // TODO: Calculate from displaysize and pixeldensity!
                playerPopupWindow.setClippingEnabled(false);
                playerPopupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
                playerPopupWindow.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
                playerPopupWindow.showAsDropDown(v);

                // Set up RecyclerView
                RecyclerView playerRecyclerView =
                        (RecyclerView) playerPopupWindow.getContentView().findViewById(R.id.playerListRecyclerView);
                teamAdapter.setTeam(teamViewModel.getAllPlayers().getValue());
                playerRecyclerView.setAdapter(teamAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                playerRecyclerView.setLayoutManager(linearLayoutManager);
            }
        });


    }


    @Override
    public void onPlayerClicked(int playerId) {
        PlayerEntity playerEntity = teamViewModel.getAllPlayers().getValue().get(playerId - 1);
        playerNameTextView.setText(playerEntity.getFirstName() + playerEntity.getLastName());
        playerNumberTextView.setText(playerEntity.getNumber() + "");
        playerDescriptionTextView.setText(playerEntity.getDescription());
        Glide.with(this)
                .load(((MainActivity) getActivity()).getImageUri())
                .centerCrop()
                .placeholder(R.drawable.avatar_icon)
                .into(playerImageView);

        // TODO: report


        gameViewModel = ViewModelProviders.of(this).get(PlayerReportViewModel.class);
        playerPopupWindow.dismiss();

        //-------TextViews--------


        Bundle extras = getArguments();
        // int playerId = extras.getInt("playerId");

        gameViewModel.setPlayerId(playerId);

        PlayerReportViewModel.PlayerReport playerReport = gameViewModel.getReportByPlayerId();
        String[] inputList = new String[3];
        inputList[0] = playerReport.onePoint + "/ " + playerReport.onePointAttempt + "/ " + ((100 / (float) playerReport.onePointAttempt) * (float) playerReport.onePoint);
        inputList[1] = (playerReport.twoPoints + "/ " + playerReport.twoPointsAttempt + "/ " + ((100 / (float) playerReport.twoPointsAttempt) * (float) playerReport.twoPoints));
        inputList[2] = playerReport.threePoints + "/ " + playerReport.threePointsAttempt + "/ " + ((100 / (float) playerReport.threePointsAttempt) * (float) playerReport.threePoints);

        String[] textlist = parseTextViewItems(inputList);

        String pointsMadeText = ((float) playerReport.onePoint + (float) playerReport.twoPoints * 2 + (float) playerReport.threePoints * 3) / playerReport.gamesPlayed + "";

        pointsMadeText = (pointsMadeText.length() > 4) ? pointsMadeText.replaceAll(" ", "").substring(0, 4) : pointsMadeText;

        if (playerReport.gamesPlayed == 0) {
            pointsMade.setText("0");
            rebounds.setText("0");
            assists.setText("0");
            steals.setText("0");
            blocks.setText("0");
            turnover.setText("0");
            fouls.setText("0");
        } else {
            gamesPlayed.setText(playerReport.gamesPlayed + "");
            pointsMade.setText(pointsMadeText);
            if (playerReport.onePointAttempt == 0)
                freeThrows.setText("0/ 0/ 0%");
            else
                freeThrows.setText(textlist[0]);
            if (playerReport.twoPointsAttempt == 0)
                fieldGoals.setText("0/ 0/ 0%");
            else
                fieldGoals.setText(textlist[1]);
            if (playerReport.threePointsAttempt == 0)
                fieldGoals3.setText("0/ 0/ 0%");
            else
                fieldGoals3.setText(textlist[2]);

            String rebText = (float) playerReport.rebound / playerReport.gamesPlayed + "";
            rebText = rebText.length() > 5 ? rebText.substring(0, 4) : rebText;
            rebounds.setText(rebText);

            String astText = ((float) playerReport.assist / playerReport.gamesPlayed + "");
            astText = astText.length() > 5 ? astText.substring(0, 4) : astText;
            assists.setText(astText);

            String blkText = (float) playerReport.block / playerReport.gamesPlayed + "";
            blkText = blkText.length() > 5 ? blkText.substring(0, 4) : blkText;
            blocks.setText(blkText);

            String stlText = (float) playerReport.steal / playerReport.gamesPlayed + "";
            stlText = stlText.length() > 5 ? stlText.substring(0, 4) : stlText;
            steals.setText(stlText);

            String tovText = (float) playerReport.turnover / playerReport.gamesPlayed + "";
            tovText = tovText.length() > 5 ? tovText.substring(0, 4) : tovText;
            turnover.setText(tovText);

            String foulText = (float) playerReport.foul / playerReport.gamesPlayed + "";
            foulText = foulText.length() > 5 ? foulText.substring(0, 4) : foulText;
            fouls.setText(foulText);
        }
    }
}

