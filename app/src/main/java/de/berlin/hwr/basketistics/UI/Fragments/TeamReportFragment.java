package de.berlin.hwr.basketistics.UI.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.berlin.hwr.basketistics.R;
import de.berlin.hwr.basketistics.UI.MainActivity;
import de.berlin.hwr.basketistics.ViewModel.TeamReportViewModel;

public class TeamReportFragment extends Fragment {

    private static final String TAG = "TeamReportFragment";

    private ImageView teamImageView;

    TeamReportViewModel teamReportViewModel;
    String teamNameString;
    
    

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
    
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.team_report_layout, container, false);

        teamNameString = ((MainActivity)getActivity()).getTeamName();
        return rootview;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        teamReportViewModel = ViewModelProviders.of(this).get(TeamReportViewModel.class);
        // Set team image
        teamImageView = getView().findViewById(R.id.teamReportLayoutImage);
        Glide.with(getActivity())
                .load(((MainActivity)getActivity()).getImageUri())
                .centerCrop()
                .placeholder(R.drawable.avatar_icon)
                .into(teamImageView);


        //-------TextViews--------
        TextView gamesPlayed = view.findViewById(R.id.visu_games_valT);
        TextView pointsMade = view.findViewById(R.id.visu_points_valT);
        TextView freeThrows = view.findViewById(R.id.visu_fts_valT);
        TextView fieldGoals = view.findViewById(R.id.visu_fgs_valT);
        TextView fieldGoals3 = view.findViewById(R.id.visu_fgs3_valT);
        TextView rebounds = view.findViewById(R.id.visu_rebounds_valT);
        TextView assists = view.findViewById(R.id.visu_assists_valT);
        TextView blocks = view.findViewById(R.id.visu_blocks_valT);
        TextView steals = view.findViewById(R.id.visu_steals_valT);
        TextView turnover = view.findViewById(R.id.visu_tov_valT);
        TextView fouls = view.findViewById(R.id.visu_fouls_valT);
        TextView teamName = view.findViewById(R.id.teamREportTEamName);
        teamName.setText(((MainActivity)getActivity()).getTeamName());


        TeamReportViewModel.TeamReport teamReport = teamReportViewModel.getReport();
        String[] inputList = new String[3];
        inputList[0] = teamReport.onePoint+ "/ " + teamReport.onePointAttempt +"/ " + ((100/(float)teamReport.onePointAttempt)*(float)teamReport.onePoint);
        inputList[1] = (teamReport.twoPoints+ "/ " + teamReport.twoPointsAttempt +"/ " + ((100/(float)teamReport.twoPointsAttempt)*(float)teamReport.twoPoints));
        inputList[2] = teamReport.threePoints+ "/ " + teamReport.threePointsAttempt +"/ " + ((100/(float)teamReport.threePointsAttempt)*(float)teamReport.threePoints);

        String[] textlist = parseTextViewItems(inputList);

        String pointsMadeText = ((float)teamReport.onePoint + (float)teamReport.twoPoints*2 + (float)teamReport.threePoints*3)/teamReport.gamesPlayed+"";

        pointsMadeText = (pointsMadeText.length()>4) ? pointsMadeText.replaceAll(" ","").substring(0,4) :  pointsMadeText ;

        if(teamReport.gamesPlayed == 0){
            pointsMade.setText("0");
            rebounds.setText("0");
            assists.setText("0");
            steals.setText("0");
            blocks.setText("0");
            turnover.setText("0");
            fouls.setText("0");
        }else {
            gamesPlayed.setText(teamReport.gamesPlayed + "");
            pointsMade.setText(pointsMadeText);
            if (teamReport.onePointAttempt == 0)
                freeThrows.setText("0/ 0/ 0%");
            else
                freeThrows.setText(textlist[0]);
            if (teamReport.twoPointsAttempt == 0)
                fieldGoals.setText("0/ 0/ 0%");
            else
                fieldGoals.setText(textlist[1]);
            if (teamReport.threePointsAttempt == 0)
                fieldGoals3.setText("0/ 0/ 0%");
            else
                fieldGoals3.setText(textlist[2]);

            String rebText = (float) teamReport.rebound / teamReport.gamesPlayed + "";
            rebText = rebText.length() > 5 ? rebText.substring(0, 4) : rebText;
            rebounds.setText(rebText);

            String astText = ((float) teamReport.assist / teamReport.gamesPlayed + "");
            astText = astText.length() > 5 ? astText.substring(0, 4) : astText;
            assists.setText(astText);

            String blkText = (float) teamReport.block / teamReport.gamesPlayed + "";
            blkText = blkText.length() > 5 ? blkText.substring(0, 4) : blkText;
            blocks.setText(blkText);

            String stlText = (float) teamReport.steal / teamReport.gamesPlayed + "";
            stlText = stlText.length() > 5 ? stlText.substring(0, 4) : stlText;
            steals.setText(stlText);

            String tovText = (float) teamReport.turnover / teamReport.gamesPlayed + "";
            tovText = tovText.length() > 5 ? tovText.substring(0, 4) : tovText;
            turnover.setText(tovText);

            String foulText = (float) teamReport.foul / teamReport.gamesPlayed + "";
            foulText = foulText.length() > 5 ? foulText.substring(0, 4) : foulText;
            fouls.setText(foulText);
        }
    }
}
