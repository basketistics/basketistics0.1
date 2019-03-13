package de.berlin.hwr.basketistics.Persistency.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import de.berlin.hwr.basketistics.Persistency.Dao.EventDao;
import de.berlin.hwr.basketistics.Persistency.Dao.EventJoinDao;
import de.berlin.hwr.basketistics.Persistency.Dao.EventTypeDao;
import de.berlin.hwr.basketistics.Persistency.Dao.MatchDao;
import de.berlin.hwr.basketistics.Persistency.Dao.PlayerDao;
import de.berlin.hwr.basketistics.Persistency.Database.Database;
import de.berlin.hwr.basketistics.Persistency.Entities.EventEntity;
import de.berlin.hwr.basketistics.Persistency.Entities.EventJoinEntity;
import de.berlin.hwr.basketistics.Persistency.Entities.EventTypeEntity;
import de.berlin.hwr.basketistics.Persistency.Entities.MatchEntity;
import de.berlin.hwr.basketistics.Persistency.Entities.PlayerEntity;

public class Repository {

    private PlayerDao playerDao;
    private List<PlayerEntity> players;
    private List<PlayerEntity> playersInCurrentGame;

    private EventDao eventDao;
    // TODO: Remove if unnecessary (storage issues with big list?).
    private List<EventEntity> events;

    private MatchDao matchDao;
    private List<MatchEntity> matches;

    private EventTypeDao eventTypeDao;

    private EventJoinDao eventJoinDao;

    public Repository(Application application) {

        Database database = Database.getDatabase(application);
        this.playerDao = database.playerDao();
        this.players = getAllPlayers();
        this.eventDao = database.eventDao();
        this.events = getAllEvents();
        this.matchDao = database.matchDao();
        this.matches = getAllMatches();
        this.eventTypeDao = database.eventTypeDao();
        this.eventJoinDao = database.eventJoinDao();

    }

