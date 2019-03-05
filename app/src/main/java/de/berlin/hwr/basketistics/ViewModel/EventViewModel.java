package de.berlin.hwr.basketistics.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import de.berlin.hwr.basketistics.Persistency.Entities.EventEntity;
import de.berlin.hwr.basketistics.Persistency.Entities.MatchEntity;
import de.berlin.hwr.basketistics.Persistency.Entities.PlayerEntity;
import de.berlin.hwr.basketistics.Persistency.Repository.Repository;

public class EventViewModel extends AndroidViewModel {

    private final static String TAG = "EventViewModel";

    private MutableLiveData<MatchEntity> currentMatch = new MutableLiveData<MatchEntity>();
    private PlayerEvents[] currentPlayerEvents;
    private List<PlayerEvents> allPlayerEvents; //TODO: Remove safely
    private Repository repository;
    private Map<Integer, Integer> currentPlayerMap;  // K: playerIndex, V: playerId
    private MutableLiveData<Integer> currentMatchId;

    public EventViewModel(@NonNull Application application) {
        super(application);
        Log.e(TAG, "Constructor.");
        // Prevent us from nullpointers.
        this.currentMatch.setValue(new MatchEntity("<no_city>", "<no_opponent>", false, "<no_date>", "<no_description>"));
        this.currentMatchId = new MutableLiveData<Integer>();
        this.repository = new Repository(application);
    }

    public void insertPlayer(int playerId, int playerIndex) {

        currentPlayerEvents[playerIndex].getPlayer().setValue(repository.getPlayerById(playerId));
        updateEvents(playerId, playerIndex);
    }

