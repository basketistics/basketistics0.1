package de.berlin.hwr.basketistics.UI;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.berlin.hwr.basketistics.R;
import de.berlin.hwr.basketistics.ViewModel.GameReportViewModel;


public class GameReportFragment extends Fragment {

    private static final String TAG = "gameReportFragment";

    GameReportViewModel gameReportViewModel;

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
        View rootview = inflater.inflate(R.layout.game_report_layout, container, false);

        Log.i(TAG, "onCreateView: in Fragment");


        return rootview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        gameReportViewModel = ViewModelProviders.of(this).get(GameReportViewModel.class);


        //-------TextViews--------

        TextView pointsMade = view.findViewById(R.id.visu_points_valG);
        TextView freeThrows = view.findViewById(R.id.visu_fts_valG);
        TextView fieldGoals = view.findViewById(R.id.visu_fgs_valG);
        TextView fieldGoals3 = view.findViewById(R.id.visu_fgs3_valG);
        TextView rebounds = view.findViewById(R.id.visu_rebounds_valG);
        TextView assists = view.findViewById(R.id.visu_assists_valG);
        TextView blocks = view.findViewById(R.id.visu_blocks_valG);
        TextView steals = view.findViewById(R.id.visu_steals_valG);
        TextView turnover = view.findViewById(R.id.visu_tov_valG);
        TextView fouls = view.findViewById(R.id.visu_fouls_valG);

        Bundle extras = getArguments();
        // int playerId = extras.getInt("playerId");
        Bundle data;

        int matchId = 0;
        
        GameReportViewModel.GameReport gameReport = gameReportViewModel.getGameReport(matchId);
        String[] inputList = new String[3];
        inputList[0] = gameReport.onePoint+ "/ " + gameReport.onePointAttempt +"/ " + ((100/(float)gameReport.onePointAttempt)*(float)gameReport.onePoint);
        inputList[1] = (gameReport.twoPoints+ "/ " + gameReport.twoPointsAttempt +"/ " + ((100/(float)gameReport.twoPointsAttempt)*(float)gameReport.twoPoints));
        inputList[2] = gameReport.threePoints+ "/ " + gameReport.threePointsAttempt +"/ " + ((100/(float)gameReport.threePointsAttempt)*(float)gameReport.threePoints);

        String[] textlist = parseTextViewItems(inputList);

        String pointsMadeText = ((float)gameReport.onePoint + (float)gameReport.twoPoints*2 + (float)gameReport.threePoints*3)+"";

        pointsMadeText = (pointsMadeText.length()>4) ? pointsMadeText.replaceAll(" ","").substring(0,4) :  pointsMadeText ;


            pointsMade.setText(pointsMadeText);
            if (gameReport.onePointAttempt == 0)
                freeThrows.setText("0/ 0/ 0%");
            else
                freeThrows.setText(textlist[0]);
            if (gameReport.twoPointsAttempt == 0)
                fieldGoals.setText("0/ 0/ 0%");
            else
                fieldGoals.setText(textlist[1]);
            if (gameReport.threePointsAttempt == 0)
                fieldGoals3.setText("0/ 0/ 0%");
            else
                fieldGoals3.setText(textlist[2]);

            String rebText = (float) gameReport.rebound + "";
            rebText = rebText.length() > 5 ? rebText.substring(0, 4) : rebText;
            rebounds.setText(rebText);

            String astText = ((float) gameReport.assist + "");
            astText = astText.length() > 5 ? astText.substring(0, 4) : astText;
            assists.setText(astText);

            String blkText = (float) gameReport.block + "";
            blkText = blkText.length() > 5 ? blkText.substring(0, 4) : blkText;
            blocks.setText(blkText);

            String stlText = (float) gameReport.steal + "";
            stlText = stlText.length() > 5 ? stlText.substring(0, 4) : stlText;
            steals.setText(stlText);

            String tovText = (float) gameReport.turnover + "";
            tovText = tovText.length() > 5 ? tovText.substring(0, 4) : tovText;
            turnover.setText(tovText);

            String foulText = (float) gameReport.foul + "";
            foulText = foulText.length() > 5 ? foulText.substring(0, 4) : foulText;
            fouls.setText(foulText);
        }

    }


