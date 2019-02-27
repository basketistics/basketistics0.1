package de.berlin.hwr.basketistics.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

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
    private List<PlayerEvents> allPlayerEvents;
    private Repository repository;
    private Map<Integer, Integer> currentPlayerMap;  // K: playerIndex, V: playerId
    private int currentMatchId;

    public EventViewModel(@NonNull Application application) {
        super(application);
        Log.e(TAG, "Constructor.");
        // Prevent us from nullpointers.
        this.currentMatch.setValue(new MatchEntity("<no_city>", "<no_opponent>", false));
        this.repository = new Repository(application);
    }

    public void setCurrentMatchId(int matchId) {
        this.currentMatchId = matchId;
    }

    public void proposeStarters(int[] starterIds, int currentMatchId) {
        Log.e(TAG, "proposeStarters() was called.");
        if (currentPlayerMap == null) {
            currentPlayerMap = new HashMap<Integer, Integer>();
            int i = 0;
            for (int starterId : starterIds) {
                currentPlayerMap.put(i, starterId);
                i++;
            }
        }
        if (currentPlayerEvents == null) {
            Log.i(TAG, "currentPlayerEvents == null.");
            currentPlayerEvents = new PlayerEvents[5];
            for (int j = 0; j < 5; j++) {
                currentPlayerEvents[j] = new PlayerEvents(
                        repository.getPlayerById(currentPlayerMap.get(j)),
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0);
                Log.e(TAG, "" + j);
            }
        }
        if (currentMatch== null) {
            try {
                currentMatch.setValue(repository.getMatchById(currentMatchId));
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int switchPlayers(int currentPlayerIndex, int newPLayerId) {

        Log.e(TAG, "switchPlayers() was called.");

        if (allPlayerEvents != null) {
            for (PlayerEvents playerEvents : allPlayerEvents) {
                if (playerEvents.getPlayer().getValue().getId() == newPLayerId) {
                    currentPlayerEvents[currentPlayerIndex] = playerEvents;
                    return 1; // 1 means success, player was in game already
                }
            }
        }
        try {
            List<EventEntity> playerEventEntities =
                    repository.getEventsByMatchAndPlayer(currentMatch.getValue().getId(), newPLayerId);
            // TODO: Ugly dependency
            PlayerEvents playerEvents = new PlayerEvents(
                    repository.getPlayerById(newPLayerId),
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0);

            for (EventEntity eventEntity : playerEventEntities) {
                switch (eventEntity.getEventType()) {
                    case 0:
                        // TODO: Exception handling, points can be null!
                        playerEvents.points.setValue(playerEvents.points.getValue() + 1);
                        break;
                    case 1:
                        playerEvents.rebound.setValue(playerEvents.rebound.getValue() + 1);
                        break;
                    case 2:
                        playerEvents.assist.setValue(playerEvents.assist.getValue() + 1);
                        break;
                    case 3:
                        playerEvents.steal.setValue(playerEvents.steal.getValue() + 1);
                        break;
                    case 4:
                        playerEvents.block.setValue(playerEvents.block.getValue() + 1);
                        break;
                    case 5:
                        playerEvents.turnover.setValue(playerEvents.turnover.getValue() + 1);
                        break;
                    case 6:
                        playerEvents.foul.setValue(playerEvents.foul.getValue() + 1);
                        break;
                    default:
                        // TODO: Exception handling!
                }
            }

            currentPlayerEvents[currentPlayerIndex] = playerEvents;
            return 2; // Means success, playerEvents were created.

        } catch (ExecutionException e) {
            e.printStackTrace();
            return -1; // ERROR
        } catch (InterruptedException e) {
            e.printStackTrace();
            return -1; // ERROR
        }

    }

    public PlayerEntity getPlayerByIndex(int index) {
        return currentPlayerEvents[index].player.getValue();
    }

    public PlayerEvents getPlayerEvents(int playerIndex) {
        return currentPlayerEvents[playerIndex];
    }

    public void fetchSavedState(int[] currentPlayers) {
        int i = 0;
        for (int playerId : currentPlayers) {
            switchPlayers(i, playerId);
        }
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
        }

        public MutableLiveData<Integer> getPoints() {
            return points;
        }

        public void addPoints(Integer playerIndex, Integer points) {
            this.points.setValue(this.points.getValue() + points);
            repository.insertEvent(new EventEntity(1, player.getValue().getId(), 1));
        }

        public MutableLiveData<Integer> getAssist() {
            return assist;
        }

        public void addAssist(Integer playerIndex, Integer assist) {
            this.assist.setValue(this.assist.getValue() + assist);
            repository.insertEvent(new EventEntity(2, player.getValue().getId(), 1));
        }

        public MutableLiveData<Integer> getRebound() {
            return rebound;
        }

        public void addRebound(Integer playerIndex, Integer rebound) {
            this.rebound.setValue(this.rebound.getValue() + rebound);
            repository.insertEvent(new EventEntity(3, player.getValue().getId(), 1));

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
            repository.insertEvent(new EventEntity(4, player.getValue().getId(), 1));
        }

        public MutableLiveData<Integer> getBlock() {
            return block;
        }

        public void addBlock(Integer playerIndex, Integer block) {
            this.block.setValue(this.block.getValue() + block);
            repository.insertEvent(new EventEntity(5, player.getValue().getId(), 1));
        }

        public MutableLiveData<Integer> getTurnover() {
            return turnover;
        }

        public void addTurnover(Integer playerIndex, Integer turnover) {
            this.turnover.setValue(this.turnover.getValue() + turnover);
            repository.insertEvent(new EventEntity(6, player.getValue().getId(), 1));
        }

        public MutableLiveData<Integer> getSteal() {
            return steal;
        }

        public void addSteal(Integer playerIndex, Integer steal) {
            this.steal.setValue(this.steal.getValue() + steal);
            repository.insertEvent(new EventEntity(7, player.getValue().getId(), 1));
        }
    }
}
