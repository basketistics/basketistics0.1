package de.berlin.hwr.basketistics.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import de.berlin.hwr.basketistics.Persistency.Entities.Player;
import de.berlin.hwr.basketistics.Persistency.MockPlayerDB;

public class TeamViewModel extends ViewModel {

    private MutableLiveData<List<Player>> team = new MutableLiveData<List<Player>>();

    public LiveData<List<Player>> getTeam() {
        if (team.getValue() == null) {
            team.setValue(new MockPlayerDB().db);
        }
        return (LiveData<List<Player>>) team;
    }
    public void addPlayer(Player player) { team.getValue().add(player); }
}
