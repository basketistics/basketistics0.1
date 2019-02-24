package de.berlin.hwr.basketistics.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import de.berlin.hwr.basketistics.Persistency.Entities.MatchEntity;
import de.berlin.hwr.basketistics.Persistency.Repository.Repository;

public class MatchesViewModel extends AndroidViewModel {

    private MutableLiveData<List<MatchEntity>> matches;
    private Repository repository;

    public MatchesViewModel(@NonNull Application application) {
        super(application);
        this.repository = new Repository(application);
        this.matches.setValue(repository.getAllMatches());
    }

    public LiveData<List<MatchEntity>> getAllMatches() {
        return (LiveData<List<MatchEntity>>) matches;
    }

    public void insert(MatchEntity matchEntity) {
        matches.getValue().add(matchEntity);
        repository.insertMatch(matchEntity);
    }
}

