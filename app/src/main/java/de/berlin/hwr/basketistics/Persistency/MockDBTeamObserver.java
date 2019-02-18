package de.berlin.hwr.basketistics.Persistency;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import de.berlin.hwr.basketistics.ViewModel.TeamViewModel;

public class MockDBTeamObserver implements Observer {

    private TeamViewModel teamViewModel;
    private MockPlayerDB mockPlayerDB;

    public MockDBTeamObserver(TeamViewModel teamViewModel, MockPlayerDB mockPlayerDB) {
        this.teamViewModel = teamViewModel;
        this.mockPlayerDB = mockPlayerDB;
    }

    @Override
    public void onChanged(@Nullable Object o) {
        this.mockPlayerDB.db = teamViewModel.getTeam().getValue();
    }
}