    private void updateEvents(int playerId, int playerIndex) {

        // Clear last players stats
        currentPlayerEvents[playerIndex].getPoints().setValue(0);
        currentPlayerEvents[playerIndex].getRebound().setValue(0);
        currentPlayerEvents[playerIndex].getAssist().setValue(0);
        currentPlayerEvents[playerIndex].getSteal().setValue(0);
        currentPlayerEvents[playerIndex].getBlock().setValue(0);
        currentPlayerEvents[playerIndex].getTurnover().setValue(0);
        currentPlayerEvents[playerIndex].getFoul().setValue(0);

        // update stats from database
        List<EventEntity> playerEvents;
        try {
            playerEvents = repository.getEventsByMatchAndPlayer(currentMatchId.getValue(), playerId);
            Log.e(TAG, "" + currentMatchId.getValue());
            for (EventEntity event : playerEvents) {
                switch (event.getEventType()) {
                    case 0:
                        // TODO: New Events are needed for points and attempts
                        currentPlayerEvents[playerIndex].getPoints().setValue(
                                currentPlayerEvents[playerIndex].getPoints().getValue() + 1);
                        break;
                    case 1:
                        currentPlayerEvents[playerIndex].getRebound().setValue(
                                currentPlayerEvents[playerIndex].getRebound().getValue() + 1);
                        break;
                    case 2:
                        currentPlayerEvents[playerIndex].getAssist().setValue(
                                currentPlayerEvents[playerIndex].getAssist().getValue() + 1);
                        break;
                    case 3:
                        currentPlayerEvents[playerIndex].getSteal().setValue(
                                currentPlayerEvents[playerIndex].getSteal().getValue() + 1);
                        break;
                    case 4:
                        currentPlayerEvents[playerIndex].getBlock().setValue(
                                currentPlayerEvents[playerIndex].getBlock().getValue() + 1);
                        break;
                    case 5:
                        currentPlayerEvents[playerIndex].getTurnover().setValue(
                                currentPlayerEvents[playerIndex].getTurnover().getValue() + 1);
                        break;
                    case 6:
                        currentPlayerEvents[playerIndex].getFoul().setValue(
                                currentPlayerEvents[playerIndex].getFoul().getValue() + 1);
                        break;
                    default:
                        // TODO: Exception handling!
                }
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void init(int[] starters, int currentMatchId) {

        if (this.currentMatchId == null) { this.currentMatchId = new MutableLiveData<Integer>(); }
        this.currentMatchId.setValue(currentMatchId);
        Log.e(TAG, "" + currentMatchId);
        Log.e(TAG, "" + this.currentMatchId.getValue());

        this.currentPlayerEvents = new PlayerEvents[5];
        for (int i = 0; i < 5; i++) {
            currentPlayerEvents[i] = new PlayerEvents(
                    repository.getPlayerById(starters[i]),
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0);
            // TODO: Is that useful??
            updateEvents(starters[i], i);
        }
    }

    public MutableLiveData<PlayerEntity> getPlayerByIndex(int index) {
        return currentPlayerEvents[index].player;
    }

    public PlayerEvents getPlayerEvents(int playerIndex) {
        return currentPlayerEvents[playerIndex];
    }

    public MutableLiveData<Integer> getCurrentMatchId() {
        return currentMatchId;
    }

    public class PlayerEvents {
        private MutableLiveData<PlayerEntity> player = new MutableLiveData<PlayerEntity>();
        private MutableLiveData<Integer> points = new MutableLiveData<Integer>();
        private MutableLiveData<Integer> assist = new MutableLiveData<Integer>();
        private MutableLiveData<Integer> rebound = new MutableLiveData<Integer>();
        private MutableLiveData<Integer> foul = new MutableLiveData<Integer>();
        private MutableLiveData<Integer> block = new MutableLiveData<Integer>();
        private MutableLiveData<Integer> turnover = new MutableLiveData<Integer>();
        private MutableLiveData<Integer> steal = new MutableLiveData<Integer>();

        // Constructor
        public PlayerEvents(
                PlayerEntity player,
                int points,
                int assist,
                int rebound,
                int foul,
                int block,
                int turnover,
                int steal) {

            this.player.setValue(player);
            this.points.setValue(points);
            this.assist.setValue(assist);
            this.rebound.setValue(rebound);
            this.foul.setValue(foul);
            this.block.setValue(block);
            this.turnover.setValue(turnover);
            this.steal.setValue(steal);
        }

        public MutableLiveData<PlayerEntity> getPlayer() {
            return player;
        }

        public void setPlayer(PlayerEntity player) {
            this.player.setValue(player);
            // TODO: Events missing!
        }

        public MutableLiveData<Integer> getPoints() {
            return points;
        }

        public void addPoints(Integer playerIndex, Integer points) {
            this.points.setValue(this.points.getValue() + points);
            repository.insertEvent(new EventEntity(1, player.getValue().getId(), currentMatchId.getValue()));
        }

        public MutableLiveData<Integer> getAssist() {
            return assist;
        }

        public void addAssist(Integer playerIndex, Integer assist) {
            this.assist.setValue(this.assist.getValue() + assist);
            repository.insertEvent(new EventEntity(2, player.getValue().getId(), currentMatchId.getValue()));
        }

        public MutableLiveData<Integer> getRebound() {
            return rebound;
        }

        public void addRebound(Integer playerIndex, Integer rebound) {
            this.rebound.setValue(this.rebound.getValue() + rebound);
            repository.insertEvent(new EventEntity(3, player.getValue().getId(), currentMatchId.getValue()));

            // Test
            List<EventEntity> events = repository.getAllEvents();
            for (EventEntity eventEntity : events) {
                Log.i(TAG, eventEntity.getTimestamp() + " " + eventEntity.getId() + " " + eventEntity.toString() + " " + eventEntity.getPlayerId() + " " + eventEntity.getEventType());
            }
        }

        public MutableLiveData<Integer> getFoul() {
            return foul;
        }

        public void addFoul(Integer playerIndex, Integer foul) {
            this.foul.setValue(this.foul.getValue() + foul);
            repository.insertEvent(new EventEntity(4, player.getValue().getId(), currentMatchId.getValue()));
        }

        public MutableLiveData<Integer> getBlock() {
            return block;
        }

        public void addBlock(Integer playerIndex, Integer block) {
            this.block.setValue(this.block.getValue() + block);
            repository.insertEvent(new EventEntity(5, player.getValue().getId(), currentMatchId.getValue()));
        }

        public MutableLiveData<Integer> getTurnover() {
            return turnover;
        }

        public void addTurnover(Integer playerIndex, Integer turnover) {
            this.turnover.setValue(this.turnover.getValue() + turnover);
            repository.insertEvent(new EventEntity(6, player.getValue().getId(), currentMatchId.getValue()));
        }

        public MutableLiveData<Integer> getSteal() {
            return steal;
        }

        public void addSteal(Integer playerIndex, Integer steal) {
            this.steal.setValue(this.steal.getValue() + steal);
            repository.insertEvent(new EventEntity(7, player.getValue().getId(), currentMatchId.getValue()));
        }
    }
}
