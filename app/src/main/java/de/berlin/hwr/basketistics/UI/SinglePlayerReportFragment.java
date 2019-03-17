package de.berlin.hwr.basketistics.UI;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import de.berlin.hwr.basketistics.R;
import de.berlin.hwr.basketistics.ViewModel.PlayerReportViewModel;
import de.berlin.hwr.basketistics.ViewModel.SingleGameReportViewModel;


public class SinglePlayerReportFragment extends Fragment {

    private static final String TAG = "PlayerReportFragment";

    PlayerReportViewModel gameViewModel;

    private String[] parseTextViewItems(String[] list){

        String[] parsedList = new String[3];
        for (int i =0; i<3;i++){
            if (list[i].length()>=12)
                parsedList[i] = (list[i].substring(0,12)+"%");
            else
                parsedList[i]=list[i]+"%";

        }

        return parsedList;
    }





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.stat_single_player, container, false);

        Log.i(TAG, "onCreateView: in Fragment");


        return rootview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        gameViewModel = ViewModelProviders.of(this).get(PlayerReportViewModel.class);


        //-------TextViews--------
        TextView gamesPlayed = view.findViewById(R.id.visu_games_played_valS);
        TextView pointsMade = view.findViewById(R.id.visu_points_valS);
        TextView freeThrows = view.findViewById(R.id.visu_fts_valS);
        TextView fieldGoals = view.findViewById(R.id.visu_fgs_valS);
        TextView fieldGoals3 = view.findViewById(R.id.visu_fgs3_valS);
        TextView rebounds = view.findViewById(R.id.visu_rebounds_valS);
        TextView assists = view.findViewById(R.id.visu_assists_valS);
        TextView blocks = view.findViewById(R.id.visu_blocks_valS);
        TextView steals = view.findViewById(R.id.visu_steals_valSi);
        TextView turnover = view.findViewById(R.id.visu_tov_valSi);
        TextView fouls = view.findViewById(R.id.visu_fouls_valSi);

        Bundle extras = getArguments();
        // int playerId = extras.getInt("playerId");
        int playerId = 1;
        gameViewModel.setPlayerId(playerId);

        PlayerReportViewModel.PlayerReport playerReport = gameViewModel.getReportByPlayerId();
        String[] inputList = new String[3];
        inputList[0] = playerReport.onePoint+ "/ " + playerReport.onePointAttempt +"/ " + ((100/(float)playerReport.onePointAttempt)*(float)playerReport.onePoint);
        inputList[1] = (playerReport.twoPoints+ "/ " + playerReport.twoPointsAttempt +"/ " + ((100/(float)playerReport.twoPointsAttempt)*(float)playerReport.twoPoints));
        inputList[2] = playerReport.threePoints+ "/ " + playerReport.threePointsAttempt +"/ " + ((100/(float)playerReport.threePointsAttempt)*(float)playerReport.threePoints);

        String[] textlist = parseTextViewItems(inputList);

        String pointsMadeText = ((float)playerReport.onePoint + (float)playerReport.twoPoints*2 + (float)playerReport.threePoints*3)/playerReport.gamesPlayed+"";

        pointsMadeText = (pointsMadeText.length()>4) ? pointsMadeText.replaceAll(" ","").substring(0,4) :  pointsMadeText ;

        if(playerReport.gamesPlayed == 0){
            pointsMade.setText("0");
            rebounds.setText("0");
            assists.setText("0");
            steals.setText("0");
            blocks.setText("0");
            turnover.setText("0");
            fouls.setText("0");
        }else {
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