    // ---------- EventTypes ---------- //
    public List<EventTypeEntity> getAllEventTypeEntities() {
        List<EventTypeEntity> eventTypeEntities= null;
        try {
            eventTypeEntities = new GetAllEventTypeEntitiesAsyncTask(eventTypeDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventTypeEntities;
    }

    private class GetAllEventTypeEntitiesAsyncTask extends AsyncTask<Void, Void, List<EventTypeEntity>> {

        private final static String TAG = "getMatchByIdAsynchTask";
        private EventTypeDao asyncEventTypeDao;

        public GetAllEventTypeEntitiesAsyncTask(EventTypeDao eventTypeDao) {
            this.asyncEventTypeDao = eventTypeDao;
        }

        @Override
        protected List<EventTypeEntity>doInBackground(Void... voids) {
            return asyncEventTypeDao.getAll();
        }
    }

    // ---------- Matches ---------- //

    public List<MatchEntity> getAllMatches() {
        List<MatchEntity> matchEntities = null;
        try {
            matchEntities = new GetAllMatchesAsyncTask(matchDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return matchEntities;
    }

    private class GetAllMatchesAsyncTask extends AsyncTask<Void, Void, List<MatchEntity>> {

        private final static String TAG = "getMatchByIdAsynchTask";
        private MatchDao asyncMatchDao;

        public GetAllMatchesAsyncTask(MatchDao matchDao) {
            this.asyncMatchDao = matchDao;
        }

        @Override
        protected List<MatchEntity>doInBackground(Void... voids) {
            return asyncMatchDao.getAll();
        }
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

    public MatchEntity getMatchById(int matchId) throws ExecutionException, InterruptedException {

        MatchEntity match = new GetMatchByIdAsyncTask(matchDao).execute(matchId).get();
        return match;
    }

    private static class GetMatchByIdAsyncTask extends AsyncTask<Integer, Void, MatchEntity> {

        private final static String TAG = "getMatchByIdAsynchTask";
        private MatchDao asyncMatchDao;

        public GetMatchByIdAsyncTask(MatchDao matchDao) {
            this.asyncMatchDao = matchDao;
        }

        @Override
        protected MatchEntity doInBackground(Integer... integers) {
            int[] ids = new int[1];
            ids[0] = integers[0];
            List<MatchEntity> matchEntities = asyncMatchDao.getAllByIds(ids);
            return matchEntities.get(0);
        }
    }

    // ---------- Events ---------- //

    public List<EventEntity> getAllEvents() {

        List<EventEntity> eventEntities = null;
        try {
            eventEntities = new GetAllEventsAsyncTask(eventDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventEntities;
    }

    private class GetAllEventsAsyncTask extends
            AsyncTask<Void, Void, List<EventEntity>>{

        private EventDao asyncEventDao;

        GetAllEventsAsyncTask(EventDao eventDao) {
            this.asyncEventDao = eventDao;
        }

        @Override
        protected List<EventEntity> doInBackground(Void... voids) {
            List<EventEntity> eventEntitiesByMatchAndPlayer = asyncEventDao.getAll();
            return eventEntitiesByMatchAndPlayer;
        }
    }


    // TODO: Maybe use cached values?
    public List<EventEntity> getEventsByMatchAndPlayer(int matchId, int playerId)
        throws ExecutionException, InterruptedException {
        List<EventEntity> eventsByMatchAndPlayer =
                new GetEventsByMatchAndPlayerAsyncTask(eventDao).execute(matchId, playerId).get();
        return eventsByMatchAndPlayer;
    }

    private  class GetEventsByMatchAndPlayerAsyncTask extends
            AsyncTask<Integer, Void, List<EventEntity>>{

        private EventDao asyncEventDao;

        GetEventsByMatchAndPlayerAsyncTask(EventDao eventDao) {
            this.asyncEventDao = eventDao;
        }

        @Override
        protected List<EventEntity> doInBackground(Integer... integers) {
            List<EventEntity> eventEntitiesByMatchAndPlayer =
                    asyncEventDao.getEventsByMatchesAndPlayers(integers[0], integers[1]);
            return eventEntitiesByMatchAndPlayer;
        }
    }

    public List<EventEntity> getEventsByMatchId(int matchId)
            throws ExecutionException, InterruptedException {
        List<EventEntity> eventsByMatch =
                new GetEventsByMatchAsyncTask(eventDao).execute(matchId).get();
        return eventsByMatch;
    }

    private  class GetEventsByMatchAsyncTask extends
            AsyncTask<Integer, Void, List<EventEntity>>{

        private EventDao asyncEventDao;

        GetEventsByMatchAsyncTask(EventDao eventDao) {
            this.asyncEventDao = eventDao;
        }

        @Override
        protected List<EventEntity> doInBackground(Integer... integers) {
            List<EventEntity> eventEntitiesByMatch =
                    asyncEventDao.getEventsByMatches(integers[0]);
            return eventEntitiesByMatch;
        }
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

    // ---------- Player ---------- //

    @Deprecated
    public void addPlayerToCurrentGame(PlayerEntity playerEntity) {
        playersInCurrentGame.add(playerEntity);
    }

    @Deprecated
    public List<PlayerEntity> getPlayersInCurrentGame(int matchId)
            throws ExecutionException, InterruptedException {

        if (playersInCurrentGame == null) {
            playersInCurrentGame
                    = new GetPlayersByEventsAsyncTask(eventDao, playerDao).execute(matchId).get();
        }
        return playersInCurrentGame;
    }

    private static class GetPlayersByEventsAsyncTask
            extends AsyncTask<Integer, Void, List<PlayerEntity>>{
        private EventDao asyncEventDao;
        private PlayerDao asyncPlayerDao;

        GetPlayersByEventsAsyncTask(EventDao eventDao, PlayerDao playerDao) {
            this.asyncEventDao = eventDao;
            this.asyncPlayerDao = playerDao;
        }

        @Override
        protected List<PlayerEntity> doInBackground(Integer... matchIds) {

            // Iterate through events and list all players involved
            List<EventEntity> eventsByMatch = asyncEventDao.getPlayerEventsByMatch(matchIds[0]);
            List<Integer> playerIdsByMatch = new ArrayList<Integer>();
            for (EventEntity eventEntity : eventsByMatch) {
                if (!playerIdsByMatch.contains(eventEntity.getPlayerId())) {
                    playerIdsByMatch.add(eventEntity.getPlayerId());
                }
            }
            // Create those players and add to list
            List<PlayerEntity> playerByMatch = new ArrayList<PlayerEntity>();
            for (Integer playerId : playerIdsByMatch) {
                playerByMatch.add(asyncPlayerDao.getPlayerById(playerId));
            }
            return playerByMatch;
        }
    }


    public List<PlayerEntity> getAllPlayers() {

        List<PlayerEntity> players = null;
        try {
            players = new GetAllPlayersAsyncTask(playerDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return players;
    }

    private class GetAllPlayersAsyncTask extends AsyncTask<Void, Void, List<PlayerEntity>> {

        private PlayerDao asyncPlayerDao;

        public GetAllPlayersAsyncTask(PlayerDao playerDao) {
            this.asyncPlayerDao = playerDao;
        }
        @Override
        protected List<PlayerEntity> doInBackground(Void... voids) {

            return asyncPlayerDao.getAllPLayers();
        }
    }

    public PlayerEntity getPlayerById(int playerId) {

        PlayerEntity playerEntity = null;
        try {
            playerEntity = new GetPlayerByIdAsyncTask(playerDao).execute(playerId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return playerEntity;
    }

    private class GetPlayerByIdAsyncTask extends AsyncTask<Integer, Void, PlayerEntity> {

        private PlayerDao asyncPlayerDao;

        public GetPlayerByIdAsyncTask(PlayerDao playerDao) {
            this.asyncPlayerDao = playerDao;
        }
        @Override
        protected PlayerEntity doInBackground(Integer... integers) {
            return asyncPlayerDao.getPlayerById(integers[0]);
        }
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


    //---------stat visualisaion-----------

    public EventJoinEntity getReboundsByPlayerId (int playerId, int matchId) {

        EventJoinEntity eventJoinEntity = null;
        try {
            eventJoinEntity = new GetReboundsByPlayerIdAsyncTask(eventJoinDao).execute(playerId, matchId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetReboundsByPlayerIdAsyncTask extends AsyncTask<Integer, Void, EventJoinEntity> {

        private EventJoinDao asyncEventJoinDao;

        public GetReboundsByPlayerIdAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected EventJoinEntity doInBackground(Integer... integers) {
            return asyncEventJoinDao.getReboundsByPlayerId(integers[0], integers[1]);
        }
    }


    public List<Integer> getPlayerIdsByMatch(int matchId) {

        List<Integer> playerIds = null;
        try {
            playerIds = new GetPlayerIdsByMatchAsyncTask(eventJoinDao).execute(matchId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return playerIds;
    }

    private class GetPlayerIdsByMatchAsyncTask extends AsyncTask<Integer, Void, List<Integer>> {

        private EventJoinDao asyncEventJoinDao;

        public GetPlayerIdsByMatchAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected List<Integer> doInBackground(Integer... integers) {
            return asyncEventJoinDao.getPlayerIdsByMatch(integers[0]);
        }
    }

    //-----One Point Made-----
    public EventJoinEntity getOnePointerByPlayerId (int playerId, int matchId) {

        EventJoinEntity eventJoinEntity = null;
        try {
            eventJoinEntity = new GetOnePointerByPlayerIdAsyncTask(eventJoinDao).execute(playerId, matchId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetOnePointerByPlayerIdAsyncTask extends AsyncTask<Integer, Void, EventJoinEntity> {

        private EventJoinDao asyncEventJoinDao;

        public GetOnePointerByPlayerIdAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected EventJoinEntity doInBackground(Integer... integers) {
            return asyncEventJoinDao.getOnePointerByPlayerId(integers[0], integers[1]);
        }
    }

    //-----Two Pointer Made-----
    public EventJoinEntity getTwoPointerByPlayerId (int playerId, int matchId) {

        EventJoinEntity eventJoinEntity = null;
        try {
            eventJoinEntity = new GetOnePointerByPlayerIdAsyncTask(eventJoinDao).execute(playerId, matchId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetTwoPointerByPlayerIdAsyncTask extends AsyncTask<Integer, Void, EventJoinEntity> {

        private EventJoinDao asyncEventJoinDao;

        public GetTwoPointerByPlayerIdAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected EventJoinEntity doInBackground(Integer... integers) {
            return asyncEventJoinDao.getTwoPointerByPlayerId(integers[0], integers[1]);
        }
    }

    //-----Three Pointer Made-----
    public EventJoinEntity getThreePointerByPlayerId (int playerId, int matchId) {

        EventJoinEntity eventJoinEntity = null;
        try {
            eventJoinEntity = new GetThreePointerByPlayerIdAsyncTask(eventJoinDao).execute(playerId, matchId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetThreePointerByPlayerIdAsyncTask extends AsyncTask<Integer, Void, EventJoinEntity> {

        private EventJoinDao asyncEventJoinDao;

        public GetThreePointerByPlayerIdAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected EventJoinEntity doInBackground(Integer... integers) {
            return asyncEventJoinDao.getThreePointerByPlayerId(integers[0], integers[1]);
        }
    }

    //-----One Point attempts-----
    public EventJoinEntity getOnePointerAttemptByPlayerId (int playerId, int matchId) {

        EventJoinEntity eventJoinEntity = null;
        try {
            eventJoinEntity = new GetOnePointerAttemptByPlayerIdAsyncTask(eventJoinDao).execute(playerId, matchId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetOnePointerAttemptByPlayerIdAsyncTask extends AsyncTask<Integer, Void, EventJoinEntity> {

        private EventJoinDao asyncEventJoinDao;

        public GetOnePointerAttemptByPlayerIdAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected EventJoinEntity doInBackground(Integer... integers) {
            return asyncEventJoinDao.getOnePointAttemptsByPlayerId(integers[0], integers[1]);
        }
    }
    //-----Two Point attempts-----
    public EventJoinEntity getTwoPointerAttemptByPlayerId (int playerId, int matchId) {

        EventJoinEntity eventJoinEntity = null;
        try {
            eventJoinEntity = new GetTwoPointerAttemptByPlayerIdAsyncTask(eventJoinDao).execute(playerId, matchId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetTwoPointerAttemptByPlayerIdAsyncTask extends AsyncTask<Integer, Void, EventJoinEntity> {

        private EventJoinDao asyncEventJoinDao;

        public GetTwoPointerAttemptByPlayerIdAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected EventJoinEntity doInBackground(Integer... integers) {
            return asyncEventJoinDao.getTwoPointAttemptsByPlayerId(integers[0], integers[1]);
        }
    }

    //-----Three Point attempts-----
    public EventJoinEntity getThreePointerAttemptByPlayerId (int playerId, int matchId) {

        EventJoinEntity eventJoinEntity = null;
        try {
            eventJoinEntity = new GetThreePointerAttemptByPlayerIdAsyncTask(eventJoinDao).execute(playerId, matchId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetThreePointerAttemptByPlayerIdAsyncTask extends AsyncTask<Integer, Void, EventJoinEntity> {

        private EventJoinDao asyncEventJoinDao;

        public GetThreePointerAttemptByPlayerIdAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected EventJoinEntity doInBackground(Integer... integers) {
            return asyncEventJoinDao.getThreePointAttemptsByPlayerId(integers[0], integers[1]);
        }
    }

    //-----Starter-----
    public List<Integer> getStarterByMatchId (int matchId) {

        List<Integer> eventJoinEntity = null;
        try {
            eventJoinEntity = new GetStarterByMatchIdAsyncTask(eventJoinDao).execute(matchId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetStarterByMatchIdAsyncTask extends AsyncTask<Integer, Void, List<Integer>> {

        private EventJoinDao asyncEventJoinDao;

        public GetStarterByMatchIdAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected List<Integer> doInBackground(Integer... integers) {
            return asyncEventJoinDao.getStarterByMatchId(integers[0]);
        }
    }

    //-----------Assists-----------
    public EventJoinEntity getAssistsByPlayerId (int playerId, int matchId) {

        EventJoinEntity eventJoinEntity = null;
        try {
            eventJoinEntity = new GetAssistsByPlayerIdAsyncTask(eventJoinDao).execute(playerId, matchId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetAssistsByPlayerIdAsyncTask extends AsyncTask<Integer, Void, EventJoinEntity> {

        private EventJoinDao asyncEventJoinDao;

        public GetAssistsByPlayerIdAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected EventJoinEntity doInBackground(Integer... integers) {
            return asyncEventJoinDao.getAssistsByPlayerId(integers[0], integers[1]);
        }
    }

    //-----------Steals-----------
    public EventJoinEntity getStealsByPlayerId (int playerId, int matchId) {

        EventJoinEntity eventJoinEntity = null;
        try {
            eventJoinEntity = new GetStealsByPlayerIdAsyncTask(eventJoinDao).execute(playerId, matchId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetStealsByPlayerIdAsyncTask extends AsyncTask<Integer, Void, EventJoinEntity> {

        private EventJoinDao asyncEventJoinDao;

        public GetStealsByPlayerIdAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected EventJoinEntity doInBackground(Integer... integers) {
            return asyncEventJoinDao.getStealsByPlayerId(integers[0], integers[1]);
        }
    }

    //-----------Blocks-----------
    public EventJoinEntity getBlocksByPlayerId (int playerId, int matchId) {

        EventJoinEntity eventJoinEntity = null;
        try {
            eventJoinEntity = new GetBlocksByPlayerIdAsyncTask(eventJoinDao).execute(playerId, matchId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetBlocksByPlayerIdAsyncTask extends AsyncTask<Integer, Void, EventJoinEntity> {

        private EventJoinDao asyncEventJoinDao;

        public GetBlocksByPlayerIdAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected EventJoinEntity doInBackground(Integer... integers) {
            return asyncEventJoinDao.getBlocksByPlayerId(integers[0], integers[1]);
        }
    }

    //-----------Turnover-----------
    public EventJoinEntity getTurnoverByPlayerId (int playerId, int matchId) {

        EventJoinEntity eventJoinEntity = null;
        try {
            eventJoinEntity = new GetTurnoverByPlayerIdAsyncTask(eventJoinDao).execute(playerId, matchId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetTurnoverByPlayerIdAsyncTask extends AsyncTask<Integer, Void, EventJoinEntity> {

        private EventJoinDao asyncEventJoinDao;

        public GetTurnoverByPlayerIdAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected EventJoinEntity doInBackground(Integer... integers) {
            return asyncEventJoinDao.getTurnoversByPlayerId(integers[0], integers[1]);
        }
    }

    //-----------Fouls-----------
    public EventJoinEntity getFoulsByPlayerId (int playerId, int matchId) {

        EventJoinEntity eventJoinEntity = null;
        try {
            eventJoinEntity = new GetFoulsByPlayerIdAsyncTask(eventJoinDao).execute(playerId, matchId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetFoulsByPlayerIdAsyncTask extends AsyncTask<Integer, Void, EventJoinEntity> {

        private EventJoinDao asyncEventJoinDao;

        public GetFoulsByPlayerIdAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected EventJoinEntity doInBackground(Integer... integers) {
            return asyncEventJoinDao.getFoulsByPlayerId(integers[0], integers[1]);
        }
    }


}




