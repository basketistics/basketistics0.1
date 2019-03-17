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

    // ---------- Enemy Points ---------- //

    public void incEnemyPoints(EventEntity eventEntity) {
        new EnemyPointsAsyncTask(eventDao, 1).execute(eventEntity);
    }


    public void decEnemyPopints(EventEntity eventEntity) {
        new EnemyPointsAsyncTask(eventDao, -1).execute(eventEntity);
    }

    private static class EnemyPointsAsyncTask extends AsyncTask<EventEntity, Void, Void>{

        private EventDao asyncEventDao;
        private int incDec;

        EnemyPointsAsyncTask(EventDao eventDao, int incDec) {
            this.asyncEventDao = eventDao;
            this.incDec = incDec;
        }

        @Override
        protected Void doInBackground(EventEntity... eventEntitys) {
            if (incDec == 1) {
                asyncEventDao.insert(eventEntitys[0]);
            } else if (incDec == -1) {
                asyncEventDao.insert(eventEntitys[0]);
            }
            return null;
        }

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


    //---------------------stat visualisaion--------------------
    //-------------By PlayerID and MatchID---------
    public Integer getReboundsByPlayerAndMatch (int playerId, int matchId) {

        Integer eventJoinEntity = null;
        try {
             eventJoinEntity = new GetReboundsByPlayerAndMatchAsyncTask(eventJoinDao).execute(playerId, matchId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetReboundsByPlayerAndMatchAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetReboundsByPlayerAndMatchAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getReboundsByPlayerAndMatch(integers[0], integers[1]);
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
    public Integer getOnePointerByPlayerAndMatch (int playerId, int matchId) {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetOnePointerByPlayerAndMatchAsyncTask(eventJoinDao).execute(playerId, matchId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetOnePointerByPlayerAndMatchAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetOnePointerByPlayerAndMatchAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getOnePointerByPlayerAndMatch(integers[0], integers[1]);
        }
    }

    //-----Two Pointer Made-----
    public Integer getTwoPointerByPlayerAndMatch (int playerId, int matchId) {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetOnePointerByPlayerAndMatchAsyncTask(eventJoinDao).execute(playerId, matchId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetTwoPointerByPlayerAndMatchAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetTwoPointerByPlayerAndMatchAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getTwoPointerByPlayerAndMatch(integers[0], integers[1]);
        }
    }

    //-----Three Pointer Made-----
    public Integer getThreePointerByPlayerAndMatch (int playerId, int matchId) {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetThreePointerByPlayerAndMatchAsyncTask(eventJoinDao).execute(playerId, matchId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetThreePointerByPlayerAndMatchAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetThreePointerByPlayerAndMatchAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getThreePointerByPlayerAndMatch(integers[0], integers[1]);
        }
    }

    //-----One Point attempts-----
    public Integer getOnePointerAttemptByPlayerAndMatch (int playerId, int matchId) {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetOnePointerAttemptByPlayerAndMatchAsyncTask(eventJoinDao).execute(playerId, matchId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetOnePointerAttemptByPlayerAndMatchAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetOnePointerAttemptByPlayerAndMatchAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getOnePointAttemptsByPlayerAndMatch(integers[0], integers[1]);
        }
    }
    //-----Two Point attempts-----
    public Integer getTwoPointerAttemptByPlayerAndMatch (int playerId, int matchId) {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetTwoPointerAttemptByPlayerAndMatchAsyncTask(eventJoinDao).execute(playerId, matchId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetTwoPointerAttemptByPlayerAndMatchAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetTwoPointerAttemptByPlayerAndMatchAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getTwoPointAttemptsByPlayerAndMatch(integers[0], integers[1]);
        }
    }

    //-----Three Point attempts-----
    public Integer getThreePointerAttemptByPlayerAndMatch (int playerId, int matchId) {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetThreePointerAttemptByPlayerAndMatchAsyncTask(eventJoinDao).execute(playerId, matchId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetThreePointerAttemptByPlayerAndMatchAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetThreePointerAttemptByPlayerAndMatchAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getThreePointAttemptsByPlayerAndMatch(integers[0], integers[1]);
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
    public Integer getAssistsByPlayerAndMatch (int playerId, int matchId) {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetAssistsByPlayerAndMatchAsyncTask(eventJoinDao).execute(playerId, matchId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetAssistsByPlayerAndMatchAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetAssistsByPlayerAndMatchAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getAssistsByPlayerAndMatch(integers[0], integers[1]);
        }
    }

    //-----------Steals-----------
    public Integer getStealsByPlayerAndMatch (int playerId, int matchId) {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetStealsByPlayerAndMatchAsyncTask(eventJoinDao).execute(playerId, matchId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetStealsByPlayerAndMatchAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetStealsByPlayerAndMatchAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getStealsByPlayerAndMatch(integers[0], integers[1]);
        }
    }

    //-----------Blocks-----------
    public Integer getBlocksByPlayerAndMatch (int playerId, int matchId) {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetBlocksByPlayerAndMatchAsyncTask(eventJoinDao).execute(playerId, matchId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetBlocksByPlayerAndMatchAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetBlocksByPlayerAndMatchAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getBlocksByPlayerAndMatch(integers[0], integers[1]);
        }
    }

    //-----------Turnover--------------
    // --
    public Integer getTurnoverByPlayerAndMatch (int playerId, int matchId) {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetTurnoverByPlayerAndMatchAsyncTask(eventJoinDao).execute(playerId, matchId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetTurnoverByPlayerAndMatchAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetTurnoverByPlayerAndMatchAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getTurnoversByPlayerAndMatch(integers[0], integers[1]);
        }
    }

    //-----------Fouls-----------
    public Integer getFoulsByPlayerAndMatch (int playerId, int matchId) {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetFoulsByPlayerAndMatchAsyncTask(eventJoinDao).execute(playerId, matchId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetFoulsByPlayerAndMatchAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetFoulsByPlayerAndMatchAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getFoulsByPlayerAndMatch(integers[0], integers[1]);
        }
    }


    //-----------By PlayerId----------

    //-----One Point Made-----
    public Integer getOnePointerByPlayerId (int playerId) {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetOnePointerByPlayerIdAsyncTask(eventJoinDao).execute(playerId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetOnePointerByPlayerIdAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetOnePointerByPlayerIdAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getOnePointerByPlayerId(integers[0]);
        }
    }

    //-----Two Pointer Made-----
    public Integer getTwoPointerByPlayerId (int playerId) {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetTwoPointerByPlayerIdAsyncTask(eventJoinDao).execute(playerId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetTwoPointerByPlayerIdAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetTwoPointerByPlayerIdAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getTwoPointerByPlayerId(integers[0]);
        }
    }

    //-----Three Pointer Made-----
    public Integer getThreePointerByPlayerId (int playerId) {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetThreePointerByPlayerIdAsyncTask(eventJoinDao).execute(playerId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetThreePointerByPlayerIdAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetThreePointerByPlayerIdAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getThreePointerByPlayerId(integers[0]);
        }
    }

    //-----One Point attempts-----
    public Integer getOnePointerAttemptByPlayerId (int playerId) {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetOnePointerAttemptByPlayerIdAsyncTask(eventJoinDao).execute(playerId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetOnePointerAttemptByPlayerIdAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetOnePointerAttemptByPlayerIdAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getOnePointAttemptsByPlayerId(integers[0]);
        }
    }
    //-----Two Point attempts-----
    public Integer getTwoPointerAttemptByPlayerId (int playerId) {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetTwoPointerAttemptByPlayerIdAsyncTask(eventJoinDao).execute(playerId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetTwoPointerAttemptByPlayerIdAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetTwoPointerAttemptByPlayerIdAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getTwoPointAttemptsByPlayerId(integers[0]);
        }
    }

    //-----Three Point attempts-----
    public Integer getThreePointerAttemptByPlayerId (int playerId) {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetThreePointerAttemptByPlayerIdAsyncTask(eventJoinDao).execute(playerId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetThreePointerAttemptByPlayerIdAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetThreePointerAttemptByPlayerIdAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getThreePointAttemptsByPlayerId(integers[0]);
        }
    }

        //-----------Assists-----------
    public Integer getAssistsByPlayerId (int playerId) {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetAssistsByPlayerIdAsyncTask(eventJoinDao).execute(playerId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetAssistsByPlayerIdAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetAssistsByPlayerIdAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getAssistsByPlayerId(integers[0]);
        }
    }

    //-----------Steals-----------
    public Integer getStealsByPlayerId (int playerId) {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetStealsByPlayerIdAsyncTask(eventJoinDao).execute(playerId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetStealsByPlayerIdAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetStealsByPlayerIdAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getStealsByPlayerId(integers[0]);
        }
    }

    //-----------Blocks-----------
    public Integer getBlocksByPlayerId (int playerId) {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetBlocksByPlayerIdAsyncTask(eventJoinDao).execute(playerId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetBlocksByPlayerIdAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetBlocksByPlayerIdAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getBlocksByPlayerId(integers[0]);
        }
    }

    //-----------rebounds-----------
    public Integer getReboundsByPlayerId (int playerId) {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetReboundsByPlayerIdAsyncTask(eventJoinDao).execute(playerId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetReboundsByPlayerIdAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetReboundsByPlayerIdAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getReboundsByPlayerId(integers[0]);
        }
    }

    //-----------Turnover-----------
    public Integer getTurnoverByPlayerId (int playerId) {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetTurnoverByPlayerIdAsyncTask(eventJoinDao).execute(playerId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetTurnoverByPlayerIdAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetTurnoverByPlayerIdAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getTurnoversByPlayerId(integers[0]);
        }
    }

    //-----------Fouls-----------
    public Integer getFoulsByPlayerId (int playerId) {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetFoulsByPlayerIdAsyncTask(eventJoinDao).execute(playerId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetFoulsByPlayerIdAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetFoulsByPlayerIdAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getFoulsByPlayerId(integers[0]);
        }
    }


    //-----------Games Played-----------
    public Integer getGamesPlayedByPlayerId (int playerId) {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetGamesPlayedByPlayerIdAsyncTask(eventJoinDao).execute(playerId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetGamesPlayedByPlayerIdAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetGamesPlayedByPlayerIdAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getGamesPlayedByPlayerId(integers[0]);
        }
    }

    
    
    //----------global team stats-------------
    //-----One Point Made-----

    public Integer getOnePointer () {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetOnePointerAsyncTask(eventJoinDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetOnePointerAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetOnePointerAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getOnePointer();
        }
    }

    //-----Two Pointer Made-----
    public Integer getTwoPointer () {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetTwoPointerAsyncTask(eventJoinDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetTwoPointerAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetTwoPointerAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getTwoPointer();
        }
    }

    //-----Three Pointer Made-----
    public Integer getThreePointer () {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetThreePointerAsyncTask(eventJoinDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetThreePointerAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetThreePointerAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getThreePointer();
        }
    }

    //-----One Point attempts-----
    public Integer getOnePointerAttempt () {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetOnePointerAttemptAsyncTask(eventJoinDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetOnePointerAttemptAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetOnePointerAttemptAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getOnePointAttempts();
        }
    }
    //-----Two Point attempts-----
    public Integer getTwoPointerAttempt () {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetTwoPointerAttemptAsyncTask(eventJoinDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetTwoPointerAttemptAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetTwoPointerAttemptAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getTwoPointAttempts();
        }
    }

    //-----Three Point attempts-----
    public Integer getThreePointerAttempt () {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetThreePointerAttemptAsyncTask(eventJoinDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetThreePointerAttemptAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetThreePointerAttemptAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getThreePointAttempts();
        }
    }

    //-----------Assists-----------
    public Integer getAssists () {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetAssistsAsyncTask(eventJoinDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetAssistsAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetAssistsAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getAssists();
        }
    }

    //-----------Steals-----------
    public Integer getSteals () {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetStealsAsyncTask(eventJoinDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetStealsAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetStealsAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getSteals();
        }
    }

    //-----------Blocks-----------
    public Integer getBlocks () {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetBlocksAsyncTask(eventJoinDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetBlocksAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetBlocksAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getBlocks();
        }
    }

    //-----------rebounds-----------
    public Integer getRebounds () {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetReboundsAsyncTask(eventJoinDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetReboundsAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetReboundsAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getRebounds();
        }
    }

    //-----------Turnover-----------
    public Integer getTurnover () {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetTurnoverAsyncTask(eventJoinDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetTurnoverAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetTurnoverAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getTurnovers();
        }
    }

    //-----------Fouls-----------
    public Integer getFouls () {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetFoulsAsyncTask(eventJoinDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetFoulsAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetFoulsAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getFouls();
        }
    }


    //-----------Games Played-----------
    public Integer getGamesPlayed () {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetGamesPlayedAsyncTask(eventJoinDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetGamesPlayedAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetGamesPlayedAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getGamesPlayed();
        }
    }



    //----------by MatchId-----------------
    //-----One Point Made-----

    public Integer getOnePointerByMatchId (int matchId) {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetOnePointerByMatchIdAsyncTask(eventJoinDao).execute(matchId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetOnePointerByMatchIdAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetOnePointerByMatchIdAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getOnePointerByMatchId(integers[0]);
        }
    }

    //-----Two Pointer Made-----
    public Integer getTwoPointerByMatchId (int matchId) {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetTwoPointerByMatchIdAsyncTask(eventJoinDao).execute(matchId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetTwoPointerByMatchIdAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetTwoPointerByMatchIdAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getTwoPointerByMatchId(integers[0]);
        }
    }

    //-----Three Pointer Made-----
    public Integer getThreePointerByMatchId (int matchId) {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetThreePointerByMatchIdAsyncTask(eventJoinDao).execute(matchId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetThreePointerByMatchIdAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetThreePointerByMatchIdAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getThreePointerByMatchId(integers[0]);
        }
    }

    //-----One Point attempts-----
    public Integer getOnePointerAttemptByMatchId (int matchId) {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetOnePointerAttemptByMatchIdAsyncTask(eventJoinDao).execute(matchId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetOnePointerAttemptByMatchIdAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetOnePointerAttemptByMatchIdAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getOnePointAttemptsByMatchId(integers[0]);
        }
    }
    //-----Two Point attempts-----
    public Integer getTwoPointerAttemptByMatchId (int matchId) {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetTwoPointerAttemptByMatchIdAsyncTask(eventJoinDao).execute(matchId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetTwoPointerAttemptByMatchIdAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetTwoPointerAttemptByMatchIdAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getTwoPointAttemptsByMatchId(integers[0]);
        }
    }

    //-----Three Point attempts-----
    public Integer getThreePointerAttemptByMatchId (int matchId) {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetThreePointerAttemptByMatchIdAsyncTask(eventJoinDao).execute(matchId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetThreePointerAttemptByMatchIdAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetThreePointerAttemptByMatchIdAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getThreePointAttemptsByMatchId(integers[0]);
        }
    }

    //-----------Assists-----------
    public Integer getAssistsByMatchId (int matchId) {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetAssistsByMatchIdAsyncTask(eventJoinDao).execute(matchId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetAssistsByMatchIdAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetAssistsByMatchIdAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getAssistsByMatchId(integers[0]);
        }
    }

    //-----------Steals-----------
    public Integer getStealsByMatchId (int matchId) {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetStealsByMatchIdAsyncTask(eventJoinDao).execute(matchId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetStealsByMatchIdAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetStealsByMatchIdAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getStealsByMatchId(integers[0]);
        }
    }

    //-----------Blocks-----------
    public Integer getBlocksByMatchId (int matchId) {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetBlocksByMatchIdAsyncTask(eventJoinDao).execute(matchId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetBlocksByMatchIdAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetBlocksByMatchIdAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getBlocksByMatchId(integers[0]);
        }
    }

    //-----------rebounds-----------
    public Integer getReboundsByMatchId (int matchId) {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetReboundsByMatchIdAsyncTask(eventJoinDao).execute(matchId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetReboundsByMatchIdAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetReboundsByMatchIdAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getReboundsByMatchId(integers[0]);
        }
    }

    //-----------Turnover-----------
    public Integer getTurnoverByMatchId (int matchId) {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetTurnoverByMatchIdAsyncTask(eventJoinDao).execute(matchId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetTurnoverByMatchIdAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetTurnoverByMatchIdAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getTurnoversByMatchId(integers[0]);
        }
    }

    //-----------Fouls-----------
    public Integer getFoulsByMatchId (int matchId) {

        Integer eventJoinEntity = null;
        try {
            eventJoinEntity = new GetFoulsByMatchIdAsyncTask(eventJoinDao).execute(matchId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eventJoinEntity;
    }

    private class GetFoulsByMatchIdAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private EventJoinDao asyncEventJoinDao;

        public GetFoulsByMatchIdAsyncTask(EventJoinDao eventJoinDao) {
            this.asyncEventJoinDao = eventJoinDao;
        }
        @Override
        protected Integer doInBackground(Integer... integers) {
            return asyncEventJoinDao.getFoulsByMatchId(integers[0]);
        }
    }

}




