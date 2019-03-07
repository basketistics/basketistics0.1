package de.berlin.hwr.basketistics.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import de.berlin.hwr.basketistics.Persistency.Entities.MatchEntity;
import de.berlin.hwr.basketistics.Persistency.Repository.Repository;

public class MatchesViewModel extends AndroidViewModel {

    private final static String TAG = "MatcheViewModel";

    private MutableLiveData<List<MatchEntity>> matches;
    private Repository repository;

    public MatchesViewModel(@NonNull Application application) {
        super(application);
        this.repository = new Repository(application);
        this.matches = new MutableLiveData<List<MatchEntity>>();
        this.matches.setValue(repository.getAllMatches());
        /*
        if (matches == null) {
            matches = new MutableLiveData<List<MatchEntity>>();
        }
        */
    }

    public MatchEntity getMatchById(int id) throws ExecutionException, InterruptedException {
        return repository.getMatchById(id);
    }

    public LiveData<List<MatchEntity>> getAllMatches() {
        return matches;
    }

    public void insert(MatchEntity matchEntity) {
        Log.i(TAG, "insert() was reached. inserting " + matchEntity.toString());
        if (matches.getValue() == null) {
            matches.setValue(new ArrayList<MatchEntity>());
        }
        matches.getValue().add(matchEntity);
        repository.insertMatch(matchEntity);
    }
}

