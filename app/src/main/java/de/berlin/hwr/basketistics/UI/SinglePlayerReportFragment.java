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

import de.berlin.hwr.basketistics.R;
import de.berlin.hwr.basketistics.ViewModel.PlayerReportViewModel;
import de.berlin.hwr.basketistics.ViewModel.SingleGameReportViewModel;



public class SinglePlayerReportFragment extends Fragment {


    PlayerReportViewModel gameViewModel;

    private static final String TAG = "PlayerReportFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View rootview = inflater.inflate(R.layout.report_activity, container, false);

        Log.i(TAG, "onCreateView: in Fragment");
        LinearLayout fragmentHolder = getActivity().findViewById(R.id.fragment_holder);
        if (fragmentHolder != null)
            fragmentHolder.setVisibility(View.VISIBLE);
        //-------TextViews--------
        TextView gamesPlayed = rootview.findViewById(R.id.visu_games_played_valS);
        TextView pointsMade = rootview.findViewById(R.id.visu_points_valS);
        TextView freeThrows = rootview.findViewById(R.id.visu_fts_valS);
        TextView fieldGoals = rootview.findViewById(R.id.visu_fgs_valS);
        TextView fieldGoals3 = rootview.findViewById(R.id.visu_fgs3_valS);
        TextView rebounds = rootview.findViewById(R.id.visu_rebounds_valS);
        TextView assists = rootview.findViewById(R.id.visu_assists_valS);
        TextView blocks = rootview.findViewById(R.id.visu_blocks_valS);
        TextView steals = rootview.findViewById(R.id.visu_steals_valS);
        TextView turnover = rootview.findViewById(R.id.visu_tov_valS);
        TextView fouls = rootview.findViewById(R.id.visu_fouls_valS);

        gameViewModel = ViewModelProviders.of(this).get(PlayerReportViewModel.class);


        Bundle extras = getArguments();
        int playerId = extras.getInt("playerId");
        gameViewModel.setPlayerId(playerId);

        PlayerReportViewModel.PlayerReport playerReport = gameViewModel.getReportByPlayerId();

        gamesPlayed.setText(playerReport.gamesPlayed+"");
        pointsMade.setText(((float)playerReport.onePoint + (float)playerReport.twoPoints*2 + (float)playerReport.threePoints*3)/playerReport.gamesPlayed+"");
        if(playerReport.onePointAttempt==0)
            freeThrows.setText("0/ 0/ 0%");
        else
            freeThrows.setText(playerReport.onePoint+ "/ " + playerReport.onePointAttempt +"/ " + ((100/(float)playerReport.onePointAttempt)*(float)playerReport.onePoint+"%"));
        if(playerReport.twoPointsAttempt==0)
            fieldGoals.setText("0/ 0/ 0%");
        else
            fieldGoals.setText(playerReport.twoPoints+ "/ " + playerReport.twoPointsAttempt +"/ " + ((100/(float)playerReport.twoPointsAttempt)*(float)playerReport.twoPoints+"%"));
        if(playerReport.twoPointsAttempt==0)
            fieldGoals3.setText("0/ 0/ 0%");
        else
            fieldGoals3.setText(playerReport.threePoints+ "/ " + playerReport.threePointsAttempt +"/ " + ((100/(float)playerReport.threePointsAttempt)*(float)playerReport.threePoints+"%"));
        rebounds.setText((float)playerReport.rebound/playerReport.gamesPlayed+"");
        assists.setText((float)playerReport.assist/playerReport.gamesPlayed+"");
        blocks.setText((float)playerReport.block/playerReport.gamesPlayed+"");
        steals.setText((float)playerReport.steal/playerReport.gamesPlayed+"");
        turnover.setText((float)playerReport.turnover/playerReport.gamesPlayed+"");
        fouls.setText((float)playerReport.foul/playerReport.gamesPlayed+"");
         
        return rootview;
    }
}
