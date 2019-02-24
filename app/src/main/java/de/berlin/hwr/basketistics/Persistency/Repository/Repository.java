package de.berlin.hwr.basketistics.Persistency.Repository;

import android.app.Application;
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
    private EventViewModel.PlayerEvents[] playerEvents;

    public Repository(Application application) {

        Database database = Database.getDatabase(application);
        this.playerDao = database.playerDao();
        this.players = playerDao.getAll();
        this.eventDao = database.eventDao();
        this.events = eventDao.getAll();
    }

    // ---------- Events ---------- //

    public List<EventEntity> getAllEvents() {
        return events;
    }

    public EventViewModel.PlayerEvents[] getPlayerEvents() {
        return null;
    }

    public void insertEvent(EventEntity eventEntity) {
        new InsertEventAsyncTask(eventDao).execute(eventEntity);
    }

    private static class InsertEventAsyncTask extends AsyncTask<EventEntity, Void, Void> {

        private EventDao asyncEventDao;

        InsertEventAsyncTask(EventDao eventDao) {this.asyncEventDao = eventDao;}

        @Override
        protected Void doInBackground(EventEntity... eventEntities) {
            asyncEventDao.insert(eventEntities[0]);
            return null;
        }
    }

    // ---------- PLayer ---------- //

    public List<PlayerEntity> getAllPlayers() {
        return players;
    }

    public void insertPlayer(PlayerEntity playerEntity) {
        new InsertPlayerAsyncTask(playerDao).execute(playerEntity);
    }

    private static class InsertPlayerAsyncTask extends AsyncTask<PlayerEntity, Void, Void> {

        private PlayerDao asyncPlayerDao;

        InsertPlayerAsyncTask(PlayerDao playerDao) {
            this.asyncPlayerDao = playerDao;
        }

        @Override
        protected Void doInBackground(PlayerEntity... params) {
            asyncPlayerDao.insert(params[0]);
            return null;
        }
    }
}
