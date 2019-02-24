package de.berlin.hwr.basketistics.Persistency.Repository;

import android.app.Application;
import android.graphics.LightingColorFilter;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;
import java.util.regex.Matcher;

import de.berlin.hwr.basketistics.Persistency.Dao.EventDao;
import de.berlin.hwr.basketistics.Persistency.Dao.MatchDao;
import de.berlin.hwr.basketistics.Persistency.Dao.PlayerDao;
import de.berlin.hwr.basketistics.Persistency.Database.Database;
import de.berlin.hwr.basketistics.Persistency.Entities.EventEntity;
import de.berlin.hwr.basketistics.Persistency.Entities.MatchEntity;
import de.berlin.hwr.basketistics.Persistency.Entities.PlayerEntity;
import de.berlin.hwr.basketistics.ViewModel.EventViewModel;

public class Repository {

    private PlayerDao playerDao;
    private List<PlayerEntity>  players;

    private EventDao eventDao;
    private List<EventEntity> events;
    private EventViewModel.PlayerEvents[] playerEvents;

    private MatchDao matchDao;
    private List<MatchEntity> matches;

    public Repository(Application application) {

        Database database = Database.getDatabase(application);
        this.playerDao = database.playerDao();
        this.players = playerDao.getAll();
        this.eventDao = database.eventDao();
        this.events = eventDao.getAll();
        this.matchDao = database.matchDao();
        this.matches = matchDao.getAll();
    }

    // ---------- Matches ---------- //

    public List<MatchEntity> getAllMatches() {
        return matches;
    }

    public void insertMatch(MatchEntity matchEntity) {
        new InsertMatchAsyncTask(matchDao).execute(matchEntity);
    }

    private static class InsertMatchAsyncTask extends AsyncTask<MatchEntity, Void, Void>{

        private final static String TAG = "InsertMatchAsyncTask";

        private MatchDao asyncMatchDao;

        InsertMatchAsyncTask(MatchDao matchDao) {
            this.asyncMatchDao = matchDao;
        }

        @Override
        protected Void doInBackground(MatchEntity... matchEntities) {
            Log.i(TAG, "inserting " + matchEntities[0].toString());
            asyncMatchDao.insert(matchEntities[0]);
            return null;
        }
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
