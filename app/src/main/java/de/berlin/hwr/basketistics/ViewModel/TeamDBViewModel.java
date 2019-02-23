package de.berlin.hwr.basketistics.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import de.berlin.hwr.basketistics.Persistency.Entities.PlayerEntity;
import de.berlin.hwr.basketistics.Persistency.Repository.Repository;

public class TeamDBViewModel extends AndroidViewModel {

    private Repository repository;
    private MutableLiveData<List<PlayerEntity>> allPlayers;

    public TeamDBViewModel(Application application) {
        super(application);
        repository = new Repository(application);
        allPlayers = new MutableLiveData<List<PlayerEntity>>();
        allPlayers.setValue(repository.getAllPlayers());
    }

    public LiveData<List<PlayerEntity>> getAllPlayers() {
        return (LiveData<List<PlayerEntity>>) allPlayers.getValue();
    }

    public void insert(PlayerEntity playerEntity) {
        repository.insert(playerEntity);
    }
}
