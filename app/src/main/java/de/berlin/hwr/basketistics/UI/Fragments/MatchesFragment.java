package de.berlin.hwr.basketistics.UI.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.List;

import de.berlin.hwr.basketistics.Persistency.Entities.MatchEntity;
import de.berlin.hwr.basketistics.R;
import de.berlin.hwr.basketistics.UI.AddMatchActivity;
import de.berlin.hwr.basketistics.UI.MainActivity;
import de.berlin.hwr.basketistics.UI.Fragments.Adapter.MatchesAdapter;
import de.berlin.hwr.basketistics.UI.OnMatchReportClickedListener;
import de.berlin.hwr.basketistics.UI.StartGameActivity;
import de.berlin.hwr.basketistics.ViewModel.GameReportViewModel;
import de.berlin.hwr.basketistics.ViewModel.MatchesViewModel;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MatchesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MatchesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MatchesFragment extends Fragment implements OnMatchReportClickedListener {

    private static final String TAG = "MatchesFragment";

    private static final int ADD_MATCH_ACTIVITY_REQUEST_CODE = 10;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private RecyclerView matchesRecyclerView;
    private MatchesAdapter matchesAdapter;
    private FloatingActionButton addMatchButton;

    private MatchesViewModel matchesViewModel;
    private GameReportViewModel gameReportViewModel;
    private static String teamName;
    private boolean showLastGame = false;


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


    public MatchesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MatchesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MatchesFragment newInstance(String param1, String param2) {
        MatchesFragment fragment = new MatchesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // get ViewModel
        matchesViewModel = ViewModelProviders.of(this).get(MatchesViewModel.class);
        teamName = ((MainActivity)getActivity()).getTeamName();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_matches, container, false);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set up RecyclerView
        matchesRecyclerView = getActivity().findViewById(R.id.matchesFragmentRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        matchesRecyclerView.setLayoutManager(linearLayoutManager);

        matchesAdapter = new MatchesAdapter(getActivity(), teamName, this);
        matchesRecyclerView.setAdapter(matchesAdapter);

        // Observe ViewModel
        matchesViewModel.getAllMatches().observe(this, new Observer<List<MatchEntity>>() {
            @Override
            public void onChanged(@Nullable List<MatchEntity> playerEntities) {
                Log.e(TAG, "matchesViewModel has changed.");
                // Update cached players
                matchesAdapter.setMatches(matchesViewModel.getAllMatches().getValue());
            }
        });

        // Start MatchesActivity on button click
        addMatchButton = getActivity().findViewById(R.id.matchAddMatchFloatingActionButton);
        addMatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addPlayerIntent = new Intent(MatchesFragment.this.getContext(), AddMatchActivity.class);
                startActivityForResult(addPlayerIntent, ADD_MATCH_ACTIVITY_REQUEST_CODE);
            }
        });

        if (showLastGame) {
            // Inflate the popup_points.xml View
            LayoutInflater layoutInflater = this.getLayoutInflater();
            View playerListView = layoutInflater.inflate(R.layout.game_report_layout, null);

            // Create the popup Window
            final PopupWindow popupWindow = new PopupWindow(getActivity());
            popupWindow.setContentView(playerListView);
            popupWindow.setFocusable(true);
            popupWindow.setClippingEnabled(false);
            popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
            popupWindow.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
            popupWindow.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

            popupWindow.showAtLocation(matchesRecyclerView, 0, 0, 0);
            ((MainActivity)getActivity()).hideTeamImage();

            // TODO: Logic.
            gameReportViewModel = ViewModelProviders.of(this).get(GameReportViewModel.class);


            //-------TextViews--------

            TextView pointsMade = popupWindow.getContentView().findViewById(R.id.visu_points_valG);
            TextView freeThrows = popupWindow.getContentView().findViewById(R.id.visu_fts_valG);
            TextView fieldGoals = popupWindow.getContentView().findViewById(R.id.visu_fgs_valG);
            TextView fieldGoals3 = popupWindow.getContentView().findViewById(R.id.visu_fgs3_valG);
            TextView rebounds = popupWindow.getContentView().findViewById(R.id.visu_rebounds_valG);
            TextView assists = popupWindow.getContentView().findViewById(R.id.visu_assists_valG);
            TextView blocks = popupWindow.getContentView().findViewById(R.id.visu_blocks_valG);
            TextView steals = popupWindow.getContentView().findViewById(R.id.visu_steals_valG);
            TextView turnover = popupWindow.getContentView().findViewById(R.id.visu_tov_valG);
            TextView fouls = popupWindow.getContentView().findViewById(R.id.visu_fouls_valG);


            int matchId = ((MainActivity)getActivity()).getLastMatchId();
            Log.e(TAG, "onViewCreated: matchId = " + matchId);

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










            playerListView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity)getActivity()).showTeamImage();
                    popupWindow.dismiss();
                }
            });

            teamName = ((MainActivity)getActivity()).getTeamName();
            matchesAdapter.setTeamName(teamName);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_MATCH_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            MatchEntity matchEntity = (MatchEntity) data.getExtras().get(AddMatchActivity.EXTRA_REPLY);
            matchesViewModel.insert(matchEntity);
            matchesAdapter.setMatches(matchesViewModel.getAllMatches().getValue());
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

        // check whether to show last game
        if (((MainActivity)getActivity()).getIsJustFinished()) {
            showLastGame = true;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onReportClicked(int matchId) {
        // Inflate the popup_points.xml View
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View playerListView = layoutInflater.inflate(R.layout.game_report_layout, null);

        // TODO: Insert actual values

        // Create the popup Window
        final PopupWindow popupWindow = new PopupWindow(getActivity());
        popupWindow.setContentView(playerListView);
        popupWindow.setFocusable(true);
        popupWindow.setClippingEnabled(false);
        popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        popupWindow.showAtLocation(matchesRecyclerView, 0, 0, 0);
        ((MainActivity)getActivity()).hideTeamImage();

        gameReportViewModel = ViewModelProviders.of(this).get(GameReportViewModel.class);

        //-------TextViews--------

        TextView pointsMade = popupWindow.getContentView().findViewById(R.id.visu_points_valG);
        TextView freeThrows = popupWindow.getContentView().findViewById(R.id.visu_fts_valG);
        TextView fieldGoals = popupWindow.getContentView().findViewById(R.id.visu_fgs_valG);
        TextView fieldGoals3 = popupWindow.getContentView().findViewById(R.id.visu_fgs3_valG);
        TextView rebounds = popupWindow.getContentView().findViewById(R.id.visu_rebounds_valG);
        TextView assists = popupWindow.getContentView().findViewById(R.id.visu_assists_valG);
        TextView blocks = popupWindow.getContentView().findViewById(R.id.visu_blocks_valG);
        TextView steals = popupWindow.getContentView().findViewById(R.id.visu_steals_valG);
        TextView turnover = popupWindow.getContentView().findViewById(R.id.visu_tov_valG);
        TextView fouls = popupWindow.getContentView().findViewById(R.id.visu_fouls_valG);

        Log.e(TAG, "onViewCreated: matchId = " + matchId);

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

        playerListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showTeamImage();
                popupWindow.dismiss();
            }
        });
    }
}
