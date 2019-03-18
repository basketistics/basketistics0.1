package de.berlin.hwr.basketistics.UI;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import de.berlin.hwr.basketistics.Persistency.Entities.PlayerEntity;
import de.berlin.hwr.basketistics.R;
import de.berlin.hwr.basketistics.UI.Fragments.Adapter.TeamAdapter;
import de.berlin.hwr.basketistics.ViewModel.TeamViewModel;

public class PlayerReportsListFragment extends Fragment implements OnPlayerClickedListener{

    private static final String TAG = "ReportPlayerPickFrag";

    RecyclerView teamRecyclerView;
    TeamAdapter teamAdapter;
    private TeamViewModel teamViewModel;
    private SharedPreferences sharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View rootView = inflater.inflate(R.layout.player_list_popup, container, false);

        // Set up RecyclerView

        teamRecyclerView = rootView.findViewById(R.id.playerListRecyclerView);
        teamAdapter = new TeamAdapter(getActivity(), this);
        teamRecyclerView.setAdapter(teamAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        LinearLayout playerListLayout = rootView.findViewById(R.id.teamListItemLinearLayout);

        teamRecyclerView.setLayoutManager(linearLayoutManager);
        // get ViewModel
        teamViewModel = ViewModelProviders.of(this).get(TeamViewModel.class);

        // Observe ViewModel
        teamViewModel.getAllPlayers().observe(this, new Observer<List<PlayerEntity>>() {
            @Override
            public void onChanged(@Nullable List<PlayerEntity> playerEntities) {
                Log.e(TAG, "teamViewModel has changed.");
                // Update cached players
                teamAdapter.setTeam(teamViewModel.getAllPlayers().getValue());
            }
        });

        return rootView;
    }

    @Override
    public void onPlayerClicked(int playerId) {
        // Show report for player (playerId)
        Log.e(TAG, "onPlayerClicked: PlayerID = " + playerId);
        Bundle data = new Bundle();
        data.putInt("playerId", playerId);





        FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                .beginTransaction();
        SinglePlayerReportFragment mFrag = new SinglePlayerReportFragment();
        mFrag.setArguments(data);
       // transaction.replace(R.id.fragmentContainer_playerstats, mFrag);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
