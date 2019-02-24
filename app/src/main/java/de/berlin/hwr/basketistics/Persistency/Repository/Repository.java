package de.berlin.hwr.basketistics.Persistency.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import de.berlin.hwr.basketistics.Persistency.Dao.EventDao;
import de.berlin.hwr.basketistics.Persistency.Dao.PlayerDao;
import de.berlin.hwr.basketistics.Persistency.Database.Database;
import de.berlin.hwr.basketistics.Persistency.Entities.EventEntity;
import de.berlin.hwr.basketistics.Persistency.Entities.PlayerEntity;
import de.berlin.hwr.basketistics.ViewModel.EventViewModel;

public class Repository {

    private PlayerDao playerDao;
    private List<PlayerEntity>  players;

    private EventDao eventDao;
    private List<EventEntity> events;

    public Repository(Application application) {

        Database database = Database.getDatabase(application);
        this.playerDao = database.playerDao();
        this.players = playerDao.getAll();
        this.eventDao = database.eventDao();
        this.events = eventDao.getAll();
    }

    public List<PlayerEntity> getAllPlayers() {
        return players;
    }

    public List<EventEntity> getAllEvents() {
        return events;
    }

    public EventViewModel.PlayerEvents[] getPlayerEvents() {
        return null;
    }

    public void insert(PlayerEntity playerEntity) {
        new insertAsynchTask(playerDao).execute(playerEntity);
    }

    private static class insertAsynchTask extends AsyncTask<PlayerEntity, Void, Void> {

        private PlayerDao asynchPlayerDao;

        insertAsynchTask(PlayerDao playerDao) {
            this.asynchPlayerDao = playerDao;
        }

        @Override
        protected Void doInBackground(PlayerEntity... params) {
            asynchPlayerDao.insert(params[0]);
            return null;
        }
    }
}
